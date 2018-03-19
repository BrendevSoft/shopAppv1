/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brendev.shopapp.dao.impl;



import com.brendev.shopapp.dao.ProfilDaoBeanLocal;
import javax.ejb.Stateless;
import com.brendev.shopapp.dao.core.BaseDaoBean;
import com.brendev.shopapp.entities.Profil;

/**
 *
 * @author NOAMESSI
 */
@Stateless
public class ProfilDaoBean extends BaseDaoBean<Profil, Long>implements ProfilDaoBeanLocal {

    public ProfilDaoBean() {
    }

    @Override
    public Class<Profil> getType(){
        return Profil.class;
    }
}
