/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brendev.shopapp.web;

import javax.inject.Named;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import org.primefaces.event.FlowEvent;

/**
 *
 * @author NOAMESSI
 */
@Named(value = "GesNiveau")
@SessionScoped
public class GesNiveau implements Serializable {

    private boolean skip;

    /**
     * Creates a new instance of FlowPrecess
     */
    public GesNiveau() {
    }

    public String onFlowProcess(FlowEvent event) {
        if (skip) {
            skip = false;   //reset in case user goes back
            return "confirm";
        } else {
            return event.getNewStep();
        }
    }

    public boolean isSkip() {
        return skip;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }

}
