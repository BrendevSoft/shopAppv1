/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brendev.shopapp.utils.dates;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * <p>
 * Cette classe permet de de manipuler les Dates c'est a dire
 * <ul>
 * <li>Ajouter et retirer un nombre d'année</li>
 * <li>Ajouter et retirer un nombre de mois</li>
 * <li>Ajouter et retirer un nombre de jour</li>
 * <li>Ajouter et retirer un nombre d'heure</li>
 * <li>Ajouter et retirer un nombre de minuite</li>
 * <li>Ajouter et retirer un nombre de second</li>
 * </ul>
 * </p>
 *
 * @author MEDIA
 */
public class ManipulationDate {

    /**
     * pour le retrait il faut ajouter un nombre négatif
     *
     * @author MEDIA
     * @param date la date a lakel on veut ajouter les jours
     * @param nbJour le nombre de jour a ajouter
     * @return un Objet de type Date
     */
    public static Date ajouterJour(Date date, int nbJour) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, nbJour);
        return cal.getTime();
    }

    /**
     * pour le retrait il faut ajouter un nombre négatif
     *
     * @author MEDIA
     * @param date la date a lakel on veut ajouter les jours
     * @param nbJour le nombre de jour a ajouter
     * @return une Date
     */
    public static Date ajouterJour2(Date date, int nbJour) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, nbJour);
        return cal.getTime();
    }

    /**
     * pour le retrait il faut ajouter un nombre négatif
     *
     * @author MEDIA
     * @param date la date a lakel on veut ajouter les jours
     * @param nbMois le nombre de mois a ajouter
     * @return un objet de type Date
     */
    public static Date ajouterMois(Date date, int nbMois) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, nbMois);
        return cal.getTime();
    }

    /**
     * pour le retrait il faut ajouter un nombre négatif
     *
     * @param date la date a lakel on veut ajouter l'année
     * @param nbAnnee le nombre d'année a ajouter
     * @return un objet de type Date
     */
    public static Date ajouterAnnee(Date date, int nbAnnee) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, nbAnnee);
        return cal.getTime();
    }

    /**
     * pour le retrait il faut ajouter un nombre négatif
     *
     * @param date la date a lakel on veut ajouter un nombre d'heure
     * @param nbHeure le nombre d'heure a ajouter
     * @return un objet de type Date
     */
    public static Date ajouterHeure(Date date, int nbHeure) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR, nbHeure);
        return cal.getTime();
    }

    /**
     * pour le retrait il faut ajouter un nombre négatif
     *
     * @param date la date a lakel on veut ajouter un nombre de minuite
     * @param nbMinute le nombre de minuite a ajouter
     * @return un objet de type Date
     */
    public static Date ajouterMinute(Date date, int nbMinute) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, nbMinute);
        return cal.getTime();
    }

    /**
     * pour le retrait il faut ajouter un nombre négatif
     *
     * @param date la date a lakel on veut ajouter un nombre de minuite
     * @param nbSeconde le nombre de seconde a ajouter
     * @return un objet de type Date
     */
    public static Date ajouterSeconde(Date date, int nbSeconde) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.SECOND, nbSeconde);
        return cal.getTime();
    }

    /**
     *
     * @param date la date a convetir
     * @return en string une date avec un format du genre
     * <strong>02/08/1503:52</strong>
     */
    public static String shortDateFormatFR(Date date) {
        DateFormat shortDateFormatFR = DateFormat.getDateTimeInstance(
                DateFormat.SHORT,
                DateFormat.SHORT);
        return shortDateFormatFR.format(date);
    }

    /**
     *
     * @param date la date a convetir
     * @return en string une date avec un format du genre
     * <strong>2 août 2015 03:52:56</strong>
     */
    public static String mediumDateFormatFR(Date date) {
        DateFormat mediumDateFormatFR = DateFormat.getDateTimeInstance(
                DateFormat.MEDIUM,
                DateFormat.MEDIUM);
        return mediumDateFormatFR.format(date);
    }

    /**
     *
     * @param date la date a convetir
     * @return en string une date avec un format du genre
     * <strong>2 août 2015 03:52:56 WEST</strong>
     */
    public static String longDateFormatFR(Date date) {
        DateFormat longDateFormatFR = DateFormat.getDateTimeInstance(
                DateFormat.LONG,
                DateFormat.LONG);
        return longDateFormatFR.format(date);
    }

    /**
     *
     * @param date la date a convetir
     * @return en string une date avec un format du genre
     * <strong>dimanche 2 août 2015 03 h 52 WEST</strong>
     */
    public static String fullDateFormatFR(Date date) {
        DateFormat fullDateFormatFR = DateFormat.getDateTimeInstance(
                DateFormat.FULL,
                DateFormat.FULL);
        return fullDateFormatFR.format(date);
    }

    public static Long dateToLong(Date date) {
        String jour = "" + date.getDate();
        String mois = "" + (date.getMonth()+1);
        String annee = "" + (date.getYear() + 1900);
        if (jour.length() == 1) {
            jour = "0" + jour;
        }
        if (mois.length() == 1) {
            mois = "0" + mois;
        }

        return Long.parseLong(annee + mois + jour);
    }
    /**
     * 
     * @param date1 la premiere date a comparer
     * @param date2 la deuxieme date a comparer
     * @return  retour un int  
     * si date1 vient avant date2 cela retourne 1
     * si date1 vient apres date2 cela retourne -1
     * si date1 est egale date2 cela retourne 0
     */

    public static int compareDate(Date date1, Date date2) {
        long dateLong1 = dateToLong(date1);
        long dateLong2 = dateToLong(date2);
        if (dateLong1 > dateLong2) {
            return 1;
        } else if (dateLong1 == dateLong2) {
            return 0;
        }
        return -1;
    }
}
