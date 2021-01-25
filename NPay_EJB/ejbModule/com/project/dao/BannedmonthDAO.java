package com.project.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import project.entities.Bannedmonth;
import project.entities.User;

//DAO - Data Access Object for Person entity
//Designed to serve as an interface between higher layers of application and data.
//Implemented as stateless Enterprise Java bean - server side code that can be invoked even remotely.

@Stateless
public class BannedmonthDAO {
	private final static String UNIT_NAME = "jsfcourse-simplePU";

	// Dependency injection (no setter method is needed)
	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;

	public void create(Bannedmonth bannedmonth) {
		em.persist(bannedmonth);
	}

	public Bannedmonth merge(Bannedmonth bannedmonth) {
		return em.merge(bannedmonth);
	}

	public void remove(Bannedmonth bannedmonth) {
		em.remove(em.merge(bannedmonth));
	}

	public Bannedmonth find(Object id) {
		return em.find(Bannedmonth.class, id);
	}

	public List<Bannedmonth> getFullList() {
		List<Bannedmonth> list = null;

		Query query = em.createQuery("select b from Bannedmonth b");

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public Bannedmonth searchByUser(int userID) {
		Bannedmonth b = new Bannedmonth();
		try {
			b = (Bannedmonth) em.createQuery("from Bannedmonth where iduser = :id").setParameter("id", userID)
					.getSingleResult();
		} catch (NoResultException e) {
			b = null;
		}
		return b;
	}
}
