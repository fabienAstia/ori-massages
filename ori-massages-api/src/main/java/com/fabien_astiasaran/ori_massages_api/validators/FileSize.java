package com.fabien_astiasaran.ori_massages_api.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
@Constraint(validatedBy = FileSizeValidator.class)
public @interface FileSize {

    static final long ONE_MB = 1024 * 1024;

    static final long TWO_MB = ONE_MB * 2;

    long max() default FileSize.ONE_MB;

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
