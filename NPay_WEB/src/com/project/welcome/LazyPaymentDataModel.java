package com.project.welcome;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.faces.context.FacesContext;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import com.project.dao.PaymentDAO;
import com.project.dao.UserDAO;

import project.entities.Payment;
import project.entities.User;

@Named
@ViewScoped
public class LazyPaymentDataModel extends LazyDataModel<Payment> {
	private static final long serialVersionUID = 1L;

	public LazyPaymentDataModel() {
	}
	private int userID;
	
	private PaymentDAO payDAO;
	
	@Override
	public List<Payment> load(int offset, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
		
		List<Payment> pays = payDAO.getPaymentList(offset, pageSize, userID);
		setRowCount(payDAO.countPayments(userID) );
		return pays;
	}

	public PaymentDAO getUserDAO() {
		return payDAO;
	}

	public void setPaymentDAO(PaymentDAO payDAO) {
		this.payDAO = payDAO;
	}
	
	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

}
