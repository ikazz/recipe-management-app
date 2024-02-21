package recipes.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {
    private static final String EMAIL_PATTERN = "^(.+)@(.+)\\.(.+)$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false; // Consider null as invalid
        }
        return value.matches(EMAIL_PATTERN);
    }
}
