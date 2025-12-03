package jmj.jpa.repository;

import jmj.jpa.model.Reply;

import jakarta.persistence.*;
import java.util.*;

public class ReplySessionRepository {
	private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa_javaPU");
	
	private EntityManager getEntityManager() {
		return emf.createEntityManager();
	}
	
	public Reply selectReplyByPrimaryKey(Long replyNo) {
		EntityManager entityManager = getEntityManager();
		try {
			return entityManager.find(Reply.class, replyNo);
		} finally {
			entityManager.close();
		}
	}

	public Integer insertReply(Reply reply) {
		EntityManager em = getEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			em.persist(reply);
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

	public Integer updateReply(Reply reply) {
		EntityManager em = getEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			em.merge(reply);
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

	public Integer deleteReply(Long replyNo) {
		EntityManager em = getEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			Reply r = em.find(Reply.class, replyNo);
			if (r != null) em.remove(r);
			tx.commit();
			return r != null ? 1 : 0;
		} catch (Exception e) {
			if (tx.isActive()) tx.rollback();
			e.printStackTrace();
			return 0;
		} finally {
			em.close();
		}
	}

	public List<Reply> selectReplyByCondition(Map<String, Object> condition) {
		EntityManager em = getEntityManager();
		try {
			StringBuilder sb = new StringBuilder("SELECT r FROM Reply r");
			if (condition != null && !condition.isEmpty()) {
				sb.append(" WHERE ");
				List<String> parts = new ArrayList<>();
				for (String key : condition.keySet()) {
					parts.add("r." + key + " = :" + key);
				}
				sb.append(String.join(" AND ", parts));
			}
			TypedQuery<Reply> q = em.createQuery(sb.toString(), Reply.class);
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