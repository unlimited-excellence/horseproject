package application.usecase

import com.axus.id.FullPermission
import com.axus.id.Session
import com.axus.id.model.entity.Token
import com.axus.id.model.value.AUID
import com.axus.winelore.WineLorePermissionBuilder
import com.axus.winelore.model.InsufficientPermissionsException
import com.axus.winelore.model.entity.Wine
import domain.ports.repositories.WineRepository
import eth.likespro.atomarix.Atom.Companion.atomic
import eth.likespro.commons.models.value.Iteration
import kotlinx.serialization.Serializable
import org.koin.java.KoinJavaComponent.get
import org.koin.java.KoinJavaComponent.inject

@Serializable
data class CreateWineRequest(
    val producer: AUID,
    val name: Wine.Name,
    val iteration: Iteration,
    val color: Wine.Color,
    val type: Wine.Type,
    val tokenId: Token.Id
) {
    suspend fun execute(): Wine {
        val session: Session = get<Session>(Session::class.java).refreshedIfExpired()
        val wineRepository: WineRepository by inject(WineRepository::class.java)

        if(!session.hasTokenPermission(tokenId.value, FullPermission(WineLorePermissionBuilder.createWine(AUID(session.auid), producer))))
            throw InsufficientPermissionsException()

        val wine = Wine(Wine.Id(), producer, name, iteration, color, type)
        return atomic {
            if(wineRepository.isExistingByProducerAndNameAndIteration(this, producer, name, iteration))
                throw Wine.ProducerAndNameAndIterationAreAlreadyUsedException()
            wineRepository.create(this, wine)
        }
    }
}