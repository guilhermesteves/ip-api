package models.common.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.common.constants.Constants;
import models.common.constants.MESSAGES;
import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import play.Logger;
import play.libs.Json;

import java.util.ArrayList;
import java.util.List;

/**
 * May the build success be with you
 * With great problems, comes great help from @guilhermesteves
 *
 * This class exists to handle better than jackson
 * this madness known as JSON
 */
public class JsonHelper {

    //**********************************************************
    // properties
    //**********************************************************

    private JsonNode jsonNode;
    private ObjectNode objectNode;
    private String DATE_PATTERN = Constants.DATE_PATTERN;

    //**********************************************************
    // getters and setters
    //**********************************************************

    public JsonNode getJSON() {
        if (objectNode != null) return objectNode;
        if (jsonNode != null) return jsonNode;
        return Json.newObject();
    }

    public JsonNode getJsonNode() {
        return jsonNode;
    }

    public ObjectNode getObjectNode() {
        return objectNode;
    }

    public Boolean hasValue(String fieldName) {
        return jsonNode.has(fieldName) && !jsonNode.get(fieldName).isNull();
    }

    public JsonNode get(String fieldName) {
        return jsonNode.has(fieldName) && !jsonNode.get(fieldName).isNull() ? jsonNode.get(fieldName) : null;
    }

    public JsonHelper addSerializable(String fieldName, JsonSerializable value, String context) {
        if (value != null) objectNode.put(fieldName, value.toJson(context));
        return this;
    }

    public JsonHelper add(String fieldName, JsonNode json) {
        if (json != null) objectNode.put(fieldName, json);
        return this;
    }

    //**********************************************************
    // constructor
    //**********************************************************

    public JsonHelper(JsonNode jsonNode) {
        this.jsonNode = jsonNode;
    }

    public JsonHelper() {
        this.objectNode = Json.newObject();
    }

    //**********************************************************
    // integer
    //**********************************************************

    public Integer getInteger(String fieldName) {
        return jsonNode.has(fieldName) && !jsonNode.get(fieldName).isNull() ? jsonNode.get(fieldName).asInt() : null;
    }

    public Integer getIntegerFromString(String fieldName) {
        if (jsonNode.has(fieldName) && !jsonNode.get(fieldName).isNull())
            return Integer.getInteger(jsonNode.get(fieldName).asText());
        else
            return null;
    }

    public JsonHelper addInteger(String fieldName, Integer value) {
        if (value != null) objectNode.put(fieldName, value);
        return this;
    }

    public JsonHelper addIntegerAsString(String fieldName, Integer value) {
        if (value == null)
            objectNode.put(fieldName, "0");
        else
            objectNode.put(fieldName, value.toString());
        return this;
    }

    public Integer addIntegerWithDefaultZero(Integer value) {
        return (value != null) ? value : 0;
    }

    public String addIntegerWithDefaultZeroString(Integer value) {
        return (value != null) ? value.toString() : "0";
    }

    public JsonHelper addIntegerMultipleChoiceToJSON(String fieldName, List<Integer> listInteger) {

        ObjectNode multipleChoice = Json.newObject();

        for (Integer value : listInteger) {
            multipleChoice.put(value.toString(), true);
        }

        objectNode.put(fieldName, multipleChoice);

        return this;
    }

    public List<Integer> getIntegerList(String fieldName) {
        if (jsonNode.has(fieldName) && !jsonNode.get(fieldName).isNull()) {

            JsonNode childArray = jsonNode.get(fieldName);
            List<Integer> list = new ArrayList<>();

            for (int i = 0; i < childArray.size(); i++)
                list.add((childArray.get(i)).asInt());

            return list.isEmpty() ? null : list;
        } else {
            return null;
        }
    }

    //**********************************************************
    // double
    //**********************************************************

    public Double getDouble(String fieldName) {
        return jsonNode.has(fieldName) && !jsonNode.get(fieldName).isNull() ? jsonNode.get(fieldName).asDouble() : null;
    }

    public JsonHelper addDouble(String fieldName, Double value) {
        if (value != null) objectNode.put(fieldName, value);
        return this;
    }

    public List<Double> getDoubleList(String fieldName) {
        if (jsonNode.has(fieldName) && !jsonNode.get(fieldName).isNull()) {

            JsonNode childArray = jsonNode.get(fieldName);
            List<Double> list = new ArrayList<>();

            for (int i = 0; i < childArray.size(); i++)
                list.add((childArray.get(i)).asDouble());

            return list.isEmpty() ? null : list;
        } else {
            return null;
        }
    }

    //**********************************************************
    // object id
    //**********************************************************

    public ObjectId getObjectId(String fieldName) {
        return jsonNode.has(fieldName) && !jsonNode.get(fieldName).isNull() ? new ObjectId(jsonNode.get(fieldName).asText()) : null;
    }

    public JsonHelper addObjectId(String fieldName, ObjectId value) {
        if (value != null) objectNode.put(fieldName, value.toString());
        return this;
    }

    public List<ObjectId> getObjectIdList(String fieldName) {
        if (jsonNode.has(fieldName) && !jsonNode.get(fieldName).isNull()) {

            JsonNode childArray = jsonNode.get(fieldName);
            List<ObjectId> list = new ArrayList<>();

            for (int i = 0; i < childArray.size(); i++) {
                ObjectId id = new ObjectId((childArray.get(i)).asText());
                list.add(id);
            }

            return list.isEmpty() ? null : list;
        } else {
            return null;
        }
    }

    //**********************************************************
    // string
    //**********************************************************

    public String getString(String fieldName) {
        return jsonNode.has(fieldName) && !jsonNode.get(fieldName).isNull() ? jsonNode.get(fieldName).asText() : null;
    }

    public JsonHelper addString(String fieldName, String value) {
        if (value != null && !("").equals(value)) objectNode.put(fieldName, value);
        return this;
    }

    public List<String> getStringList(String fieldName) {
        if (jsonNode.has(fieldName) && !jsonNode.get(fieldName).isNull()) {

            JsonNode childArray = jsonNode.get(fieldName);
            List<String> list = new ArrayList<>();

            for (int i = 0; i < childArray.size(); i++)
                list.add((childArray.get(i)).asText());

            return list.isEmpty() ? null : list;
        } else {
            return null;
        }
    }

    //**********************************************************
    // boolean
    //**********************************************************

    public Boolean getBoolean(String fieldName) {
        return jsonNode.has(fieldName) && !jsonNode.get(fieldName).isNull() ?
                (jsonNode.get(fieldName).asBoolean() || ("1").equals(jsonNode.get(fieldName).asText()) || ("true").equals(jsonNode.get(fieldName).asText())) :
                null;
    }

    public JsonHelper addBoolean(String fieldName, Boolean value) {
        if (value != null)
            objectNode.put(fieldName, value);

        return this;
    }

    public List<Boolean> getBooleanList(String fieldName) {
        if (jsonNode.has(fieldName) && !jsonNode.get(fieldName).isNull()) {

            JsonNode childArray = jsonNode.get(fieldName);
            List<Boolean> list = new ArrayList<>();

            for (int i = 0; i < childArray.size(); i++)
                list.add((childArray.get(i)).asBoolean());

            return list.isEmpty() ? null : list;
        } else {
            return null;
        }
    }

    //**********************************************************
    // date
    //**********************************************************

    public DateTime getDate(String fieldName) {
        DateTime date = null;

        if (jsonNode.has(fieldName) && !jsonNode.get(fieldName).isNull()) {
            String stringDate = jsonNode.get(fieldName).asText();
            if (!("").equals(stringDate)) {
                try {
                    date = new DateTime(stringDate);
                } catch (Exception e) {
                    Logger.error(e.getMessage());
                }
            }
        }
        return date;
    }

    public DateTime getFormattedDate(String fieldName) {
        DateTime date = null;

        if (jsonNode.has(fieldName) && !jsonNode.get(fieldName).isNull()) {
            String stringDate = jsonNode.get(fieldName).asText();
            if (!("").equals(stringDate)) {
                try {
                    date = new DateTime(stringDate);
                } catch (Exception e) {
                    Logger.error(e.getMessage());
                }
            }
        }

        if (date != null)
            date = DateTimeFormat.forPattern(DATE_PATTERN).parseDateTime(DateTimeFormat.forPattern(DATE_PATTERN).print(date));

        return date;
    }

    public JsonHelper addDate(String fieldName, DateTime value) {
        if (value != null) {
            value = DateTimeFormat.forPattern(DATE_PATTERN).parseDateTime(DateTimeFormat.forPattern(DATE_PATTERN).print(value));
            objectNode.put(fieldName, value.toString());
        }
        return this;
    }

    public JsonHelper addFormattedDate(String fieldName, DateTime value) {
        if (value != null) objectNode.put(fieldName, DateTimeFormat.forPattern(DATE_PATTERN).print(value));
        return this;
    }

    //**********************************************************
    // list
    //**********************************************************

    public JsonNode getArray(String fieldName) {
        return jsonNode.has(fieldName) && !jsonNode.get(fieldName).isNull() ? jsonNode.get(fieldName) : null;
    }

    public JsonHelper addArray(String fieldName, JsonNode value) {
        if (value != null) objectNode.put(fieldName, value);
        return this;
    }

    //**********************************************************
    // other
    //**********************************************************

    public static JsonNode error(String message) {
        ObjectNode error = Json.newObject();
        error.put("error", message);
        return error;
    }

    public static JsonNode error(ObjectNode message) {
        ObjectNode error = Json.newObject();
        error.put("error", message);
        return error;
    }

    public static JsonNode unauthorized() {
        ObjectNode error = Json.newObject();
        error.put("error", MESSAGES.UNAUTHORIZED_403);
        return error;
    }

    public static JsonNode notFound() {
        ObjectNode error = Json.newObject();
        error.put("error", MESSAGES.NOT_FOUND_404);
        return error;
    }
}
