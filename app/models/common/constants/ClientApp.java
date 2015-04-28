package models.common.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * May the build success be with you
 * With great problems, comes great help from @guilhermesteves
 *
 * This is used to block any public request not authorized
 * by a proper known client
 */
public class ClientApp {

    //**********************************************************
    // properties
    //**********************************************************

    private String clientId;

    private String clientSecret;

    private String redirect;

    private Boolean staticHTML;

    private Boolean ajaxLogin = false;

    private static Map<String,ClientApp> map = new HashMap<String,ClientApp>();

    static {
        map.put(Locals.USER_CLIENT_ID, new ClientApp().setClientId(Locals.USER_CLIENT_ID).setClientSecret(Locals.USER_CLIENT_SECRET).setAjaxLogin(true));
        map.put(Locals.STAFF_CLIENT_ID, new ClientApp().setClientId(Locals.STAFF_CLIENT_ID).setClientSecret(Locals.STAFF_CLIENT_SECRET).setAjaxLogin(true));
    }

    //**********************************************************
    // getters and setters
    //**********************************************************

    public String getClientId() {
        return clientId;
    }

    public ClientApp setClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public ClientApp setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
        return this;
    }

    public String getRedirect() {
        return redirect;
    }

    public ClientApp setRedirect(String redirect) {
        this.redirect = redirect;
        return this;
    }

    public Boolean getStaticHTML() {
        return staticHTML;
    }

    public ClientApp setStaticHTML(Boolean staticHTML) {
        this.staticHTML = staticHTML;
        return this;
    }

    public Boolean getAjaxLogin() {
        return ajaxLogin;
    }

    public ClientApp setAjaxLogin(Boolean ajaxLogin) {
        this.ajaxLogin = ajaxLogin;
        return this;
    }

    public static boolean validate(String clientId, String clientSecret){
        ClientApp clientApp = load(clientId);
        return clientApp != null && clientApp.getClientSecret().equals(clientSecret);
    }

    public static ClientApp load(String clientId){
        return map.get(clientId);
    }
}
