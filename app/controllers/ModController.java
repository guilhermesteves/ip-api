package controllers;

import actions.AdminAction;
import actions.Cors;
import actions.Error;
import actions.ModAction;
import com.fasterxml.jackson.databind.JsonNode;
import models.common.constants.Constants;
import models.common.constants.MESSAGES;
import models.history.StaffHistory;
import models.common.json.JsonContext;
import models.common.json.JsonHelper;
import models.users.Admin;
import models.users.Mod;
import models.users.ModLevel;
import models.users.ModList;
import models.users.auth.ModAuth;
import play.mvc.Controller;
import play.mvc.Result;
import utils.Utils;

/**
 * May the build success be with you
 * With great problems, comes great help from @guilhermesteves
 *
 * All methods for manipulating Mods belongs
 * here in this Controller
 *
 * @see models.users.Mod
 * @see models.users.ModList
 * @see models.users.ModLevel
 */
@Cors
@Error
public class ModController extends Controller {

    // Constants Helpers
    private static String MOD_CLASS = Utils.getCollectionFromClass(Mod.class);

    //**********************************************************
    // CRUD & My
    //**********************************************************

    @ModAction
    public static Result my() {

        ModAuth modAuth = (ModAuth) ctx().args.get(Constants.MOD_AUTH);
        Mod mod = modAuth.getMod();

        if (mod == null)
            return notFound(JsonHelper.error(MESSAGES.MOD.NOT_FOUND));

        return ok(mod.toJson(JsonContext.DEFAULT));
    }

    @ModAction(modLevel = 0)
    public static Result create() {
        Mod mod =  Mod.fromJson(request().body().asJson());

        mod.create();

        Mod author = (Mod) ctx().args.get(Constants.MOD_AUTH);
        StaffHistory.create(author,
                mod.getId(), MOD_CLASS, mod.getDetails());

        return ok(mod.toJson(JsonContext.DEFAULT));
    }

    @ModAction(modLevel = 2)
    public static Result list(Integer offset, Integer limit, Integer modLevel, String board){
        JsonNode array = ModList.list(offset, limit, modLevel, board).toJson(JsonContext.REF);

        return ok(array);
    }

    @ModAction(modLevel = 2)
    public static Result load(String id){
        Mod mod =  Mod.load(id);

        if (mod == null)
            return notFound(JsonHelper.error(MESSAGES.MOD.NOT_FOUND));

        return ok(mod.toJson(JsonContext.DEFAULT));
    }

    @ModAction
    public static Result update() {

        Mod toUpdate = (Mod) ctx().args.get(Constants.MOD_AUTH);

        JsonNode json = request().body().asJson();
        Mod modFromJson =  Mod.fromJson(json);

        toUpdate.update(modFromJson);

        StaffHistory.update(toUpdate,
                toUpdate.getId(), MOD_CLASS, toUpdate.getDetails(), toUpdate.getChanges(modFromJson));

        return ok(toUpdate.toJson(JsonContext.DEFAULT));
    }

    @ModAction(modLevel = 0)
    public static Result updateById(String id) {

        Mod toUpdate = Mod.load(id);

        if (toUpdate == null)
            return notFound(JsonHelper.error(MESSAGES.MOD.NOT_FOUND));

        JsonNode json = request().body().asJson();
        Mod modFromJson =  Mod.fromJson(json);

        toUpdate.update(modFromJson);

        Mod author = (Mod) ctx().args.get(Constants.MOD_AUTH);
        StaffHistory.update(author,
                toUpdate.getId(), MOD_CLASS, toUpdate.getDetails(), toUpdate.getChanges(modFromJson));


        return ok(toUpdate.toJson(JsonContext.DEFAULT));
    }

    @ModAction(modLevel = 0)
    public static Result delete(String id) {

        Mod toDelete = Mod.load(id);

        if (toDelete == null)
            return notFound(JsonHelper.error(MESSAGES.MOD.NOT_FOUND));

        if (toDelete.getAccessLevel() == ModLevel.GLOBAL)
            return forbidden(JsonHelper.error(MESSAGES.MOD.GLOBAL_CANT_DELETE_GLOBAL));

        Mod.delete(id);

        Mod author = (Mod) ctx().args.get(Constants.MOD_AUTH);
        StaffHistory.delete(author,
                toDelete.getId(), MOD_CLASS, toDelete.getDetails());

        return ok();
    }

    //**********************************************************
    // Change State
    //**********************************************************

    @ModAction(modLevel = 0)
    public static Result activate(String id) {

        Mod toActivate = Mod.load(id);

        if (toActivate == null)
            return notFound(JsonHelper.error(MESSAGES.MOD.NOT_FOUND));

        toActivate.activate();

        Mod author = (Mod) ctx().args.get(Constants.MOD_AUTH);
        StaffHistory.activate(author,
                toActivate.getId(), MOD_CLASS, toActivate.getDetails());

        return ok();
    }

    @ModAction(modLevel = 0)
    public static Result deactivate(String id) {

        Mod toDeactivate = Mod.load(id);

        if (toDeactivate == null)
            return notFound(JsonHelper.error(MESSAGES.MOD.NOT_FOUND));

        toDeactivate.deactivate();

        Mod author = (Mod) ctx().args.get(Constants.MOD_AUTH);
        StaffHistory.deactivate(author,
                toDeactivate.getId(), MOD_CLASS, toDeactivate.getDetails());

        return ok();
    }

    //**********************************************************
    // Admin Methods
    //**********************************************************

    @AdminAction
    public static Result createAsAdmin() {

        Mod mod =  Mod.fromJson(request().body().asJson());
        mod.create();

        Admin author = (Admin) ctx().args.get(Constants.ADMIN_AUTH);
        StaffHistory.create(author,
                mod.getId(), MOD_CLASS, mod.getDetails());

        return ok(mod.toJson(JsonContext.DEFAULT));
    }

    @AdminAction
    public static Result listAsAdmin(Integer offset, Integer limit, Integer modLevel, String board) {

        JsonNode array = ModList.list(offset, limit, modLevel, board).toJson(JsonContext.REF);
        return ok(array);
    }

    @AdminAction
    public static Result loadAsAdmin(String id) {

        Mod mod =  Mod.load(id);

        if (mod == null)
            return notFound(JsonHelper.error(MESSAGES.MOD.NOT_FOUND));

        return ok(mod.toJson(JsonContext.DEFAULT));
    }

    @AdminAction
    public static Result updateAsAdmin(String id) {

        Mod mod = Mod.load(id);

        if (mod == null)
            return notFound(JsonHelper.error(MESSAGES.MOD.NOT_FOUND));

        JsonNode json = request().body().asJson();
        Mod modFromJson =  Mod.fromJson(json);

        mod.update(modFromJson);

        Admin author = (Admin) ctx().args.get(Constants.ADMIN_AUTH);
        StaffHistory.update(author,
                mod.getId(), MOD_CLASS, mod.getDetails(), mod.getChanges(modFromJson));

        return ok(mod.toJson(JsonContext.DEFAULT));
    }

    @AdminAction
    public static Result deleteAsAdmin(String id) {

        Mod mod = Mod.load(id);

        if (mod == null)
            return notFound(JsonHelper.error(MESSAGES.MOD.NOT_FOUND));

        Mod.delete(id);

        Admin author = (Admin) ctx().args.get(Constants.ADMIN_AUTH);
        StaffHistory.delete(author,
                mod.getId(), MOD_CLASS, mod.getDetails());

        return ok();
    }

    //**********************************************************
    // Change State
    //**********************************************************

    @AdminAction
    public static Result activateAsAdmin(String id) {

        Mod mod = Mod.load(id);

        if (mod == null)
            return notFound(JsonHelper.error(MESSAGES.MOD.NOT_FOUND));

        mod.activate();

        Admin author = (Admin) ctx().args.get(Constants.ADMIN_AUTH);
        StaffHistory.activate(author,
                mod.getId(), MOD_CLASS, mod.getDetails());

        return ok();
    }

    @AdminAction
    public static Result deactivateAsAdmin(String id) {

        Mod mod = Mod.load(id);

        if (mod == null)
            return notFound(JsonHelper.error(MESSAGES.MOD.NOT_FOUND));

        mod.deactivate();

        Admin author = (Admin) ctx().args.get(Constants.ADMIN_AUTH);
        StaffHistory.deactivate(author, mod.getId(), MOD_CLASS, mod.getDetails());

        return ok();
    }
}
