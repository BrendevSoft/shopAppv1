/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author MEDIA
 */
package com.brendev.shopapp.utils.converter;

import com.brendev.shopapp.entities.Utilisateur;
import com.brendev.shopapp.services.UtilisateurServiceBeanLocal;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import org.primefaces.validate.ClientValidator;

@FacesValidator("custom.emailValidator")
public class EmailValidator implements Validator, ClientValidator {

    @EJB
    private UtilisateurServiceBeanLocal usbl;
    private Pattern pattern;
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Zaz0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public EmailValidator() {
        pattern = Pattern.compile(EMAIL_PATTERN);
    }

    @Override
    public void validate(FacesContext context, UIComponent component, Object value)
            throws ValidatorException {
        if (value == null) {
            return;
        }
        if (!pattern.matcher(value.toString()).matches()) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ereur de validation, l'Email saisi n'est pas valide",
                    value + " n'est pas un email valide;"));
        }

        List<Utilisateur> utilisateurs = this.usbl.getAll();
        for (Utilisateur utilisateur : utilisateurs) {
            if (value.toString().equals(utilisateur.getAdresse().getEmail())) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ereur de validation, l'Email saisi est déja pris",
                        value + " n'est pas un email valide;"));
            }

        }

    }

    @Override
    public Map<String, Object> getMetadata() {
        return null;
    }

    @Override
    public String getValidatorId() {
        return "custom.emailValidator";
    }

    public UtilisateurServiceBeanLocal getUsbl() {
        return usbl;
    }

    public void setUsbl(UtilisateurServiceBeanLocal usbl) {
        this.usbl = usbl;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

}
