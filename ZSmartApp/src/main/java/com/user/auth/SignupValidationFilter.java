package com.user.auth;

import java.io.IOException;
import com.user.auth.*;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.mindrot.jbcrypt.BCrypt;

import utils.CommonLogger;
import utils.DB;
import utils.JSON;

/**
 * Servlet Filter implementation class ValidationFilter
 */
@WebFilter("/v1/signup")
public class SignupValidationFilter extends HttpFilter {
       
    /**
     * @see HttpFilter#HttpFilter()
     */
    public SignupValidationFilter() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		/*
		 * Take username, mailID, passwd as params
		 * Check null, "", regex - (mail, passwd), already exists (username, mailID)
		 * chain to SignupUser
		 */
		
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        // Set CORS headers
        httpResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        httpResponse.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.setHeader("Access-Control-Max-Age", "3600");
		Logger logger = new CommonLogger(SignupValidationFilter.class).getLogger();
//		System.out.println("slkfndjnxdfjon");
		try {
			System.out.println("user"+request.getParameter("userName")+"mail"+request.getParameter("mailID")+request.getParameter("passwd"));
			String username = request.getParameter("userName");
		    String mailID = request.getParameter("mailID");
		    String passwd = request.getParameter("passwd");

		    JSONObject errJson;
			// Validate parameters for null or empty values
		    if (username == null || username.trim().isEmpty() ||
		            mailID == null || mailID.trim().isEmpty() ||
		            passwd == null || passwd.trim().isEmpty()) {
		    	logger.error("Invalid creds: null or Empty"+username+" "+passwd+" "+mailID);
		    	errJson = JSON.Create(401, "invalid Credentials!");
		        response.getWriter().write(errJson.toString());
		        return; 
		    }
		    
		    // mail check
		    if (!mailID.matches(".+@.+\\.com")) {
		    	errJson = JSON.Create(401, "Invalid email!");
		        response.getWriter().write(errJson.toString());
		        return;
		    }
		    
		    // username, email exist check
		    if (isExistMail(mailID)) {
		    	errJson = JSON.Create(401, "Email already exists!");
		        response.getWriter().write(errJson.toString());
		        return;
			}else if (isExistUserName(username)) {
		    	errJson = JSON.Create(401, "Username already taken!");
		        response.getWriter().write(errJson.toString());
		        return;
		    }else if(!isValidPassword(passwd)) {
				errJson = JSON.Create(401, "Invalid password pattern!");
		        response.getWriter().write(errJson.toString());
		        return;
			}else{
				logger.info("Valid user credentials: "+mailID+" passwd:"+passwd+" username:"+username);
				chain.doFilter(request, response);
			}
			
			
		}
		catch(Exception e) {
			logger.error("Error on checking signup credentials: "+e);
			JSONObject errJson = JSON.Create(400, "Invalid credentials!");
	        response.getWriter().write(errJson.toString());
		}
	}

	private boolean isExistMail(String mailID) {
        return (DB.checkValueisExist(mailID, "Users", "mailID"));
	}

	private boolean isExistUserName(String username) {
        return DB.checkValueisExist(username, "Users", "userName");
	}

	private boolean isValidPassword(String password) {
        return (password.length() >= 8 && password.matches(".*\\d.*") && password.matches(".*[!@#$%^&*].*"));
    }


	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
