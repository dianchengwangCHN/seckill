package com.seckill.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {})
public @interface IsEmail {

    boolean required() default true;

    String message() default "Invaild Email Address";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
