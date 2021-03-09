package com.project.payment;

import java.io.IOException;
import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import javax.faces.simplesecurity.RemoteClient;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import com.project.dao.BannedmonthDAO;

import project.entities.Bannedmonth;
import project.entities.User;

@Named
@SessionScoped
public class PaymentBB implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String PAGE_STAY_AT_THE_SAME = null;

	private User loaded = null;

	@Inject
	FacesContext context;

	public void onLoad() throws IOException {
		RemoteClient<User> rm = new RemoteClient<User>();
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		rm = RemoteClient.load(session);
		loaded = (User) rm.getDetails();
		if (loaded != null) {
			
		}

	}

}
