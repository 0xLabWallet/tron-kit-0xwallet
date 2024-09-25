package com.wallet0x.tronkit.decoration

import com.wallet0x.tronkit.models.Address

open class TransactionDecoration {
    open fun tags(userAddress: Address): List<String> = listOf()
}
