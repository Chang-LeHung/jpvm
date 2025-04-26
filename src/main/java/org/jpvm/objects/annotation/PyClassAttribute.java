package org.jpvm.objects.annotation;

import java.lang.annotation.*;

/**
 * this annotation is used to for those attributes of native object like a.name, a.age ... etc
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PyClassAttribute {
}
