package models.common.json;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * May the build success be with you
 * With great problems, comes great help from @guilhermesteves
 *
 * Interface to always set the toJson in
 * a model
 */
public interface JsonSerializable  {

    default JsonNode toJson() {
        return this.toJson(JsonContext.DEFAULT);
    }

    public JsonNode toJson(String context);

}
