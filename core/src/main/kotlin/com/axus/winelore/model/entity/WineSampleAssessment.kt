package com.axus.winelore.model.entity

import com.axus.id.model.value.AUID
import eth.likespro.commons.models.Entity
import eth.likespro.commons.models.Validatable
import eth.likespro.commons.models.Value
import eth.likespro.commons.models.value.Timestamp
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.json.JSONObject
import java.util.*

@Serializable
data class WineSampleAssessment(
    override val id: Id = Id(),
    val commissionId: Commission.Id,
    val from: AUID,
    val wineSampleId: WineSample.Id,
    val mark: Mark,
    @Contextual
    val metadata: JSONObject = JSONObject(),
    val createdAt: Timestamp = Timestamp.now(),
): Entity<WineSampleAssessment.Id> {
    class CommissionIdAndAUIDAndWineSampleIdAreAlreadyUsedException : RuntimeException("WineSampleAssessment Commission.Id, AUID and WineSample.Id are already used.")

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
                throw IsInvalidException("WineSampleAssessment ID must be 36 characters long")

            return this
        }
    }

    @Serializable
    data class Mark(val value: Int): Validatable<Mark> {
        class IsInvalidException(override val message: String) : RuntimeException(message)

        init {
            throwIfInvalid()
        }

        override fun throwIfInvalid(): Mark = this.also {
            if (value < 0 || value > 100) {
                throw IsInvalidException("Mark must be between 0 and 100")
            }
        }
    }
}