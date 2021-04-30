package com.my.DAO;

import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;



public class DAO {
	private static final Logger log = Logger.getAnonymousLogger();
	private static final ThreadLocal session = new ThreadLocal();
	private static final SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
	
	protected DAO() {
		
	}
	
	public static Session getSession() {
		Session session = (Session)DAO.session.get();
		if (session==null) {
			session= sessionFactory.openSession();
			DAO.session.set(session);
		}
		
		return session;
	}
	
	protected void beginTranscation() {
		getSession().beginTransaction();
	}
	
	protected void commit() {
		getSession().getTransaction().commit();
	}
	
	protected void rollback() {
		getSession().getTransaction().rollback();
		getSession().close();
		DAO.session.set(null);
	}
	
	public static void close() {
		getSession().close();
		DAO.session.set(null);
	}
}
