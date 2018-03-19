/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brendev.shopapp.services.impl;

import com.brendev.shopapp.dao.RoleDaoBeanLocal;
import com.brendev.shopapp.dao.core.BaseDaoBeanLocal;
import com.brendev.shopapp.entities.Role;
import com.brendev.shopapp.services.RoleServiceBeanLocal;
import com.brendev.shopapp.services.core.BaseServiceBean;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author NOAMESSI
 */
@Stateless
public class RoleServiceBean extends BaseServiceBean<Role, Long>implements RoleServiceBeanLocal {

    @EJB
    private RoleDaoBeanLocal rdbl;
    
    @Override
    public BaseDaoBeanLocal<Role, Long> getDao(){
        return rdbl;
    }
}
