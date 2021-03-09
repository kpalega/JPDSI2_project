package com.project.welcome;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import javax.faces.simplesecurity.RemoteClient;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.primefaces.model.LazyDataModel;

import com.project.dao.BannedmonthDAO;
import com.project.dao.PaymentDAO;
import com.project.group.LazyUserDataModel;

import project.entities.Bannedmonth;
import project.entities.Payment;
import project.entities.Team;
import project.entities.User;

@Named
@SessionScoped
public class WelcomeBB implements Serializable {
	private static final long serialVersionUID = 1L;

	private Bannedmonth ban;
	private List <Team> teams;
	private List <Payment> payments;
	private User loaded = null;
	private LazyDataModel<Payment> lazyPaymentModel;

	@EJB
	BannedmonthDAO banDAO;

	@EJB
	PaymentDAO payDAO;
	
	@Inject
	FacesContext context;


	@PostConstruct
    public void init() {
        lazyPaymentModel = new LazyPaymentDataModel();
        ((LazyPaymentDataModel) lazyPaymentModel).setPaymentDAO(payDAO);
        RemoteClient<User> rm = new RemoteClient<User>();
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		rm = RemoteClient.load(session);
		loaded = (User) rm.getDetails();
        ((LazyPaymentDataModel) lazyPaymentModel).setUserID(loaded.getIduser());
        }
	
	@Transactional
	public void onLoad() throws IOException {
		if (loaded != null) {
			ban = banDAO.searchByUser(loaded.getIduser());
			setTeams(loaded.getTeams());
			setPayments(payDAO.searchPaymentByUser(loaded.getIduser()));
		}
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "B³êdne wywo³anie", null));
	}

	public List <Team> getTeams() {
		return teams;
	}

	public void setTeams(List <Team> teams) {
		this.teams = teams;
	}
	
	public User getLoaded() {
		return loaded;
	}

	public void setLoaded(User loaded) {
		this.loaded = loaded;
	}

	public Bannedmonth getBan() {
		return ban;
	}

	public void setBan(Bannedmonth ban) {
		this.ban = ban;
	}

	public List <Payment> getPayments() {
		return payments;
	}

	public void setPayments(List <Payment> payments) {
		this.payments = payments;
	}

	public LazyDataModel<Payment> getLazyPaymentModel() {
		return lazyPaymentModel;
	}

	public void setLazyPaymentModel(LazyDataModel<Payment> lazyPaymentModel) {
		this.lazyPaymentModel = lazyPaymentModel;
	}
	
}
