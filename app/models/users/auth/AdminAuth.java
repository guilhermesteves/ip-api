package models.users.auth;

import models.users.Admin;
import play.cache.Cache;

import java.util.UUID;

/**
 * May the build success be with you
 * With great problems, comes great help from @guilhermesteves
 *
 * Object responsible to authenticate the Admin and control
 * all aspects of a session/cache/tokens etc
 */
public class AdminAuth {

    //**********************************************************
    // constants
    //**********************************************************

    public static final int TOKEN_EXPIRATION = 60 * 5;
    public static final int adminAuth_EXPIRATION = 60 * 2;

    private static final String AUTH = "";
    private static final String AUTH_CODE = "";

    //**********************************************************
    // properties
    //**********************************************************

    private Admin admin;
    private String token;
    private String refreshToken;

    //**********************************************************
    // getters and setters
    //**********************************************************


    public Admin getAdmin() {
        return admin;
    }

    public AdminAuth setAdmin(Admin admin) {
        this.admin = admin;
        return this;
    }

    public String getToken() {
        return token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    //**********************************************************
    // authentication
    //**********************************************************

    public static String login(String email, String password) {

        Admin admin = Admin.loadByEmail(email);

        if(admin == null || !admin.isPasswordCorrect(password))
            // TODO: SEND JSON ERROR
            return null;

        AdminAuth adminAuth = new AdminAuth().setAdmin(admin);

        // TODO: SAVE IN DATABASE

        return adminAuth.generateAuthCode();

    }

    public String generateAuthCode() {
        String authCode = UUID.randomUUID().toString();
        Cache.set("adminAuthCode_" + authCode, this, adminAuth_EXPIRATION);

        return authCode;
    }

    public static AdminAuth exchangeAuthCode(String authCode) {
        AdminAuth auth = (AdminAuth) Cache.get("adminAuthCode_" + authCode);

        if(auth != null) {
            // cleaning token
            Cache.set("adminAuthCode_" + authCode, null, 0);

            auth.generateToken();
            auth.generateRefreshToken();
        }

        return auth;
    }

    public String generateToken(){
        token = UUID.randomUUID().toString();
        Cache.set("adminAuth_" + token, this, TOKEN_EXPIRATION);

        return token;
    }

    public String generateRefreshToken() {
        refreshToken = UUID.randomUUID().toString();

        Cache.set("adminAuth_" + refreshToken, this);

        return refreshToken;
    }

    public static AdminAuth getAdminAuthByToken(String token) {
        return (AdminAuth) Cache.get("adminAuth_" + token);
    }

    private static AdminAuth getAdminAuthByRefreshToken(String refreshToken) {
        return (AdminAuth) Cache.get("adminAuth_" + refreshToken);
    }

    public static AdminAuth refreshToken(String refreshToken, String token) {
        AdminAuth auth = getAdminAuthByRefreshToken(refreshToken);

        if(auth == null || !token.equals(auth.getToken()))
            return null;

        // cleaning token
        Cache.set("adminAuth_" + token,null,0);

        auth.generateToken();

        return auth;
    }

    public void logout(){
        // cleaning token & refresh token
        Cache.set("adminAuth_" + token, null, 0);
        Cache.set("adminAuth_" + refreshToken, null, 0);
    }


}