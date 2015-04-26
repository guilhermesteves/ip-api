package actions;

import models.common.json.JsonHelper;
import models.common.constants.ClientApp;
import models.common.constants.Constants;
import play.libs.F;
import play.mvc.*;

/**
 * May the build success be with you
 * With great problems, comes great help from @guilhermesteves
 *
 * Policy to restrict only the registered
 * clients in ClientApp to the system
 *
 * @see models.common.constants.ClientApp
 */
public class ClientPolicy extends Action<ClientAction> {

    public F.Promise<Result> call(Http.Context ctx) throws Throwable {

        boolean validated = false;
        String basicAuth = ctx.request().getHeader("Authorization");

        if (basicAuth != null && basicAuth.contains(Constants.BASIC)) {
            basicAuth = basicAuth.replace(Constants.BASIC,"").trim();

            String clientId = basicAuth.substring(0, basicAuth.indexOf(":"));
            String clientSecret = basicAuth.substring(basicAuth.indexOf(":")+1);

            validated = ClientApp.validate(clientId, clientSecret);
        }

        if (validated)
            return delegate.call(ctx);
        else
            return F.Promise.<Result>pure(Results.unauthorized(JsonHelper.unauthorized()));
    }
}
