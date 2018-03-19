package com.brendev.shopapp.shiro;

import com.brendev.shopapp.dao.ProfilRoleDaoBeanLocal;
import com.brendev.shopapp.dao.ProfilUtilisateurDaoBeanLocal;
import com.brendev.shopapp.dao.UtilisateurDaoBeanLocal;
import com.brendev.shopapp.entities.Profil;
import com.brendev.shopapp.entities.ProfilRole;
import com.brendev.shopapp.entities.ProfilUtilisateur;
import com.brendev.shopapp.entities.Utilisateur;
import java.util.ArrayList;
import java.util.List;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import javax.ejb.EJB;

public class EntityRealm extends AuthorizingRealm {

    @EJB
    protected ProfilUtilisateurDaoBeanLocal pudbl;
    protected UtilisateurDaoBeanLocal udbl;

    protected ProfilRoleDaoBeanLocal prdbl;

    private List<ProfilUtilisateur> profilUtilisateurs;
    protected static ProfilUtilisateur profilUtilisateur;
    protected static List<Utilisateur> utilisateurs;
    protected static Utilisateur utilisateur;

    protected static Profil profil;
    protected static List<ProfilRole> profilRoles;

    public EntityRealm() throws NamingException {
        this.profilUtilisateurs = new ArrayList<>();
        System.out.println("enter entity realm");
        this.setName("entityRealm");
        CredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher("SHA-256");
        this.setCredentialsMatcher(credentialsMatcher);
        InitialContext context = new InitialContext();
        //La classe session bean de l'utilisateur(précise la classe du sesion bean)
        this.pudbl = (ProfilUtilisateurDaoBeanLocal) context.lookup("java:global/shopApp/ProfilUtilisateurDaoBean");
        this.udbl = (UtilisateurDaoBeanLocal) context.lookup("java:global/shopApp/UtilisateurDaoBean");
        //La classe session bean des roles par profil(précise la classe du sesion bean)
        this.prdbl = (ProfilRoleDaoBeanLocal) context.lookup("java:global/shopApp/ProfilRoleDaoBean");
        System.out.println("out entity realm");
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {

        final UsernamePasswordToken token = (UsernamePasswordToken) authcToken;

        utilisateur = udbl.getOneBy("login", token.getUsername());

        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo();
        try {
            if (utilisateur != null) {

                simpleAuthenticationInfo = new SimpleAuthenticationInfo(utilisateur.getLogin(), utilisateur.getMotPasse(), getName());

            } else {
                simpleAuthenticationInfo = null;
                throw new UnknownAccountException("Utilisateur inconnu");
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return simpleAuthenticationInfo;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //????????????????????????????????????????
        String userId = (String) principals.fromRealm(this.getName()).iterator().next();
        //Dans les guillemets on met le nom dansla base de donnée, ensuite on met la valeur
        utilisateur = udbl.getBy("login", userId).get(0);
        if (utilisateur != null) {
            final SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            profilUtilisateurs = this.pudbl.getBy("utilisateur", utilisateur);
            if (!profilUtilisateurs.isEmpty()) {
                for (ProfilUtilisateur pu : profilUtilisateurs) {
                    if (pu.getDateRevocation() == null) {
                        profilRoles = this.prdbl.getBy("profil", pu.getProfil());
                    }
                }

            }

            final List<String> roles = new ArrayList<>();
            for (ProfilRole proRole : profilRoles) {
                roles.add(proRole.getRole().getNom());
            }
            info.addRoles(roles);

            return info;
        } else {
            return null;
        }
    }

    /*@Override
     protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        
     SimpleAuthorizationInfo info = null;

     try {
     String userId = (String) principals.fromRealm(this.getName()).iterator().next();
     personnel = pdaol.getOneBy("login", userId);
     if (personnel != null) {
               
     info = new SimpleAuthorizationInfo();
     String nomGroupeUtil = personnel.getCategoriePersonnel().getNom();
     info.addRole(nomGroupeUtil);
                
     final List<String> perm = new ArrayList<>();
     for (ProfilRole ca : personnel.getCategoriePersonnel().getCategoriePersonnelRoles()) {
     perm.add(ca.getRole().getNom());
     }
     info.addStringPermissions(perm);

     }
     } catch (Exception e) {

     }
     return info;
     }*/
    public static Utilisateur getUser() {
        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser.isAuthenticated()) {
            return utilisateur;
        }
        return null;
    }

    public static Subject getSubject() {
        return SecurityUtils.getSubject();

    }

    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

}
