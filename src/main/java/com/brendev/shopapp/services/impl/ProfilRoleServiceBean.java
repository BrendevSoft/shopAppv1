/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brendev.shopapp.services.impl;


import com.brendev.shopapp.dao.ProfilRoleDaoBeanLocal;
import com.brendev.shopapp.dao.core.BaseDaoBeanLocal;
import com.brendev.shopapp.entities.Profil;
import com.brendev.shopapp.entities.ProfilRole;
import com.brendev.shopapp.entities.ProfilRoleId;
import com.brendev.shopapp.entities.Role;
import com.brendev.shopapp.services.ProfilRoleServiceBeanLocal;
import com.brendev.shopapp.services.core.BaseServiceBean;
import com.brendev.shopapp.services.core.BaseServiceBeanLocal;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;


/**
 *
 * @author NOAMESSI
 */
@Stateless
public class ProfilRoleServiceBean extends BaseServiceBean<ProfilRole, ProfilRoleId> implements ProfilRoleServiceBeanLocal {

    @EJB
    private ProfilRoleDaoBeanLocal prdbl;

    @Override
    public BaseDaoBeanLocal<ProfilRole, ProfilRoleId> getDao() {
        return prdbl;
    }


    @Override
    public List<Role> getProfilRoles(Profil profil) {
        return this.prdbl.getProfilRoles(profil);
    }

 
    @Override
    public ProfilRole getProfilRoles(Profil profil, Role role) {
        return this.prdbl.getProfilRoles(profil, role);
    }


    @Override
    public boolean supProfilRoles(ProfilRole cRole) {
        return this.prdbl.supProfilRoles(cRole);
    }

}
