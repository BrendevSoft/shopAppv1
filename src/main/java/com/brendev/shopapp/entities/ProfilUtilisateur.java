/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brendev.shopapp.entities;

import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Administrateur
 */
@Entity
@Table(name = "profilUtilisateur")
@XmlRootElement
public class ProfilUtilisateur extends BaseEntity{
    @EmbeddedId
    ProfilUtilisateurId id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utilisateur",insertable = true,updatable = true)
    private Utilisateur utilisateur;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profil",insertable = true,updatable = true)
    private Profil profil;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "dateAffectation")
    private Date dateAffectation;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "dateRevocation")
    private Date dateRevocation;
    
    @Column(name = "motifRevocation")
    private String motifRevocation;

    public ProfilUtilisateur() {
    }

    public ProfilUtilisateurId getId() {
        return id;
    }

    public void setId(ProfilUtilisateurId id) {
        this.id = id;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Profil getProfil() {
        return profil;
    }

    public void setProfil(Profil profil) {
        this.profil = profil;
    }

    public Date getDateAffectation() {
        return dateAffectation;
    }

    public void setDateAffectation(Date dateAffectation) {
        this.dateAffectation = dateAffectation;
    }

    public Date getDateRevocation() {
        return dateRevocation;
    }

    public void setDateRevocation(Date dateRevocation) {
        this.dateRevocation = dateRevocation;
    }

    public String getMotifRevocation() {
        return motifRevocation;
    }

    public void setMotifRevocation(String motifRevocation) {
        this.motifRevocation = motifRevocation;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.id);
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
        final ProfilUtilisateur other = (ProfilUtilisateur) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ProfilUtilisateur{" + "id=" + id + ", utilisateur=" + utilisateur + ", profil=" + profil + ", dateAffectation=" + dateAffectation + ", dateRevocation=" + dateRevocation + ", motifRevocation=" + motifRevocation + '}';
    }
    
    
}
