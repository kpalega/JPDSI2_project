package com.project.user;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.project.dao.UserDAO;
import project.entities.User;

@Named
@ViewScoped
public class UserBB implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String PAGE_PERSON_LIST = "personList?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private User person = new User();
	private User loaded = null;

	@EJB
	UserDAO personDAO;

	@Inject
	FacesContext context;

	@Inject
	Flash flash;
	
}
