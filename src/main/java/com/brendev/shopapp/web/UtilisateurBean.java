/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brendev.shopapp.web;

import com.brendev.shopapp.entities.Profil;
import com.brendev.shopapp.entities.ProfilUtilisateur;
import com.brendev.shopapp.entities.ProfilUtilisateurId;
import com.brendev.shopapp.entities.Utilisateur;
import com.brendev.shopapp.services.ProfilServiceBeanLocal;
import com.brendev.shopapp.services.ProfilUtilisateurServiceBeanLocal;
import com.brendev.shopapp.services.UtilisateurServiceBeanLocal;
import com.brendev.shopapp.utils.constantes.Constante;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import org.primefaces.event.FlowEvent;

/**
 *
 * @author Administrateur
 */
@Named(value = "utilisateurBean")
@ViewScoped
public class UtilisateurBean implements Serializable {

    private Utilisateur utilisateur;
    private ProfilUtilisateur profilUtilisateur;
    private List<ProfilUtilisateur> profilUtilisateurs;
    private List<Utilisateur> utilisateurs;
    private Profil profil;
    private List<Profil> profils;
    private boolean skip;
    private String space = "  ";
    private List<String> situation;
    private List<String> pays;
    private List<Utilisateur> utilisateurs1;
    private Date date = new Date();
    private Utilisateur u;

    @EJB
    private UtilisateurServiceBeanLocal usbl;
    @EJB
    private ProfilServiceBeanLocal psbl1;
    @EJB
    private ProfilUtilisateurServiceBeanLocal pusbl;

    /**
     * Creates a new instance of UtilisateurBean
     */
    public UtilisateurBean() {
        this.utilisateur = new Utilisateur();
        this.utilisateurs = new ArrayList<>();
        this.utilisateurs1 = new ArrayList<>();
        this.profil = new Profil();
        this.profils = new ArrayList<>();
        this.situation = new ArrayList<>();
        this.pays = new ArrayList<>();
        this.u = new Utilisateur();
        this.profilUtilisateur = new ProfilUtilisateur();
        this.profilUtilisateurs = new ArrayList<>();
    }

    public void nouveau(ActionEvent actionEvent) {
        this.utilisateur = new Utilisateur();
    }

    public void cancel(ActionEvent actionEvent) {
        this.utilisateur = new Utilisateur();
    }

    public Date max() {
        Calendar ca = Calendar.getInstance();
        ca.add(Calendar.YEAR, -15);
        return ca.getTime();
    }

    public void getObject(Long id) {
        this.utilisateur = this.usbl.find(id);
    }
    
    public void getObject(ProfilUtilisateurId id){
        this.profilUtilisateur = this.pusbl.find(id);
        this.utilisateur = this.profilUtilisateur.getUtilisateur();
        this.profil = this.profilUtilisateur.getProfil();
    }

    public String onFlowProcess(FlowEvent event) {
        if (skip) {
            skip = false;   //reset in case user goes back
            return "confirm";
        } else {
            return event.getNewStep();
        }
    }

    public void associerProfil() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            for (Utilisateur utilisateur1 : utilisateurs1) {
                this.profilUtilisateur.setProfil(profil);
                this.profilUtilisateur.setUtilisateur(utilisateur1);
                this.profilUtilisateur.setDateAffectation(date);
                this.pusbl.saveOne(profilUtilisateur);
            }
            context.addMessage(null, new FacesMessage(Constante.ENREGISTREMENT_REUSSIT));
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(Constante.ENREGISTREMENT_ECHOUE));
        }
    }

    public List<Utilisateur> utilisateurIsProfil() {
        List<Utilisateur> us = this.usbl.getAll();
        List<Utilisateur> us1 = new ArrayList<>();
        for (Utilisateur us11 : us) {
           /* if (us11.getProfil() != null) {
                us1.add(us11);
            }*/
        }
        return us1;
    }
   

    public List<Utilisateur> utilisateursNonProfil() {
        return this.pusbl.getUtilisateursProfil();
    }

    public void utilisateursSelectProfil() {
        for (Utilisateur utilisateur1 : utilisateursNonProfil()) {
            utilisateurs1.add(utilisateur1);
        }
    }

    public void activer(){
         System.out.println("testt");
    }
    
    public void activerUtilisateur(Long u) {
       FacesContext context = FacesContext.getCurrentInstance();
        try {
            List<Utilisateur> us = this.usbl.getBy("id", u);
            Utilisateur u1 = new Utilisateur();
            u1 = us.get(0);
            u1.setActif(true);
            this.usbl.updateOne(u1);
            context.addMessage(null, new FacesMessage(u1.getLogin().concat(space) + "activé"));
        } catch (Exception e) {
            e.getMessage();
            context.addMessage(null, new FacesMessage(Constante.DESACTIVATION_ECHOU));
        }
    }

    public void desactiverUtilisateur(Long u) {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            List<Utilisateur> us = this.usbl.getBy("id", u);
            Utilisateur u1 = new Utilisateur();
            u1 = us.get(0);
            u1.setActif(false);
            this.usbl.updateOne(u1);
            context.addMessage(null, new FacesMessage(u1.getLogin().concat(space) + "désactivé"));
        } catch (Exception e) {
            e.getMessage();
            context.addMessage(null, new FacesMessage(Constante.DESACTIVATION_ECHOU));
        }
    }

    public List<Utilisateur> utilisateurActif() {
        List<Utilisateur> us = this.pusbl.getUtilisateursNonProfil();
        List<Utilisateur> us1 = new ArrayList<>();
        for (Utilisateur us11 : us) {
            if (us11.getActif() == true) {
                us1.add(us11);
            }
        }
        return us1;
    }

    public List<Utilisateur> utilisateurInactif() {
        List<Utilisateur> us = this.pusbl.getUtilisateursNonProfil();
        List<Utilisateur> us1 = new ArrayList<>();
        for (Utilisateur us11 : us) {
            if (us11.getActif() == false) {
                us1.add(us11);
            }
        }
        return us1;
    }

    public void modifierPersonnelProfil() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
           // this.utilisateur.setProfil(profil);
            this.usbl.updateOne(utilisateur);
            context.addMessage(null, new FacesMessage(Constante.MODIFICATION_REUSSIT));
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public boolean checkIntConnection() {
        boolean status = false;
        Socket sock = new Socket();
        InetSocketAddress address = new InetSocketAddress("www.google.com", 80);
        try {
            sock.connect(address, 3000);
            if (sock.isConnected()) {
                status = true;
            }
        } catch (Exception e) {

        } finally {
            try {
                sock.close();
            } catch (Exception e) {

            }
        }

        return status;
    }

    public Profil getProfil() {
        return profil;
    }

    public void setProfil(Profil profil) {
        this.profil = profil;
    }

    public List<Profil> getProfils() {
        profils = this.psbl1.getAll();
        return profils;
    }

    public void setProfils(List<Profil> profils) {
        this.profils = profils;
    }

    public ProfilServiceBeanLocal getPsbl1() {
        return psbl1;
    }

    public void setPsbl1(ProfilServiceBeanLocal psbl1) {
        this.psbl1 = psbl1;
    }

    public boolean isSkip() {
        return skip;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    public String getSpace() {
        return space;
    }

    public void setSpace(String space) {
        this.space = space;
    }

    public List<String> getSituation() {
        situation.add("Célibataire");
        situation.add("fiancé(e)");
        situation.add("marié(e)");
        situation.add("divorcé(e)");
        situation.add("veuf(ve)");
        return situation;
    }

    public void setSituation(List<String> situation) {
        this.situation = situation;
    }

    public List<String> getPays() {
        pays.add("Togolaise");
        pays.add("Béninoise");
        return pays;
    }

    public void setPays(List<String> pays) {
        this.pays = pays;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public List<Utilisateur> getUtilisateurs() {
        return utilisateurs;
    }

    public void setUtilisateurs(List<Utilisateur> utilisateurs) {
        this.utilisateurs = utilisateurs;
    }

    public List<Utilisateur> getUtilisateurs1() {
        return utilisateurs1;
    }

    public void setUtilisateurs1(List<Utilisateur> utilisateurs1) {
        this.utilisateurs1 = utilisateurs1;
    }

    public UtilisateurServiceBeanLocal getUsbl() {
        return usbl;
    }

    public void setUsbl(UtilisateurServiceBeanLocal usbl) {
        this.usbl = usbl;
    }

    public ProfilUtilisateur getProfilUtilisateur() {
        return profilUtilisateur;
    }

    public void setProfilUtilisateur(ProfilUtilisateur profilUtilisateur) {
        this.profilUtilisateur = profilUtilisateur;
    }

    public List<ProfilUtilisateur> getProfilUtilisateurs() {
        this.profilUtilisateurs = this.pusbl.getAll();
        return profilUtilisateurs;
    }

    public void setProfilUtilisateurs(List<ProfilUtilisateur> profilUtilisateurs) {
        this.profilUtilisateurs = profilUtilisateurs;
    }

    public Utilisateur getU() {
        return u;
    }

    public void setU(Utilisateur u) {
        this.u = u;
    }

    public ProfilUtilisateurServiceBeanLocal getPusbl() {
        return pusbl;
    }

    public void setPusbl(ProfilUtilisateurServiceBeanLocal pusbl) {
        this.pusbl = pusbl;
    }

    
}
