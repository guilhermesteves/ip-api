package actions;

import models.common.json.JsonHelper;
import models.common.constants.Constants;
import models.users.auth.UserAuth;
import play.libs.F;
import play.mvc.*;

/**
 * May the build success be with you
 * With great problems, comes great help from @guilhermesteves
 *
 * Policy to restrict the access for Users
 */
public class UserPolicy extends Action<UserAction> {

    public F.Promise<Result> call(Http.Context ctx) throws Throwable {

        UserAuth auth = null;
        String token = ctx.request().getHeader("Authorization");

        if (token != null) {
            token = token.replace(Constants.BEARER, "").trim();
            auth = UserAuth.getUserAuthByToken(token);
        }

        // TODO: SAVE IN DATABASE

        if (!configuration.block() ||
                ( auth != null && (!configuration.onlyActive() || auth.getUser().getActive()))) {

            ctx.args.put(Constants.USER_AUTH, auth);
            return delegate.call(ctx);

        } else {
            return F.Promise.<Result>pure(Results.unauthorized(JsonHelper.unauthorized()));
        }
    }
}