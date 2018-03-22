/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brendev.shopapp.utils.constantes;

import com.brendev.shopapp.entities.Utilisateur;
import com.brendev.shopapp.shiro.EntityRealm;
import java.io.IOException;
import java.io.Serializable;
import org.omnifaces.util.Faces;

/**
 *
 * @author
 */
public abstract class Constante implements Serializable {

    public static final String VALIDATION_ECHOUE = "Opération échouée";
    public static final String VALIDATION_REUSSIE = "Le dossier vient d'etre accepté";
    public static final String ACTIVATION_REUSSIE = "Activation réussie";
    public static final String DESACTIVATION_REUSSIE = "Echec de la désactivation";
    public static final String ACTIVATION_ECHOUE = "Echec de l'activation";
    public static final String DESACTIVATION_ECHOU = "Echec de la désactivation";
    public static final String DEMANDE_REUSSIT = "Demande d'adhésion envoyé";
    public static final String DEMANDE_ECHOU = "Demande d'adhésion échoué";
    public static final String DESACTIVATION_REUSSIT = "Projet fini";
    public static final String DESACTIVATION_ECHOUE = "Erreur d'arret de la publication";
    public static final String PUBLICATION_REUSSIT = "Publication réussi";
    public static final String PUBLICATION_ECHOUE = "Publication échoué";
    public static final String ENREGISTREMENT_REUSSIT = "Enrégistrement réussi";
    public static final String CONFIRMER_ENREGISTREMENT = "Êtes-vous sûre de l'Enregistrement?";
    public static final String CONFIRMER_ENVOI = "Êtes-vous sûre de l'envoi?";
    public static final String CONFIRMER_MODIFICATION = "Êtes-vous sûre de la Modification?";
    public static final String CONFIRMER_AJOUTER_LIGNE = "Êtes-vous sûre de l'Ajout?";
    public static final String CONFIRMER_RETIRER_LIGNE = "Êtes-vous sûre du Retrait?";
    public static final String ANNULER = "Êtes-vous sûre de l'Annulation?";
    public static final String MODIFICATION_REUSSIT = "Modification réussie";
    public static final String ENREGISTREMENT_ECHOUE = "Enregistrement échoué";
    public static final String MODIFICATION_ECHOUE = "Modification échouée";
    public static final String MESSAGE_EN_ATTENTE = "En attente";
    public static final String MESSAGE_CONFIRME = "En attente";
    public static final String MESSAGE_ENVOYE = "Envoyer";
    public static final String MESSAGE_ANNULER = "Annuler";
    public static final String PREFIX_CLIENT = "CLIENT/SMS/";
    public static final String PROFIL_CREER_MODIFIER_GROUPE_MESSAGE = "Creer un groupe";
    public static final String PROFIL_CONFIRMER_ENVOI = "Confirmer l'envoi";
    public static final String PROFIL_ADMINISTRATEUR = "Administrateur";
    public static final String BOUTON_ANNULER = "Annuler";
    public static final String BOUTON_MODIFIER = "Modifier";
    public static final String BOUTON_VALIDER = "Valider";
    public static final String MOT_DE_PASSE_DEFAUT = "admin";

    public Constante() {
    }

    public static void bloqueLien() {
        Utilisateur user = EntityRealm.getUser();
        try {
            if (user == null) {
                Faces.redirect("access-denied.xhtml");
            }
        } catch (IOException ex) {
            System.out.println("Sa n'a pas marché!!!!!!!");
        }
    }
}
