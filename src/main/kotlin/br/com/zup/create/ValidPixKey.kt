package br.com.zup.create

import io.micronaut.core.annotation.AnnotationValue
import kotlin.annotation.AnnotationTarget.*
import kotlin.annotation.AnnotationRetention.*
import io.micronaut.validation.validator.constraints.ConstraintValidator
import io.micronaut.validation.validator.constraints.ConstraintValidatorContext
import javax.inject.Singleton
import javax.validation.Constraint
import kotlin.reflect.KClass

@MustBeDocumented
@Target(CLASS, TYPE)
@Retention(RUNTIME)
@Constraint(validatedBy = [ValidPixKeyValidator::class])
annotation class ValidPixKey(
    val message: String = "Chave Pix inválida",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<*>> = []
)

@Singleton
class ValidPixKeyValidator: ConstraintValidator<ValidPixKey, NewPixKeyRequest> {
    override fun isValid(
        value: NewPixKeyRequest?,
        annotationMetadata: AnnotationValue<ValidPixKey>,
        context: ConstraintValidatorContext
    ): Boolean {
        if (value?.keyType == null)
            return false

        return value.keyType.validate(value.keyValue)
    }

}

