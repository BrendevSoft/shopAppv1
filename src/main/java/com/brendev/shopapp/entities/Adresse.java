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
public class Adresse implements Serializable{
    @Column(name = "pays")
    private String pays = " ";
    
    @Column(name = "ville")
    private String ville = " ";
            
    @Column(name = "region")
    private String region = " ";   
    
    @Column(name = "prefecture")
    private String prefecture = " ";   
    
    @Column(name = "quartier")
    private String quartier = " ";
    
    @Column(name = "rue")
    private String rue = " ";
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "telephone")
    private String telephone = " ";
    
    @Column(name = "telephone1")
    private String telephone1 = " ";
    
    @Column(name = "boitePostal")
    private String boitePostal = " ";

    public Adresse() {
    }
    
    public void detruire(){
        
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getQuartier() {
        return quartier;
    }

    public void setQuartier(String quartier) {
        this.quartier = quartier;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getTelephone1() {
        return telephone1;
    }

    public void setTelephone1(String telephone1) {
        this.telephone1 = telephone1;
    }

    public String getBoitePostal() {
        return boitePostal;
    }

    public void setBoitePostal(String boitePostal) {
        this.boitePostal = boitePostal;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPrefecture() {
        return prefecture;
    }

    public void setPrefecture(String prefecture) {
        this.prefecture = prefecture;
    }
   
    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Adresse other = (Adresse) obj;
        return true;
    }

    @Override
    public String toString() {
        return "Adresse{" + "pays=" + pays + ", ville=" + ville + ", quartier=" + quartier + ", rue=" + rue + ", email=" + email + ", telephone=" + telephone + ", telephone1=" + telephone1 + ", boitePostal=" + boitePostal + '}';
    }
    
}
