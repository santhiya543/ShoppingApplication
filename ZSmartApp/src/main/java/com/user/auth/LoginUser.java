package com.user.auth;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.user.details.UserDAO;

import utils.CommonLogger;
import utils.JSON;

/**
 * Servlet implementation class LoginUser
 */
@WebServlet("/v1/login")
public class LoginUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/*
		 * check whether passwd is correct.
		 * create new session
		 * add to cookie
		 */

		
		Logger logger = new CommonLogger(LoginUser.class).getLogger();
		String mailID = (String) request.getAttribute("mailID");
		String Passwd = request.getParameter("passwd");


		System.out.println(mailID +"    "+Passwd);
		try {
			boolean isValid = UserDAO.getObj().LoginUser(mailID, Passwd);
			
			if (isValid) {
				// creating session
				String sessionID = UserDAO.createNewSession(mailID);
				List<Cookie> cookies = new ArrayList<Cookie>();
				cookies.add(new Cookie("userID", mailID));
				cookies.add(new Cookie("sessionID", sessionID));
				
				cookies.forEach(x-> response.addCookie(x));
				System.out.println(cookies);
				//String userName = UserDAO.getObj().getUserNameFromMailID(mailID);
				//File profile = new File("webapps/Demo/images/" + userName + ".jpg");
//				if(!profile.exists()){
//					copyProfilePicture(userName);
//				}
				// boolean isAdmin = UserDAO.getObj().isAdmin(mailID);
				JSONObject respObject = new JSONObject();
				
		        respObject.put("statuscode", 200);
				respObject.put("sessionID", sessionID);
				respObject.put("mailID", mailID);
//				respObject.put("userName", userName);
				// respObject.put("isAdmin", isAdmin);

		        logger.info("Login successfull! 200 :"+mailID);
		        response.getWriter().write(respObject.toString());
			}else {
				JSONObject errJson = JSON.Create(401, "Invalid Credentials!");
				response.getWriter().write(errJson.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("check this error :" + e);
			JSONObject errJson = JSON.Create(400, "Something went wrong! Please re-login.");
			response.getWriter().write(errJson.toString());
		}

	}

//	public static void main(String[] args) {
//		copyProfilePicture("Vijila");
//	}
//
//
//	public static void copyProfilePicture(String userName) {
//        Path sourcePath = Paths.get("webapps/Demo/images/default.jpg");
//        Path destinationPath = Paths.get("webapps/Demo/images/" + userName + ".jpg");
//
//        try {
////            Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
//            System.out.println("Profile picture created for user: " + userName);
//        } catch (IOException e) {
//            System.err.println("Error copying profile picture for user: " + userName);
//            e.printStackTrace();
//        }
//    }  

}
