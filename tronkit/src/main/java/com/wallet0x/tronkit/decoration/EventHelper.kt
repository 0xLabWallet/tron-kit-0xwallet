package com.wallet0x.tronkit.decoration

import com.wallet0x.tronkit.decoration.trc20.Trc20ApproveEvent
import com.wallet0x.tronkit.decoration.trc20.Trc20TransferEvent
import com.wallet0x.tronkit.models.Trc20EventRecord

object EventHelper {

    fun eventFromRecord(record: Trc20EventRecord): Event? = when (record.type) {
        "Transfer" -> Trc20TransferEvent(record)
        "Approval" -> Trc20ApproveEvent(record)
        else -> null
    }

}
