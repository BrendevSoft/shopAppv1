/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brendev.shopapp.web;

import com.brendev.shopapp.entities.Profil;
import com.brendev.shopapp.services.ProfilServiceBeanLocal;
import com.brendev.shopapp.utils.constantes.Constante;
import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author NOAMESSI
 */
@Named(value = "profilBean")
@ViewScoped
public class ProfillBean implements Serializable {

    private Profil profil;
    private List<Profil> profils;
    @EJB
    private ProfilServiceBeanLocal psbl;

    /**
     * Creates a new instance of CategoriePersonnelBean
     */
    public ProfillBean() {
        this.profils = new ArrayList<>();
        this.profil = new Profil();
    }

    public void cancel(ActionEvent actionEvent) {
        this.profil = new Profil();
    }

    public void save(ActionEvent actionEvent) {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            if (this.profil.getId() == null) {
                this.psbl.saveOne(profil);
                context.addMessage(null, new FacesMessage(Constante.ENREGISTREMENT_REUSSIT));
            } else {
                this.psbl.updateOne(profil);
                context.addMessage(null, new FacesMessage(Constante.MODIFICATION_REUSSIT));
            }
            this.profil = new Profil();
            this.profil.detruire();
        } catch (Exception e) {
            e.getMessage();
            context.addMessage(null, new FacesMessage(Constante.ENREGISTREMENT_ECHOUE));
        }

    }

    public void getObject(Long id) {
        this.profil = this.psbl.find(id);
    }

    public Profil getProfil() {
        return profil;
    }

    public void setProfil(Profil profil) {
        this.profil = profil;
    }

    public List<Profil> getProfils() {
        profils = this.psbl.getAll();
        return profils;
    }

    public void setProfils(List<Profil> profils) {
        this.profils = profils;
    }

    public ProfilServiceBeanLocal getPsbl() {
        return psbl;
    }

    public void setPsbl(ProfilServiceBeanLocal psbl) {
        this.psbl = psbl;
    }

   
}
