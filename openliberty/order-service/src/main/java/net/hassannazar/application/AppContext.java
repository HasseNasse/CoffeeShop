package net.hassannazar.application;

import javax.inject.Qualifier;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Purpose:
 * CDI qualifier which is used to identify the executor instance
 *
 * @author Hassan Nazar
 * @author www.hassannazar.net
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
public @interface AppContext {
}
