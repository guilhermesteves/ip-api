package models.users.auth;

import models.users.Mod;
import play.cache.Cache;

import java.util.UUID;

/**
 * May the build success be with you
 * With great problems, comes great help from @guilhermesteves
 *
 * Object responsible to authenticate the Mod and control
 * all aspects of a session/cache/tokens etc
 */
public class ModAuth {

    //**********************************************************
    // constants
    //**********************************************************

    public static final int TOKEN_EXPIRATION = 60 * 5;
    public static final int AUTH_EXPIRATION = 60 * 2;

    private static final String AUTH = "modAuth_";
    private static final String AUTH_CODE = "modAuthCode_";

    //**********************************************************
    // properties
    //**********************************************************

    private Mod mod;
    private String token;
    private String refreshToken;

    //**********************************************************
    // getters and setters
    //**********************************************************

    public Mod getMod() {
        return mod;
    }

    public ModAuth setMod(Mod mod) {
        this.mod = mod;

        return this;
    }

    public String getToken() {
        return token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }


    //**********************************************************
    // authentication methods
    //**********************************************************

    public static String login(String email, String password) {
        Mod mod = Mod.loadByEmail(email);

        if (mod == null || !mod.isPasswordCorrect(password))
            // TODO: SEND JSON ERROR
            return null;

        ModAuth modAuth = new ModAuth().setMod(mod);

        // TODO: SAVE IN DATABASE

        return modAuth.generateAuthCode();

    }

    public String generateAuthCode() {
        String authCode = UUID.randomUUID().toString();
        Cache.set(AUTH_CODE + authCode, this, AUTH_EXPIRATION);

        return authCode;
    }

    public static ModAuth exchangeAuthCode(String authCode) {
        ModAuth auth = (ModAuth) Cache.get(AUTH_CODE + authCode);

        if (auth != null) {
            Cache.set(AUTH_CODE + authCode, null, 0);

            auth.generateToken();
            auth.generateRefreshToken();
        }

        return auth;
    }

    public String generateToken() {
        token = UUID.randomUUID().toString();
        Cache.set(AUTH + token, this, TOKEN_EXPIRATION);

        return token;
    }

    public String generateRefreshToken() {
        refreshToken = UUID.randomUUID().toString();

        Cache.set(AUTH + refreshToken, this);

        return refreshToken;
    }

    public static ModAuth getModAuthByToken(String token) {
        return (ModAuth) Cache.get(AUTH + token);
    }

    private static ModAuth getModAuthByRefreshToken(String refreshToken) {
        return (ModAuth) Cache.get(AUTH + refreshToken);
    }

    public static ModAuth refreshToken(String refreshToken, String token) {
        ModAuth auth = getModAuthByRefreshToken(refreshToken);

        if (auth == null || !token.equals(auth.getToken()))
            return null;

        // refresh mod data, roles and permissions
        auth.setMod(Mod.load(auth.getMod().getId()));

        auth.generateToken();

        return auth;
    }

    public void logout() {
        //cleaning token
        Cache.set(AUTH + token, null, 0);

        //cleaning refreshToken
        Cache.set(AUTH + refreshToken, null, 0);
    }
}