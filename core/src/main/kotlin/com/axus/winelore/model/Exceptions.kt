package com.axus.winelore.model

class InsufficientPermissionsException : RuntimeException("Not enough permissions.")

// <============ Wine Service ============>

class WineProducerAndNameAndIterationAreAlreadyUsedException : RuntimeException("Wine Producer and Name and Iteration are already used.")

// <============ Competition Service ============>

class CompetitionNameIsAlreadyUsedException : RuntimeException("Competition Name is already used.")

// <============ Commission Service ============>

class CommissionCompetitionIdAndNameAreAlreadyUsedException : RuntimeException("Commission Competition.Id and Name are already used.")

// <============ CommissionParticipant Service ============>

class CommissionParticipantCompetitionIdAndAUIDAreAlreadyUsedException : RuntimeException("CommissionParticipant Competition.Id and AUID are already used.")

// <============ WineSample Service ============>

class WineSampleCompetitionIdAndCodeAreAlreadyUsedException : RuntimeException("WineSample Competition.Id and Code are already used.")
class WineSampleCompetitionIdAndPreviousWineSampleIdAreAlreadyUsedException : RuntimeException("WineSample Competition.Id and WineSample.Id of previousWineSampleId are already used.")