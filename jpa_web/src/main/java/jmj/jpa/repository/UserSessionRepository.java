package jmj.jpa.repository;

import jmj.jpa.model.User;

import jakarta.persistence.*;
import java.util.*;

public class UserSessionRepository {
	private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa_javaPU");
	
	private EntityManager getEntityManager() {
		return emf.createEntityManager();
	}
	
	public User selectUserByPrimaryKey(String userId) {
		EntityManager entityManager = getEntityManager();
		try {
			return entityManager.find(User.class, userId);
		} finally {
			entityManager.close();
		}
	}
	
	public Integer insertUser(User user) {
		EntityManager em = getEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			em.persist(user);
			tx.commit();
			return 1;
		} catch (Exception e) {
			if (tx.isActive()) tx.rollback();
			e.printStackTrace();
			return 0;
		} finally {
			em.close();
		}
	}
	
	public Integer updateUser(User user) {
		EntityManager em = getEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			em.merge(user);
			tx.commit();
			return 1;
		} catch (Exception e) {
			if (tx.isActive()) tx.rollback();
			e.printStackTrace();
			return 0;
		} finally {
			em.close();
		}
	}
	
	public Integer deleteUser(String userId) {
		EntityManager em = getEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			User u = em.find(User.class, userId);
			if (u != null) em.remove(u);
			tx.commit();
			return u != null ? 1 : 0;
		} catch (Exception e) {
			if (tx.isActive()) tx.rollback();
			e.printStackTrace();
			return 0;
		} finally {
			em.close();
		}
	}
	
	public List<User> selectUserByCondition(Map<String, Object> condition) {
		EntityManager em = getEntityManager();
		try {
			StringBuilder sb = new StringBuilder("SELECT u FROM User u");
			if (condition != null && !condition.isEmpty()) {
				sb.append(" WHERE ");
				List<String> parts = new ArrayList<>();
				for (String key : condition.keySet()) {
					parts.add("u." + key + " = :" + key);
				}
				sb.append(String.join(" AND ", parts));
			}
			TypedQuery<User> q = em.createQuery(sb.toString(), User.class);
			if (condition != null) {
				for (Map.Entry<String, Object> e : condition.entrySet()) {
					q.setParameter(e.getKey(), e.getValue());
				}
			}
			return q.getResultList();
		} finally {
			em.close();
		}
	}
}