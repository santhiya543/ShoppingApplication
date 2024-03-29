package com.user.auth;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletResponse;

import utils.CommonLogger;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import com.user.auth.*;

import utils.DB;
import utils.JSON;



import com.user.details.UserDAO;

/**
 * Servlet Filter implementation class LoginValidationFilter
 */
@WebFilter("/v1/login")
public class LoginValidationFilter extends HttpFilter implements Filter {
       
    private static final long serialVersionUID = 1L;

	/**
     * @see HttpFilter#HttpFilter()
     */
    public LoginValidationFilter() {
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
		 * Take mailID, Passwd as params
		 * null, "" check, Validate on DB.
		 * Chain with LoginUser
		 */
		HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        // Set CORS headers
        httpResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        httpResponse.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.setHeader("Access-Control-Max-Age", "3600");
		

		Logger logger = new CommonLogger(LoginValidationFilter.class).getLogger();
	
		String userName = request.getParameter("userName");
		String mailID = request.getParameter("mailID");
	    String passwd = request.getParameter("passwd");
		System.out.println(mailID+"   " +passwd);
		try{
				if(userName != "" && userName != null && userName != "undefined"){
					if(validUserName(userName)){
						mailID = setMailFromUserName(userName);
						request.setAttribute("mailID", mailID);
					}
				}else{
					request.setAttribute("mailID", mailID);
				}
				if ((validMailID(mailID))) {
					logger.info("Login credentials passed:" + mailID + " pass:" + passwd);
					chain.doFilter(request, response);
				}
				else{
					JSONObject errJson = JSON.Create(401, "Invalid Credentials!");
					response.getWriter().write(errJson.toString());
				}
		}
		catch(Exception e){
			e.printStackTrace();
			logger.error(e);
			JSONObject errJson = JSON.Create(400, "Invalid credentials!");
	        response.getWriter().write(errJson.toString());
		}
	   
		
	}

	private String setMailFromUserName(String userName) throws Exception {
		return UserDAO.getObj().getMailIDFromUserName(userName);
	}

	private boolean validMailID(String mailID) {
		return DB.checkValueisExist(mailID, "Users", "mailID");
	}

	private boolean validUserName(String userName) {
		return DB.checkValueisExist(userName, "Users", "userName");
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
