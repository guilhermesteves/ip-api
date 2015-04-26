import com.fasterxml.jackson.databind.node.ObjectNode;
import models.common.DisplayableException;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.api.mvc.EssentialFilter;
import play.filters.gzip.GzipFilter;
import play.libs.F;
import play.libs.Json;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;

import java.lang.reflect.Method;

/**
 * May the build success be with you
 * With great problems, comes great help from @guilhermesteves
 *
 * This is the Global file. Be careful and don't
 * do anything stupid, alright son?
 *
 */
public class Global extends GlobalSettings {

    @Override
    public <T extends EssentialFilter> Class<T>[] filters() {
        return new Class[]{GzipFilter.class};
    }

    @Override
    public F.Promise<Result> onError(Http.RequestHeader request, Throwable e) {
        ObjectNode json = Json.newObject();

        String msg;
        DisplayableException exception = null;

        if (e instanceof DisplayableException) {
            exception = (DisplayableException) e;
        } else if (e.getCause() instanceof DisplayableException) {
            exception = (DisplayableException) e.getCause();
        }

        if (exception != null) {
            ObjectNode errors = exception.getErrors();

            if (errors != null) {
                json.put("error", errors);
            } else {
                msg = e.getMessage();
                Logger.info(msg);
                json.put("error", msg);
            }
        } else {
            msg = e.getMessage();
            Logger.info(msg);
            json.put("error", msg);
        }

        F.Promise<Result> pure = F.Promise.<Result>pure(Results.badRequest(json));

        return pure;
    }

    @Override
    public Action onRequest(Http.Request request, Method actionMethod) {
        return new Action.Simple() {
            public F.Promise<Result> call(Http.Context ctx) throws Throwable {
                ctx.session().clear();
                F.Promise<Result> result = delegate.call(ctx);
                ctx.response().setHeader("Cache-Control","no-cache");
                return result;
            }
        };
    }

    @Override
    public void onStart(Application app) {
        super.onStart(app);
    }
}
