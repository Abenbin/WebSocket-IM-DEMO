/**
 * ieen-socket
 * LoginController.java
 * create date : 2017年12月24日
 * Copyright © 2014-2016 MITAPLE Corporation.All rights reserved.
 * update date: 2017年12月24日
 */
package com.mitaple.allwis.sockete.login;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @version 1.0.0
 * @author liuwb
 * @since 1.0.0
 *
 */
public class LoginController extends HttpServlet {

	private static final long serialVersionUID = 7424603507902797081L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println(request.getParameter("loginName"));
		response.sendRedirect("index.jsp");
	}
}
