package models.system;

import com.fasterxml.jackson.databind.JsonNode;
import models.common.BaseEnum;
import models.common.constants.FIELDS;
import models.common.json.JsonHelper;
import models.common.json.JsonSerializable;

/**
 * May the build success be with you
 * With great problems, comes great help from @guilhermesteves
 *
 * Settings Types
 */
public enum ValueType implements BaseEnum, JsonSerializable {

    //**********************************************************
    // values
    //**********************************************************

    STRING("String"),
    INTEGER("Integer"),
    DOUBLE("Double"),
    DATE("Date"),
    BOOLEAN("Boolean");

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

    ValueType(String description) {
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

    public static ValueType fromJson(JsonNode json) {
        return values()[json.get(FIELDS.ENUM.ID).asInt()];
    }

    public static ValueType fromJson(String fieldName, JsonNode json){
        JsonHelper j = new JsonHelper(json);
        return j.hasValue(fieldName) ? ValueType.fromJson(j.get(fieldName)) : null;
    }
}
