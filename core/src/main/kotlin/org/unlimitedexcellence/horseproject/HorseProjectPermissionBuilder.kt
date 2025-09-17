package org.unlimitedexcellence.horseproject

import com.axus.id.model.aggregate.FullPermission
import com.axus.id.model.value.AUID
import com.axus.id.model.value.Permission

object HorseProjectPermissionBuilder {
    // <============ Transaction Service ============>

    fun createTransaction(horseProjectAUID: AUID, sender: AUID?, recipient: AUID?, grantee: AUID? = null) = FullPermission(
        sender ?: horseProjectAUID,
        grantee,
        horseProjectAUID,
        Permission("transaction:createTransaction:${recipient?.value}")
    )
}