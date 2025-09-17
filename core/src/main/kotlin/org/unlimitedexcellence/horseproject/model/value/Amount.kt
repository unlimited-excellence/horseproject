package org.unlimitedexcellence.horseproject.model.value

import eth.likespro.commons.models.Value
import kotlinx.serialization.Serializable

@Serializable
data class Amount(
    val value: Long,
) : Value {
    operator fun plus(other: Amount): Amount = Amount(this.value + other.value)
    operator fun minus(other: Amount): Amount = Amount(this.value - other.value)

    operator fun compareTo(other: Amount): Int = this.value.compareTo(other.value)

    companion object {
        val ZERO = Amount(0L)
    }
}