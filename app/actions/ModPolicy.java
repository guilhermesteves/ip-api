package actions;

import models.common.json.JsonHelper;
import models.common.constants.Constants;
import models.users.auth.ModAuth;
import play.libs.F;
import play.mvc.*;

/**
 * May the build success be with you
 * With great problems, comes great help from @guilhermesteves
 *
 * Policy to restrict the access for Mods
 */
public class ModPolicy extends Action<ModAction> {

    public F.Promise<Result> call(Http.Context ctx) throws Throwable {

        ModAuth modAuth = null;
        String token = ctx.request().getHeader("Authorization");

        if (token != null) {
            token = token.replace(Constants.BEARER, "").trim();
            modAuth = ModAuth.getModAuthByToken(token);
        }

        // TODO: SAVE IN DATABASE

        // Mod must be active and must have access to particular
        // action based on their level
        if (!configuration.block() || (modAuth != null &&
                        (configuration.modLevel() < modAuth.getMod().getAccessLevel().getLevel()) &&
                        (!configuration.onlyActive() || modAuth.getMod().getActive()))) {

            ctx.args.put(Constants.MOD_AUTH, modAuth);
            return delegate.call(ctx);

        } else {
            return F.Promise.<Result>pure(Results.unauthorized(JsonHelper.unauthorized()));
        }
    }
}
