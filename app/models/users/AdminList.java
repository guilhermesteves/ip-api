package models.users;

import com.fasterxml.jackson.databind.JsonNode;
import models.common.db.factory.SimpleDAOFactory;
import models.common.json.JsonListSerializer;
import models.common.json.JsonSerializable;
import org.apache.commons.collections.IteratorUtils;

import java.util.ArrayList;
import java.util.Collection;

/**
 * May the build success be with you
 * With great problems, comes great help from @guilhermesteves
 *
 * Object to list Admins
 */
public class AdminList extends ArrayList<Admin> implements JsonSerializable {

    //**********************************************************
    // constructor
    //**********************************************************

    public AdminList() {
        super();
    }

    public AdminList(Collection<? extends Admin> c) {
        super(c);
    }

    //**********************************************************
    // dao
    //**********************************************************

    public static AdminList list(Integer limit, Integer offset, Boolean onlySuperAdmin) {
        Iterable<Admin> iterable = SimpleDAOFactory.getInstance().getAdminDAO().find(getQuery(onlySuperAdmin), offset, limit);

        return new AdminList(IteratorUtils.toList(iterable.iterator()));
    }

    private static String getQuery(Boolean onlySuperAdmin) {

        if (onlySuperAdmin != null)
            return onlySuperAdmin ? "{superAdmin: true}" : "{superAdmin: false}";
        else
            return "{}";
    }

    //**********************************************************
    // json
    //**********************************************************

    @Override
    public JsonNode toJson(String context) {
        return JsonListSerializer.toJson(this, context);
    }

    public static AdminList fromJson(JsonNode json){
        return (AdminList) JsonListSerializer.fromJson(json, new AdminList(), Admin.class);
    }
}