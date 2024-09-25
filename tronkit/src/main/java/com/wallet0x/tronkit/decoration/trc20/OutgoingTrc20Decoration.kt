package com.wallet0x.tronkit.decoration.trc20

import com.wallet0x.tronkit.models.Address
import com.wallet0x.tronkit.decoration.TokenInfo
import com.wallet0x.tronkit.decoration.TransactionDecoration
import com.wallet0x.tronkit.models.TransactionTag
import java.math.BigInteger

class OutgoingTrc20Decoration(
    val contractAddress: Address,
    val to: Address,
    val value: BigInteger,
    val sentToSelf: Boolean,
    val tokenInfo: TokenInfo?
) : TransactionDecoration() {

    override fun tags(userAddress: Address): List<String> =
        listOf(contractAddress.base58, TransactionTag.TRC20_TRANSFER, TransactionTag.trc20Outgoing(contractAddress.base58), TransactionTag.OUTGOING)

}
