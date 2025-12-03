package jmj.jpa.repository;

import jmj.jpa.model.Comment;

import jakarta.persistence.*;
import java.util.*;

public class CommentSessionRepository {
	private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa_javaPU");
	
	private EntityManager getEntityManager() {
		return emf.createEntityManager();
	}
	
	public Comment selectCommentByPrimaryKey(Long commentNo) {
		EntityManager entityManager = getEntityManager();
		try {
			return entityManager.find(Comment.class, commentNo);
		} finally {
			entityManager.close();
		}
	}
	
	public Integer insertComment(Comment comment) {
		EntityManager em = getEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			em.persist(comment);
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
	
	public Integer updateComment(Comment comment) {
		EntityManager em = getEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			em.merge(comment);
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
	
	public Integer deleteComment(Long commentNo) {
		EntityManager em = getEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			Comment c = em.find(Comment.class, commentNo);
			if (c != null) em.remove(c);
			tx.commit();
			return c != null ? 1 : 0;
		} catch (Exception e) {
			if (tx.isActive()) tx.rollback();
			e.printStackTrace();
			return 0;
		} finally {
			em.close();
		}
	}
	
	public List<Comment> selectCommentByCondition(Map<String, Object> condition) {
		EntityManager em = getEntityManager();
		try {
			StringBuilder sb = new StringBuilder("SELECT c FROM Comment c");
			if (condition != null && !condition.isEmpty()) {
				sb.append(" WHERE ");
				List<String> parts = new ArrayList<>();
				for (String key : condition.keySet()) {
					parts.add("c." + key + " = :" + key);
				}
				sb.append(String.join(" AND ", parts));
			}
			TypedQuery<Comment> q = em.createQuery(sb.toString(), Comment.class);
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