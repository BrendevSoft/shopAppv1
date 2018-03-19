/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brendev.shopapp.dao.core;

import java.util.List;
import javax.ejb.Local;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author NOAMESSI
 * @param <T> type de sort value
 * @param <PK> type de sort value
 */
@Local
public interface BaseDaoBeanLocal<T, PK extends java.io.Serializable> {
    T getOne(final PK id);

    Long count();

    List<T> getAll();

    List<T> getAll(String sortProperty, boolean sortAsc);

    /**
     *
     * @param <E> type de sort value
     * @param sortProperty type de sort value
     * @param sortValue type de sort value
     * @return une liste de T elts triÃ© par sortProperty like sortValue
     */
    <E> List<T> getAll(String sortProperty, E sortValue);

    /**
     *
     * @param <E> type de sort value
     * @param sortProperty type de sort value
     * @param sortValue type de sort value
     * @return une liste de T elts triÃ© par sortProperty = sortValue
     */
    <E> List<T> getBy(String sortProperty, E sortValue);

    /**
     *
     * @param <E> type de sort value
     * @param sortProperty type de sort value
     * @param sortValue type de sort value
     * @return un Ã©lÃ©ment de T elts triÃ© par sortProperty = sortValue
     */
    <E> T getOneBy(String sortProperty, E sortValue);

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
    <E, F> List<T> getBy(String sortProperty, String andSortProperty, E sortValue, F andSortValue);

    /**
     *
     * @param <E> type de sort value
     * @param sortProperty type de sort value
     * @param sortValue type de sort value
     * @return une liste de T elts triÃ© par sortProperty pour sortValue
     */
    public <E> List<T> getNonBy(String sortProperty, E sortValue);

    /**
     *
     * @param <E> type de sort value
     * @param sortProperty type de sort value
     * @param sortValue type de sort value
     * @param sortAsc type de sort value
     * @return une liste de T elts triÃ© par sortProperty like sortValue
     */
    <E> List<T> getAll(String sortProperty, E sortValue, boolean sortAsc);

    List<T> getAll(int first, int count, String sortProperty, boolean sortAsc);

    T saveOne(final T t);

    T updateOne(final T t);

    boolean deleteOne(final PK id);

    boolean deleteOne(final T t);

    void deleteAll();

    List<T> executeQuery(Query query);

    int executeUpdate(Query query);

    EntityManager getEntityManager();

    public boolean deleteRealOne(PK id);

    boolean deleteRealOne(final T t);
    
    public T find(PK id);
    
}
