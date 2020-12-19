package com.project.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import project.entities.Team;


//DAO - Data Access Object for Person entity
//Designed to serve as an interface between higher layers of application and data.
//Implemented as stateless Enterprise Java bean - server side code that can be invoked even remotely.

@Stateless
public class TeamDAO {
	private final static String UNIT_NAME = "jsfcourse-simplePU";

	// Dependency injection (no setter method is needed)
	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;

	public void create(Team team) {
		em.persist(team);
	}

	public Team merge(Team team) {
		return em.merge(team);
	}

	public void remove(Team team) {
		em.remove(em.merge(team));
	}

	public Team find(Object id) {
		return em.find(Team.class, id);
	}
	public List<Team> getFullList(){
		List<Team> list = null;
		
		Query query = em.createQuery("select t from Team t");
		
		try {
			list = query.getResultList();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
