package models.users.auth;

import models.users.User;
import play.cache.Cache;

import java.util.UUID;

/**
 * May the build success be with you
 * With great problems, comes great help from @guilhermesteves
 *
 * Object responsible to authenticate the User and control
 * all aspects of a session/cache/tokens etc
 */
public class UserAuth {

    //**********************************************************
    // constants
    //**********************************************************

    public static final int TOKEN_EXPIRATION = 60 * 5;
    public static final int AUTH_EXPIRATION = 60 * 2;

    private static final String AUTH = "userAuth_";
    private static final String AUTH_CODE = "userAuthCode_";

    //**********************************************************
    // properties
    //**********************************************************

    private User user;
    private String token;
    private String refreshToken;

    //**********************************************************
    // getters and setters
    //**********************************************************

    public User getUser() {
        return user;
    }

    public UserAuth setUser(User user) {
        this.user = user;
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

    public static String login(String identifier) {
        User user = User.loadByIdentifier(identifier);

        if (user == null)
            return null;

        UserAuth userAuth = new UserAuth().setUser(user);

        // TODO: SAVE IN DATABASE

        return userAuth.generateAuthCode();
    }

    public String generateAuthCode() {
        String authCode = UUID.randomUUID().toString();
        Cache.set(AUTH_CODE + authCode, this, AUTH_EXPIRATION);

        return authCode;
    }

    public static UserAuth exchangeAuthCode(String authCode) {
        UserAuth auth = (UserAuth) Cache.get(AUTH_CODE + authCode);

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

    public static UserAuth getUserAuthByToken(String token) {
        return (UserAuth) Cache.get(AUTH + token);
    }

    private static UserAuth getUserAuthByRefreshToken(String refreshToken) {
        return (UserAuth) Cache.get(AUTH + refreshToken);
    }

    public static UserAuth refreshToken(String refreshToken, String token) {
        UserAuth auth = getUserAuthByRefreshToken(refreshToken);

        if(auth==null || !token.equals(auth.getToken()))
            return null;

        // refresh user data, roles and permissions
        auth.setUser(User.load(auth.getUser().getId()));

        auth.generateToken();

        return auth;
    }

    public void logout(){

        //cleaning token
        Cache.set(AUTH + token, null, 0);

        //cleaning refreshToken
        Cache.set(AUTH + refreshToken, null, 0);
    }

}
