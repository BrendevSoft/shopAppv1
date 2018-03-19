/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brendev.shopapp.entities;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Brendev
 */
@Entity
@Table(name = "profilRole")
@XmlRootElement
public class ProfilRole extends BaseEntity {

    @EmbeddedId
    @Column(name = "id", nullable = false)
    ProfilRoleId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profil", insertable = true, updatable = true)
    private Profil profil;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role", insertable = true, updatable = true)
    private Role role;

    public ProfilRole() {
    }

    public void detruire() {

    }

    public ProfilRole(Profil profil, Role role) {
        this.profil = profil;
        this.role = role;
    }

    public ProfilRoleId getId() {
        return id;
    }

    public void setId(ProfilRoleId id) {
        this.id = id;
    }

    public Profil getProfil() {
        return profil;
    }

    public void setProfil(Profil profil) {
        this.profil = profil;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + Objects.hashCode(this.id);
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
        final ProfilRole other = (ProfilRole) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ProfilRole{" + "id=" + id + ", profil=" + profil + ", role=" + role + '}';
    }

}
