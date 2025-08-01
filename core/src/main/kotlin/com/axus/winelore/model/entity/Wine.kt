package com.axus.winelore.model.entity

import com.axus.id.model.value.AUID
import eth.likespro.commons.models.Entity
import eth.likespro.commons.models.Validatable
import eth.likespro.commons.models.Value
import eth.likespro.commons.models.value.Iteration
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class Wine(
    override val id: Id = Id(),
    val producer: AUID,
    val name: Name,
    val iteration: Iteration,
    val color: Color,
    val type: Type
): Entity<Wine.Id> {
    @Serializable
    data class Id(
        val value: String = UUID.randomUUID().toString()
    ): Value, Validatable<Id> {
        class IsInvalidException(override val message: String) : RuntimeException()

        init {
            throwIfInvalid()
        }

        override fun throwIfInvalid(): Id {
            if(value.length != 36)
                throw IsInvalidException("Wine ID must be 36 characters long")

            return this
        }
    }

    @Serializable
    data class Name(val value: String): Value, Validatable<Name> {
        class IsInvalidException(override val message: String) : RuntimeException()

        init {
            throwIfInvalid()
        }

        override fun throwIfInvalid(): Name {
            if(value.length > 64)
                throw IsInvalidException("Wine.Name length must be not greater than 64 characters")
            if(value.startsWith(" ") || value.endsWith(" "))
                throw IsInvalidException("Wine.Name must not start or end with spaces")

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