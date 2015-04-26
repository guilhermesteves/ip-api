package actions;

import play.libs.F;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

/**
 * May the build success be with you
 * With great problems, comes great help from @guilhermesteves
 *
 * Policy to restrict Cors
 */
public class CorsPolicy extends Action<Cors> {

    public F.Promise<Result> call(Http.Context context) throws Throwable {
        Http.Response response = context.response();

        response.setHeader("Access-Control-Allow-Origin", configuration.value());
        response.setHeader("Access-Control-Allow-Headers", "X-Requested-With");

        return delegate.call(context);
    }
}
