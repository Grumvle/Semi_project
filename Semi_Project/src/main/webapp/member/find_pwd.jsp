<<<<<<< HEAD
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
=======
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
>>>>>>> GM
    
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
     
<div>
<<<<<<< HEAD
		<h3>��� ã��</h3>
=======
		<h3>비번 찾기</h3>
>>>>>>> GM
		<br>
		<form action="<%=request.getContextPath()%>/find_pwd.do" method="post">
			<table border="1" cellspacing="0">

				<tr>
<<<<<<< HEAD
					<th>�̸� �Է�</th>
					<td><input type="text" name="name"></td>
				</tr>

				<tr>
					<th>e-mail �Է�</th>
					<td><input type="text" name="email"></td>
				</tr>

				<tr>
					<td colspan="2" align="center"><input type="submit" value="����">
=======
					<th>이름 입력</th>
					<td><input type="text" name="member_name"></td>
				</tr>
	
				<tr>
					<th>아이디 입력</th>
					<td><input type="text" name="member_id"></td>
				</tr>
	
				<tr>
					<th>e-mail 입력</th>
					<td><input type="text" name="member_email"></td>
				</tr>

				<tr>
					<td colspan="2" align="center"><input type="submit" value="전송">
>>>>>>> GM
					</td>
				</tr>


			</table>
		</form>
	</div>
     
</body>
</html>