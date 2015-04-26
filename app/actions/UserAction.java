package actions;

import play.mvc.With;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * May the build success be with you
 * With great problems, comes great help from @guilhermesteves
 *
 * Annotation to describe User access
 * to the system
 */
@With(UserPolicy.class)
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserAction {
    boolean block() default true;
    boolean onlyActive() default true;
}
