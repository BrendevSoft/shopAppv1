/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brendev.shopapp.web;

import com.brendev.shopapp.entities.ProfilRole;
import com.brendev.shopapp.entities.ProfilRoleId;
import com.brendev.shopapp.entities.Role;
import com.brendev.shopapp.entities.Utilisateur;
import com.brendev.shopapp.services.ProfilRoleServiceBeanLocal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author Brendev
 */
@Named(value = "profilRoleBean")
@RequestScoped
public class ProfilRoleBean {

    private ProfilRole personnelRole;
    private List<ProfilRole> personnelRoles;
    private Role role;
    private Utilisateur utilisateur;
    
    @EJB
    private ProfilRoleServiceBeanLocal prsbl;
    
    public ProfilRoleBean() {
        this.utilisateur = new Utilisateur();
        this.role = new Role();
        this.personnelRole = new ProfilRole();
    }
      
    public void getObject(ProfilRoleId id){
        this.prsbl.find(id);
    }

    public ProfilRole getPersonnelRole() {
        return personnelRole;
    }

    public void setPersonnelRole(ProfilRole personnelRole) {
        this.personnelRole = personnelRole;
    }

    public List<ProfilRole> getPersonnelRoles() {
        personnelRoles = new ArrayList<>();
        personnelRoles = this.prsbl.getAll();
        return personnelRoles;
    }

    public void setPersonnelRoles(List<ProfilRole> personnelRoles) {
        this.personnelRoles = personnelRoles;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }


    public ProfilRoleServiceBeanLocal getPrsbl() {
        return prsbl;
    }

    public void setPrsbl(ProfilRoleServiceBeanLocal prsbl) {
        this.prsbl = prsbl;
    }

}