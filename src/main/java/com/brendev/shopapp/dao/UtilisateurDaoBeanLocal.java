/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brendev.shopapp.dao;

import com.brendev.shopapp.dao.core.BaseDaoBeanLocal;
import com.brendev.shopapp.entities.Utilisateur;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Administrateur
 */
@Local
public interface UtilisateurDaoBeanLocal extends BaseDaoBeanLocal<Utilisateur, Long>{
    
    public List<Utilisateur> getUtilisateursProfil();

    public List<Utilisateur> getUtilisateursNonProfil();

}
