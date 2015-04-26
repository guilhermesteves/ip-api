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
 * Annotation to describe Mod access
 * to the system, including all levels
 * of permission
 *
 * @see models.users.ModLevel
 */
@With(ModPolicy.class)
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ModAction {
    boolean block() default true;
    boolean onlyActive() default true;

    // Janitor is the lowest level
    int modLevel() default 3;
}