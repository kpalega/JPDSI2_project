package com.project.user;

import java.io.IOException;
import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import javax.faces.simplesecurity.RemoteClient;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import com.project.dao.UserDAO;
import project.entities.User;

@Named
@ViewScoped
public class UserBB implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String PAGE_PERSON_LIST = "personList?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private User u = new User();
	private User loaded = null;

	public User getU() {
		return u;
	}

	public void setU(User u) {
		this.u = u;
	}

	public User getLoaded() {
		return loaded;
	}

	public void setLoaded(User loaded) {
		this.loaded = loaded;
	}

	@EJB
	UserDAO userDAO;

	@Inject
	FacesContext context;

	public String changeDatas() {
		RemoteClient<User> rm = new RemoteClient<User>();
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		rm = RemoteClient.load(session);
		loaded = (User) rm.getDetails();
		if (loaded != null) {
			if ((loaded.getFname() != u.getFname()) && !(u.getFname().equals(""))) {
				loaded.setFname(u.getFname());
			}
			if (loaded.getEmail() != u.getEmail() && !(u.getEmail().equals(""))) {
				loaded.setEmail(u.getEmail());
			}
			if (loaded.getPassword() != u.getPassword() && !(u.getPassword().equals(""))) {
				loaded.setPassword(u.getPassword());
			}
			try {
				userDAO.merge(loaded);
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Poprawnie zmieniono", null));
			} catch (Exception e) {
				e.printStackTrace();
				context.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Wyst¹pi³ b³¹d podczas zapisu", null));
				return PAGE_STAY_AT_THE_SAME;
			}
			return PAGE_STAY_AT_THE_SAME;
		}
		return PAGE_STAY_AT_THE_SAME;
	}
}
