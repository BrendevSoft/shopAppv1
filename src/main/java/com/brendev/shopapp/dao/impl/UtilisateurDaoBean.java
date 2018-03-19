/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brendev.shopapp.dao.impl;

import com.brendev.shopapp.dao.UtilisateurDaoBeanLocal;
import com.brendev.shopapp.dao.core.BaseDaoBean;
import com.brendev.shopapp.entities.Utilisateur;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Administrateur
 */
@Stateless
public class UtilisateurDaoBean extends BaseDaoBean<Utilisateur, Long>implements UtilisateurDaoBeanLocal {

    public UtilisateurDaoBean() {
    }
    
   @Override
   public Class<Utilisateur> getType(){
       return Utilisateur.class;
   }
   
   @Override
    public List<Utilisateur> getUtilisateursProfil() {
        return getEntityManager()
                .createQuery("SELECT p FROM Utilisateur p WHERE p.profil IS NULL")
                .getResultList();
    }


    @Override
    public List<Utilisateur> getUtilisateursNonProfil() {
        return getEntityManager()
                .createQuery("SELECT p FROM Utilisateur p WHERE p.profil IS NOT NULL")
                .getResultList();
    }
    
}
