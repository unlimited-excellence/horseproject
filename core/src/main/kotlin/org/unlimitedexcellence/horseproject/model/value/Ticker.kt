package org.unlimitedexcellence.horseproject.model.value

import eth.likespro.commons.models.Value
import kotlinx.serialization.Serializable

@Serializable
data class Ticker(
    val value: String
) : Value/*, Validatable<Ticker>*/ {
//    open class IsInvalidException(override val message: String) : RuntimeException()
//    class LengthIsInvalidException(message: String) : IsInvalidException(message)
//    class InvalidCharactersException(message: String) : IsInvalidException(message)
//
//    init {
//        throwIfInvalid()
//    }
//
//    override fun throwIfInvalid(): Ticker = this.also {
//        if(value.length !in 1..16)
//    }
}