package org.jpvm.objects.annotation;

import java.lang.annotation.*;

/**
 * this annotation is used to call method of native object like list.extend() list.pop()
 * list.append() ... etc
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PyClassMethod {
}
