package models.users;

import com.fasterxml.jackson.databind.JsonNode;
import models.common.BaseEnum;
import models.common.json.JsonHelper;
import models.common.json.JsonSerializable;
import models.common.constants.FIELDS;

/**
 * May the build success be with you
 * With great problems, comes great help from @guilhermesteves
 *
 * This defines all possibles levels of actions for Mods
 */
public enum ModLevel implements BaseEnum, JsonSerializable {

    //**********************************************************
    // values
    //**********************************************************

    GLOBAL("Global", 0),
    MULTI_BOARD("MB", 1),
    SINGLE_BOARD("SB", 2),
    JANITOR("Janitor", 3);

    //**********************************************************
    // properties
    //**********************************************************

    private final String description;
    private final Integer level;

    //**********************************************************
    // getters and setters
    //**********************************************************

    public String getDescription() {
        return description;
    }

    public Integer getLevel() {
        return level;
    }

    public static ModLevel getModLevel(Integer level) {
        return values()[level];
    }

    //**********************************************************
    // constructor
    //**********************************************************

    ModLevel(String description, Integer level) {
        this.description = description;
        this.level = level;
    }

    //**********************************************************
    // json
    //**********************************************************

    @Override
    public JsonNode toJson(String context) {

        JsonHelper j = new JsonHelper();

        j.addInteger(FIELDS.ENUM.ID, ordinal());
        j.addString(FIELDS.ENUM.DESCRIPTION, getDescription());
        j.addInteger(FIELDS.ENUM.MOD_LEVEL.LEVEL, getLevel());

        return j.getJSON();
    }

    public static ModLevel fromJson(JsonNode json) {
        return values()[json.get(FIELDS.ENUM.ID).asInt()];
    }

    public static ModLevel fromJson(String fieldName, JsonNode json){
        JsonHelper j = new JsonHelper(json);
        return j.hasValue(fieldName) ? ModLevel.fromJson(j.get(fieldName)) : null;
    }
}
