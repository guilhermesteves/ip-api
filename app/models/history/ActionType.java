package models.history;

import com.fasterxml.jackson.databind.JsonNode;
import models.common.json.JsonHelper;
import models.common.json.JsonSerializable;
import models.common.constants.FIELDS;
import models.common.BaseEnum;

/**
 * May the build success be with you
 * With great problems, comes great help from @guilhermesteves
 */
public enum ActionType implements BaseEnum, JsonSerializable {

    //**********************************************************
    // values
    //**********************************************************

    CREATE(" criou "),
    UPDATE(" atualizou "),
    DELETE(" deletou "),

    PASSWORD_CHANGE(" mudou a senha "),

    ACTIVATE(" ativou "),
    DEACTIVATE(" desativou "),

    BAN(" baniu "),
    ARCHIVE(" arquivo ");

    //**********************************************************
    // properties
    //**********************************************************

    private final String description;

    //**********************************************************
    // getters and setters
    //**********************************************************

    @Override
    public String getDescription() {
        return description;
    }

    //**********************************************************
    // constructor
    //**********************************************************


    ActionType(String description) {
        this.description = description;
    }

    //**********************************************************
    // json
    //**********************************************************

    @Override
    public JsonNode toJson(String context) {

        JsonHelper j = new JsonHelper();

        j.addInteger(FIELDS.ENUM.ID, ordinal());
        j.addString(FIELDS.ENUM.DESCRIPTION, getDescription());

        return j.getJSON();
    }

    public static ActionType fromJson(JsonNode json) {
        return values()[json.get(FIELDS.ENUM.ID).asInt()];
    }

    public static ActionType fromJson(String fieldName, JsonNode json){
        JsonHelper j = new JsonHelper(json);
        return j.hasValue(fieldName) ? ActionType.fromJson(j.get(fieldName)) : null;
    }

}
