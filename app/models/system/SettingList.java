package models.system;

import com.fasterxml.jackson.databind.JsonNode;
import models.common.db.factory.SimpleDAOFactory;
import models.common.json.JsonListSerializer;
import models.common.json.JsonSerializable;
import models.common.db.generic.SimpleDAOImpl;
import org.apache.commons.collections.IteratorUtils;
import org.jongo.MongoCollection;

import java.util.ArrayList;
import java.util.Collection;

/**
 * May the build success be with you
 * With great problems, comes great help from @guilhermesteves
 *
 * Object to list Settings
 */
public class SettingList extends ArrayList<Setting> implements JsonSerializable {

    //**********************************************************
    // constructor
    //**********************************************************

    public SettingList() {
        super();
    }

    public SettingList(Collection<? extends Setting> c) {
        super(c);
    }

    //**********************************************************
    // dao
    //**********************************************************

    public static SettingList list(Integer offset, Integer limit) {
        Iterable<Setting> iterable = SimpleDAOFactory.getInstance().getSettingDAO().listAll(offset, limit);

        return new SettingList(IteratorUtils.toList(iterable.iterator()));
    }

    //**********************************************************
    // json
    //**********************************************************

    @Override
    public JsonNode toJson(String context){
        return JsonListSerializer.toJson(this, context);
    }

    public static SettingList fromJson(JsonNode json){
        return (SettingList) JsonListSerializer.fromJson(json, new SettingList(), Setting.class);
    }
}
