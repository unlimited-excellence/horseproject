package com.axus.winelore.model.entity

import com.axus.id.model.value.AUID
import eth.likespro.commons.models.Entity
import eth.likespro.commons.models.Validatable
import eth.likespro.commons.models.Value
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class CommissionParticipant(
    override val id: Id = Id(),
    val commissionId: Commission.Id,
    val auid: AUID,
    val role: Role
): Entity<CommissionParticipant.Id> {
    open class IsIncorrectException(override val message: String) : RuntimeException()
    class CompetitionIdAndAUIDAreAlreadyUsedException : IsIncorrectException("CommissionParticipant Competition.Id and AUID are already used.")
    class IsUnavailableException : IsIncorrectException("CommissionParticipant is unavailable for this Commission.")

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
                throw IsInvalidException("CommissionParticipant ID must be 36 characters long")

            return this
        }
    }

    enum class Role {
        HEAD_OF_EXPERTS,
        EXPERT,
        ;
        class IsInvalidException(override val message: String) : RuntimeException()
    }
}