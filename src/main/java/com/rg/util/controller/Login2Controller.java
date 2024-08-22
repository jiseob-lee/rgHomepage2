package com.rg.util.controller;

//import java.util.List;

//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpSession;

//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;

//import com.rg.util.SessionConfig;

//@Controller
public class Login2Controller {
	/*
	@RequestMapping(value = "/rg/loginBackup.do", method = RequestMethod.POST)
	public String login(HttpServletRequest request, HttpSession session, RedirectAttributes rttr) throws Exception {
		String id = request.getParameter("id");
		if(id != null){
			String userId = SessionConfig.getSessionidCheck("login_id", id);
			System.out.println("##### " + id + " : " + userId);
			session.setMaxInactiveInterval(60 * 60);
			session.setAttribute("login_id", id);
			return "redirect:/home.do";
		}
		return "redirect:/main.do";
	}
	
	@RequestMapping(value = "/rg/mainBackup.do")
	public String index(HttpSession session) throws Exception {
		return "login";
	}
	
	@RequestMapping(value = "/rg/homeBackup.do")
	public String home(HttpSession session) throws Exception {
		return "home";
	}

	@RequestMapping(value = {"/rg/sessionC.do", "/sessionC.do"}, method = {RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	public String sessionC(HttpServletRequest request, HttpSession session, RedirectAttributes rttr) throws Exception {
		
		String rootPath = request.getSession().getServletContext().getRealPath("/");
		System.out.println("##### rootPath sessionC : " + rootPath);
		
		String id = "cdef";
		if (request.getParameter("id") != null && !"".equals(request.getParameter("id"))) {
			id = request.getParameter("id");
		}
		String userId = "";
		if (id != null) {
			userId = SessionConfig.getSessionidCheck("login_id", id);
			System.out.println("##### sessionC : " + id + " : " + userId + " : " + session.getAttribute("login_id"));
			//session.setMaxInactiveInterval(60 * 60);
			session.setMaxInactiveInterval(20);
			session.setAttribute("login_id", id);
			//return "redirect:/home.do";
		}
		return id + " : " + userId;
	}

	@RequestMapping(value = {"/rg/sessionV.do", "/sessionV.do"}, method = {RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	public String sessionV(HttpServletRequest request, HttpSession session, RedirectAttributes rttr) throws Exception {
		
		String rootPath = request.getSession().getServletContext().getRealPath("/");
		System.out.println("##### rootPath sessionV : " + rootPath);

		String login_id = (String)session.getAttribute("login_id");
		
		System.out.println("##### sessionV : " + login_id + ".");
		
		//SessionConfig sessionConfig = new SessionConfig();
		List<String> sessionList = SessionConfig.getSessions();
		if (sessionList != null) {
			for (int i=0; i < sessionList.size(); i++) {
				System.out.println("#### sessionList : " + sessionList.get(i));
			}
		}
		return login_id;
	}
	*/
}