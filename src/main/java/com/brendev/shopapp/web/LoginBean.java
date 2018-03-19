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
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

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

    private String username;
    private String password;
    private String newPass;
    private String retapPass;
    private String lastPass;
    private String us = "";
    private String recupResponse = "";
    private String question;
    private String reponse;
    private String profil, creerProfil, modifierProfil, associerPoste, associerProfil, associerRole,
            activerCompte, desactiverCompte, securite, inscription, groupement, demande, personnel,
            ajouterDemande, modifierDemande, validerDemande, retirerEnfant, ajouterPersonnel, modifierPersonnel,
            ActiverPersonnel, DesactiverPersonnel, Equipe, gererEquipe, mesEquipes, projet, ajouterProjet, modifierProjet,
            publierProjet, mettreFinProjet, parrainage, etat;
    private Utilisateur user;
    private Utilisateur users;
    private ProfilUtilisateur profilUtilisateur;
    private boolean remember = false;
    private boolean admin;
    private Date date = new Date();

    @EJB//HUM Tu m'as vraiment dérangé.plus de 24h avant de me rappeller de te mettre.hum
    private ProfilRoleServiceBeanLocal prsbl;

    /**
     * Creates a new instance of LoginBean
     */
    public LoginBean() {
        user = new Utilisateur();
        users = new Utilisateur();
        journalisation = new MethodeJournalisation();
    }

    @PostConstruct
    public void init() {
        int i = 0;
        //Vérification de la connexion internet
       /* boolean test = this.checkIntConnection();
        FacesContext context = FacesContext.getCurrentInstance();
        if (test) {
            context.addMessage(null, new FacesMessage("Connexion Internet disponible"));
        } else {
            context.addMessage(null, new FacesMessage("Connexion Internet non disponible"));
        }*/

       
        List<Role> all = rsl.getAll();
        if (all.isEmpty()) {

            //Securite
            this.rsl.saveOne(new Role("Créer profil"));
            this.rsl.saveOne(new Role("Modifier profil"));
            this.rsl.saveOne(new Role("Associer profil"));
            this.rsl.saveOne(new Role("Associer role"));
            this.rsl.saveOne(new Role("Activer compte"));
            this.rsl.saveOne(new Role("Désactiver compte"));

        }

        List<Profil> alle = psbl.getAll();
        if (alle.isEmpty()) {
            this.psbl.saveOne(new Profil("Administrateur", " "));
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
               // journalisation.saveLog4j(LoginBean.class.getName(), Level.INFO, "insérrer");
                tx.commit();
            } catch (Exception e) {
                try {
                    tx.rollback();
                } catch (IllegalStateException ex) {
                    Logger.getLogger(UtilisateurBean.class.getName()).log(Level.FATAL, null, ex);
                } catch (SecurityException ex) {
                    Logger.getLogger(UtilisateurBean.class.getName()).log(Level.FATAL, null, ex);
                } catch (SystemException ex) {
                    Logger.getLogger(UtilisateurBean.class.getName()).log(Level.FATAL, null, ex);
                }
            }
        }

    }

   /* public boolean checkIntConnection() {
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
    }*/

    public void login() throws IOException {
        try {
            System.out.println("user=" + username);
            System.out.println("ps=" + password);
            user = usbl.getOneBy("login", username);
            if (user != null) {
                if (user.getActif() == false) {
                    RequestContext context = RequestContext.getCurrentInstance();
                    context.execute("PF('error').show();");
                    username = "";
                    return;
                }
            }

            if (user != null) {
                boolean test = new Sha256Hash("admin").toHex().equals(user.getMotPasse());
                if (test && user.getActif() == true) {

                    RequestContext context = RequestContext.getCurrentInstance();
                    context.execute("PF('dialogpasse').show();");
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

                //Debut Sécurité
                if (subject.hasRole("Créer profil") || subject.hasRole("Modifier profil")
                        || subject.hasRole("Associer profil") || subject.hasRole("Associer role")
                        || subject.hasRole("Activer compte") || subject.hasRole("Désactiver compte")) {
                    this.securite = "true";
                } else {
                    this.securite = "false";
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

               /* if (!avoir) {
                    RequestContext context = RequestContext.getCurrentInstance();
                    context.execute("PF('error').show();");
                    username = "";
                    return;
                }*/

            }
            journalisation.saveLog4j(LoginBean.class.getName(), Level.INFO, "Journaliser");
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
        Utilisateur usere = EntityRealm.getUser();
        if (usere == null) {
            return "Admin";
        }
        return EntityRealm.getUser().getNom().concat(" ").concat(EntityRealm.getUser().getPrenom());
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

    public void modifierPasse() {
        if (newPass.trim().equals(retapPass.trim())) {
            RequestContext.getCurrentInstance().execute("PF('dialogRecup').hide();");
            user.setMotPasse(new Sha256Hash(newPass.trim()).toHex());
            user.setQuestion(question);
            user.setReponse(reponse);
            usbl.updateOne(user);
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
        Utilisateur use = this.usbl.getOneBy("login", us);
        if (use.getActif() == true) {
            if (reponse.equals(use.getReponse())) {
                RequestContext.getCurrentInstance().execute("PF('dialogRecup').hide();");
                use.setMotPasse(new Sha256Hash("admin").toHex());
                use.setQuestion(null);
                use.setReponse(null);
                usbl.updateOne(use);
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
        if (!us.equals("")) {
            Utilisateur use = this.usbl.getOneBy("login", us);
            String quest = "";
            if (use != null) {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("PF('dialogOublie').hide();");
                if (!use.getMotPasse().equals(new Sha256Hash("admin").toHex())) {
                    if (use.getActif() == true) {
                        quest = use.getQuestion();

                        context.execute("PF('dialogRecup').show();");
                        return quest;
                    } else {
                        us = "";
                        context.execute("PF('dialogOublie').hide();");
                        FacesMessage mf = new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                "Votre compte est inactif,contactez l'administrateur", "");
                        FacesContext.getCurrentInstance().addMessage("erreur_login", mf);
                    }
                } else {
                    context.execute("PF('dialogOublie').hide();");
                    FacesMessage mf = new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Connectez vous à votre compte pour changer votre mot de passe", "");
                    FacesContext.getCurrentInstance().addMessage("erreur_login", mf);
                }
            } else {
                us = "";
                FacesMessage mf = new FacesMessage(FacesMessage.SEVERITY_FATAL,
                        "le login saisi est inconnu", "");
                FacesContext.getCurrentInstance().addMessage("erreur_login", mf);
            }
        }
        return "";
    }

    public void synchroniser() {

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

    public String getLastPass() {
        return lastPass;
    }

    public void setLastPass(String lastPass) {
        this.lastPass = lastPass;
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

    public String getUs() {
        return us;
    }

    public void setUs(String us) {
        this.us = us;
    }

    public Utilisateur getUser() {
        return user;
    }

    public void setUser(Utilisateur user) {
        this.user = user;
    }

    public Utilisateur getUsers() {
        return users;
    }

    public void setUsers(Utilisateur users) {
        this.users = users;
    }

    public String getRecupResponse() {
        recupResponse = this.recupererQuestion();
        return recupResponse;
    }

    public void setRecupResponse(String recupResponse) {
        this.recupResponse = recupResponse;
    }

    public ProfilUtilisateurServiceBeanLocal getPusbl() {
        return pusbl;
    }

    public void setPusbl(ProfilUtilisateurServiceBeanLocal pusbl) {
        this.pusbl = pusbl;
    }

    public ProfilUtilisateur getProfilUtilisateur() {
        return profilUtilisateur;
    }

    public void setProfilUtilisateur(ProfilUtilisateur profilUtilisateur) {
        this.profilUtilisateur = profilUtilisateur;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public String getInscription() {
        return inscription;
    }

    public void setInscription(String inscription) {
        this.inscription = inscription;
    }

    public String getGroupement() {
        return groupement;
    }

    public void setGroupement(String groupement) {
        this.groupement = groupement;
    }

    public String getDemande() {
        return demande;
    }

    public void setDemande(String demande) {
        this.demande = demande;
    }

    public String getPersonnel() {
        return personnel;
    }

    public void setPersonnel(String personnel) {
        this.personnel = personnel;
    }

    public String getAjouterDemande() {
        return ajouterDemande;
    }

    public void setAjouterDemande(String ajouterDemande) {
        this.ajouterDemande = ajouterDemande;
    }

    public String getModifierDemande() {
        return modifierDemande;
    }

    public void setModifierDemande(String modifierDemande) {
        this.modifierDemande = modifierDemande;
    }

    public String getValiderDemande() {
        return validerDemande;
    }

    public void setValiderDemande(String validerDemande) {
        this.validerDemande = validerDemande;
    }

    public String getRetirerEnfant() {
        return retirerEnfant;
    }

    public void setRetirerEnfant(String retirerEnfant) {
        this.retirerEnfant = retirerEnfant;
    }

    public String getAjouterPersonnel() {
        return ajouterPersonnel;
    }

    public void setAjouterPersonnel(String ajouterPersonnel) {
        this.ajouterPersonnel = ajouterPersonnel;
    }

    public String getModifierPersonnel() {
        return modifierPersonnel;
    }

    public void setModifierPersonnel(String modifierPersonnel) {
        this.modifierPersonnel = modifierPersonnel;
    }

    public String getActiverPersonnel() {
        return ActiverPersonnel;
    }

    public void setActiverPersonnel(String ActiverPersonnel) {
        this.ActiverPersonnel = ActiverPersonnel;
    }

    public String getDesactiverPersonnel() {
        return DesactiverPersonnel;
    }

    public void setDesactiverPersonnel(String DesactiverPersonnel) {
        this.DesactiverPersonnel = DesactiverPersonnel;
    }

    public String getEquipe() {
        return Equipe;
    }

    public void setEquipe(String Equipe) {
        this.Equipe = Equipe;
    }

    public String getGererEquipe() {
        return gererEquipe;
    }

    public void setGererEquipe(String gererEquipe) {
        this.gererEquipe = gererEquipe;
    }

    public String getMesEquipes() {
        return mesEquipes;
    }

    public void setMesEquipes(String mesEquipes) {
        this.mesEquipes = mesEquipes;
    }

    public String getProjet() {
        return projet;
    }

    public void setProjet(String projet) {
        this.projet = projet;
    }

    public String getAjouterProjet() {
        return ajouterProjet;
    }

    public void setAjouterProjet(String ajouterProjet) {
        this.ajouterProjet = ajouterProjet;
    }

    public String getModifierProjet() {
        return modifierProjet;
    }

    public void setModifierProjet(String modifierProjet) {
        this.modifierProjet = modifierProjet;
    }

    public String getPublierProjet() {
        return publierProjet;
    }

    public void setPublierProjet(String publierProjet) {
        this.publierProjet = publierProjet;
    }

    public String getMettreFinProjet() {
        return mettreFinProjet;
    }

    public void setMettreFinProjet(String mettreFinProjet) {
        this.mettreFinProjet = mettreFinProjet;
    }

    public String getParrainage() {
        return parrainage;
    }

    public void setParrainage(String parrainage) {
        this.parrainage = parrainage;
    }

    public MethodeJournalisation getJournalisation() {
        return journalisation;
    }

    public void setJournalisation(MethodeJournalisation journalisation) {
        this.journalisation = journalisation;
    }
    
    

}
