package models.common.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import play.libs.Json;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * May the build success be with you
 * With great problems, comes great help from @guilhermesteves
 *
 * Serialize lists made easy
 */
public class JsonListSerializer {

    public static <T extends JsonSerializable> JsonNode toJson(List<T> list, String context) {
        ArrayNode json = Json.newObject().arrayNode();

        Iterator<T> iterator = list.iterator();

        while (iterator.hasNext()){
            json.add((iterator.next()).toJson(context));
        }

        return json;
    }

    public static <T> List<T>   fromJson(JsonNode json, List<T> list, Class<T> elementClass) {

        try {
            for(int i=0; i < json.size(); i++ ){

                Method method = elementClass.getDeclaredMethod("fromJson",JsonNode.class);

                list.add((T) method.invoke(null, json.get(i)));
            }

        } catch (NoSuchMethodException e) {
            throw  new RuntimeException("JsonListSerializer error -- NoSuchMethodException");
        } catch (InvocationTargetException e) {
            throw  new RuntimeException("JsonListSerializer error -- InvocationTargetException");
        } catch (IllegalAccessException e) {
            throw  new RuntimeException("JsonListSerializer error -- IllegalAccessException");
        }

        return list;
    }

}

