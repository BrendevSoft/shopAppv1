/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brendev.shopapp.services;

import com.brendev.shopapp.entities.Profil;
import com.brendev.shopapp.entities.ProfilRole;
import com.brendev.shopapp.entities.ProfilRoleId;
import com.brendev.shopapp.entities.Role;
import com.brendev.shopapp.services.core.BaseServiceBeanLocal;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author NOAMESSI
 */
@Local
public interface ProfilRoleServiceBeanLocal extends BaseServiceBeanLocal<ProfilRole, ProfilRoleId>{

    public List<Role> getProfilRoles(Profil profil);

    public ProfilRole getProfilRoles(Profil profil, Role role);

    public boolean supProfilRoles(ProfilRole cRole);
    
}
