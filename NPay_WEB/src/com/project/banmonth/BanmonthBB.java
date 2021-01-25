package com.project.banmonth;

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
public class BanmonthBB implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String PAGE_STAY_AT_THE_SAME = null;

	private User loaded = null;
	private Bannedmonth ban = new Bannedmonth();
	private Bannedmonth newBan = new Bannedmonth();

	public Bannedmonth getNewBan() {
		return newBan;
	}

	public void setNewBan(Bannedmonth newBan) {
		this.newBan = newBan;
	}

	public Bannedmonth getBan() {
		return ban;
	}

	public void setBan(Bannedmonth ban) {
		this.ban = ban;
	}

	public User getLoaded() {
		return loaded;
	}

	public void setLoaded(User loaded) {
		this.loaded = loaded;
	}

	@EJB
	BannedmonthDAO banDAO;

	@Inject
	FacesContext context;

	public void onLoad() throws IOException {
		RemoteClient<User> rm = new RemoteClient<User>();
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		rm = RemoteClient.load(session);
		loaded = (User) rm.getDetails();
		if (loaded != null) {
			try {
				ban = banDAO.searchByUser(loaded.getIduser());
			} catch (Exception e) {
				ban = null;
			}
		}

	}

	public String changeDatas() throws IOException {
		if (ban != null) {
			if (newBan.getMonth1() != ban.getMonth1() && !(newBan.getMonth1() == "")) {
				ban.setMonth1(newBan.getMonth1());
			}
			if (newBan.getMonth2() != ban.getMonth2() && !(newBan.getMonth2() == "")) {
				ban.setMonth2(newBan.getMonth2());
			}
			try {
				if (ban.getMonth1().equals(ban.getMonth2()))
					ban.setMonth2("");
			} catch (Exception e) {
			}
			try {
				banDAO.merge(ban);
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Poprawnie zmieniono", null));
			} catch (Exception e) {
				e.printStackTrace();
				context.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Wyst¹pi³ b³¹d podczas zapisu", null));
				return PAGE_STAY_AT_THE_SAME;
			}
			return PAGE_STAY_AT_THE_SAME;
		}
		context.addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nieprawid³owe wywo³anie systemu.", null));
		return PAGE_STAY_AT_THE_SAME;
	}
}
