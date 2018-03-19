/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brendev.shopapp.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author Brendev
 */
@MappedSuperclass
public class BaseEntity implements Serializable{

    @Column(name = "version",nullable = false)
    Integer version = 1;
    
}
