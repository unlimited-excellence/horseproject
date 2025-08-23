package app.di

import com.axus.id.AXUSIDEndpoint
import com.axus.winelore.WineLoreEndpoint
import domain.ports.repositories.*
import eth.likespro.lpfcp.LPFCP
import infrastructure.persistence.repositories.*
import org.koin.dsl.module

val universalModule = module {
    single { WineRepositoryImpl() as WineRepository }
    single { CompetitionRepositoryImpl() as CompetitionRepository }
    single { WineSampleRepositoryImpl() as WineSampleRepository }
    single { CommissionRepositoryImpl() as CommissionRepository }
    single { CommissionParticipantRepositoryImpl() as CommissionParticipantRepository }
    single { AbstractCommissionRepositoryImpl() as AbstractCommissionRepository }
    single { WineSampleAssessmentRepositoryImpl() as WineSampleAssessmentRepository }
    single { LPFCP.getProcessor<WineLoreEndpoint>("http://localhost:50006/lpfcp") }
}