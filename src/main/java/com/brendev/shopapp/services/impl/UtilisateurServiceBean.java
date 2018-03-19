/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brendev.shopapp.services.impl;

import com.brendev.shopapp.dao.UtilisateurDaoBeanLocal;
import com.brendev.shopapp.dao.core.BaseDaoBeanLocal;
import com.brendev.shopapp.entities.Utilisateur;
import com.brendev.shopapp.services.UtilisateurServiceBeanLocal;
import com.brendev.shopapp.services.core.BaseServiceBean;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Administrateur
 */
@Stateless
public class UtilisateurServiceBean extends BaseServiceBean<Utilisateur, Long>implements UtilisateurServiceBeanLocal {
    
    @EJB
    private UtilisateurDaoBeanLocal udbl;
    
    @Override
    protected BaseDaoBeanLocal<Utilisateur, Long> getDao(){
        return udbl;
    }
    
     @Override
     public List<Utilisateur> getUtilisateursProfil(){
         return this.udbl.getUtilisateursProfil();
     }
     
    @Override
     public List<Utilisateur> getUtilisateursNonProfil(){
         return this.udbl.getUtilisateursNonProfil();
     }
}
