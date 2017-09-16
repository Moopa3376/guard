package net.moopa3376.guard.annotation.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Moopa on 28/06/2017.
 * blog: leeautumn.net
 *
 * @autuor : Moopa
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiHttpParam {
    public String params() default "[]";
}
