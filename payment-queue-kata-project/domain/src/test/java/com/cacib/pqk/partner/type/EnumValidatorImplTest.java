package com.cacib.pqk.partner.type;

import com.cacib.pqk.domain.partner.type.EnumValidator;
import com.cacib.pqk.domain.partner.type.EnumValidatorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnumValidatorImplTest {

    private EnumValidatorImpl validator;

    enum DummyEnum {
        VALUE_ONE, VALUE_TWO
    }

    @BeforeEach
    void setUp() {
        validator = new EnumValidatorImpl();

        // ✅ Implémentation manuelle de l'annotation
        EnumValidator annotation = new EnumValidator() {
            @Override
            public Class<? extends Enum<?>> enumClass() {
                return DummyEnum.class;
            }

            @Override
            public String message() {
                return "Invalid enum value";
            }

            @Override
            public Class<?>[] groups() {
                return new Class[0];
            }

            @Override
            public Class<? extends jakarta.validation.Payload>[] payload() {
                return new Class[0];
            }

            @Override
            public Class<? extends java.lang.annotation.Annotation> annotationType() {
                return EnumValidator.class;
            }
        };

        validator.initialize(annotation);
    }

    @Test
    void shouldReturnTrueForValidEnumValue() {
        assertTrue(validator.isValid(DummyEnum.VALUE_ONE, null));
    }

    @Test
    void shouldReturnFalseForNullValue() {
        assertFalse(validator.isValid(null, null));
    }

    @Test
    void shouldReturnTrueForSameStrEnum() {
        enum OtherEnum { VALUE_ONE }
        assertTrue(validator.isValid(OtherEnum.VALUE_ONE, null));
    }
}
