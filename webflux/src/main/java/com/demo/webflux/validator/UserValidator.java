package com.demo.webflux.validator;


import com.demo.webflux.entity.User;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;



/**
 * 实现Spring Validator接口
 *
 * @Author: xush
 * @Date: 2021-6-1
 * @Version: v1.0
 */
public class UserValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        ValidationUtils.rejectIfEmpty(errors, "name", "name.empty");
        User user = (User) o;
        if (user.getAge() < 20) {
            errors.rejectValue("age", "age is small");
        } else if (user.getAge() > 110) {
            errors.rejectValue("age", "too.darn.old");
        }
    }
}
