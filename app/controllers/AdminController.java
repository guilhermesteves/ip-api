package controllers;

import actions.AdminAction;
import actions.Cors;
import actions.Error;
import com.fasterxml.jackson.databind.JsonNode;
import models.common.constants.Constants;
import models.common.constants.MESSAGES;
import models.history.StaffHistory;
import models.common.json.JsonContext;
import models.common.json.JsonHelper;
import models.users.Admin;
import models.users.AdminList;
import models.users.auth.AdminAuth;
import play.mvc.Controller;
import play.mvc.Result;
import utils.Utils;

/**
 * May the build success be with you
 * With great problems, comes great help from @guilhermesteves
 *
 * All methods for manipulating Admins belongs
 * here in this Controller
 *
 * @see models.users.Admin
 * @see models.users.AdminList
 */
@Cors
@Error
public class AdminController extends Controller {

    // Constants Helpers
    private static String ADMIN_CLASS = Utils.getCollectionFromClass(Admin.class);

    //**********************************************************
    // CRUD & My
    //**********************************************************

    @AdminAction
    public static Result my() {

        AdminAuth adminAuth = (AdminAuth) ctx().args.get(Constants.ADMIN_AUTH);
        Admin admin = adminAuth.getAdmin();

        if (admin == null)
            return notFound(JsonHelper.error(MESSAGES.ADMIN.NOT_FOUND));

        return ok(admin.toJson(JsonContext.DEFAULT));
    }

    @AdminAction(superAdmin = true)
    public static Result create() {

        Admin admin =  Admin.fromJson(request().body().asJson());
        admin.create();

        StaffHistory.create(admin,
                admin.getId(), ADMIN_CLASS, admin.getDetails());

        return ok(admin.toJson(JsonContext.DEFAULT));
    }

    public static Result list(Integer offset, Integer limit, Boolean superAdmin){

        JsonNode array = AdminList.list(offset, limit, superAdmin).toJson(JsonContext.REF);
        return ok(array);
    }

    @AdminAction
    public static Result load(String id) {

        Admin admin =  Admin.load(id);

        if (admin == null)
            return notFound(JsonHelper.error(MESSAGES.ADMIN.NOT_FOUND));

        return ok(admin.toJson(JsonContext.DEFAULT));
    }

    @AdminAction
    public static Result update() {

        Admin toUpdate = (Admin) ctx().args.get(Constants.ADMIN_AUTH);

        JsonNode json = request().body().asJson();
        Admin adminFromJson =  Admin.fromJson(json);

        toUpdate.update(adminFromJson);

        StaffHistory.update(toUpdate,
                toUpdate.getId(), ADMIN_CLASS, toUpdate.getDetails(), toUpdate.getChanges(adminFromJson));

        return ok(toUpdate.toJson(JsonContext.DEFAULT));
    }

    @AdminAction(superAdmin = true)
    public static Result update(String id) {

        Admin toUpdate = Admin.load(id);

        if (toUpdate == null)
            return notFound(JsonHelper.error(MESSAGES.ADMIN.NOT_FOUND));

        JsonNode json = request().body().asJson();
        Admin adminFromJson =  Admin.fromJson(json);

        toUpdate.update(adminFromJson);

        Admin author = (Admin) ctx().args.get(Constants.ADMIN_AUTH);
        StaffHistory.update(author,
                toUpdate.getId(), ADMIN_CLASS, toUpdate.getDetails(), toUpdate.getChanges(adminFromJson));

        return ok(toUpdate.toJson(JsonContext.DEFAULT));
    }

    @AdminAction
    public static Result delete(String id) {

        Admin author = (Admin) ctx().args.get(Constants.ADMIN_AUTH);
        Admin toDelete = Admin.load(id);

        if (toDelete == null)
            return notFound(JsonHelper.error(MESSAGES.ADMIN.NOT_FOUND));

        if (toDelete.getSuperAdmin() && !author.getSuperAdmin())
            return notFound(JsonHelper.error(MESSAGES.ADMIN.ADMIN_CANT_SUPER_ADMIN));

        Admin.delete(id);

        StaffHistory.delete(author,
                toDelete.getId(), ADMIN_CLASS, toDelete.getDetails());

        return ok();
    }

    //**********************************************************
    // Change State
    //**********************************************************

    @AdminAction
    public static Result activate(String id) {

        Admin author = (Admin) ctx().args.get(Constants.ADMIN_AUTH);
        Admin toActivate = Admin.load(id);

        if (toActivate == null)
            return notFound(JsonHelper.error(MESSAGES.ADMIN.NOT_FOUND));

        if (toActivate.getSuperAdmin() && !author.getSuperAdmin())
            return notFound(JsonHelper.error(MESSAGES.ADMIN.ADMIN_CANT_SUPER_ADMIN));

        toActivate.activate();

        StaffHistory.activate(author,
                toActivate.getId(), ADMIN_CLASS, toActivate.getDetails());
        return ok();
    }

    @AdminAction
    public static Result deactivate(String id) {

        Admin author = (Admin) ctx().args.get(Constants.ADMIN_AUTH);
        Admin toDeactivate = Admin.load(id);

        if (toDeactivate == null)
            return notFound(JsonHelper.error(MESSAGES.ADMIN.NOT_FOUND));

        if (toDeactivate.getSuperAdmin() && !author.getSuperAdmin())
            return notFound(JsonHelper.error(MESSAGES.ADMIN.ADMIN_CANT_SUPER_ADMIN));

        toDeactivate.deactivate();

        StaffHistory.deactivate(author,
                toDeactivate.getId(), ADMIN_CLASS, toDeactivate.getDetails());
        return ok();
    }
}
