/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brendev.shopapp.utils.transaction;

import javax.naming.InitialContext;
import javax.transaction.UserTransaction;

/**
 *
 * @author NOAMESSI
 */
public class TransactionManager {

    public TransactionManager() {
    }

    public static UserTransaction getUserTransaction() {
        UserTransaction utx;
        try {
            InitialContext context = new InitialContext();
            utx = (UserTransaction) context.lookup("java:comp/UserTransaction");
            return utx;
        } catch (Exception e) {
            return null;
        }
    }

}
