package com.wallet0x.tronkit.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class TransactionSyncState(
    @PrimaryKey
    val id: String,
    val blockTimestamp: Long
)