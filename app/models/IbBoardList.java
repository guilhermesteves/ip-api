package models;

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
 * Created with IntelliJ IDEA.
 * User: Guilherme Esteves (@guilhermesteves)
 * Date: 3/8/15
 * Time: 3:12 AM
 * May the build success be with you
 */
public class IbBoardList extends ArrayList<IbBoard> implements JsonSerializable {

    //**********************************************************
    // constructor
    //**********************************************************

    public IbBoardList() {
        super();
    }

    public IbBoardList(Collection<? extends IbBoard> c) {
        super(c);
    }

    //**********************************************************
    // dao
    //**********************************************************

    public static IbBoardList list(Integer offset, Integer limit) {
        return new IbBoardList(SimpleDAOFactory.getInstance().getBoardDAO().listAll(offset, limit));
    }

    //**********************************************************
    // json
    //**********************************************************

    @Override
    public JsonNode toJson(String context) {
        return JsonListSerializer.toJson(this, context);
    }

    public static IbBoardList fromJson(JsonNode json){
        return (IbBoardList) JsonListSerializer.fromJson(json, new IbBoardList(), IbBoard.class);
    }
}
