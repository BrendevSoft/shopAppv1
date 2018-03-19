/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brendev.shopapp.dao;


import com.brendev.shopapp.dao.core.BaseDaoBeanLocal;
import com.brendev.shopapp.entities.ProfilUtilisateur;
import com.brendev.shopapp.entities.ProfilUtilisateurId;
import com.brendev.shopapp.entities.Utilisateur;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Administrateur
 */
@Local
public interface ProfilUtilisateurDaoBeanLocal extends BaseDaoBeanLocal<ProfilUtilisateur, ProfilUtilisateurId> {

    public List<Utilisateur> getUtilisateursProfil();

    public List<Utilisateur> getUtilisateursProfile();

    public List<Utilisateur> getUtilisateursNonProfil();

    public List<Utilisateur> getUtilisateursNonProfile();
}
