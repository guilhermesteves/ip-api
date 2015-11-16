package controllers.auth;

import actions.ClientAction;
import actions.UserAction;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.common.json.JsonHelper;
import models.common.constants.ClientApp;
import models.common.constants.Constants;
import models.common.constants.FIELDS;
import models.users.auth.ModAuth;
import models.users.auth.UserAuth;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * May the build success be with you
 * With great problems, comes great help from @guilhermesteves
 *
 * Anypath with OPTIONS will use this class
 *
 * @see models.users.User
 */
public class UserAuthentications extends Controller {

    public static Result login(String client) {

        ClientApp clientApp = ClientApp.load(client);

        if (clientApp == null)
            return notFound();

        String identifier;

        if (request().body().asFormUrlEncoded() != null) {
            identifier = request().body().asFormUrlEncoded().get(FIELDS.LOGIN.IDENTIFIER)[0];
        } else {
            identifier = (request().body().asJson().has(FIELDS.LOGIN.IDENTIFIER) && !request().body().asJson().get(FIELDS.LOGIN.IDENTIFIER).isNull()) ? request().body().asJson().get(FIELDS.LOGIN.IDENTIFIER).asText() : "";
        }

        String authCode = UserAuth.login(identifier);

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

    @UserAction
    public static Result logout() {
        UserAuth auth = (UserAuth)ctx().args.get(Constants.USER_AUTH);
        auth.logout();
        return ok();
    }

    @ClientAction
    @BodyParser.Of(BodyParser.Json.class)
    public static Result exchangeAuthCode() {

        JsonNode body = request().body().asJson();
        String authCode = body.get(Constants.AUTH_CODE).asText();

        UserAuth auth = UserAuth.exchangeAuthCode(authCode);

        if (auth != null) {
            ObjectNode json = Json.newObject();
            json.put(Constants.TOKEN, auth.getToken());
            json.put(Constants.REFRESH_TOKEN, auth.getRefreshToken());
            json.put(Constants.EXPIRES_IN, UserAuth.TOKEN_EXPIRATION);

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

        UserAuth auth = UserAuth.refreshToken(refreshToken, token);

        if (auth != null && auth.getToken() != null) {

            ObjectNode json = Json.newObject();
            json.put(Constants.TOKEN, auth.getToken());
            json.put(Constants.EXPIRES_IN, ModAuth.TOKEN_EXPIRATION);

            return ok(json);

        } else
            return unauthorized(JsonHelper.unauthorized());
    }
}
