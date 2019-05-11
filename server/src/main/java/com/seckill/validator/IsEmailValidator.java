package com.seckill.validator;

import com.seckill.util.ValidatorUtil;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsEmailValidator implements ConstraintValidator<IsEmail, String> {

    private boolean required = false;

    public void initialize(IsEmail constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (required) {
            return ValidatorUtil.isEmail(value);
        } else {
            if (StringUtils.isEmpty(value)) {
                return true;
            } else {
                return ValidatorUtil.isEmail(value);
            }
        }
    }
}
