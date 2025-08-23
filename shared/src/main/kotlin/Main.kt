import com.axus.id.Session
import com.axus.id.model.entity.Token
import com.axus.id.model.value.AUID
import com.axus.winelore.WineLoreEndpoint
import com.axus.winelore.model.entity.*
import eth.likespro.commons.models.Pagination
import eth.likespro.commons.models.value.Iteration
import eth.likespro.lpfcp.LPFCP

fun main() {
    val lpfcpEndpoint = LPFCP.getProcessor<WineLoreEndpoint>("http://localhost:50006/lpfcp")
    val startedAt = System.currentTimeMillis()

    val session = Session(System.getenv("AXUS_ID_TEST_USER_AUID")?.toLongOrNull()!!, System.getenv("AXUS_ID_TEST_USER_TOKEN")!!).refreshedIfExpired()
//    println(session.selfUser.createTokenSystem(listOf(FullPermission(
//        session.auid,
//        session.auid,
//        11,
//        ".*"
//    ))))

    // <============ Create wine ============>

//    println(lpfcpEndpoint.createWine(
//        AUID(session.auid),
//        Wine.Name("Шампанське України Болград Брют"),
//        Iteration(0),
//        Wine.Color.RED,
//        Wine.Type.SPARKLING,
//        Token.Id(session.token)
//    ))

//    println(lpfcpEndpoint.getWine(Wine.Id("6bfc6963-b2cc-44e3-8028-885af8233a40")))
//    println(lpfcpEndpoint.getWineByProducerAndNameAndIteration(AUID(10), Wine.Name("Шампанське України Болград Брют"), Iteration(0)))

    // <============ Create wine competition ============>

//    println(lpfcpEndpoint.createCompetition(
//        AUID(session.auid),
//        Competition.Name("La Competition"),
//        Timestamp.now(),
//        Token.Id(session.token)
//    ))
//    println(lpfcpEndpoint.getCompetition(Competition.Id("5f2afa21-7668-4db3-b949-159c8a7ab31b")))
//    println(lpfcpEndpoint.getCompetitionByName(Competition.Name("La Competition")))

    // <============ Create wine commission ============>

//    println(lpfcpEndpoint.createCommission(
//        Competition.Id("5f2afa21-7668-4db3-b949-159c8a7ab31b"),
//        Commission.Name("Червона комісія"),
//        Token.Id(session.token)
//    ))
//    println(lpfcpEndpoint.createCommission(
//        Competition.Id("5f2afa21-7668-4db3-b949-159c8a7ab31b"),
//        Commission.Name("Head of Experts, approved"),
//        Token.Id(session.token)
//    ))
//    println(lpfcpEndpoint.createCommission(
//        Competition.Id("5f2afa21-7668-4db3-b949-159c8a7ab31b"),
//        Commission.Name("Head of Experts, not approved"),
//        Token.Id(session.token)
//    ))
//    println(lpfcpEndpoint.createCommission(
//        Competition.Id("5f2afa21-7668-4db3-b949-159c8a7ab31b"),
//        Commission.Name("Head of Experts, no wines & not approved"),
//        Token.Id(session.token)
//    ))
//    println(lpfcpEndpoint.createCommission(
//        Competition.Id("5f2afa21-7668-4db3-b949-159c8a7ab31b"),
//        Commission.Name("Expert"),
//        Token.Id(session.token)
//    ))
//    println(lpfcpEndpoint.createCommission(
//        Competition.Id("5f2afa21-7668-4db3-b949-159c8a7ab31b"),
//        Commission.Name("Ніхто"),
//        Token.Id(session.token)
//    ))

//    println(lpfcpEndpoint.getCommission(
//        Commission.Id("7a0a6a1f-ca13-4b64-8cf7-c8e33df3b50a")
//    ))
//    println(lpfcpEndpoint.getCommissionByCompetitionIdAndName(
//        Competition.Id("5f2afa21-7668-4db3-b949-159c8a7ab31b"),
//        Commission.Name("Червона комісія"),
//    ))

    // <============ Create wine commission participant ============>

//    println(lpfcpEndpoint.createCommissionParticipant(
//        Commission.Id("05c54e94-efaf-4c5c-8984-f85f55edbde7"),
//        AUID(session.auid),
//        CommissionParticipant.Role.HEAD_OF_EXPERTS,
//        Token.Id(session.token)
//    ))
//    println(lpfcpEndpoint.getCommissionParticipantByCommissionIdAndAUID(Commission.Id("7a0a6a1f-ca13-4b64-8cf7-c8e33df3b50a"), AUID(session.auid)))

//    println(lpfcpEndpoint.getCommissionsByParticipant(
//        AUID(session.auid),
//        Pagination(0, 1000)
//    ))

//    println(lpfcpEndpoint.createCommissionParticipant(
//        Commission.Id("7405b7a0-3df9-422d-9dee-faec880cc2a6"),
//        AUID(14),
//        CommissionParticipant.Role.HEAD_OF_EXPERTS,
//        Token.Id(session.token)
//    ))
//    println(lpfcpEndpoint.createCommissionParticipant(
//        Commission.Id("4b70ee7d-ef8a-490f-9fcb-13989884d681"),
//        AUID(14),
//        CommissionParticipant.Role.HEAD_OF_EXPERTS,
//        Token.Id(session.token)
//    ))
//    println(lpfcpEndpoint.createCommissionParticipant(
//        Commission.Id("fb8920ab-b593-4fa1-94e6-3db6adc15db5"),
//        AUID(14),
//        CommissionParticipant.Role.HEAD_OF_EXPERTS,
//        Token.Id(session.token)
//    ))
//    println(lpfcpEndpoint.createCommissionParticipant(
//        Commission.Id("edca9133-c8ec-4b49-9124-a25529a196a4"),
//        AUID(14),
//        CommissionParticipant.Role.EXPERT,
//        Token.Id(session.token)
//    ))
//    println(lpfcpEndpoint.createCommissionParticipant(
//        Commission.Id("9eec405a-7d8e-4c4a-9411-9a5568bcc6f8"),
//        AUID(session.auid), // Me but not @nazik_rachok
//        CommissionParticipant.Role.HEAD_OF_EXPERTS,
//        Token.Id(session.token)
//    ))

    // <============ Create wine sample ============>

//    println(lpfcpEndpoint.createWineSample(
//        Commission.Id("05c54e94-efaf-4c5c-8984-f85f55edbde7"),
//        Wine.Id("654fbf45-fddf-4311-b46e-94c3691370c9"),
//        WineSample.Code("1-A-3-60-102"),
//        WineSample.Id.NONE, // WineSample.Id("a8a7b1a1-9d0c-450e-9738-b3c8bd489961"),
//        Token.Id(session.token)
//    ))
//    println(lpfcpEndpoint.getWineSample(WineSample.Id("2f3fbf6d-e770-42a5-a754-a3d0c3734edd")))
//    println(lpfcpEndpoint.getWineSampleByCommissionIdAndCode(Commission.Id("7a0a6a1f-ca13-4b64-8cf7-c8e33df3b50a"), WineSample.Code("1-A-3-60-102")))

    println(lpfcpEndpoint.createWineSample(
        Commission.Id("7405b7a0-3df9-422d-9dee-faec880cc2a6"),
        Wine.Id("654fbf45-fddf-4311-b46e-94c3691370c9"),
        WineSample.Code("1-A-3-60-102"),
        WineSample.Id.NONE, // WineSample.Id("a8a7b1a1-9d0c-450e-9738-b3c8bd489961"),
        Token.Id(session.token)
    ))
    println(lpfcpEndpoint.createWineSample(
        Commission.Id("4b70ee7d-ef8a-490f-9fcb-13989884d681"),
        Wine.Id("654fbf45-fddf-4311-b46e-94c3691370c9"),
        WineSample.Code("1-A-3-60-102"),
        WineSample.Id.NONE, // WineSample.Id("a8a7b1a1-9d0c-450e-9738-b3c8bd489961"),
        Token.Id(session.token)
    ))
    println(lpfcpEndpoint.createWineSample(
        Commission.Id("edca9133-c8ec-4b49-9124-a25529a196a4"),
        Wine.Id("654fbf45-fddf-4311-b46e-94c3691370c9"),
        WineSample.Code("1-A-3-60-102"),
        WineSample.Id.NONE, // WineSample.Id("a8a7b1a1-9d0c-450e-9738-b3c8bd489961"),
        Token.Id(session.token)
    ))
    println(lpfcpEndpoint.createWineSample(
        Commission.Id("9eec405a-7d8e-4c4a-9411-9a5568bcc6f8"),
        Wine.Id("654fbf45-fddf-4311-b46e-94c3691370c9"),
        WineSample.Code("1-A-3-60-102"),
        WineSample.Id.NONE, // WineSample.Id("a8a7b1a1-9d0c-450e-9738-b3c8bd489961"),
        Token.Id(session.token)
    ))







//    println(credentialsService.createUser(Username("likespro15"), Password("12345678")))
//    credentialsService.changeUsername(AUID(8), Username("12345678l9."))
//    credentialsService.changePassword(AUID(8), Password("<PASSWORD>"))
//    println(credentialsService.checkCredentials(AUID(5), Password("<PASSWORD>")))

//    permissionService.grantPermission(AUID(5), AUID(6), AUID(1), PermissionPattern("^profile:.+$"))
//    println(permissionService.isPermissionExisting(FullPermission(
//        from = AUID(5),
//        to = AUID(6),
//        context = AUID(1),
//        permission = Permission("profile:write")
//    )))
//    println(permissionService.getGivenPermissions(pagination = Pagination(1, 1)))

//    println(tokenService.createTokenSystem(AUID(5), listOf(Permission("profile:write"), Permission("profile:read"))))
//    println(tokenService.updateToken(RefreshToken.Id("xuHHQad04HFwrkEwVVIGu3lqA9B3ncxKaNi0loFU03ai7iaxj03-7BMHqc2AxPL_Q_bWn-IuXtZ8uQE5OxgUSZUzp4NkGVCGe7saTOjPBrRkMN35HKxnMhIFK9WU94XB")))
//    println(tokenService.getToken(Token.Id("YLKUyvBXIPg4DrXJ35fQB-EbkKr_TRV8oZ80SJJM2G9STZyLKhZl9hoG_8fp1Elx")))
//    println(tokenService.getTokenByRefreshTokenId(RefreshToken.Id("fUvW75PzCNTP8-pc4dcWzms9xEfkiGjckZ8THc2QTl_6_0sX9uy2byQ-xSy_uPzAxioYenmvADu5SOQjwU4h8zhDlfXeMIAIpE1Y_eEfthyowZAysowPFcABI0VKQj8G")))



    // <============ Register user ============>

//    println(credentialsService.createUser(Username("eskpirlo.twinkkk"), Password("12345678")))

    // <============ Create first token to log in to AXUS ID Flow ============>

//    println(lpfcpGateway.createTokenSystemWithCredentials(AUID(5), Password("12345678"), listOf(
//        FullPermissionPattern(
//            from = AUID(5),
//            to = AUID(5),
//            context = Credentials.AXUSID.auid,
//            permissionPattern = PermissionPattern("token:.*")
//        )
//    )))

    // <============ Create tokens with tokens ============>

//    println(lpfcpGateway.createTokenSystemWithToken(
//        AUID(5),
//        listOf(
//            AuthGateway.PermissionBuilder.changeUsername(AUID(5), AUID(5)).toFullPermissionPattern(),
//        ),
//        Token.Id("3Yh2uw2hLrVsdjr5rFc8KI_fvxurXyPLyY5GntHIakYX6kD-XTgUpR8550lpgQ_m")
//    ))

    // <============ Change username ============>

//    lpfcpGateway.changeUsername(
//        AUID(5),
//        Username("exterros"),
//        Token.Id("SlLoERDyEIqwJYTMo94or6cxsYgIgzU6PmVkSC0JrCO881oACMK8oqNNUpiaJbpY")
//    )

    // <============ Change password ============>

//    lpfcpGateway.changePassword(
//        AUID(5),
//        Password("12345678"),
//        Token.Id("3Yh2uw2hLrVsdjr5rFc8KI_fvxurXyPLyY5GntHIakYX6kD-XTgUpR8550lpgQ_m")
//    )

    // <============ Create permission ============>

//    println(lpfcpGateway.grantPermission(
//        AuthGateway.PermissionBuilder.createPermission(AUID(5), AUID(4)).toFullPermissionPattern(),
//        Token.Id("UglkZi3fZbNMbpWGf_MUxdqwmUavF-575ni5sFz213lrxJbHFbgrAVCrrVAqUUl4")
//    ))


    // <============ Get all granted permissions ============>

//    println(lpfcpGateway.filterPermissions(
//        AUID(5),
//        AUID(4),
//        Credentials.AXUSID.auid,
//        null,
//        Pagination(0, 1000),
//        Token.Id("3Yh2uw2hLrVsdjr5rFc8KI_fvxurXyPLyY5GntHIakYX6kD-XTgUpR8550lpgQ_m")
//    ))

    // <============ Check if permission was granted ============>

//    println(lpfcpGateway.isPermissionExisting(
//        AuthGateway.PermissionBuilder.filterIssuedPermissions(AUID(5), AUID(5)),
//        Token.Id("3Yh2uw2hLrVsdjr5rFc8KI_fvxurXyPLyY5GntHIakYX6kD-XTgUpR8550lpgQ_m")
//    ))

    // <============ Get profile ============>

//    println(lpfcpGateway.getProfile(AUID(5)))

    // <============ Get Default Variation ID ============>

//    println(lpfcpGateway.getDefaultVariationId(AUID(5)))

    // <============ Get Variation ============>

//    println(lpfcpGateway.getVariation(Variation.Id("0ed1492d-c382-469f-82f8-3f722a1e7d9e")))

    // <============ Create Variation ============>

//    println(lpfcpGateway.createVariation(
//        auid = AUID(5),
//        firstName = FirstName("Likesrpro"),
//        lastName = LastName("twiknk"),
//        status = null,
//        description = null,
//        location = null,
//        tokenId = Token.Id("iqR-vSzdwce0Pw-NbnM6Fjyf5D0RhpWfIYno3ktT0A-LVFgTL8cjGdjt_kzbM2h7")
//    ))

    // <============ Change First Name in variation ============>

//    lpfcpGateway.changeVariationFirstName(Variation.Id("0ed1492d-c382-469f-82f8-3f722a1e7d9e"),
//        FirstName("Andrij"), Token.Id("iqR-vSzdwce0Pw-NbnM6Fjyf5D0RhpWfIYno3ktT0A-LVFgTL8cjGdjt_kzbM2h7"))


//    println("{\"value\":\"xuHHQad04HFwrkEwVVIGu3lqA9B3ncxKaNi0loFU03ai7iaxj03-7BMHqc2AxPL_Q_bWn-IuXtZ8uQE5OxgUSZUzp4NkGVCGe7saTOjPBrRkMN35HKxnMhIFK9WU94XB\"}".decodeObject<RefreshToken.Id>())

//    println(lpfcpGateway.getUserByUsername(Username("likespro")))
//    println(lpfcpGateway.updateToken(RefreshToken.Id("xuHHQad04HFwrkEwVVIGu3lqA9B3ncxKaNi0loFU03ai7iaxj03-7BMHqc2AxPL_Q_bWn-IuXtZ8uQE5OxgUSZUzp4NkGVCGe7saTOjPBrRkMN35HKxnMhIFK9WU94XB")))



    // <============ Start Competition  ============>
//    println(lpfcpEndpoint.getCommissionParticipantByCommissionIdAndAUID(
//        Commission.Id("05c54e94-efaf-4c5c-8984-f85f55edbde7"),
//        AUID(session.auid)
//    ))
//    println(lpfcpEndpoint.startCommission(
//        AUID(session.auid),
//        Commission.Id("05c54e94-efaf-4c5c-8984-f85f55edbde7"),
//        Token.Id(session.token)
//    ))

    println(System.currentTimeMillis() - startedAt)
}