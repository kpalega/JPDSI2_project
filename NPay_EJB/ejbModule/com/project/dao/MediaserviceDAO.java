package com.project.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import project.entities.Mediaservice;


//DAO - Data Access Object for Person entity
//Designed to serve as an interface between higher layers of application and data.
//Implemented as stateless Enterprise Java bean - server side code that can be invoked even remotely.

@Stateless
public class MediaserviceDAO {
	private final static String UNIT_NAME = "jsfcourse-simplePU";

	// Dependency injection (no setter method is needed)
	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;

	public void create(Mediaservice mediaservice) {
		em.persist(mediaservice);
	}

	public Mediaservice merge(Mediaservice mediaservice) {
		return em.merge(mediaservice);
	}

	public void remove(Mediaservice mediaservice) {
		em.remove(em.merge(mediaservice));
	}

	public Mediaservice find(Object id) {
		return em.find(Mediaservice.class, id);
	}

	public List<Mediaservice> getFullList(){
		List<Mediaservice> list = null;
		
		Query query = em.createQuery("select m from Mediaservice m");
		
		try {
			list = query.getResultList();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
