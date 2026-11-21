package cl.sonidovivo.usuarios.validacion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

@Documented
@Constraint (validatedBy = RutValido.Validador.class)
@Target ({ElementType.FIELD, ElementType.PARAMETER, ElementType.RECORD_COMPONENT})
@Retention (RetentionPolicy.RUNTIME)
public @interface RutValido {

    String message() default "El RUT no es válido.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default{};

    class Validador implements ConstraintValidator<RutValido, String>{
        @Override
        public boolean isValid(String valor, ConstraintValidatorContext context){
            return valor == null ||  ValidadorRut.esValido(valor);
        }
    }
    
}
