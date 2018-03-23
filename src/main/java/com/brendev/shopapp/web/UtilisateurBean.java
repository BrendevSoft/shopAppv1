/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brendev.shopapp.web;

import com.brendev.shopapp.entities.Adresse;
import com.brendev.shopapp.entities.Profil;
import com.brendev.shopapp.entities.ProfilUtilisateur;
import com.brendev.shopapp.entities.ProfilUtilisateurId;
import com.brendev.shopapp.entities.Utilisateur;
import com.brendev.shopapp.services.ProfilServiceBeanLocal;
import com.brendev.shopapp.services.ProfilUtilisateurServiceBeanLocal;
import com.brendev.shopapp.services.UtilisateurServiceBeanLocal;
import com.brendev.shopapp.utils.constantes.Constante;
import com.brendev.shopapp.utils.constantes.MethodeJournalisation;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.ServletContext;
import org.apache.log4j.Level;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.apache.shiro.crypto.hash.Sha256Hash;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;

/**
 *
 * @author Administrateur
 */
@Named(value = "utilisateurBean")
@ViewScoped
public class UtilisateurBean implements Serializable {

    private Adresse adresse;
    private Utilisateur utilisateur;
    private ProfilUtilisateur profilUtilisateur;
    private List<ProfilUtilisateur> profilUtilisateurs;
    private List<Utilisateur> utilisateurs;
    private List<Utilisateur> list;
    private Profil profil;
    private List<Profil> profils;
    private boolean skip;
    private String space = "  ";
    private List<String> situation;
    private List<String> pays;
    private List<Utilisateur> utilisateurs1;
    private Date date = new Date();
    private Utilisateur u;
    private MethodeJournalisation journalisation;

    private InputStream inptStrm;

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
        this.adresse = new Adresse();
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
        this.journalisation = new MethodeJournalisation();
        this.list = new ArrayList<>();
    }

    public void handleFileUpload(FileUploadEvent event) {
        try {
            String image = String.valueOf((int) (Math.random() * 10000000));
            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String newFileName = servletContext.getRealPath("") + File.separator + "resources" + File.separator + "images" + File.separator + image + event.getFile().getFileName();
            InputStream inputStream = event.getFile().getInputstream();
            inptStrm = event.getFile().getInputstream();
            ImageOutputStream out = new FileImageOutputStream(new File(newFileName));
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            inputStream.close();
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void importer() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        if (inptStrm != null) {
            ArrayList<String> values = new ArrayList<String>();
            try {
                InputStream input = inptStrm;
                XSSFWorkbook wb = new XSSFWorkbook(input);
                XSSFSheet sheet = wb.getSheetAt(0);
                XSSFRow v = sheet.getRow(0);
                Iterator rows = sheet.rowIterator();
                while (rows.hasNext()) {
                    values.clear();
                    XSSFRow row = (XSSFRow) rows.next();
                    //if (row.getRowNum() > 0) {
                    Iterator cells = row.cellIterator();

                    while (cells.hasNext()) {
                        XSSFCell cell = (XSSFCell) cells.next();

                        if (XSSFCell.CELL_TYPE_NUMERIC == cell.getCellType()) {
                            values.add(Integer.toString((int) cell.getNumericCellValue()));
                        } else if (XSSFCell.CELL_TYPE_STRING == cell.getCellType()) {
                            values.add(cell.getStringCellValue());
                        }
                    }

                    if (values.size() > 4) {
                        if (values.get(0).length() > 1 && values.get(1).length() > 1 && values.get(2).length() > 1 && values.get(3).matches("^[00228|+228]?[9|2][\\d]{7,}$") && values.get(4).matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Zaz0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
                            if (values.get(2).equals("Masculin") || values.get(2).equals("Feminin")) {
                                utilisateur.setNom(values.get(0));
                                utilisateur.setPrenom(values.get(1));
                                utilisateur.setSexe(values.get(2));
                                adresse.setEmail(values.get(4).replace(",", "."));
                                adresse.setTelephone(values.get(3));
                                utilisateur.setAdresse(adresse);
                                utilisateur.setLogin(utilisateur.getPrenom().charAt(0) + "." + utilisateur.getNom());
                                if (usbl.getBy("login", utilisateur.getLogin()).isEmpty()) {
                                    utilisateur.setMotPasse(new Sha256Hash(Constante.MOT_DE_PASSE_DEFAUT).toHex());
                                    usbl.saveOne(utilisateur);
                                    journalisation.saveLog4j(UtilisateurBean.class.getName(), Level.INFO, "Enregistrement d'un personnel :" + utilisateur.getNom() + " " + utilisateur.getPrenom());
                                    adresse = new Adresse();
                                    utilisateur = new Utilisateur();
                                    context.addMessage(null, new FacesMessage("Enrégistrement réussi!"));

                                } else {
                                    int test = 1;
                                    do {
                                        test++;
                                    } while (usbl.getBy("login", utilisateur.getLogin() + "" + test).isEmpty() == false);
                                    utilisateur.setMotPasse(new Sha256Hash(Constante.MOT_DE_PASSE_DEFAUT).toHex());
                                    utilisateur.setLogin(utilisateur.getLogin() + "" + test);
                                    usbl.saveOne(utilisateur);

                                    journalisation.saveLog4j(UtilisateurBean.class.getName(), Level.INFO, "Enregistrement d'un personnel :" + utilisateur.getNom() + " " + utilisateur.getPrenom());
                                    adresse = new Adresse();
                                    utilisateur = new Utilisateur();
                                    context.addMessage(null, new FacesMessage("Enrégistrement réussi!"));

                                }
                            } else {
                                int nbrLigne1 = row.getRowNum() + 1;
                                context.addMessage(null, new FacesMessage("Le sexe saisit Ã la ligne " + nbrLigne1 + " est incorrect , le sexe doit être Masculun ou Feminin svp!"));
                            }
                        } else {
                            int nbrLigne2 = row.getRowNum() + 1;
                            context.addMessage(null, new FacesMessage("Syntaxe de la ligne " + nbrLigne2 + " est incorrect !"));
                        }
                    } else {
                        int nbrLigne = row.getRowNum() + 1;
                        context.addMessage(null, new FacesMessage("Valeurs insuffisantes Ã  la ligne " + nbrLigne + " !"));
                        values.clear();
                    }
                }

                inptStrm = null;
            } catch (Exception ex) {
                System.out.println("Erreur");
                ex.printStackTrace();
            }
        } else {
            context.addMessage(null, new FacesMessage("Veuillez choisir le fichier a importer svp !"));
        }

    }

    public void annulerImporter() {
        inptStrm = null;
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

    public void getObject(ProfilUtilisateurId id) {
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

    public void activer() {
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

    public Adresse getAdresse() {
        return adresse;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    public List<Utilisateur> getList() {
        this.list = this.usbl.getAll();
        return list;
    }

    public void setList(List<Utilisateur> list) {
        this.list = list;
    }

    public MethodeJournalisation getJournalisation() {
        return journalisation;
    }

    public void setJournalisation(MethodeJournalisation journalisation) {
        this.journalisation = journalisation;
    }

    public InputStream getInptStrm() {
        return inptStrm;
    }

    public void setInptStrm(InputStream inptStrm) {
        this.inptStrm = inptStrm;
    }

}
