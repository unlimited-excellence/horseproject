package com.axus.winelore.model.entity

import eth.likespro.commons.models.Entity
import eth.likespro.commons.models.Validatable
import eth.likespro.commons.models.Value
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class WineSample(
    override val id: Id = Id(),
    val commissionId: Commission.Id,
    val wineId: Wine.Id,
    val code: Code,
    var previousWineSampleId: Id,
): Entity<WineSample.Id> {
    class CompetitionIdAndCodeAreAlreadyUsedException : RuntimeException("WineSample Competition.Id and Code are already used.")
    class CompetitionIdAndPreviousWineSampleIdAreAlreadyUsedException : RuntimeException("WineSample Competition.Id and WineSample.Id of previousWineSampleId are already used.")

    @Serializable
    data class Id(
        val value: String = UUID.randomUUID().toString()
    ): Value, Validatable<Id> {
        class IsInvalidException(override val message: String) : RuntimeException()

        init {
            throwIfInvalid()
        }

        companion object {
            val NONE = Id("00000000-0000-0000-0000-000000000000")
        }

        override fun throwIfInvalid(): Id {
            if(value.length != 36)
                throw IsInvalidException("WineSample must be 36 characters long")

            return this
        }
    }

    @Serializable
    data class Code(
        val value: String
    ): Value, Validatable<Code> {
        class IsInvalidException(override val message: String) : RuntimeException()

        init {
            throwIfInvalid()
        }

        override fun throwIfInvalid(): Code {
            if(value.length >  32)
                throw IsInvalidException("WineSample.Code must be not greater than 32 characters long")

            return this
        }
    }
}