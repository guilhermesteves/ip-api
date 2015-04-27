package controllers.auth;

import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

/**
 * May the build success be with you
 * With great problems, comes great help from @guilhermesteves
 *
 * Anypath with OPTIONS will use this method
 */
public class Cors extends Controller {

    public static Result options(String anyPath) {

        response().setHeader("Access-Control-Allow-Origin","*");
        response().setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, HEAD");
        response().setHeader("Access-Control-Max-Age", "3600");
        response().setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");

        return ok();
    }
}
