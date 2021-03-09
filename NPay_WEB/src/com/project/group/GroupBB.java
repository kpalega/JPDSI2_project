package com.project.group;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import javax.faces.simplesecurity.RemoteClient;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.primefaces.event.RowEditEvent;
import org.primefaces.model.LazyDataModel;

import com.project.dao.BannedmonthDAO;
import com.project.dao.MediaserviceDAO;
import com.project.dao.PaymentDAO;
import com.project.dao.TeamDAO;
import com.project.dao.UserDAO;
import com.project.welcome.LazyPaymentDataModel;

import project.entities.Bannedmonth;
import project.entities.Mediaservice;
import project.entities.Payment;
import project.entities.Team;
import project.entities.User;

@Named
@ViewScoped
public class GroupBB implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String PAGE_MAIN = "/pages/user/groups?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;
	private static final String PAGE_EDIT = "/pages/user/groupEdit?faces-redirect=true";

	private Team group = new Team();
	private Mediaservice media = new Mediaservice();
	private User loaded = null;
	private List<Team> userGroups = new ArrayList<Team>();
	private List<User> selectedUsers = new ArrayList<User>();
	private Team selectedTeam;
	private LazyDataModel<User> lazyUserModel;
	private int userIDforPayment;
	private String month;
	private Payment payment = new Payment();

	@EJB
	PaymentDAO payDAO;
	@EJB
	UserDAO userDAO;
	@EJB
	TeamDAO teamDAO;
	@EJB
	MediaserviceDAO mediaDAO;
	@EJB
	BannedmonthDAO banDAO;

	@Inject
	FacesContext context;

	@PostConstruct
    public void init() {
        lazyUserModel = new LazyUserDataModel();
        ((LazyUserDataModel) lazyUserModel).setUserDAO(userDAO);
        RemoteClient<User> rm = new RemoteClient<User>();
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		rm = RemoteClient.load(session);
		loaded = (User) rm.getDetails();
        ((LazyUserDataModel) lazyUserModel).setUserID(loaded.getIduser());
        }
	
	public void onLoad() {
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

	public void onRowEdit(RowEditEvent<Team> event) {
		try {
			Team t = teamDAO.find(event.getObject().getIdteam());
			t.setName(event.getObject().getName());
			Mediaservice m = t.getMediaservice();
			m.setPrize(event.getObject().getMediaservice().getPrize());
			m.setService(event.getObject().getMediaservice().getService());
			mediaDAO.merge(m);
			t.setMediaservice(m);
			teamDAO.merge(t);
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Zapisano", null));
		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Wyst¹pi³ b³¹d podczas zapisu", null));
		}
	}

	public void onRowCancel(RowEditEvent<Team> event) {
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Odrzucono", null));
	}

	public Map<String, Integer> getStringList() {
		List<User> user = teamDAO.getUserList(selectedTeam);
		Map<String, Integer> userName = new LinkedHashMap<String, Integer>();
		for (User u : user) {
			userName.put(u.getFname(), u.getIduser());
		}
		return userName;
	}

	public void addPayment() {
		if (selectedTeam == null) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "B³êdne wywo³anie.", null));
		} else {
			try {

				User user = userDAO.find(userIDforPayment);
				Bannedmonth ban = banDAO.searchByUser(userIDforPayment);
				if((ban.getMonth1().contains(month)) || (ban.getMonth2().contains(month))) {
					context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Ten miesi¹c nie mo¿e zostaæ przypisany dla tego u¿ytkownika", null));
				} else {
					payment.setMonth(month);
					payment.setUser(user);
					payment.setTeam(selectedTeam);
					payDAO.create(payment);
					payment.setIdpayment(0);
					context.addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_INFO, "Pomyœlnie dodano p³atnoœæ.", null));
				}
			} catch (Exception e) {
				e.printStackTrace();
				context.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Wyst¹pi³ b³¹d podczas zapisu", null));
			}
		}
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

	public Team getSelectedTeam() {
		return selectedTeam;
	}

	public void setSelectedTeam(Team selectedTeam) {
		this.selectedTeam = selectedTeam;
	}

	public LazyDataModel<User> getLazyUserModel() {
		return lazyUserModel;
	}

	public void setLazyUserModel(LazyDataModel<User> lazyUserModel) {
		this.lazyUserModel = lazyUserModel;
	}

	public int getUserIDforPayment() {
		return userIDforPayment;
	}

	public void setUserIDforPayment(int userIDforPayment) {
		this.userIDforPayment = userIDforPayment;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

}
