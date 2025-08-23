package com.axus.winelore.model.entity

import eth.likespro.commons.models.Entity
import eth.likespro.commons.models.Validatable
import eth.likespro.commons.models.Value
import eth.likespro.commons.models.value.Timestamp
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class Commission(
    override val id: Id = Id(),
    val competitionId: Competition.Id,
    val name: Name,
    var isApproved: Boolean = false,
    var currentWineSampleId: WineSample.Id? = null,
    var startedAt: Timestamp? = null,
    val endedAt: Timestamp? = null,
): Entity<Commission.Id> {
    class CompetitionIdAndNameAreAlreadyUsedException : RuntimeException("Commission Competition.Id and Name are already used.")
    class IsNotApprovedException : RuntimeException("Commission must be approved before starting.")
    class IsNotStartedException : RuntimeException("Commission is not started yet.")
    class IsAlreadyStartedException : RuntimeException("Commission is already started.")
    class IsAlreadyEndedException : RuntimeException("Commission is already ended.")
    class NoWineSamplesException : RuntimeException("Commission must have at least one WineSample to start.")

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
                throw IsInvalidException("Commission ID must be 36 characters long")

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
            if (value.length > 64)
                throw IsInvalidException("Commission.Name length must be not greater than 64 characters")
            if (value.startsWith(" ") || value.endsWith(" "))
                throw IsInvalidException("Commission.Name must not start or end with spaces")

            return this
        }
    }
}