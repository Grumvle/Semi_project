package com.member.action;

import java.util.Random;
import java.io.PrintWriter;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.servlet.http.HttpSession;

import com.member.model.MemberDAO;
import com.member.model.MemberDTO;

import sha256.Encryption;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MemberFindPwdAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html charset=utf-8");

		String memberId = request.getParameter("member_id");
		String memberName = request.getParameter("member_name");
		String memberEmail = request.getParameter("member_email");

		// 먼저 아이디로 회원정보를 받아오고 가져온 데이터에서 email값을 비교하여 존재하지 않으면 인증메일 보내지 못함
		/*
		 * Member m = new MemberService().memberLogin(memberId); if(m==null ||
		 * !m.getEmail().equals(email)) {
		 * 
		 * req.setAttribute("msg", "아이디나 이메일 정보가 맞지 않습니다"); req.setAttribute("loc",
		 * "/member/searchPw");
		 * req.getRequestDispatcher("/views/common/msg.jsp").forward(req, resp);
		 * 
		 * return; }
		 */

		MemberDAO dao = MemberDAO.getInstance();

		MemberDTO dto = new MemberDTO();

		dto = dao.MemberFindPwd(memberId);

		System.out.println("DAO : " + dto.getMember_id());
		System.out.println("Action : " + memberName);
		

		if (!dto.getMember_id().equals(memberId) || !dto.getMember_name().equals(memberName) || !dto.getMember_email().equals(memberEmail)) {
			
					PrintWriter out = response.getWriter();

					out.println("<script>");
					out.println("alert('입력받은 정보의 가입자가 없습니다')");
					out.println("history.back()");
					out.println("</script>");

				
			
		} else {

			// mail server 설정
			String host = "smtp.gmail.com";
			String user = "hujik1234@gmail.com"; // 자신의 네이버 계정
			String password = "jlifuiaarcstlfip";// 자신의 네이버 패스워드

			// 메일 받을 주소
			/* String to_email = m.getEmail(); */
			String to_email = memberEmail;

			// SMTP 서버 정보를 설정한다.
			Properties prop = new Properties();

			prop.put("mail.smtp.host", host);
			prop.put("mail.smtp.port", 465);
			prop.put("mail.smtp.auth", "true");
			prop.put("mail.smtp.ssl.enable", "true");
			prop.put("mail.smtp.ssl.trust", host);

			// 인증 번호 생성기
			StringBuffer temp = new StringBuffer();
			Random rnd = new Random();
			for (int i = 0; i < 10; i++) {
				int rIndex = rnd.nextInt(3);
				switch (rIndex) {
				case 0:
					// a-z
					temp.append((char) ((int) (rnd.nextInt(26)) + 97));
					break;
				case 1:
					// A-Z
					temp.append((char) ((int) (rnd.nextInt(26)) + 65));
					break;
				case 2:
					// 0-9
					temp.append((rnd.nextInt(10)));
					break;
				}
			}

			String AuthenticationKey = temp.toString();

			System.out.println(AuthenticationKey);

			String encode = Encryption.encodeSha256(AuthenticationKey);

			dao.TempPwdUpdate(dto.getMember_no(), encode);

			Session session = Session.getDefaultInstance(prop, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(user, password);
				}
			});

			// email 전송
			try {
				MimeMessage msg = new MimeMessage(session);
				msg.setFrom(new InternetAddress(user, "Pooding"));
				msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to_email));

				// 메일 제목
				msg.setSubject("오! 메일이 도착했어요~");
				// 메일 내용
				msg.setText("인증 번호는 :" + temp);

				Transport.send(msg);

				System.out.println("이메일 전송");

			} catch (Exception e) {
				e.printStackTrace();// TODO: handle exception
			}
			HttpSession saveKey = request.getSession();
			saveKey.setAttribute("AuthenticationKey", AuthenticationKey);
			// 패스워드 바꿀때 뭘 바꿀지 조건에 들어가는 id
			/*
			 * req.setAttribute("id", memberId);
			 * req.getRequestDispatcher("/views/login_myPage/searchPasswordEnd.jsp").forward
			 * (req, resp);
			 */
			PrintWriter out = response.getWriter();

			out.println("<script>");
			out.println("alert('이메일로 전송을 완료했습니다.')");
			out.println("location.href='main.do'");
			out.println("</script>");

		}
		return null;
	}
}