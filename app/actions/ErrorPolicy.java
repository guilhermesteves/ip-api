package actions;

import com.fasterxml.jackson.databind.node.ObjectNode;
import models.common.DisplayableException;
import models.common.json.JsonHelper;
import play.Logger;
import play.libs.F;
import play.libs.Json;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;

/**
 * May the build success be with you
 * With great problems, comes great help from @guilhermesteves
 *
 * Policy to handle all errors thrown
 * in the Controllers and/or models
 *
 * @see models.common.DisplayableException
 */
public  class ErrorPolicy extends Action<Cors> {

    public F.Promise<Result> call(Http.Context context) throws Throwable {

        F.Promise<Result> call;

        try {
            call = delegate.call(context);

        } catch (Exception e) {

            e.printStackTrace();

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
                    JsonHelper.error(errors);
                } else {
                    msg = e.getMessage();
                    Logger.info(msg);
                    JsonHelper.error(msg);
                }

            } else {
                msg = e.getMessage();
                Logger.info(msg);
                JsonHelper.error(msg);
            }

            call = F.Promise.<Result>pure(Results.badRequest(json));
        }

        return call;
    }

}
