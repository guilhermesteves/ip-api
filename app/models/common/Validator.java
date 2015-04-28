package models.common;

import com.fasterxml.jackson.databind.node.ObjectNode;
import play.data.validation.Validation;
import play.libs.Json;

import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;

/**
 * May the build success be with you
 * With great problems, comes great help from @guilhermesteves
 */
public class Validator {

    public static <T> boolean validate(T obj){
        return validate(obj, true);
    }

    public static <T> boolean validate(T obj, boolean throwsException){
        Set<ConstraintViolation<T>> constraintViolations = Validation.getValidator().validate(obj);

        if(throwsException && constraintViolations.size() > 0)
            throw new DisplayableException(toJson(constraintViolations));

        return constraintViolations.size() == 0;
    }

    private static <T>  String format(Set<ConstraintViolation<T>> errors){
        return format(errors,"\n");
    }

    private static <T>  String format(Set<ConstraintViolation<T>> errors, String separator){
        String error = "";

        ArrayList<ConstraintViolation<?>> array = new ArrayList<ConstraintViolation<?>>(errors);

        Collections.sort(array, new Comparator<ConstraintViolation<?>>() {

            @Override
            public int compare(ConstraintViolation<?> o1, ConstraintViolation<?> o2) {
                return o1.getMessage().compareToIgnoreCase(o2.getMessage());
            }
        });

        for (ConstraintViolation<?> constraintViolation : array) {
            error += (!error.equals("") ? separator : "") + constraintViolation.getPropertyPath() + " - " + constraintViolation.getMessage();
        }
        return error;
    }


    private static <T>  ObjectNode toJson(Set<ConstraintViolation<T>> errors){
        ObjectNode node = Json.newObject();

        ArrayList<ConstraintViolation<?>> array = new ArrayList<ConstraintViolation<?>>(errors);

        Collections.sort(array, new Comparator<ConstraintViolation<?>>() {

            @Override
            public int compare(ConstraintViolation<?> o1, ConstraintViolation<?> o2) {
                return o1.getMessage().compareToIgnoreCase(o2.getMessage());
            }
        });

        for (ConstraintViolation<?> constraintViolation : array) {
            node.put(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
        }
        return node;
    }
}
