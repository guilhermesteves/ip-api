package controllers.auth;

import actions.AdminAction;
import actions.ClientAction;
import actions.Cors;
import actions.Error;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.common.constants.ClientApp;
import models.common.constants.Constants;
import models.common.constants.FIELDS;
import models.common.constants.MESSAGES;
import models.history.StaffHistory;
import models.common.json.JsonHelper;
import models.users.Admin;
import models.users.auth.AdminAuth;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import utils.Utils;

/**
 * May the build success be with you
 * With great problems, comes great help from @guilhermesteves
 *
 * All authentication methods for Admins
 *
 * @see models.users.Admin
 */
@Cors
@Error
public class AdminAuthentications  extends Controller {

    public static Result login(String client) {

        ClientApp clientApp = ClientApp.load(client);

        if (clientApp == null)
            return notFound();

        String name, password;

        if (request().body().asFormUrlEncoded() != null) {
            name = request().body().asFormUrlEncoded().get(FIELDS.LOGIN.NAME)[0];
            password = request().body().asFormUrlEncoded().get(FIELDS.LOGIN.PASSWORD)[0];
        } else {
            name = (request().body().asJson().has(FIELDS.LOGIN.NAME) && !request().body().asJson().get(FIELDS.LOGIN.NAME).isNull()) ? request().body().asJson().get(FIELDS.LOGIN.NAME).asText() : "";
            password = (request().body().asJson().has(FIELDS.LOGIN.PASSWORD) && !request().body().asJson().get(FIELDS.LOGIN.PASSWORD).isNull()) ?  request().body().asJson().get(FIELDS.LOGIN.PASSWORD).asText() : "";
        }

        String authCode = AdminAuth.login(name, password);

        if (authCode == null && clientApp.getAjaxLogin())
            return unauthorized(JsonHelper.unauthorized());
        else if(authCode == null)
            return redirect("/");

        if (clientApp.getAjaxLogin()) {
            ObjectNode json = Json.newObject();
            json.put(Constants.AUTH_CODE, authCode);

            return ok(json);

        } else if (clientApp.getStaticHTML()) {
            return redirect(clientApp.getRedirect() +"#/a/"+authCode);
        } else {
            return redirect(clientApp.getRedirect() +"?authCode="+authCode);
        }
    }

    @AdminAction
    public static Result logout() {
        AdminAuth auth = (AdminAuth)ctx().args.get(Constants.ADMIN_AUTH);
        auth.logout();
        return ok();
    }

    @ClientAction
    @BodyParser.Of(BodyParser.Json.class)
    public static Result exchangeAuthCode() {

        JsonNode body = request().body().asJson();
        String authCode = body.get(Constants.AUTH_CODE).asText();

        AdminAuth auth = AdminAuth.exchangeAuthCode(authCode);

        if (auth != null) {
            // returning json
            ObjectNode json = Json.newObject();
            json.put(Constants.TOKEN, auth.getToken());
            json.put(Constants.REFRESH_TOKEN, auth.getRefreshToken());
            json.put(Constants.EXPIRES_IN, AdminAuth.TOKEN_EXPIRATION);

            return ok(json);

        } else
            return unauthorized(JsonHelper.unauthorized());

    }

    @ClientAction
    @BodyParser.Of(BodyParser.Json.class)
    public static Result refreshToken() {

        JsonNode body = request().body().asJson();
        String token = body.get(Constants.TOKEN).asText();
        String refreshToken = body.get(Constants.REFRESH_TOKEN).asText();

        AdminAuth auth = AdminAuth.refreshToken(refreshToken, token);

        if (auth != null && auth.getToken() != null) {

            ObjectNode json = Json.newObject();
            json.put(Constants.TOKEN, auth.getToken());
            json.put(Constants.EXPIRES_IN, AdminAuth.TOKEN_EXPIRATION);

            return ok(json);

        } else
            return unauthorized(JsonHelper.unauthorized());
    }

    @AdminAction
    @BodyParser.Of(BodyParser.Json.class)
    public static Result updatePassword() {
        JsonNode json = request().body().asJson();
        JsonHelper j = new JsonHelper(json);

        String oldPassword = j.getString(FIELDS.BASE_USER.STAFF.OLD_PASSWORD);
        String newPassword = j.getString(FIELDS.BASE_USER.STAFF.NEW_PASSWORD);

        if (Utils.isNullOrEmpty(oldPassword) || Utils.isNullOrEmpty(newPassword))
            return badRequest(JsonHelper.error(MESSAGES.MISSING_INFO));

        AdminAuth adminAuth = (AdminAuth) ctx().args.get(Constants.ADMIN_AUTH);
        Admin admin = adminAuth.getAdmin();

        if (!admin.isPasswordCorrect(oldPassword))
            return badRequest(JsonHelper.error(MESSAGES.INCORRECT_PASSWORD));

        admin.updatePassword(newPassword);

        StaffHistory.changePassword(admin,
                admin.getId(), Utils.getCollectionFromClass(Admin.class), admin.getDetails());

        return ok();
    }
}
