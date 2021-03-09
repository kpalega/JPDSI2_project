package com.project.dao;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import project.entities.Team;
import project.entities.User;

//DAO - Data Access Object for Person entity
//Designed to serve as an interface between higher layers of application and data.
//Implemented as stateless Enterprise Java bean - server side code that can be invoked even remotely.

@Stateless
public class UserDAO {
	private final static String UNIT_NAME = "jsfcourse-simplePU";

	@Inject
	FacesContext context;
	// Dependency injection (no setter method is needed)
	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;

	public void create(User user) {
		em.persist(user);
	}

	public User merge(User user) {
		return em.merge(user);
	}

	public void remove(User user) {
		em.remove(em.merge(user));
	}

	public User find(Object id) {
		return em.find(User.class, id);
	}

	public User login(String email, String password) {
		User u = new User();
		try {
			u = (User) em.createQuery("from User where email = :email and password = :password")
					.setParameter("email", email).setParameter("password", password).getSingleResult();
		} catch (NoResultException e) {
			u = null;
		}
		return u;
	}

	public List<User> getFullList() {
		List<User> list = null;

		Query query = em.createQuery("select u from User u order by fname");

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<User> getUserList(int start, int size, int userID) {
		Query query = em.createQuery("select u from User u where iduser != :iduser").setParameter("iduser", userID)
				.setFirstResult(start).setMaxResults(size);

		try {
			List<User> list = query.getResultList();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public int countUsers(int userID) {
		TypedQuery<Long> query = (TypedQuery<Long>) em
				.createQuery("select count (*) from User u where iduser != :iduser ").setParameter("iduser", userID);
		int i = 0;
		try {
			i = query.getSingleResult().intValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}

}
