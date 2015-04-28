package models.history;

import com.fasterxml.jackson.databind.JsonNode;
import models.common.BaseEnum;
import models.common.json.JsonHelper;
import models.common.json.JsonSerializable;
import models.common.constants.FIELDS;

/**
 * May the build success be with you
 * With great problems, comes great help from @guilhermesteves
 */
public enum ActionEntity implements BaseEnum, JsonSerializable {

    //**********************************************************
    // values
    //**********************************************************

    USUARIO(" usuário "),
    UPDATE(" atualizou "),
    DELETE(" deletou "),
    BAN(" baniu "),
    ARCHIVE(" arquivou ");

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

    ActionEntity(String description) {
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

    public static ActionEntity fromJson(JsonNode json) {
        return values()[json.get(FIELDS.ENUM.ID).asInt()];
    }

    public static ActionEntity fromJson(String fieldName, JsonNode json){
        JsonHelper j = new JsonHelper(json);
        return j.hasValue(fieldName) ? ActionEntity.fromJson(j.get(fieldName)) : null;
    }

}
