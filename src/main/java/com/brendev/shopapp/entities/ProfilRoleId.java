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
 * @author Brendev
 */
@Embeddable
public class ProfilRoleId implements Serializable{
    
    @Column(name = "profil",insertable = false,updatable = false)
    private Long profil;
    
    @Column(name = "role",insertable = false,updatable = false)
    private Long role;

    public ProfilRoleId() {
    }

    public Long getProfil() {
        return profil;
    }

    public void setProfil(Long profil) {
        this.profil = profil;
    }

    public Long getRole() {
        return role;
    }

    public void setRole(Long role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "ProfilRoleId{" + "profil=" + profil + ", role=" + role + '}';
    }

   
  
}
