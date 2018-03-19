/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brendev.shopapp.dao.core;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Brendev
 * @param <T> type de sort value
 * @param <PK> type de sort value
 */
public abstract class BaseDaoBean<T , PK extends java.io.Serializable> implements BaseDaoBeanLocal<T, PK>{
    @PersistenceContext(unitName = "shopAppPU")
    protected EntityManager em;
    protected Class<T> type;

    public BaseDaoBean() {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().
                getGenericSuperclass();
        this.type = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
    }

    public BaseDaoBean(Class<T> type) {
        this.type = type;
    }
    
     /**
     *
     * @param id type de sort value
     * @return type de sort value
     */
    @Override
    public T getOne(final PK id) {
        return (T) em.find(type, id);
    }

    /**
     *
     * @return type de sort value
     */
    @Override
    public Long count() {
        return (Long) em.createQuery("SELECT COUNT(t) FROM "
                + type.getSimpleName()+ " t").getSingleResult();
    }

    /**
     *
     * @return type de sort value
     */
    @Override
    public List<T> getAll() {
        return (List<T>) em.createQuery("SELECT t FROM "
                + type.getSimpleName() + " t").getResultList();
    }

    /**
     *
     * @param sortProperty type de sort value
     * @param sortAsc type de sort value
     * @return type de sort value
     */
    @Override
    public List<T> getAll(String sortProperty, boolean sortAsc) {
        return (List<T>) em.createQuery("SELECT t FROM " + type.getSimpleName()
                + "ORDER BY t." + sortProperty
                + ((sortAsc) ? " ASC" : " DESC")).getResultList();
    }

    /**
     *
     * @param <E> type de sort value
     * @param sortProperty type de sort value
     * @param sortValue type de sort value
     * @return une liste de T elts triÃ© par sortProperty like sortValue
     */
    @Override
    public <E> List<T> getAll(String sortProperty, E sortValue) {
        try {
            String jpql = "SELECT t FROM " + type.getSimpleName()
                    + " t WHERE t." + sortProperty + " LIKE :d ";
            Query query = em.createQuery(jpql);
            query.setParameter("d", "%" + sortValue + "%");
            return query.getResultList();
        } catch (NoResultException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    @Override
    public <E> List<T> getAll(String sortProperty, E sortValue, boolean sortAsc) {
        try {
            String jpql = "SELECT t FROM " + type.getSimpleName()
                    + " t WHERE t." + sortProperty + " LIKE :d  "
                    + " ORDER BY t." + sortProperty + ((sortAsc) ? " ASC" : " DESC");
            Query query = em.createQuery(jpql);
            query.setParameter("d", "%" + sortValue + "%");
            return query.getResultList();
        } catch (NoResultException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param <E> type de sort value
     * @param sortProperty type de sort value
     * @param sortValue type de sort value
     * @return une liste de T elts triÃ© par sortProperty = sortValue
     */
    @Override
    public <E> List<T> getBy(String sortProperty, E sortValue) {
        try {
            String jpql = "SELECT t FROM " + type.getSimpleName()
                    + " t WHERE t." + sortProperty + " =:d "
                    + " ORDER BY t." + sortProperty + " ASC";
            Query query = em.createQuery(jpql);
            query.setParameter("d", sortValue);
            return query.getResultList();
        } catch (NoResultException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param <E> type de sort value
     * @param sortProperty type de sort value
     * @param sortValue type de sort value
     * @return un Ã©lÃ©ment de T elts triÃ© par sortProperty = sortValue
     */
    @Override
    public <E> T getOneBy(String sortProperty, E sortValue) {
        try {
            String jpql = "SELECT t FROM " + type.getSimpleName()
                    + " t WHERE t." + sortProperty + " =:d "
                    + " ORDER BY t." + sortProperty + " ASC";
            Query query = em.createQuery(jpql);
            query.setParameter("d", sortValue);
            return (T) query.getSingleResult();
        } catch (NoResultException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param <E> type de sort value
     * @param <F> type de sort value
     * @param sortProperty premier condition
     * @param andSortProperty seconde condition
     * @param sortValue triÃ© par cette valleur
     * @param andSortValue type de sort value
     * @return une liste de T elts triÃ© par sortProperty = sortValue et
     * andSortProperty
     */
    @Override
    public <E, F> List<T> getBy(String sortProperty, String andSortProperty, E sortValue, F andSortValue) {
        try {
            String jpql = "SELECT t FROM " + type.getSimpleName()
                    + " t WHERE t." + sortProperty + " =:e "
                    + "AND t." + andSortProperty + " =:f "
                    + " ORDER BY t." + sortProperty + " ASC";
            Query query = em.createQuery(jpql);
            query.setParameter("e", sortValue);
            query.setParameter("f", andSortValue);
            return query.getResultList();
        } catch (NoResultException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param <E> type de sort value
     * @param sortProperty type de sort value
     * @param sortValue type de sort value
     * @return une liste de T elts triÃ© par sortProperty pour sortValue
     */
    @Override
    public <E> List<T> getNonBy(String sortProperty, E sortValue) {
        try {
            String jpql = "SELECT t FROM " + type.getSimpleName()
                    + " t WHERE t." + sortProperty + " <>:d ";
            Query query = em.createQuery(jpql);
            query.setParameter("d", sortValue);
            return query.getResultList();
        } catch (NoResultException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    @Override
    public List<T> getAll(int first, int count, String sortProperty, boolean sortAsc) {
        return (List<T>) em.createQuery("SELECT t FROM " + type.getSimpleName()
                + " t ORDER BY t." + sortProperty
                + ((sortAsc) ? " ASC" : " DESC")).
                setFirstResult(first).setMaxResults(count).getResultList();
    }

    @Override
    public T saveOne(final T t) {
        em.persist(t);
        return t;
    }

    @Override
    public T updateOne(final T t) {
        return (T) em.merge(t);
    }

    @Override
    public boolean deleteOne(final PK id) {
        T t = em.find(type, id);
        if (t == null) {
            return false;
        } else {
            Query requette = em.createQuery("UPDATE " + type.getSimpleName()
                    + " t SET t.supprimer=1 "
                    + "WHERE t.id =:id");
            requette.setParameter("id", id);
            requette.executeUpdate();
        }
        return true;
    }

    @Override
    public boolean deleteRealOne(final PK id) {
        try {

            em.createQuery("DELETE FROM " + type.getSimpleName() + " a " + "WHERE a.id =:id")
                    .setParameter("id", id)
                    .executeUpdate();
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    /**
     *
     * @param t type de sort value
     * @return type de sort value
     */
    @Override
    public boolean deleteOne(final T t) {
        em.remove(t);
        return true;
    }

    /**
     *
     */
    @Override
    public void deleteAll() {
        em.createQuery("DELETE FROM " + type.getSimpleName()).executeUpdate();
    }

    /**
     *
     * @param query type de sort value
     * @return type de sort value
     */
    @Override
    public List<T> executeQuery(Query query) {
        return (List<T>) query.getResultList();
    }

    /**
     *
     * @param query type de sort value
     * @return type de sort value
     */
    @Override
    public int executeUpdate(Query query) {
        return query.executeUpdate();
    }

    /**
     *
     * @return type de sort value
     */
    @Override
    public EntityManager getEntityManager() {
        return this.em;
    }

    @Override
    public boolean deleteRealOne(T t) {
        em.remove(t);
        return true;
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public Class<T> getType() {
        return type;
    }

    public void setType(Class<T> type) {
        this.type = type;
    }
    
     @Override
    public T find(PK id) {
        if (id == null) {
            return null;
        } else {
            return (T) em.find(getType(), id);
        }
    }

    
    
    
}
