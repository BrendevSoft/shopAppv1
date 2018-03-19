/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brendev.shopapp.dao.impl;

import com.brendev.shopapp.dao.RoleDaoBeanLocal;
import com.brendev.shopapp.dao.core.BaseDaoBean;
import com.brendev.shopapp.entities.Role;
import javax.ejb.Stateless;

/**
 *
 * @author NOAMESSI
 */
@Stateless
public class RoleDaoBean extends BaseDaoBean<Role, Long>implements RoleDaoBeanLocal {

    public RoleDaoBean() {
    }

    @Override
    public Class<Role> getType(){
        return Role.class;
    }
    
}
