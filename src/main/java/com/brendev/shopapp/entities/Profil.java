/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brendev.shopapp.entities;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Brendev
 */
@Entity
@Table(name = "profil")
@XmlRootElement
public class Profil extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nom")
    private String nom = " ";

    @Column(name = "description")
    private String description = " ";

    @OneToMany(mappedBy = "profil", fetch = FetchType.LAZY)
    private Set<ProfilRole> profilRoles = new HashSet<ProfilRole>();

    @OneToMany(mappedBy = "profil", fetch = FetchType.LAZY)
    private Set<ProfilUtilisateur> profilUtilisateurs = new HashSet<ProfilUtilisateur>();

    public Profil() {
    }

    public void detruire() {

    }

    public Profil(String nom, String description) {
        this.nom = nom;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @XmlTransient
    public Set<ProfilRole> getProfilRoles() {
        return profilRoles;
    }

    public void setProfilRoles(Set<ProfilRole> profilRoles) {
        this.profilRoles = profilRoles;
    }

    @XmlTransient
    public Set<ProfilUtilisateur> getProfilUtilisateurs() {
        return profilUtilisateurs;
    }

    public void setProfilUtilisateurs(Set<ProfilUtilisateur> profilUtilisateurs) {
        this.profilUtilisateurs = profilUtilisateurs;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Profil other = (Profil) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Profil{" + "id=" + id + ", nom=" + nom + ", description=" + description + ", profilRoles=" + profilRoles + '}';
    }

}
