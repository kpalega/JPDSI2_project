package com.project.group;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import javax.faces.simplesecurity.RemoteClient;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import com.project.dao.MediaserviceDAO;
import com.project.dao.TeamDAO;
import com.project.dao.UserDAO;

import project.entities.Mediaservice;
import project.entities.Team;
import project.entities.User;

@Named
@ViewScoped
public class GroupBB implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String PAGE_MAIN = "/pages/user/groups?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private Team group = new Team();
	private Mediaservice media = new Mediaservice();
	private User loaded = null;
	private List<Team> userGroups = new ArrayList<Team>();
	private List<User> users = new ArrayList<User>();
	private List<User> selectedUsers = new ArrayList<User>();

	@EJB
	UserDAO userDAO;
	@EJB
	TeamDAO teamDAO;
	@EJB
	MediaserviceDAO mediaDAO;

	@Inject
	FacesContext context;

	public void onLoad() {
		RemoteClient<User> rm = new RemoteClient<User>();
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		rm = RemoteClient.load(session);
		loaded = (User) rm.getDetails();
		users = userDAO.getFullList();
		Iterator<User> i = users.iterator();
		while (i.hasNext()) {
			User u = i.next();
			if (u.getIduser() == loaded.getIduser()) {
				i.remove();
			}
		}
		setUsers(users);
		userGroups = loaded.getTeams();
	}

	public String saveDatas() {
		try {
			mediaDAO.create(this.media);
			Mediaservice lastM = new Mediaservice();
			lastM = mediaDAO.searchLastAdded();
			group.setMediaservice(lastM);
			group.setUsers(selectedUsers);
			teamDAO.create(group);
			Team lastT = new Team();
			lastT = teamDAO.searchLastAdded();
			for (User x : selectedUsers) {
				x.getTeams().add(lastT);
				userDAO.merge(x);
			}
			loaded.getTeams().add(lastT);
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Pomyœlnie dodano grupê", null));
		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Wyst¹pi³ b³¹d podczas zapisu", null));
			return PAGE_STAY_AT_THE_SAME;
		}

		return PAGE_MAIN;
	}

	public Mediaservice getMedia() {
		return media;
	}

	public void setMedia(Mediaservice media) {
		this.media = media;
	}

	public Team getGroup() {
		return group;
	}

	public void setGroup(Team g) {
		this.group = g;
	}

	public User getLoaded() {
		return loaded;
	}

	public void setLoaded(User loaded) {
		this.loaded = loaded;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<User> getSelectedUsers() {
		return selectedUsers;
	}

	public void setSelectedUsers(List<User> selectedUsers) {
		this.selectedUsers = selectedUsers;
	}

	public List<Team> getUserGroups() {
		return userGroups;
	}

	public void setUserGroups(List<Team> userGroups) {
		this.userGroups = userGroups;
	}

}
