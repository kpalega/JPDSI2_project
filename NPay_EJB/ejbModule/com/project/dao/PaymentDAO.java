package com.project.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import project.entities.Bannedmonth;
import project.entities.Payment;
import project.entities.User;


//DAO - Data Access Object for Person entity
//Designed to serve as an interface between higher layers of application and data.
//Implemented as stateless Enterprise Java bean - server side code that can be invoked even remotely.

@Stateless
public class PaymentDAO {
	private final static String UNIT_NAME = "jsfcourse-simplePU";

	// Dependency injection (no setter method is needed)
	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;

	public void create(Payment payment) {
		em.persist(payment);
	}

	public Payment merge(Payment payment) {
		return em.merge(payment);
	}

	public void remove(Payment payment) {
		em.remove(em.merge(payment));
	}

	public Payment find(Object id) {
		return em.find(Payment.class, id);
	}
	public List<Payment> getFullList(){
		List<Payment> list = null;
		
		Query query = em.createQuery("select p from Payment p");
		
		try {
			list = query.getResultList();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List <Payment> searchPaymentByUser(int userID) {
		List <Payment> payments;
		try {
			payments = em.createQuery("from Payment where iduser = :id ORDER BY idpayment DESC").setParameter("id", userID).getResultList();
		} catch (NoResultException e) {
			payments = null;
		}
		return payments;
	}
	
	public List<Payment> getPaymentList(int start, int size, int userID) {
		Query query = em.createQuery("select p from Payment p where iduser = :iduser ORDER BY idpayment DESC").setParameter("iduser", userID)
				.setFirstResult(start).setMaxResults(size);
		
		try {
			List<Payment> list  = query.getResultList();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public int countPayments(int userID) {
		TypedQuery<Long> query = (TypedQuery<Long>) em.createQuery("select count (*) from Payment p where iduser = :iduser  ").setParameter("iduser", userID);
		int i = 0;
		try {
			i = query.getSingleResult().intValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}
}
