 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brendev.shopapp.web;

import com.brendev.shopapp.entities.Adresse;
import com.brendev.shopapp.entities.Profil;
import com.brendev.shopapp.entities.ProfilRole;
import com.brendev.shopapp.entities.ProfilUtilisateur;
import com.brendev.shopapp.entities.Role;
import com.brendev.shopapp.entities.Utilisateur;
import com.brendev.shopapp.services.ProfilRoleServiceBeanLocal;
import com.brendev.shopapp.services.ProfilServiceBeanLocal;
import com.brendev.shopapp.services.ProfilUtilisateurServiceBeanLocal;
import com.brendev.shopapp.services.RoleServiceBeanLocal;
import com.brendev.shopapp.services.UtilisateurServiceBeanLocal;
import com.brendev.shopapp.shiro.EntityRealm;
import com.brendev.shopapp.utils.constantes.MethodeJournalisation;
import com.brendev.shopapp.utils.transaction.TransactionManager;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.omnifaces.util.Faces;
import org.primefaces.context.RequestContext;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 *
 * @author NOAMESSI
 */
@Named(value = "loginBean")
@SessionScoped
public class LoginBean implements Serializable {

    private MethodeJournalisation journalisation;

    @EJB
    private RoleServiceBeanLocal rsl;

    @EJB
    private ProfilServiceBeanLocal psbl;

    @EJB
    private UtilisateurServiceBeanLocal usbl;

    @EJB
    private ProfilUtilisateurServiceBeanLocal pusbl;
    @EJB//HUM Tu m'as vraiment dérangé.plus de 24h avant de me rappeller de te mettre.hum
    private ProfilRoleServiceBeanLocal prsbl;

    private Date date = new Date();
    private String username;
    private String password;
    private String newPass;
    private String retapPass;
    private String lastPass;
    private String per = "";
    private String question;
    private String reponse;
    private String creerCabinet, modifierCabinet, creerPersonnel, modifierPersonnel, creerClient, modifierClient,
            creerCycle, modifierCycle, creerExercice, modifierExercice, creerTypeMision, modifierTypeMision,
            creerClasseur, modifierClasseur, creerDossier, modifierDossier, creerContrat, modifierContrat,
            mesEquipes, creerMission, modifierMission, creerEquipe, modifierEquipe,
            affecterMembre, validerMembre, creerClasseurClient, modifierClasseurClient, creerDossierClient,
            modifierDossierClient, creerFichier, modifierFichier, poste, profil,
            creerPoste, modifierPoste, creerProfil, modifierProfil, associerPoste, associerProfil, associerRole,
            activerCompte, desactiverCompte, administration, mission, rapport, etat, securite, personnel, cabinet,
            client, cycle, exercice, typeMission, classeur, dossier, contrat, missione, equipe, classeurClient, dossierClient, fichier;
    private Utilisateur pers;
    private Utilisateur perse;
    private boolean remember = false;
    private boolean admin;

    /**
     * Creates a new instance of LoginBean
     */
    public LoginBean() {
        pers = new Utilisateur();
        perse = new Utilisateur();
        journalisation = new MethodeJournalisation();
    }

    @PostConstruct
    public void init() {
        List<Role> all = rsl.getAll();
        if (all.isEmpty()) {;
            this.rsl.saveOne(new Role("Créer poste"));
            this.rsl.saveOne(new Role("Modifier poste"));
            this.rsl.saveOne(new Role("Créer profil"));
            this.rsl.saveOne(new Role("Modifier profil"));
            this.rsl.saveOne(new Role("Associer poste"));
            this.rsl.saveOne(new Role("Associer profil"));
            this.rsl.saveOne(new Role("Associer role"));
            this.rsl.saveOne(new Role("Activer compte"));
            this.rsl.saveOne(new Role("Désactiver compte"));
        }

        List<Profil> alle = psbl.getAll();
        if (alle.isEmpty()) {
            this.psbl.saveOne(new Profil("Administrateur", "administrer"));
        }

        List<Profil> profils = psbl.getBy("nom", "Super");
        UserTransaction tx = TransactionManager.getUserTransaction();
        if (profils.isEmpty()) {
            try {
                tx.begin();
                this.psbl.saveOne(new Profil("Super", ""));
                List<Role> roles = this.rsl.getAll();
                for (Role role : roles) {
                    ProfilRole pr = new ProfilRole();
                    pr.setRole(role);
                    pr.setProfil(psbl.getBy("nom", "Super").get(0));
                    prsbl.saveOne(pr);
                }

                Utilisateur u = new Utilisateur();
                Adresse a = new Adresse();
                u.setLogin("Brenda");
                u.setQuestion("Brenda");
                u.setReponse("Brenda");
                u.setNom("Brenda");
                u.setPrenom("Brenda");
                a.setEmail("bobo@bibo.vino");
                u.setAdresse(a);
                u.setMotPasse(new Sha256Hash("@frica").toHex());
                u.setActif(true);
                usbl.saveOne(u);

                ProfilUtilisateur pu = new ProfilUtilisateur();
                pu.setUtilisateur(usbl.getBy("login", "Brenda").get(0));
                pu.setDateAffectation(date);
                pu.setProfil(psbl.getBy("nom", "Super").get(0));
                pusbl.saveOne(pu);
                tx.commit();
            } catch (Exception e) {
                try {
                    tx.rollback();
                } catch (IllegalStateException ex) {
                    Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SecurityException ex) {
                    Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SystemException ex) {
                    Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    public void login() throws IOException {
        try {
            System.out.println("user=" + username);
            System.out.println("ps=" + password);
            pers = usbl.getOneBy("login", username);
            if (pers != null) {
                if (pers.getActif() == false) {
                    RequestContext context = RequestContext.getCurrentInstance();
                    context.execute("PF('error').show();");
                    username = "";
                    return;
                }
            }

            if (pers != null) {
                boolean test = new Sha256Hash("admin").toHex().equals(pers.getMotPasse());
                if (test && pers.getActif() == true) {

                    RequestContext context = RequestContext.getCurrentInstance();
                    context.execute("PF('dialogpasse').show();");
                    context.execute("PF('dialogRecup').hide();");
                    return;
                }

            }

            UsernamePasswordToken token = new UsernamePasswordToken(username.trim(), password.trim());
            //”Remember Me” built-in, just do this:
            token.setRememberMe(false);
            //With most of Shiro, you'll always want to make sure you're working with 
            //the currently executing user, referred to as the subject
            SecurityUtils.getSubject().login(token);
            //Authenticate the subject by passing
            //the user name and password token
            //into the login method
            // currentUser.login(token);
//            Long total = this.psl.count();
//            List<Role> roleProf = new ArrayList<>();
//            if (total > 0) {
//                if (!username.equalsIgnoreCase("admin")) {
//                    pers = EntityRealm.getUser();
//                    if (pers != null) {
//                        roleProf = prsbl.getProfilRoles(pers.getProfil());
//                        System.out.println(roleProf);
//                    }
//                }
//            }

            List<Role> roles = this.rsl.getAll();
            Boolean avoir = false;
            Subject subject = EntityRealm.getSubject();
            for (Role role : roles) {
                if (subject.hasRole(role.getNom())) {
                    avoir = true;
                }
            }

            if (!username.equalsIgnoreCase("admin")) {

                if (subject.hasRole("Créer poste") || subject.hasRole("Modifier poste")
                        || subject.hasRole("Créer profil") || subject.hasRole("Modifier profil")
                        || subject.hasRole("Associer poste") || subject.hasRole("Associer profil")
                        || subject.hasRole("Associer role") || subject.hasRole("Activer compte")
                        || subject.hasRole("Désactiver compte")) {
                    this.securite = "true";
                } else {
                    this.securite = "false";
                }

                if (subject.hasRole("Créer poste") || subject.hasRole("Modifier poste")) {
                    this.poste = "true";
                } else {
                    this.poste = "false";
                }

                if (subject.hasRole("Créer poste")) {
                    this.creerPoste = "true";
                } else {
                    this.creerPoste = "false";
                }

                if (subject.hasRole("Modifier poste")) {
                    this.modifierPoste = "true";
                } else {
                    this.modifierPoste = "false";
                }

                if (subject.hasRole("Créer profil") || subject.hasRole("Modifier profil")) {
                    this.profil = "true";
                } else {
                    this.profil = "false";
                }

                if (subject.hasRole("Créer profil")) {
                    this.creerProfil = "true";
                } else {
                    this.creerProfil = "false";
                }

                if (subject.hasRole("Modifier profil")) {
                    this.modifierProfil = "true";
                } else {
                    this.modifierProfil = "false";
                }

                if (subject.hasRole("Associer poste")) {
                    this.associerPoste = "true";
                } else {
                    this.associerPoste = "false";
                }

                if (subject.hasRole("Associer profil")) {
                    this.associerProfil = "true";
                } else {
                    this.associerProfil = "false";
                }

                if (subject.hasRole("Associer role")) {
                    this.associerRole = "true";
                } else {
                    this.associerRole = "false";
                }

                if (subject.hasRole("Activer compte")) {
                    this.activerCompte = "true";
                } else {
                    this.activerCompte = "false";
                }

                if (subject.hasRole("Désactiver compte")) {
                    this.desactiverCompte = "true";
                } else {
                    this.desactiverCompte = "false";
                }

                if (!avoir) {
                    RequestContext context = RequestContext.getCurrentInstance();
                    context.execute("PF('error').show();");
                    username = "";
                    return;
                }

            }
            journalisation.saveLog4j(LoginBean.class.getName(), org.apache.log4j.Level.INFO, "Journaliser");
            SavedRequest savedRequest = WebUtils.getAndClearSavedRequest(Faces.getRequest());
            Faces.redirect(savedRequest != null ? savedRequest.getRequestUrl() : "index.xhtml");
        } catch (AuthenticationException e) {
            e.printStackTrace();
            FacesMessage mf = new FacesMessage(FacesMessage.SEVERITY_FATAL,
                    "Nom d'utlisateur ou mot de passe incorrect", "");
            FacesContext.getCurrentInstance().addMessage("", mf);
        }
        //return "index";
    }

    public String currentUser() {
        Utilisateur user = EntityRealm.getUser();
        if (user == null) {
            return "Admin";
        }
        return EntityRealm.getUser().getNom();
    }

    public Date sessionTime() {
        return EntityRealm.getSubject().getSession().getStartTimestamp();
    }

    public void logout() {
        try {
            EntityRealm.getSubject().logout();
            Faces.redirect("login.xhtml");
            username = "";
        } catch (IOException ex) {
        }

    }

    /*public void modifierPasse() {
     if (newPass.trim().equals(retapPass.trim())) {
     pers.setMotPasse(new Sha256Hash(newPass.trim()).toHex());
     psl.updateOne(pers);
     FacesMessage mf = new FacesMessage(FacesMessage.SEVERITY_INFO,
     "Mot de passe corriger", "");
     FacesContext.getCurrentInstance().addMessage("erreur_login", mf);
     } else {
     FacesMessage mf = new FacesMessage(FacesMessage.SEVERITY_FATAL,
     "Les mots de passe ne concorde pas", "");
     FacesContext.getCurrentInstance().addMessage("erreur_login", mf);
     }
     }*/
    public void modifierPasse() {
        if (newPass.trim().equals(retapPass.trim())) {
            pers.setMotPasse(new Sha256Hash(newPass.trim()).toHex());
            pers.setQuestion(question);
            pers.setReponse(reponse);
            usbl.updateOne(pers);
            question = "";
            reponse = "";
            RequestContext.getCurrentInstance().execute("PF('dialogpasse').hide();");
            RequestContext.getCurrentInstance().execute("PF('dialogRecup').hide();");
            FacesMessage mf = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Mot de passe corriger", "");
            FacesContext.getCurrentInstance().addMessage("erreur_login", mf);
        } else {
            FacesMessage mf = new FacesMessage(FacesMessage.SEVERITY_FATAL,
                    "Les mots de passe ne concorde pas", "");
            FacesContext.getCurrentInstance().addMessage("erreur_login", mf);
        }
    }

    public void modifierPasse2() {
        if (new Sha256Hash(lastPass).toHex().equals(EntityRealm.getUser().getMotPasse())) {
            if (newPass.trim().equals(retapPass.trim())) {
                if (new Sha256Hash(newPass).toHex().equals(EntityRealm.getUser().getMotPasse())) {
                    FacesMessage mf = new FacesMessage(FacesMessage.SEVERITY_FATAL,
                            "Tapez un mot de passe différent de l'ancien", "");
                    FacesContext.getCurrentInstance().addMessage("erreur_login", mf);
                    newPass = "";
                    lastPass = "";
                    retapPass = "";
                } else {
                    EntityRealm.getUser().setMotPasse(new Sha256Hash(newPass.trim()).toHex());
                    usbl.updateOne(EntityRealm.getUser());
                    FacesMessage mf = new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Mot de passe corrigé", "");
                    FacesContext.getCurrentInstance().addMessage("erreur_login", mf);
                    RequestContext context = RequestContext.getCurrentInstance();
                    context.execute("PF('dialogpasse').hide()");
                }
            } else {
                FacesMessage mf = new FacesMessage(FacesMessage.SEVERITY_FATAL,
                        "Les mots de passe ne concorde pas", "");
                FacesContext.getCurrentInstance().addMessage("erreur_login", mf);
                newPass = "";
                lastPass = "";
                retapPass = "";
            }

        } else {
            FacesMessage mf = new FacesMessage(FacesMessage.SEVERITY_FATAL,
                    "mot de passe incorrect!!!", "");
            FacesContext.getCurrentInstance().addMessage("erreur_login", mf);
            newPass = "";
            lastPass = "";
            retapPass = "";
        }
    }

    public void reinitialiserPasse() {
        Utilisateur pe = this.usbl.getOneBy("login", per);
        if (pe.getActif() == true) {
            if (reponse.equals(pe.getReponse())) {
                pe.setMotPasse(new Sha256Hash("admin").toHex());
                pe.setQuestion(null);
                pe.setReponse(null);
                usbl.updateOne(pe);
                question = "";
                reponse = "";
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("PF('dialogRecup').hide();");
                FacesMessage mf = new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Mot de passe réinitialisé", "");
                FacesContext.getCurrentInstance().addMessage("erreur_login", mf);
            } else {
                FacesMessage mf = new FacesMessage(FacesMessage.SEVERITY_FATAL,
                        "La reponse est fausse", "");
                FacesContext.getCurrentInstance().addMessage("erreur_login", mf);
            }
        }

    }

    public String recupererQuestion() {
        if (!per.equals("")) {
            Utilisateur pe = this.usbl.getOneBy("login", per);
            String quest = "";
            if (pe != null) {
                if (!pe.getMotPasse().equals(new Sha256Hash("admin").toHex())) {
                    if (pe.getActif() == true) {
                        quest = pe.getQuestion();
                        RequestContext context = RequestContext.getCurrentInstance();
                        context.execute("PF('dialogRecup').show();");
                        context.execute("PF('dialogOublie').hide();");
                        return quest;
                    } else {
                        per = "";
                        RequestContext context = RequestContext.getCurrentInstance();
                        context.execute("PF('dialogOublie').hide();");
                        FacesMessage mf = new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                "Votre compte est inactif,contactez l'administrateur", "");
                        FacesContext.getCurrentInstance().addMessage("erreur_login", mf);
                    }
                } else {
                    RequestContext context = RequestContext.getCurrentInstance();
                    context.execute("PF('dialogOublie').hide();");
                    FacesMessage mf = new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Connectez vous à votre compte pour changer votre mot de passe", "");
                    FacesContext.getCurrentInstance().addMessage("erreur_login", mf);
                }
            } else {
                per = "";
                FacesMessage mf = new FacesMessage(FacesMessage.SEVERITY_FATAL,
                        "le login saisi est inconnu", "");
                FacesContext.getCurrentInstance().addMessage("erreur_login", mf);
            }
        }
        return "";
    }

    public RoleServiceBeanLocal getRsl() {
        return rsl;
    }

    public void setRsl(RoleServiceBeanLocal rsl) {
        this.rsl = rsl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }

    public String getRetapPass() {
        return retapPass;
    }

    public void setRetapPass(String retapPass) {
        this.retapPass = retapPass;
    }

    public Utilisateur getPers() {
        return pers;
    }

    public void setPers(Utilisateur pers) {
        this.pers = pers;
    }

    public boolean isRemember() {
        return remember;
    }

    public void setRemember(boolean remember) {
        this.remember = remember;
    }

    public ProfilServiceBeanLocal getPsbl() {
        return psbl;
    }

    public void setPsbl(ProfilServiceBeanLocal psbl) {
        this.psbl = psbl;
    }

    public ProfilRoleServiceBeanLocal getPrsbl() {
        return prsbl;
    }

    public void setPrsbl(ProfilRoleServiceBeanLocal prsbl) {
        this.prsbl = prsbl;
    }

    public String getCreerCabinet() {
        return creerCabinet;
    }

    public void setCreerCabinet(String creerCabinet) {
        this.creerCabinet = creerCabinet;
    }

    public String getModifierCabinet() {
        return modifierCabinet;
    }

    public void setModifierCabinet(String modifierCabinet) {
        this.modifierCabinet = modifierCabinet;
    }

    public String getCreerPersonnel() {
        return creerPersonnel;
    }

    public void setCreerPersonnel(String creerPersonnel) {
        this.creerPersonnel = creerPersonnel;
    }

    public String getModifierPersonnel() {
        return modifierPersonnel;
    }

    public void setModifierPersonnel(String modifierPersonnel) {
        this.modifierPersonnel = modifierPersonnel;
    }

    public String getCreerClient() {
        return creerClient;
    }

    public void setCreerClient(String creerClient) {
        this.creerClient = creerClient;
    }

    public String getModifierClient() {
        return modifierClient;
    }

    public void setModifierClient(String modifierClient) {
        this.modifierClient = modifierClient;
    }

    public String getCreerCycle() {
        return creerCycle;
    }

    public void setCreerCycle(String creerCycle) {
        this.creerCycle = creerCycle;
    }

    public String getModifierCycle() {
        return modifierCycle;
    }

    public void setModifierCycle(String modifierCycle) {
        this.modifierCycle = modifierCycle;
    }

    public String getCreerExercice() {
        return creerExercice;
    }

    public void setCreerExercice(String creerExercice) {
        this.creerExercice = creerExercice;
    }

    public String getModifierExercice() {
        return modifierExercice;
    }

    public void setModifierExercice(String modifierExercice) {
        this.modifierExercice = modifierExercice;
    }

    public String getCreerTypeMision() {
        return creerTypeMision;
    }

    public void setCreerTypeMision(String creerTypeMision) {
        this.creerTypeMision = creerTypeMision;
    }

    public String getModifierTypeMision() {
        return modifierTypeMision;
    }

    public void setModifierTypeMision(String modifierTypeMision) {
        this.modifierTypeMision = modifierTypeMision;
    }

    public String getCreerClasseur() {
        return creerClasseur;
    }

    public void setCreerClasseur(String creerClasseur) {
        this.creerClasseur = creerClasseur;
    }

    public String getModifierClasseur() {
        return modifierClasseur;
    }

    public void setModifierClasseur(String modifierClasseur) {
        this.modifierClasseur = modifierClasseur;
    }

    public String getCreerDossier() {
        return creerDossier;
    }

    public void setCreerDossier(String creerDossier) {
        this.creerDossier = creerDossier;
    }

    public String getModifierDossier() {
        return modifierDossier;
    }

    public void setModifierDossier(String modifierDossier) {
        this.modifierDossier = modifierDossier;
    }

    public String getCreerContrat() {
        return creerContrat;
    }

    public void setCreerContrat(String creerContrat) {
        this.creerContrat = creerContrat;
    }

    public String getModifierContrat() {
        return modifierContrat;
    }

    public void setModifierContrat(String modifierContrat) {
        this.modifierContrat = modifierContrat;
    }

    public String getMesEquipes() {
        return mesEquipes;
    }

    public void setMesEquipes(String mesEquipes) {
        this.mesEquipes = mesEquipes;
    }

    public String getCreerMission() {
        return creerMission;
    }

    public void setCreerMission(String creerMission) {
        this.creerMission = creerMission;
    }

    public String getModifierMission() {
        return modifierMission;
    }

    public void setModifierMission(String modifierMission) {
        this.modifierMission = modifierMission;
    }

    public String getCreerEquipe() {
        return creerEquipe;
    }

    public void setCreerEquipe(String creerEquipe) {
        this.creerEquipe = creerEquipe;
    }

    public String getModifierEquipe() {
        return modifierEquipe;
    }

    public void setModifierEquipe(String modifierEquipe) {
        this.modifierEquipe = modifierEquipe;
    }

    public String getAffecterMembre() {
        return affecterMembre;
    }

    public void setAffecterMembre(String affecterMembre) {
        this.affecterMembre = affecterMembre;
    }

    public String getValiderMembre() {
        return validerMembre;
    }

    public void setValiderMembre(String validerMembre) {
        this.validerMembre = validerMembre;
    }

    public String getCreerClasseurClient() {
        return creerClasseurClient;
    }

    public void setCreerClasseurClient(String creerClasseurClient) {
        this.creerClasseurClient = creerClasseurClient;
    }

    public String getModifierClasseurClient() {
        return modifierClasseurClient;
    }

    public void setModifierClasseurClient(String modifierClasseurClient) {
        this.modifierClasseurClient = modifierClasseurClient;
    }

    public String getCreerFichier() {
        return creerFichier;
    }

    public void setCreerFichier(String creerFichier) {
        this.creerFichier = creerFichier;
    }

    public String getModifierFichier() {
        return modifierFichier;
    }

    public void setModifierFichier(String modifierFichier) {
        this.modifierFichier = modifierFichier;
    }

    public String getCreerPoste() {
        return creerPoste;
    }

    public void setCreerPoste(String creerPoste) {
        this.creerPoste = creerPoste;
    }

    public String getModifierPoste() {
        return modifierPoste;
    }

    public void setModifierPoste(String modifierPoste) {
        this.modifierPoste = modifierPoste;
    }

    public String getCreerProfil() {
        return creerProfil;
    }

    public void setCreerProfil(String creerProfil) {
        this.creerProfil = creerProfil;
    }

    public String getModifierProfil() {
        return modifierProfil;
    }

    public void setModifierProfil(String modifierProfil) {
        this.modifierProfil = modifierProfil;
    }

    public String getAssocierPoste() {
        return associerPoste;
    }

    public void setAssocierPoste(String associerPoste) {
        this.associerPoste = associerPoste;
    }

    public String getAssocierProfil() {
        return associerProfil;
    }

    public void setAssocierProfil(String associerProfil) {
        this.associerProfil = associerProfil;
    }

    public String getAssocierRole() {
        return associerRole;
    }

    public void setAssocierRole(String associerRole) {
        this.associerRole = associerRole;
    }

    public String getActiverCompte() {
        return activerCompte;
    }

    public void setActiverCompte(String activerCompte) {
        this.activerCompte = activerCompte;
    }

    public String getDesactiverCompte() {
        return desactiverCompte;
    }

    public void setDesactiverCompte(String desactiverCompte) {
        this.desactiverCompte = desactiverCompte;
    }

    public String getPer() {
        return per;
    }

    public void setPer(String per) {
        this.per = per;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    public Utilisateur getPerse() {
        return perse;
    }

    public void setPerse(Utilisateur perse) {
        this.perse = perse;
    }

    public String getLastPass() {
        return lastPass;
    }

    public void setLastPass(String lastPass) {
        this.lastPass = lastPass;
    }

    public String getAdministration() {
        return administration;
    }

    public void setAdministration(String administration) {
        this.administration = administration;
    }

    public String getPersonnel() {
        return personnel;
    }

    public void setPersonnel(String personnel) {
        this.personnel = personnel;
    }

    public String getMission() {
        return mission;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }

    public String getRapport() {
        return rapport;
    }

    public void setRapport(String rapport) {
        this.rapport = rapport;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getSecurite() {
        return securite;
    }

    public void setSecurite(String securite) {
        this.securite = securite;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public String getCabinet() {
        return cabinet;
    }

    public void setCabinet(String cabinet) {
        this.cabinet = cabinet;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    public String getExercice() {
        return exercice;
    }

    public void setExercice(String exercice) {
        this.exercice = exercice;
    }

    public String getTypeMission() {
        return typeMission;
    }

    public void setTypeMission(String typeMission) {
        this.typeMission = typeMission;
    }

    public String getClasseur() {
        return classeur;
    }

    public void setClasseur(String classeur) {
        this.classeur = classeur;
    }

    public String getDossier() {
        return dossier;
    }

    public void setDossier(String dossier) {
        this.dossier = dossier;
    }

    public String getContrat() {
        return contrat;
    }

    public void setContrat(String contrat) {
        this.contrat = contrat;
    }

    public String getMissione() {
        return missione;
    }

    public void setMissione(String missione) {
        this.missione = missione;
    }

    public String getEquipe() {
        return equipe;
    }

    public void setEquipe(String equipe) {
        this.equipe = equipe;
    }

    public String getCreerDossierClient() {
        return creerDossierClient;
    }

    public void setCreerDossierClient(String creerDossierClient) {
        this.creerDossierClient = creerDossierClient;
    }

    public String getModifierDossierClient() {
        return modifierDossierClient;
    }

    public void setModifierDossierClient(String modifierDossierClient) {
        this.modifierDossierClient = modifierDossierClient;
    }

    public String getClasseurClient() {
        return classeurClient;
    }

    public void setClasseurClient(String classeurClient) {
        this.classeurClient = classeurClient;
    }

    public String getDossierClient() {
        return dossierClient;
    }

    public void setDossierClient(String dossierClient) {
        this.dossierClient = dossierClient;
    }

    public String getFichier() {
        return fichier;
    }

    public void setFichier(String fichier) {
        this.fichier = fichier;
    }

    public String getPoste() {
        return poste;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    public String getProfil() {
        return profil;
    }

    public void setProfil(String profil) {
        this.profil = profil;
    }

    public UtilisateurServiceBeanLocal getUsbl() {
        return usbl;
    }

    public void setUsbl(UtilisateurServiceBeanLocal usbl) {
        this.usbl = usbl;
    }

    public ProfilUtilisateurServiceBeanLocal getPusbl() {
        return pusbl;
    }

    public void setPusbl(ProfilUtilisateurServiceBeanLocal pusbl) {
        this.pusbl = pusbl;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public MethodeJournalisation getJournalisation() {
        return journalisation;
    }

    public void setJournalisation(MethodeJournalisation journalisation) {
        this.journalisation = journalisation;
    }
    
    

}
