/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brendev.shopapp.services.core;

import com.brendev.shopapp.dao.core.BaseDaoBeanLocal;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author NOAMESSI
 * @param <T>
 * @param <PK>
 */
public abstract class BaseServiceBean<T, PK extends java.io.Serializable> implements BaseServiceBeanLocal<T, PK>{
    
    protected abstract BaseDaoBeanLocal<T, PK> getDao();

    public BaseServiceBean() {
    }
    
    @Override
    public synchronized T getOne(PK id) {
        return getDao().getOne(id);
    }
    
    @Override
    public synchronized T find(PK id) {
        return getDao().find(id);
    }

    @Override
    public synchronized Long count() {
        return this.getDao().count();
    }

    @Override
    public synchronized List<T> getAll() {
        return this.getDao().getAll();
    }

    @Override
    public synchronized List<T> getAll(String sortProperty) {
        return this.getDao().getAll(sortProperty, true);
    }

    /**
     *
     * @param sortProperty
     * @param sortAsc
     * @return
     */
    @Override
    public synchronized List<T> getAll(String sortProperty, boolean sortAsc) {
        return this.getDao().getAll(sortProperty, sortAsc);
    }

    @Override
    public synchronized <E> List<T> getAll(String sortProperty, E sortValue) {
        return this.getDao().getAll(sortProperty, sortValue);
    }

    @Override
    public synchronized <E> List<T> getAll(String sortProperty, E sortValue, boolean sortAsc) {
        return this.getDao().getAll(sortProperty, sortValue, sortAsc);
    }

    @Override
    public synchronized <E> List<T> getBy(String sortProperty, E sortValue) {
        return this.getDao().getBy(sortProperty, sortValue);
    }

    @Override
    public synchronized <E> T getOneBy(String sortProperty, E sortValue) {
        return this.getDao().getOneBy(sortProperty, sortValue);
    }

    @Override
    public synchronized <E, F> List<T> getBy(String sortProperty, String andSortProperty, E sortValue, F andSortValue) {
        return this.getDao().getBy(sortProperty, sortValue);
    }

    @Override
    public synchronized <E> List<T> getNonBy(String sortProperty, E sortValue) {
        return this.getDao().getNonBy(sortProperty, sortValue);
    }

    @Override
    public synchronized List<T> getAll(int first, int count, String sortProperty, boolean sortAsc) {
        return this.getDao().getAll(first, count, sortProperty, sortAsc);
    }

    @Override
    public synchronized T saveOne(T t) {
        return this.getDao().saveOne(t);
    }

    @Override
    public synchronized T updateOne(T t) {
        return this.getDao().updateOne(t);
    }

    @Override
    public synchronized void deleteOne(PK id) {
        this.getDao().deleteOne(id);
    }

    @Override
    public synchronized void deleteOne(T t) {
        this.getDao().deleteOne(t);
    }

    @Override
    public synchronized void deleteRange(PK[] pks) {
        for (int i = 0; i < pks.length; i++) {
            this.getDao().deleteOne(pks[i]);
        }
    }

    @Override
    public synchronized void deleteRange(Collection<T> ts) {
        this.deleteRange(ts.iterator());
    }

    @Override
    public synchronized void deleteRange(Iterator<T> ts) {
        while (ts.hasNext()) {
            this.getDao().deleteOne(ts.next());
        }
    }

    @Override
    public synchronized Collection<T> saveRange(Collection<T> ts) {
        return this.saveRange(ts.iterator());
    }

    @Override
    public synchronized Collection<T> saveRange(Iterator<T> ts) {
        Collection<T> ts2 = new LinkedList<T>();
        while (ts.hasNext()) {
            ts2.add(this.getDao().saveOne(ts.next()));
        }
        return ts2;
    }

    @Override
    public synchronized Collection<T> updateRange(Collection<T> ts) {
        return this.updateRange(ts.iterator());
    }

    @Override
    public synchronized Collection<T> updateRange(Iterator<T> ts) {
        Collection<T> ts2 = new LinkedList<T>();
        while (ts.hasNext()) {
            ts2.add(this.getDao().updateOne(ts.next()));
        }
        return ts2;
    }

    @Override
    public synchronized void deleteAll() {
        this.getDao().deleteAll();
    }

    @Override
    public boolean exist(PK pk) {
        return this.getDao().getOne(pk) != null;
    }

    @Override
    public boolean deleteRealOne(PK id) {
        return this.getDao().deleteRealOne(id);
    }
    
}
