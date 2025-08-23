package com.axus.winelore.model.entity

import com.axus.id.model.value.AUID
import eth.likespro.commons.models.Entity
import eth.likespro.commons.models.Validatable
import eth.likespro.commons.models.Value
import eth.likespro.commons.models.value.Timestamp
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class Competition(
    override val id: Id = Id(),
    val organizer: AUID,
    val name: Name,
    var plannedStartAt: Timestamp
): Entity<Competition.Id> {
    class NameIsAlreadyUsedException : RuntimeException("Competition Name is already used.")

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
                throw IsInvalidException("Competition ID must be 36 characters long")

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
                throw IsInvalidException("Competition.Name length must be not greater than 64 characters")
            if(value.startsWith(" ") || value.endsWith(" "))
                throw IsInvalidException("Competition.Name must not start or end with spaces")

            return this
        }
    }
}