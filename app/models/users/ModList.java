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
 * Object to list Mods
 */
public class ModList extends ArrayList<Mod> implements JsonSerializable {

    //**********************************************************
    // constructor
    //**********************************************************

    public ModList() {
        super();
    }

    public ModList(Collection<? extends Mod> c) {
        super(c);
    }

    //**********************************************************
    // dao
    //**********************************************************

    public static ModList list(Integer offset, Integer limit, Integer modLevel, String board) {

        Iterable<Mod> iterable = SimpleDAOFactory.getInstance().getModDAO().find(getQuery(modLevel, board), offset, limit);

        return new ModList(IteratorUtils.toList(iterable.iterator()));
    }

    public static ModList listAll(Integer offset, Integer limit) {
        return (ModList) SimpleDAOFactory.getInstance().getModDAO().listAll(offset, limit);
    }

    private static String getQuery(Integer modLevel, String board) {
        String modLevelFilter = "";
        if (modLevel != null && modLevel >= 0 && modLevel <= 3) {
            ModLevel level = ModLevel.getModLevel(modLevel);

            if (level != null)
                modLevelFilter = "accessLevel: " + level;
        }

        String boardFilter = "";
        if (board != null && !("").equals(board))
            boardFilter = "moderatingBoards: { $matches: " + board + "}";

        return "{ " +
                (!("").equals(modLevelFilter) ? modLevelFilter + ", " : "") +
                (!("").equals(boardFilter) ? boardFilter : "") +
                " }";
    }

    //**********************************************************
    // json
    //**********************************************************

    @Override
    public JsonNode toJson(String context) {
        return JsonListSerializer.toJson(this, context);
    }

    public static ModList fromJson(JsonNode json) {
        return (ModList) JsonListSerializer.fromJson(json, new ModList(), Mod.class);
    }
}