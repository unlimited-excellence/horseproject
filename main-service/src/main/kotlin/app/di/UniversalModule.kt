package app.di

import domain.ports.repositories.CommissionRepository
import domain.ports.repositories.CommissionParticipantRepository
import domain.ports.repositories.CompetitionRepository
import domain.ports.repositories.WineRepository
import domain.ports.repositories.WineSampleRepository
import infrastructure.persistence.repositories.CommissionRepositoryImpl
import infrastructure.persistence.repositories.CommissionParticipantRepositoryImpl
import infrastructure.persistence.repositories.CompetitionRepositoryImpl
import infrastructure.persistence.repositories.WineRepositoryImpl
import infrastructure.persistence.repositories.WineSampleRepositoryImpl
import org.koin.dsl.module

val universalModule = module {
    single { WineRepositoryImpl() as WineRepository }
    single { CompetitionRepositoryImpl() as CompetitionRepository }
    single { CommissionParticipantRepositoryImpl() as CommissionParticipantRepository }
    single { WineSampleRepositoryImpl() as WineSampleRepository }
    single { CommissionRepositoryImpl() as CommissionRepository }
}