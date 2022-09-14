package com.auth.jwt.validator.payment;



import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Constraint(validatedBy = PaymentConstraint.class)
public @interface PaymentMethodAnnotation {

    String message();
    public Class<?>[] groups() default{};
    public Class<? extends Payload>[] payload() default{};
}
