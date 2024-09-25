package com.wallet0x.tronkit.models

import com.wallet0x.tronkit.decoration.TransactionDecoration

class FullTransaction(
    val transaction: Transaction,
    val decoration: TransactionDecoration
)
