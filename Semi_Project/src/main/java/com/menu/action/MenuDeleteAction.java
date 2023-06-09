package com.menu.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.member.action.Action;
import com.member.action.ActionForward;
import com.menu.model.MenuDAO;

public class MenuDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, Exception {
		
		
		int menu_idx = Integer.parseInt(request.getParameter("num"));
		int rst_no = Integer.parseInt(request.getParameter("rst_no"));
		
		
		System.out.println("rst_no>>>"+rst_no);
		System.out.println("menu_idx>>>"+menu_idx);
		
		MenuDAO dao = MenuDAO.getInstance();
		
		int res = dao.deleteMenu(menu_idx,rst_no);
		
		PrintWriter out = response.getWriter();
		
		out.println(res);
		
		return null;
	}

}
