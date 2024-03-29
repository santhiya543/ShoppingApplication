package com.user.auth;

import java.io.IOException;
import com.user.auth.*;
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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpFilter;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import utils.CommonLogger;
import utils.JSON;
import utils.DB;
import java.util.ArrayList;
import java.util.List;

@WebFilter("/v1/user/*")
public class SessionValidationFilter extends HttpFilter implements Filter {

	static Logger logger = new CommonLogger(SessionValidationFilter.class).getLogger();

	public SessionValidationFilter() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void destroy() {
		// TODO Auto-generated method stub
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		Cookie[] cookies = req.getCookies();

		String sessionID = null;
		String mailID = null;
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
			if (validateSession(sessionID, mailID)) {
				chain.doFilter(request, response);

			} else {
				res.addCookie(new Cookie("sessionID", ""));
				res.addCookie(new Cookie("userID", ""));
				JSONObject errJson = JSON.Create(400, "Invalid Session!");
				response.getWriter().write(errJson.toString());
			}
		} catch (Exception e) {
			logger.error("exception on session validation- session:"+sessionID+" mailID"+mailID + e);
			JSONObject errJson = JSON.Create(400, "Invalid Session! Please re-login");
			response.getWriter().write(errJson.toString());
		}

	}

	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

	private boolean validateSession(String sessionID, String mailID) throws Exception {

		return DB.validateSession(sessionID, mailID);
	}

}
