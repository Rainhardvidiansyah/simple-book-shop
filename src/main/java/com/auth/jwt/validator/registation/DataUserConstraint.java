package com.auth.jwt.validator.registation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DataUserValidation.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DataUserConstraint {
    String message() default "Data must be complete!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
