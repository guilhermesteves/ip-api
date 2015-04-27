package controllers;

import actions.AdminAction;
import com.fasterxml.jackson.databind.JsonNode;
import models.common.json.JsonContext;
import models.common.json.JsonHelper;
import models.common.constants.MESSAGES;
import models.system.Setting;
import models.system.SettingList;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * May the build success be with you
 * With great problems, comes great help from @guilhermesteves
 *
 * All methods for define settings that will
 * change the behavior of the application
 * must be here
 */
public class SettingsController extends Controller {

    //**********************************************************
    // CRUD
    //**********************************************************

    @AdminAction(superAdmin = true)
    public static Result create() {

        Setting setting =  Setting.fromJson(request().body().asJson());
        setting.create();

        return ok(setting.toJson(JsonContext.DEFAULT));
    }

    public static Result list(Integer offset, Integer limit){
        JsonNode array = SettingList.list(offset, limit).toJson(JsonContext.REF);
        return ok(array);
    }

    @AdminAction
    public static Result load(String id) {

        Setting setting =  Setting.load(id);

        if (setting == null)
            return notFound(JsonHelper.error(MESSAGES.SETTING.NOT_FOUND));

        return ok(setting.toJson(JsonContext.DEFAULT));
    }

    @AdminAction
    public static Result update(String id) {

        JsonNode json = request().body().asJson();
        Setting settingFromJson =  Setting.fromJson(json);

        Setting toUpdate = Setting.load(id);

        if (toUpdate == null) {

            toUpdate = Setting.loadByKey(settingFromJson.getKey());

            if (toUpdate == null)
                return notFound(JsonHelper.error(MESSAGES.SETTING.NOT_FOUND));
        }

        toUpdate.setValue(settingFromJson.getValueAsString());
        toUpdate.update();

        return ok(toUpdate.toJson(JsonContext.DEFAULT));
    }

    @AdminAction
    public static Result delete(String id) {

        Setting toDelete = Setting.load(id);

        if (toDelete == null)
            return notFound(JsonHelper.error(MESSAGES.SETTING.NOT_FOUND));

        Setting.delete(id);

        return ok();
    }
}
