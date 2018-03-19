/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brendev.shopapp.services.impl;

import com.brendev.shopapp.dao.ProfilUtilisateurDaoBeanLocal;
import com.brendev.shopapp.dao.core.BaseDaoBeanLocal;
import com.brendev.shopapp.entities.ProfilUtilisateur;
import com.brendev.shopapp.entities.ProfilUtilisateurId;
import com.brendev.shopapp.entities.Utilisateur;
import com.brendev.shopapp.services.ProfilUtilisateurServiceBeanLocal;
import com.brendev.shopapp.services.core.BaseServiceBean;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Administrateur
 */
@Stateless
public class ProfilUtilisateurServiceBean extends BaseServiceBean<ProfilUtilisateur, ProfilUtilisateurId> implements ProfilUtilisateurServiceBeanLocal {

    @EJB
    private ProfilUtilisateurDaoBeanLocal pudbl;

    @Override
    protected BaseDaoBeanLocal<ProfilUtilisateur, ProfilUtilisateurId> getDao() {
        return pudbl;
    }

    @Override
    public List<Utilisateur> getUtilisateursProfil() {
        return this.pudbl.getUtilisateursProfil();
    }

    @Override
    public List<Utilisateur> getUtilisateursNonProfil() {
        return this.pudbl.getUtilisateursNonProfil();
    }
    
     @Override
    public List<Utilisateur> getUtilisateursProfile() {
        return this.pudbl.getUtilisateursProfile();
    }

    @Override
    public List<Utilisateur> getUtilisateursNonProfile() {
        return this.pudbl.getUtilisateursNonProfile();
    }
}
