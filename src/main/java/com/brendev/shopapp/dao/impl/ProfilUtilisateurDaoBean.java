/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brendev.shopapp.dao.impl;

import com.brendev.shopapp.dao.ProfilUtilisateurDaoBeanLocal;
import com.brendev.shopapp.dao.core.BaseDaoBean;
import com.brendev.shopapp.entities.ProfilUtilisateur;
import com.brendev.shopapp.entities.ProfilUtilisateurId;
import com.brendev.shopapp.entities.Utilisateur;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Administrateur
 */
@Stateless
public class ProfilUtilisateurDaoBean extends BaseDaoBean<ProfilUtilisateur, ProfilUtilisateurId>implements ProfilUtilisateurDaoBeanLocal {

    public ProfilUtilisateurDaoBean() {
    }

    @Override
    public Class<ProfilUtilisateur> getType(){
        return ProfilUtilisateur.class;
    }
    
    
    @Override
    public List<Utilisateur> getUtilisateursProfil() {
        return getEntityManager()
                .createQuery("SELECT p FROM Utilisateur p WHERE p NOT IN (SELECT pi.utilisateur FROM ProfilUtilisateur pi WHERE pi.dateRevocation IS NULL)")
                .getResultList();
    }
    
    @Override
     public List<Utilisateur> getUtilisateursProfile() {
        return getEntityManager()
                .createQuery("SELECT p FROM Utilisateur p,ProfilUtilisateur pi WHERE pi.utilisateur = p AND pi.dateRevocation IS NOT NULL")
                .getResultList();
    }


    
    @Override
    public List<Utilisateur> getUtilisateursNonProfil() {
        return getEntityManager()
                .createQuery("SELECT p FROM Utilisateur p WHERE p IN (SELECT pi.utilisateur FROM ProfilUtilisateur pi WHERE pi.dateRevocation IS NULL AND pi.utilisateur = p)")
                .getResultList();
    }
    

    @Override
    public List<Utilisateur> getUtilisateursNonProfile() {
        return getEntityManager()
                .createQuery("SELECT p FROM Utilisateur p,ProfilUtilisateur pi WHERE pi.utilisateur = p AND pi.dateRevocation IS NOT NULL")
                .getResultList();
    }
}
