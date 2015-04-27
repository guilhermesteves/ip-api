package controllers;

import actions.Cors;
import actions.Error;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * May the build success be with you
 * With great problems, comes great help from @guilhermesteves
 *
 * All methods for simple routes that
 * dont require any logic in models
 * should be here
 */
@Cors
@Error
public class ApplicationController extends Controller {

    //**********************************************************
    // default result
    //**********************************************************

    private static ObjectNode json = Json.newObject();

    public static Result index() {
        return ok(json.put("app", "Tokumei Kokoro"));
    }

    public static Result apiV1() {
        return ok(json.put("api", "v1"));
    }

}
