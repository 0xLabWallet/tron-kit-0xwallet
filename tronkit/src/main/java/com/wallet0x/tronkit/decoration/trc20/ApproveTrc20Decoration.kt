package com.wallet0x.tronkit.decoration.trc20

import com.wallet0x.tronkit.models.Address
import com.wallet0x.tronkit.decoration.TransactionDecoration
import com.wallet0x.tronkit.models.TransactionTag
import java.math.BigInteger

class ApproveTrc20Decoration(
    val contractAddress: Address,
    val spender: Address,
    val value: BigInteger
) : TransactionDecoration() {

    override fun tags(userAddress: Address): List<String> =
        listOf(contractAddress.hex, TransactionTag.TRC20_APPROVE)
}
