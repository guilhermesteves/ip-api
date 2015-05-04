package controllers;

import actions.*;
import actions.Error;
import com.fasterxml.jackson.databind.JsonNode;
import models.*;
import models.common.constants.Constants;
import models.common.constants.MESSAGES;
import models.history.StaffHistory;
import models.common.json.JsonContext;
import models.common.json.JsonHelper;
import models.users.Admin;
import models.users.Mod;
import models.users.ModLevel;
import play.mvc.Controller;
import play.mvc.Result;
import utils.Utils;

/**
 * May the build success be with you
 * With great problems, comes great help from @guilhermesteves
 *
 * All methods for manipulating Boards belongs
 * here in this Controller
 *
 * @see models.IbBoard
 * @see models.IbBoardList
 */
@Cors
@Error
public class BoardController extends Controller {

    // Constants Helpers
    private static String BOARD_CLASS = Utils.getCollectionFromClass(IbBoard.class);

    //**********************************************************
    // User Methods
    //**********************************************************

    @UserAction
    public static Result list(Integer offset, Integer limit){
        JsonNode array = IbBoardList.list(offset, limit).toJson(JsonContext.REF);

        return ok(array);
    }

    @UserAction
    public static Result load(String id) {
        IbBoard ibBoard =  IbBoard.load(id);

        if (ibBoard == null)
            return notFound(JsonHelper.error(MESSAGES.BOARD.NOT_FOUND));

        // TODO: APPLY USER LOGIC

        return ok(ibBoard.toJson(JsonContext.DEFAULT));
    }

    @UserAction
    public static Result loadBySlug(String slug) {
        IbBoard ibBoard =  IbBoard.loadBySlug(slug);

        if (ibBoard == null)
            return notFound(JsonHelper.error(MESSAGES.BOARD.NOT_FOUND));

        // TODO: APPLY USER LOGIC

        return ok(ibBoard.toJson(JsonContext.DEFAULT));
    }

    @UserAction
    public static Result loadByLongSlug(String longSlug) {
        IbBoard ibBoard =  IbBoard.loadByLongSlug(longSlug);

        if (ibBoard == null)
            return notFound(JsonHelper.error(MESSAGES.BOARD.NOT_FOUND));

        // TODO: APPLY USER LOGIC

        return ok(ibBoard.toJson(JsonContext.DEFAULT));
    }

    @UserAction
    public static Result catalog(String slug) {
        IbThreadList catalog = IbBoard.loadCatalogBySlug(slug);

        if (catalog == null)
            return notFound(MESSAGES.BOARD.CATALOG_NOT_FOUND);

        return null;
        //return ok(catalog.toJson(JsonContext.USER));
    }

    //**********************************************************
    // Mod Methods
    //**********************************************************

    @ModAction(modLevel = 0)
    public static Result createAsMod() {

        IbBoard ibBoard =  IbBoard.fromJson(request().body().asJson());

        ibBoard.create();

        Mod author = (Mod) ctx().args.get(Constants.MOD_AUTH);
        StaffHistory.create(author,
                ibBoard.getId(), BOARD_CLASS, ibBoard.getDetails());

        return ok(ibBoard.toJson(JsonContext.DEFAULT));
    }

    @ModAction
    public static Result listAsMod(Integer offset, Integer limit){
        JsonNode array = IbBoardList.list(offset, limit).toJson(JsonContext.REF);

        return ok(array);
    }

    @ModAction
    public static Result loadAsMod(String id) {
        IbBoard ibBoard =  IbBoard.load(id);

        if (ibBoard == null)
            return notFound(JsonHelper.error(MESSAGES.BOARD.NOT_FOUND));

        return ok(ibBoard.toJson(JsonContext.DEFAULT));
    }

    @ModAction
    public static Result loadBySlugAsMod(String slug) {
        IbBoard ibBoard =  IbBoard.loadBySlug(slug);

        if (ibBoard == null)
            return notFound(JsonHelper.error(MESSAGES.BOARD.NOT_FOUND));

        return ok(ibBoard.toJson(JsonContext.DEFAULT));
    }

    @ModAction
    public static Result loadByLongSlugAsMod(String longSlug) {
        IbBoard ibBoard =  IbBoard.loadByLongSlug(longSlug);

        if (ibBoard == null)
            return notFound(JsonHelper.error(MESSAGES.BOARD.NOT_FOUND));

        return ok(ibBoard.toJson(JsonContext.DEFAULT));
    }

    @ModAction(modLevel = 2)
    public static Result updateAsMod(String id) {

        Mod author = (Mod) ctx().args.get(Constants.MOD_AUTH);
        IbBoard toUpdate = IbBoard.load(id);

        if (toUpdate == null)
            return notFound(JsonHelper.error(MESSAGES.BOARD.NOT_FOUND));

        // If the mod isn't Global, he must have this board
        if (author.getAccessLevel() != ModLevel.GLOBAL &&
                (author.getModeratingBoards() == null || !author.getModeratingBoards().contains(toUpdate)))
            return badRequest(JsonHelper.error(MESSAGES.MOD.DO_NOT_HAVE_BOARD));

        JsonNode json = request().body().asJson();
        IbBoard ibBoardFromJson =  IbBoard.fromJson(json);

        toUpdate.update(ibBoardFromJson);

        StaffHistory.update(author,
                toUpdate.getId(), BOARD_CLASS, toUpdate.getDetails(), toUpdate.getChanges(ibBoardFromJson));

        return ok(toUpdate.toJson(JsonContext.DEFAULT));
    }

    @ModAction(modLevel = 0)
    public static Result deleteAsMod(String id) {

        IbBoard toDelete = IbBoard.load(id);

        if (toDelete == null)
            return notFound(JsonHelper.error(MESSAGES.MOD.NOT_FOUND));

        IbBoard.delete(id);

        Mod author = (Mod) ctx().args.get(Constants.MOD_AUTH);
        StaffHistory.delete(author,
                toDelete.getId(), BOARD_CLASS, toDelete.getDetails());

        return ok();
    }

    @ModAction(modLevel = 0)
    public static Result activateAsMod(String id) {

        IbBoard toActivate = IbBoard.load(id);

        if (toActivate == null)
            return notFound(JsonHelper.error(MESSAGES.BOARD.NOT_FOUND));

        toActivate.activate();

        Mod author = (Mod) ctx().args.get(Constants.MOD_AUTH);
        StaffHistory.activate(author,
                toActivate.getId(), BOARD_CLASS, toActivate.getDetails());

        return ok();
    }

    @ModAction(modLevel = 0)
    public static Result deactivateAsMod(String id) {

        IbBoard toDeactivate = IbBoard.load(id);

        if (toDeactivate == null)
            return notFound(JsonHelper.error(MESSAGES.BOARD.NOT_FOUND));

        toDeactivate.deactivate();

        Mod author = (Mod) ctx().args.get(Constants.MOD_AUTH);
        StaffHistory.deactivate(author,
                toDeactivate.getId(), BOARD_CLASS, toDeactivate.getDetails());

        return ok();
    }

    @ModAction
    public static Result catalogAsMod(String slug) {
        IbThreadList catalog = IbBoard.loadCatalogBySlug(slug);

        if (catalog == null)
            return notFound(MESSAGES.BOARD.CATALOG_NOT_FOUND);

        return null;
        //return ok(catalog.toJson(JsonContext.MOD));
    }

    //**********************************************************
    // Admin Methods
    //**********************************************************

    @AdminAction
    public static Result createAsAdmin() {

        IbBoard ibBoard =  IbBoard.fromJson(request().body().asJson());

        ibBoard.create();

        Admin author = (Admin) ctx().args.get(Constants.ADMIN_AUTH);
        StaffHistory.create(author,
                ibBoard.getId(), BOARD_CLASS, ibBoard.getDetails());

        return ok(ibBoard.toJson(JsonContext.DEFAULT));
    }

    @AdminAction
    public static Result listAsAdmin(Integer offset, Integer limit){
        JsonNode array = IbBoardList.list(offset, limit).toJson(JsonContext.REF);

        return ok(array);
    }

    @AdminAction
    public static Result loadAsAdmin(String id) {
        IbBoard ibBoard =  IbBoard.load(id);

        if (ibBoard == null)
            return notFound(JsonHelper.error(MESSAGES.BOARD.NOT_FOUND));

        return ok(ibBoard.toJson(JsonContext.DEFAULT));
    }

    @AdminAction
    public static Result loadBySlugAsAdmin(String slug) {
        IbBoard ibBoard =  IbBoard.loadBySlug(slug);

        if (ibBoard == null)
            return notFound(JsonHelper.error(MESSAGES.BOARD.NOT_FOUND));

        return ok(ibBoard.toJson(JsonContext.DEFAULT));
    }

    @AdminAction
    public static Result loadByLongSlugAsAdmin(String longSlug) {
        IbBoard ibBoard =  IbBoard.loadByLongSlug(longSlug);

        if (ibBoard == null)
            return notFound(JsonHelper.error(MESSAGES.BOARD.NOT_FOUND));

        return ok(ibBoard.toJson(JsonContext.DEFAULT));
    }

    @AdminAction
    public static Result updateAsAdmin(String id) {

        IbBoard ibBoard = IbBoard.load(id);

        if (ibBoard == null)
            return notFound(JsonHelper.error(MESSAGES.BOARD.NOT_FOUND));

        JsonNode json = request().body().asJson();
        IbBoard ibBoardFromJson =  IbBoard.fromJson(json);

        ibBoard.update(ibBoardFromJson);

        Admin author = (Admin) ctx().args.get(Constants.ADMIN_AUTH);
        StaffHistory.update(author,
                ibBoard.getId(), BOARD_CLASS, ibBoard.getDetails(), ibBoard.getChanges(ibBoardFromJson));

        return ok(ibBoard.toJson(JsonContext.DEFAULT));
    }

    @ModAction(modLevel = 0)
    public static Result deleteAsAdmin(String id) {

        IbBoard toDelete = IbBoard.load(id);

        if (toDelete == null)
            return notFound(JsonHelper.error(MESSAGES.MOD.NOT_FOUND));

        IbBoard.delete(id);

        Admin author = (Admin) ctx().args.get(Constants.ADMIN_AUTH);
        StaffHistory.delete(author,
                toDelete.getId(), BOARD_CLASS, toDelete.getDetails());

        return ok();
    }

    @AdminAction
    public static Result activateAsAdmin(String id) {

        IbBoard toActivate = IbBoard.load(id);

        if (toActivate == null)
            return notFound(JsonHelper.error(MESSAGES.BOARD.NOT_FOUND));

        toActivate.activate();

        Admin author = (Admin) ctx().args.get(Constants.ADMIN_AUTH);
        StaffHistory.activate(author,
                toActivate.getId(), BOARD_CLASS, toActivate.getDetails());

        return ok();
    }

    @AdminAction
    public static Result deactivateAsAdmin(String id) {

        IbBoard toDeactivate = IbBoard.load(id);

        if (toDeactivate == null)
            return notFound(JsonHelper.error(MESSAGES.BOARD.NOT_FOUND));

        toDeactivate.deactivate();

        Admin author = (Admin) ctx().args.get(Constants.ADMIN_AUTH);
        StaffHistory.deactivate(author,
                toDeactivate.getId(), BOARD_CLASS, toDeactivate.getDetails());

        return ok();
    }

    @AdminAction
    public static Result catalogAsAdmin(String slug) {
        IbThreadList catalog = IbBoard.loadCatalogBySlug(slug);

        if (catalog == null)
            return notFound(MESSAGES.BOARD.CATALOG_NOT_FOUND);

        return null;
        //return ok(catalog.toJson(JsonContext.USER));
    }
}
