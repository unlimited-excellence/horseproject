package app.di

import org.unlimitedexcellence.horseproject.HorseProjectEndpoint
import domain.ports.repositories.*
import eth.likespro.lpfcp.LPFCP
import infrastructure.persistence.repositories.*
import org.koin.dsl.module

val universalModule = module {
    single { TransactionRepositoryImpl() as TransactionRepository }
    single { UserBalanceAbstractRepositoryImpl(get<TransactionRepository>(TransactionRepository::class)) as UserBalanceAbstractRepository }
    single { LPFCP.getProcessor<HorseProjectEndpoint>("http://localhost:50006/lpfcp") }
}