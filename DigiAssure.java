/******************************************************************************
Copyright © 2016 Capgemini Group of companies. All rights reserved
(Subject to Limited Distribution and Restricted Disclosure Only.)
THIS SOURCE FILE MAY CONTAIN INFORMATION WHICH IS THE PROPRIETARY
INFORMATION OF Capgemini GROUP OF COMPANIES AND IS INTENDED FOR USE
ONLY BY THE ENTITY WHO IS ENTITLED TO AND MAY CONTAIN
INFORMATION THAT IS PRIVILEGED, CONFIDENTIAL, OR EXEMPT FROM
DISCLOSURE UNDER APPLICABLE LAW.
YOUR ACCESS TO THIS SOURCE FILE IS GOVERNED BY THE TERMS AND
CONDITIONS OF AN AGREEMENT BETWEEN YOU AND Capgemini GROUP OF COMPANIES.
The USE, DISCLOSURE REPRODUCTION OR TRANSFER OF THIS PROGRAM IS
RESTRICTED AS SET FORTH THEREIN.
******************************************************************************/

package com.cg.digi.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.RandomStringUtils;
import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cg.digi.logger.DigiLoggerUtils;
import com.cg.digi.logger.DigiLoggerUtils.LEVEL;
import com.cg.digi.model.Chat;
import com.cg.digi.model.Credentials;
import com.cg.digi.model.Project;
import com.cg.digi.model.User;
import com.cg.digi.service.ILoginService;
import com.cg.digi.service.SendMail;
import com.cg.digi.utilities.FileUtils;

@Scope("session")
@Controller
public class DigiAssure {

	@Value("${hpalm_access}")
	String hpalm_access;

	@Value("${mobileLabUserManual}")
	String mobileLabUserManual;

	@Value("${macroFile}")
	String macroFile;

	@Value("${BDDCucumberAppium}")
	String bDDCucumberAppium;

	@Value("${OPTIKUserManual}")
	String oPTIKUserManual;

	@Value("${FunctionalSeetestManual}")
	String functionalSeetestManual;

	@Autowired
	ILoginService loginService;

	/***************************** chatbot *****************************/
	@RequestMapping(value = "Chatbotinput")
	public void getInputfrombot(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		String inputmsg = request.getParameter("input");
		// //system.out.println.println("input data is: "+inputmsg);
		String userid = (String) session.getAttribute("userid");
		String sessionid = session.toString();
		//// system.out.println.println("userid inside save input : "+ userid+ "session
		//// id is: "+sessionid);
		loginService.saveuserinput(inputmsg, userid, sessionid);

	}

	@RequestMapping(value = "Chatbotoutput")
	public void getOutputfrombot(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {

		String outputmsg = request.getParameter("output");
		// //system.out.println.println("output data is: "+outputmsg);
		String userid = (String) session.getAttribute("userid");
		String sessionid = session.toString();
		//// system.out.println.println("userid inside save output : "+ userid);
		loginService.savebotoutput(outputmsg, userid, sessionid);
	}

	@RequestMapping(value = "chathistory")
	public String getchatfromdb(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {

		String userid = (String) session.getAttribute("userid");
		String sessionid = session.toString();
		//// system.out.println.println.println("userid inside getsender chat : "+
		//// userid);

		ArrayList<Chat> userinput = loginService.getuserchat(userid, sessionid);// done
		//// system.out.println.println.println("entering bot chat fetching dao");
		ArrayList<Chat> botoutput = loginService.getbotchat(userid, sessionid);
		// system.out.println.println("Collected bot chat adding into allchat list");
		// //successful
		ArrayList<Chat> allchat = new ArrayList<Chat>();

		allchat.addAll(userinput);
		//// system.out.println.println("All chat(1): "+ allchat+ "size
		//// "+allchat.size());

		allchat.addAll(botoutput);
		response.setContentType("application/json");
		//// system.out.println.println("All chat(2): "+ allchat +"size
		//// "+allchat.size());

		JSONArray jsonArr = new JSONArray();
		JSONArray sortedJsonArray = new JSONArray();
		for (Chat chat : allchat) {
			JSONObject obj = new JSONObject();
			obj.put("creation time", chat.getStarttime());
			obj.put("message", chat.getMessage());
			obj.put("sender", chat.getSender());
			obj.put("sessionid", chat.getSessionid());
			jsonArr.add(obj);
		}
		List<JSONObject> jsonValues = new ArrayList<JSONObject>();
		for (int i = 0; i < jsonArr.size(); i++) {
			jsonValues.add((JSONObject) jsonArr.get(i));
		}
		Collections.sort(jsonValues, new Comparator<JSONObject>() {

			private static final String KEY_NAME = "creation time";

			@Override
			public int compare(JSONObject a, JSONObject b) {
				String valA = new String();
				String valB = new String();

				valA = (String) a.get(KEY_NAME);
				valB = (String) b.get(KEY_NAME);

				return valA.compareTo(valB);

			}
		});

		for (int i = 0; i < jsonArr.size(); i++) {

			sortedJsonArray.add(jsonValues.get(i));
		}
		// system.out.println.println("json chat: "+ sortedJsonArray.toString());
		// //updated
		request.setAttribute("history", sortedJsonArray);
		RequestDispatcher rd = request.getRequestDispatcher("demobot.jsp");
		rd.forward(request, response);
		return "demobot";
	}

	/***************************** chatbot ends *****************************/

	/***************************** chatbot console *****************************/
	@RequestMapping(value = "dashboardAndMonitoring/qualityGateConfiguration/Chatbotinput")
	public void getInputfrombot2(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		String inputmsg = request.getParameter("input");
		// system.out.println.println("input data is: "+inputmsg);
		String userid = (String) session.getAttribute("userid");
		String sessionid = session.toString();
		// system.out.println.println("userid inside save input : "+ userid+ "session id
		// is: "+sessionid);
		loginService.saveuserinput(inputmsg, userid, sessionid);

	}

	@RequestMapping(value = "dashboardAndMonitoring/qualityGateConfiguration/Chatbotoutput")
	public void getOutputfrombot2(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {

		String outputmsg = request.getParameter("output");
		// system.out.println.println("output data is: "+outputmsg);
		String userid = (String) session.getAttribute("userid");
		String sessionid = session.toString();
		// system.out.println.println("userid inside save output : "+ userid);
		loginService.savebotoutput(outputmsg, userid, sessionid);
	}

	@RequestMapping(value = "dashboardAndMonitoring/qualityGateConfiguration/chathistory")
	public String getchatfromdb2(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {

		String userid = (String) session.getAttribute("userid");
		String sessionid = session.toString();
		// system.out.println.println("userid inside getsender chat : "+ userid);

		ArrayList<Chat> userinput = loginService.getuserchat(userid, sessionid);// done
		// system.out.println.println("entering bot chat fetching dao");
		ArrayList<Chat> botoutput = loginService.getbotchat(userid, sessionid);
		// system.out.println.println("Collected bot chat adding into allchat list");
		// //successful
		ArrayList<Chat> allchat = new ArrayList<Chat>();

		allchat.addAll(userinput);
		//// system.out.println.println("All chat(1): "+ allchat+ "size
		//// "+allchat.size());

		allchat.addAll(botoutput);
		response.setContentType("application/json");
		//// system.out.println.println("All chat(2): "+ allchat +"size
		//// "+allchat.size());

		JSONArray jsonArr = new JSONArray();
		JSONArray sortedJsonArray = new JSONArray();
		for (Chat chat : allchat) {
			JSONObject obj = new JSONObject();
			obj.put("creation time", chat.getStarttime());
			obj.put("message", chat.getMessage());
			obj.put("sender", chat.getSender());
			obj.put("sessionid", chat.getSessionid());
			jsonArr.add(obj);
		}
		List<JSONObject> jsonValues = new ArrayList<JSONObject>();
		for (int i = 0; i < jsonArr.size(); i++) {
			jsonValues.add((JSONObject) jsonArr.get(i));
		}
		Collections.sort(jsonValues, new Comparator<JSONObject>() {

			private static final String KEY_NAME = "creation time";

			@Override
			public int compare(JSONObject a, JSONObject b) {
				String valA = new String();
				String valB = new String();

				valA = (String) a.get(KEY_NAME);
				valB = (String) b.get(KEY_NAME);

				return valA.compareTo(valB);

			}
		});

		for (int i = 0; i < jsonArr.size(); i++) {

			sortedJsonArray.add(jsonValues.get(i));
		}
		// system.out.println.println("json chat: "+ sortedJsonArray.toString());
		request.setAttribute("history", sortedJsonArray);
		RequestDispatcher rd = request.getRequestDispatcher("demobot.jsp");
		rd.forward(request, response);
		return "demobot";
	}

	/***************************** chatbot ends *****************************/

	@RequestMapping(value = "login")
	public String login(Model model, @RequestParam("username") String username,
			@RequestParam("password") String password, HttpSession session) {
		DigiLoggerUtils.log("Form Details :: User Name :: " + username + " :: Password ::" + password + "", LEVEL.info,
				DigiAssure.class);
		User user = loginService.loginValidation(username, password);

		// Checking user name and password initially before DB validation
		if (username == "" || password == "") {
			model.addAttribute("error", "UserName and Password is Mandatory");
			return "login";
		}

		// Post DB validation; user name and password
		if (user == null) {
			// TexttoSpeech.dospeak("Hello User !! Invalid Credentials", "kevin16");
			model.addAttribute("error", "Invalid Login Credentials");
			return "login";
		} else {
			session.setAttribute("userName", user.getFirstName() + "  " + user.getLastName());
			session.setAttribute("userpass", user.getUserPassword());
			session.setAttribute("userNameNoSpace", user.getFirstName() + "_" + user.getLastName());
			session.setAttribute("userid", user.getUserid()); //
			session.setAttribute("projectid", user.getProjectid());
			session.setAttribute("rolename", user.getRolename());
		}
		model.addAttribute("username", username);
		boolean res = checkapi(model, session, user.getUserid());
		if (res) {

			model.addAttribute("setstatus", "display");
		} else {

			model.addAttribute("setstatus", "nodisplay");
		}

		// TexttoSpeech.dospeak("Hello "+user.getFirstName()+" Welcome to Digi Assure",
		// "kevin16");
		return "landing_new";

	}

	@RequestMapping(value = "/adminProject")
	public String admin(Model model, HttpSession session) {
		List<Project> Projects = new ArrayList<Project>();
		Projects = loginService.getProjects();

		//// system.out.println.println("========="+Projects);
		model.addAttribute("project", Projects);

		return "/admin/adminProject";
	}

	@RequestMapping(value = "redirect")
	public String redirect(Model model, @RequestParam("username") String username,
			@RequestParam("password") String password, HttpSession session) {

		DigiLoggerUtils.log("Form Details :: User Name :: " + username + " :: Password ::" + password + "", LEVEL.info,
				DigiAssure.class);

		User user = loginService.loginValidation(username, password);

		// Post DB validation; user name and password
		if (user.getUserName() == null || user.getUserid() == null) {
			// TexttoSpeech.dospeak("Hello User !! Invalid Credentials", "kevin16");
			model.addAttribute("error", "Invalid Login Credentials");
			return "login";
		} else {
			session.setAttribute("userName", user.getUserName());
			session.setAttribute("userNameNoSpace", user.getFirstName() + "_" + user.getLastName());
			session.setAttribute("userid", user.getUserid());
			session.setAttribute("projectid", user.getProjectid());
			session.setAttribute("rolename", user.getRolename());
			// system.out.println.println("Rolename " + user.getRolename());

		}

		model.addAttribute("username", username);
		boolean res = checkapi(model, session, user.getUserid());
		if (res) {

			model.addAttribute("setstatus", "display");
		} else {

			model.addAttribute("setstatus", "nodisplay");
		}
		// TexttoSpeech.dospeak("Hello "+user.getFirstName()+" Welcome to Digi Assure",
		// "kevin16");
		return "landing_new";
	}

	@RequestMapping(value = "integratedQALabs/mobileLab/downloadExcelUtility")
	public String downloadExcelUtility(Model model, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		DigiLoggerUtils.log("HP ALM UTILITY FILE PATH : " + hpalm_access, LEVEL.info);
		FileUtils.downloadFile(hpalm_access, session, response, request);
		return "integratedQALabs/mobileLab/mobileLab";
	}

	@RequestMapping(value = "integratedQALabs/mobileLab/downloadManual")
	public String downloadManual(Model model, HttpSession session, HttpServletRequest request,
			HttpServletResponse response, @RequestParam("toolName") String toolName) {
		if (toolName.equalsIgnoreCase("mobileLabUserManual")) {
			FileUtils.downloadFile(mobileLabUserManual, session, response, request);
		} else if (toolName.equalsIgnoreCase("BDDCucumberAppium")) {
			FileUtils.downloadFile(bDDCucumberAppium, session, response, request);
		} else if (toolName.equalsIgnoreCase("OPTIKUserManual")) {
			FileUtils.downloadFile(oPTIKUserManual, session, response, request);
		} else if (toolName.equalsIgnoreCase("FunctionalSeetestManual")) {
			FileUtils.downloadFile(functionalSeetestManual, session, response, request);
		}
		return "integratedQALabs/mobileLab/mobileLab";
	}

	@RequestMapping(value = "logout")
	public String logout(Model model, HttpSession session) {
		DigiLoggerUtils.log("Session ID IN BEFORE LOGOUT : " + session.getId(), LEVEL.info);
		session.invalidate();
		return "login";
	}

	@RequestMapping(value = "settings")
	public String settings(Model model, HttpSession session) {
		// system.out.println.println("inside settings");
		return "settingpg";
	}

	@RequestMapping(value = "settings/editSetting")
	public String editUser(Model model, HttpSession session, @RequestParam("userid") String userid) {
		try {
			Credentials user = loginService.getcredentials(Integer.parseInt(userid.trim()));
			model.addAttribute("user", user);

		} catch (Exception e) {

			List<Credentials> users = loginService.getAllUsers();

			model.addAttribute("users", users);
			model.addAttribute("message", "Problem in editing the user details...");

			return "settings/editSetting";
		}
		return "settings/editSetting";
	}

	@RequestMapping(value = "fetchapi")
	public String fetchcredentials(Model model, @RequestParam("pcloudyuname") String puname,
			@RequestParam("pcloudykey") String pkey, @RequestParam("pcloudypass") String ppass, HttpSession session) {
		//// system.out.println.println("api details fetched");
		/*
		 * session.setAttribute("pcloudyUsername", puname);
		 * session.setAttribute("pcloudyApi", pkey)
		 */;
		String userid = session.getAttribute("userid").toString();
		boolean result = loginService.addcredentials(userid, puname, pkey, ppass);
		if (result) {
			// system.out.println.println("credential added");
		}
		boolean res = checkapi(model, session, userid);
		// System.out.println("icon checker"+ res);
		if (res) {

			model.addAttribute("setstatus", "display");
		} else {

			model.addAttribute("setstatus", "nodisplay");
		}
		return "landing_new";
	}

	public boolean checkapi(Model model, HttpSession session, String userid) {
		userid = session.getAttribute("userid").toString();
		List<Credentials> cr = loginService.getcredentials(userid);
		System.out.println("Userid: " + userid);

		session.setAttribute("credentials", cr);
		boolean flag = false;
		if (cr.isEmpty()) {
			flag = true;
			// System.out.println("cr is empty");
		} else {
			flag = false;
			// System.out.println("cr isnt empty");
		}
		return flag;
	}

	@RequestMapping(value = "views/login/register")
	public String register(Model model, @ModelAttribute("User") User userDetails, HttpSession session) {

		if (loginService.checkEmail(userDetails.getEmail())) {
			model.addAttribute("error", "Error: User already exists.. Please Login!!!");
		} else {
			String tempPassword = RandomStringUtils.randomAlphanumeric(10);
			userDetails.setUserPassword(tempPassword);
			userDetails.setStatus("N");

			if (loginService.addUser(userDetails)) {
				File file = new File(macroFile);
				if (file.exists()) {
					String userName = userDetails.getEmail();
					file = new File(macroFile + userName);
					file.mkdir();
					if (file.exists()) {
						// File dest = new File(macroFile + userName +
						// "/Macro.vbs");
						if (SendMail.sendMail(macroFile, userName, tempPassword)) {
							model.addAttribute("message", "User Registered successfully !! ");
							model.addAttribute("message1", " Activation mail has been sent to your email Id...");
						} else {
							model.addAttribute("error", "Error: Failed to Register ");
							model.addAttribute("error", "Error: Please contact admin !!!");
						}
					} else {
						model.addAttribute("error", "Error: Failed to Register ");
						model.addAttribute("error", "Error: Please contact admin !!!");
					}
				} else {
					model.addAttribute("error", "Error:Failed to Register ");
					model.addAttribute("error", "Error:Please contact admin !!!");
				}

			} else {
				model.addAttribute("error", "Error: Failed to Register ");
				model.addAttribute("error", "Error: Please try again later !!");
			}
		}

		return "views/login/register";

	}

	@RequestMapping(value = "activate")
	public String userActivate(Model model, HttpSession session, @RequestParam("userName") String userName,
			@RequestParam("userPassword") String userPassword, @RequestParam("repeatPassword") String repeatPassword,
			@RequestParam("password") String password) {
		User userDetails = loginService.loginValidation(userName, password);
		if (userDetails == null) {
			model.addAttribute("error", "Error: Invalid UserName or Password...");
			return "activation";
		} else {
			loginService.activateUser(userName);
			loginService.updatePassword(userName, userPassword);
			session.setAttribute("userName", userDetails.getFirstName() + "  " + userDetails.getLastName());
			session.setAttribute("userNameNoSpace", userDetails.getFirstName() + "_" + userDetails.getLastName());
			session.setAttribute("userid", userDetails.getUserid());
			session.setAttribute("projectid", userDetails.getProjectid());
			session.setAttribute("roleid", userDetails.getRoleid());
			model.addAttribute("username", userName);

			boolean res = checkapi(model, session, userDetails.getUserid());
			if (res) {

				model.addAttribute("setstatus", "display");
			} else {

				model.addAttribute("setstatus", "nodisplay");
			}
			return "landing_new";
		}

	}

	@RequestMapping(value = "views/login/forgotPassword")
	public String forgotPassword(Model model, HttpSession session,
			@RequestParam(value = "forgotEmailId") String emailId, HttpServletRequest request) {
		if (loginService.checkEmail(emailId)) {
			String tempPassword = RandomStringUtils.randomAlphanumeric(10);
			if (loginService.updatePassword(emailId, tempPassword)) {
				File file = new File(macroFile);
				if (file.exists()) {
					file = new File(macroFile + emailId);
					file.mkdir();

					if (file.exists()) {
						if (SendMail.sendMail(macroFile, emailId, tempPassword)) {
							model.addAttribute("message", "Success:New password has been sent to your email Id... ");
						} else {
							model.addAttribute("error", "Error: Failed!!! Please contact admin!!! ");
						}

					} else {
						model.addAttribute("error", "Error: Failed!!! Please contact admin!!! ");
					}

				} else {
					model.addAttribute("error", "Error: Failed!!! Please contact admin!!! ");
				}
			} else {
				model.addAttribute("error", "Error: Failed!!! Please contact admin!!! ");
			}

		} else {
			model.addAttribute("error", "Error: User doesnot exists!!!");
		}
		return "views/login/forgotPassword";
	}
}
