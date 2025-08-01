package com.axus.winelore

import com.axus.id.model.aggregate.FullPermission
import com.axus.id.model.value.AUID
import com.axus.id.model.value.Permission
import com.axus.winelore.model.entity.Commission
import com.axus.winelore.model.entity.Competition
import com.axus.winelore.model.entity.CommissionParticipant

object WineLorePermissionBuilder {
    // <============ Wine Service ============>

    fun createWine(wineLoreAUID: AUID, producer: AUID, grantee: AUID = AUID.ANYONE) = FullPermission(
        producer,
        grantee,
        wineLoreAUID,
        Permission("wine:createWine")
    )

    // <============ Competition Service ============>

    fun createCompetition(wineLoreAUID: AUID, organizer: AUID, grantee: AUID = AUID.ANYONE) = FullPermission(
        organizer,
        grantee,
        wineLoreAUID,
        Permission("competition:createCompetition")
    )
    fun startCompetition(wineLoreAUID: AUID, organizer: AUID, competitionId: Competition.Id, grantee: AUID = AUID.ANYONE) = FullPermission(
        organizer,
        grantee,
        wineLoreAUID,
        Permission("competition:${competitionId.value}:startCompetition")
    )

    // <============ Commission Service ============>

    fun createCommission(wineLoreAUID: AUID, competitionOrganizer: AUID, competitionId: Competition.Id, grantee: AUID = AUID.ANYONE) = FullPermission(
        competitionOrganizer,
        grantee,
        wineLoreAUID,
        Permission("competition:${competitionId.value}:createCommission")
    )

    // <============ CommissionParticipant Service ============>

    fun createCommissionParticipant(wineLoreAUID: AUID, competitionOrganizer: AUID, commissionId: Commission.Id, role: CommissionParticipant.Role, grantee: AUID = AUID.ANYONE) = FullPermission(
        competitionOrganizer,
        grantee,
        wineLoreAUID,
        Permission("commission:${commissionId.value}:createCommissionParticipant:$role")
    )

    // <============ WineSample Service ============>
    
    fun createWineSample(wineLoreAUID: AUID, competitionOrganizer: AUID, commissionId: Commission.Id, grantee: AUID = AUID.ANYONE) = FullPermission(
        competitionOrganizer,
        grantee,
        wineLoreAUID,
        Permission("commission:${commissionId.value}:createWineSample")
    )
}