package com.project.login;



import java.util.HashSet;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.simplesecurity.RemoteClient;

import javax.inject.Named;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.project.dao.UserDAO;
import project.entities.User;

@Named
@RequestScoped
public class LoginBB {

	private static final String PAGE_MAIN= "/pages/user/welcome?faces-redirect=true";
	private static final String PAGE_LOGIN= "/public/login";
	private static final String PAGE_STAY_AT_THE_SAME = null;	
    
	private String email;
	private String password;
	
	public String getEmail() {
		return this.email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return this.password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@EJB
	UserDAO userDAO;

	public String doLogin() {
		FacesContext ctx = FacesContext.getCurrentInstance();
        
		User user = userDAO.login(email, password);
				
		if (user == null) {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Niepoprawny login lub has³o", null));
			return PAGE_STAY_AT_THE_SAME;
		}
		RemoteClient<User> client = new RemoteClient<User>();
		client.setDetails(user);
		client.getRoles().add(user.getRole());
		HttpServletRequest request = (HttpServletRequest) ctx.getExternalContext().getRequest();
		client.store(request);
		return PAGE_MAIN;
	}
	
	public String doLogout(){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		session.invalidate();
		return PAGE_LOGIN;
	}
}
