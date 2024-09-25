package com.wallet0x.tronkit.decoration.trc20

import com.wallet0x.tronkit.models.Address
import com.wallet0x.tronkit.decoration.Event
import com.wallet0x.tronkit.decoration.TokenInfo
import com.wallet0x.tronkit.models.TransactionTag
import com.wallet0x.tronkit.models.Trc20EventRecord
import java.math.BigInteger

class Trc20TransferEvent(
    record: Trc20EventRecord
) : Event(record.transactionHash, record.contractAddress) {

    val from: Address = record.from
    val to: Address = record.to
    val value: BigInteger = record.value
    val tokenInfo: TokenInfo = TokenInfo(record.tokenName, record.tokenSymbol, record.tokenDecimal)

    override fun tags(userAddress: Address): List<String> {
        val tags = mutableListOf(contractAddress.base58, TransactionTag.TRC20_TRANSFER)

        if (from == userAddress) {
            tags.add(TransactionTag.trc20Outgoing(contractAddress.base58))
            tags.add(TransactionTag.OUTGOING)
        }

        if (to == userAddress) {
            tags.add(TransactionTag.trc20Incoming(contractAddress.base58))
            tags.add(TransactionTag.INCOMING)
        }

        return tags
    }
}
