/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cp.data.crud.interfaces;

import java.util.List;
import jakarta.persistence.EntityManager;
/**
 *
 * @author utfpr
 */
public abstract class AbstractCrud<T> {
    
    private Class<T> entityClass;

    public AbstractCrud(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public Exception persist(T entity) {
        try {
            getEntityManager().getTransaction().begin();
            getEntityManager().persist(entity);
            getEntityManager().flush();
            getEntityManager().getTransaction().commit();
            return null;
        } catch (Exception e) {
            getEntityManager().getTransaction().rollback();
            return e;
        }
    }
    
    public Exception merge(T entity) {
        try {
            getEntityManager().getTransaction().begin();
            getEntityManager().merge(entity);
            getEntityManager().flush();
            getEntityManager().getTransaction().commit();
            return null;
        } catch (Exception e) {
            getEntityManager().getTransaction().rollback();
            return e;
        }
    }
    
    public Exception remove(T entity) {
        try {
            getEntityManager().getTransaction().begin();
            getEntityManager().remove(getEntityManager().merge(entity));
            getEntityManager().flush();
            getEntityManager().getTransaction().commit();
            return null;
        } catch (Exception e) {
            getEntityManager().getTransaction().rollback();
            return e;
        }
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> getAll() {
        try {
            jakarta.persistence.criteria.CriteriaQuery cq;
            cq = getEntityManager().getCriteriaBuilder().createQuery();
            cq.select(cq.from(entityClass));
            return getEntityManager().createQuery(cq).getResultList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<T> findRange(int[] range) {
        jakarta.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        jakarta.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        jakarta.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        jakarta.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        jakarta.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

}
