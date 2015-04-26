package actions;

import models.common.json.JsonHelper;
import models.common.constants.Constants;
import models.users.auth.AdminAuth;
import play.libs.F;
import play.mvc.*;

/**
 * May the build success be with you
 * With great problems, comes great help from @guilhermesteves
 *
 * Policy to restrict the access for Admins
 */
public class AdminPolicy extends Action<AdminAction> {

    public F.Promise<Result> call(Http.Context ctx) throws Throwable {

        AdminAuth auth = null;
        String token = ctx.request().getHeader("Authorization");

        if (token != null) {
            token = token.replace(Constants.BEARER, "").trim();
            auth = AdminAuth.getAdminAuthByToken(token);
        }

        // TODO: SAVE IN DATABASE

        if (!configuration.block() || (auth != null &&
                (configuration.superAdmin() && !auth.getAdmin().getSuperAdmin()) &&
                (!configuration.onlyActive() || auth.getAdmin().getActive()))) {
            ctx.args.put(Constants.ADMIN_AUTH, auth);
            return delegate.call(ctx);

        } else {
            return F.Promise.<Result>pure(Results.unauthorized(JsonHelper.unauthorized()));
        }
    }
}
