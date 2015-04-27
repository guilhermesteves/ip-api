package models.system;

import com.fasterxml.jackson.databind.JsonNode;
import models.common.BaseModel;
import models.common.json.JsonHelper;
import models.common.json.JsonSerializable;
import models.common.constants.FIELDS;
import models.common.db.factory.SimpleDAOFactory;
import org.joda.time.DateTime;

/**
 * May the build success be with you
 * With great problems, comes great help from @guilhermesteves
 *
 * Settings that may affect the behavior of the entire
 * application, define by value and key
 */
public class Setting implements BaseModel, JsonSerializable {

    //**********************************************************
    // properties
    //**********************************************************

    private String id;

    private String key;

    private String value;

    private ValueType type;

    //**********************************************************
    // getters and setters
    //**********************************************************

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public Setting setKey(String key) {
        this.key = key;
        return this;
    }

    public Object getValue() {

        switch (getType()) {

            case INTEGER:
                return getValueAsInteger();

            case DOUBLE:
                return getValueAsDouble();

            case DATE:
                return getValueAsDate();

            case BOOLEAN:
                return getValueAsBoolean();

            default:
                return getValueAsString();
        }
    }

    public String getValueAsString() {
        return value;
    }

    public Integer getValueAsInteger() {
        return Integer.valueOf(value);
    }

    public Double getValueAsDouble() {
        return Double.valueOf(value);
    }

    public DateTime getValueAsDate() {
        return DateTime.parse(value);
    }

    public Boolean getValueAsBoolean() {

        if (("true").equals(value))
            return true;
        else if (("false").equals(value))
            return false;
        else
            return null;
    }

    public Setting setValue(String value) {
        this.value = value;
        return this;
    }

    public ValueType getType() {

        if (type == null)
            return ValueType.STRING;
        else
            return type;
    }

    public Setting setType(ValueType type) {
        this.type = type;
        return this;
    }

    //**********************************************************
    // dao
    //**********************************************************

    public void create() {
        SimpleDAOFactory.getInstance().getSettingDAO().create(this);
    }

    public static Setting load(String id) {
        return SimpleDAOFactory.getInstance().getSettingDAO().load(id, Setting.class);
    }

    public void update() {
        SimpleDAOFactory.getInstance().getSettingDAO().update(this);
    }

    public static void delete(String id) {
        SimpleDAOFactory.getInstance().getSettingDAO().delete(id, Setting.class);
    }

    //**********************************************************

    public static Setting loadByKey(String key) {
        return SimpleDAOFactory.getInstance().getSettingDAO().loadByKey(key);
    }

    //**********************************************************
    // json
    //**********************************************************

    @Override
    public JsonNode toJson(String context) {

        JsonHelper j = new JsonHelper();

        j.addString(FIELDS.SETTINGS.ID, getId());

        j.addString(FIELDS.SETTINGS.KEY, getKey())
                .addString(FIELDS.SETTINGS.VALUE, getValueAsString());

        j.addSerializable(FIELDS.SETTINGS.TYPE, getType(), context);

        return j.getJSON();
    }

    public static Setting fromJson(JsonNode json) {

        JsonHelper j = new JsonHelper(json);
        Setting setting = new Setting();

        setting.setId(j.getString(FIELDS.SETTINGS.ID));

        setting.setKey(j.getString(FIELDS.SETTINGS.KEY))
                .setValue(j.getString(FIELDS.SETTINGS.VALUE));

        setting.setType(ValueType.fromJson(FIELDS.SETTINGS.TYPE, json));

        return setting;
    }
}
