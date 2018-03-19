/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brendev.shopapp.services;

import com.brendev.shopapp.entities.ProfilUtilisateur;
import com.brendev.shopapp.entities.ProfilUtilisateurId;
import com.brendev.shopapp.entities.Utilisateur;
import com.brendev.shopapp.services.core.BaseServiceBeanLocal;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Administrateur
 */
@Local
public interface ProfilUtilisateurServiceBeanLocal extends BaseServiceBeanLocal<ProfilUtilisateur, ProfilUtilisateurId> {

    public List<Utilisateur> getUtilisateursProfil();

    public List<Utilisateur> getUtilisateursNonProfil();

    public List<Utilisateur> getUtilisateursProfile();

    public List<Utilisateur> getUtilisateursNonProfile();
}
