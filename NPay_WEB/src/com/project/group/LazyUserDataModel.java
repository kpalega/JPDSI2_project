package com.project.group;

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
import com.project.dao.UserDAO;

import project.entities.User;

@Named
@ViewScoped
public class LazyUserDataModel extends LazyDataModel<User>{
	private static final long serialVersionUID = 1L;

	public LazyUserDataModel() {
	}
	private int userID;
	
	private UserDAO userDAO;
	
	@Override
	public List<User> load(int offset, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
		
		List<User> users = userDAO.getUserList(offset, pageSize, userID);
		setRowCount(userDAO.countUsers(userID) );
		return users;
	}

	@Override
	public User getRowData(String rowKey) {
		
		return userDAO.find(Integer.parseInt(rowKey));
	}
	
	@Override
	public String getRowKey(User object) {
		return String.valueOf(object.getIduser());
	}
	
	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	
	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

}
