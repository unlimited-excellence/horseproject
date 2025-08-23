package com.axus.winelore.model.entity

import com.axus.id.model.value.AUID
import eth.likespro.commons.models.Entity
import eth.likespro.commons.models.Validatable
import eth.likespro.commons.models.Value
import eth.likespro.commons.models.value.Iteration
import eth.likespro.commons.models.value.Timestamp
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class Wine(
    override val id: Id = Id(),
    val producer: AUID,
    val name: Name,
    val iteration: Iteration,
    val color: Color,
    val type: Type,
    val createdAt: Timestamp = Timestamp.now(),
): Entity<Wine.Id> {
    open class IsIncorrectException(override val message: String) : RuntimeException()
    class ProducerAndNameAndIterationAreAlreadyUsedException : IsIncorrectException("Wine Producer and Name and Iteration combination is already used.")

    @Serializable
    data class Id(
        val value: String = UUID.randomUUID().toString()
    ): Value, Validatable<Id> {
        open class IsInvalidException(override val message: String) : RuntimeException()
        class LengthIsInvalidException(message: String) : IsInvalidException(message)

        init {
            throwIfInvalid()
        }

        override fun throwIfInvalid(): Id {
            if(value.length != 36)
                throw LengthIsInvalidException("Wine ID must be 36 characters long")

            return this
        }
    }

    @Serializable
    data class Name(val value: String): Value, Validatable<Name> {
        open class IsInvalidException(override val message: String) : RuntimeException()
        class LengthIsInvalidException(message: String) : IsInvalidException(message)
        class StartsOrEndsWithSpaceException(message: String) : IsInvalidException(message)

        init {
            throwIfInvalid()
        }

        override fun throwIfInvalid(): Name {
            if(value.length > 64)
                throw LengthIsInvalidException("Wine.Name length must be not greater than 64 characters")
            if(value.startsWith(" ") || value.endsWith(" "))
                throw StartsOrEndsWithSpaceException("Wine.Name must not start or end with spaces")

            return this
        }
    }

    enum class Color {
        RED,
        WHITE,
        ROSE
    }

    enum class Type {
        STILL,
        SPARKLING,
        SPIRITOUS
    }
}