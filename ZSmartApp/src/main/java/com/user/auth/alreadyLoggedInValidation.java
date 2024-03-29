package com.user.auth;

// public class alreadyLoggedInValidation

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import utils.CommonLogger;
import utils.JSON;
import utils.DB;
import java.util.ArrayList;
import java.util.List;
import com.user.auth.*;

@WebServlet("/v1/checkSession")
public class alreadyLoggedInValidation extends HttpServlet {

	static Logger logger = new CommonLogger(SessionValidationFilter.class).getLogger();

	public alreadyLoggedInValidation() {
		super();
		// TODO Auto-generated constructor stub
	}

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		/*
		 * check whether passwd is correct.
		 * create new session
		 * add to cookie
		 */

		Cookie[] cookies = request.getCookies();

		String sessionID = null;
		String mailID = null;
        System.out.println(cookies);
		  if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("sessionID")) {
                     sessionID = cookie.getValue();
                }else if (cookie.getName().equals("userID")) {
                mailID = cookie.getValue();
               }
            }
        }
        try {
			response.getWriter().write(validateSession(sessionID, mailID)+"");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	private boolean validateSession(String sessionID, String mailID) throws Exception {

		return DB.validateSession(sessionID, mailID);
	}

}
