/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brendev.shopapp.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Administrateur
 */
@Embeddable
public class ProfilUtilisateurId implements Serializable{
    
    @Column(name = "utilisateur",insertable = false,updatable = false)
    private Long utilisateur;
    
    @Column(name = "profil",insertable = false,updatable = false)
    private Long profil;

    public ProfilUtilisateurId() {
    }

    public Long getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Long utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Long getProfil() {
        return profil;
    }

    public void setProfil(Long profil) {
        this.profil = profil;
    }

    @Override
    public String toString() {
        return "ProfilUtilisateurId{" + "utilisateur=" + utilisateur + ", profil=" + profil + '}';
    }
    
    
}
