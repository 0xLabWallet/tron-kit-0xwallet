package com.wallet0x.tronkit.decoration.trc20

import com.wallet0x.tronkit.models.Address
import com.wallet0x.tronkit.decoration.Event
import com.wallet0x.tronkit.decoration.TokenInfo
import com.wallet0x.tronkit.models.TransactionTag
import com.wallet0x.tronkit.models.Trc20EventRecord
import java.math.BigInteger

class Trc20ApproveEvent(
    record: Trc20EventRecord
) : Event(record.transactionHash, record.contractAddress) {

    val owner: Address = record.from
    val spender: Address = record.to
    val value: BigInteger = record.value
    val tokenInfo: TokenInfo = TokenInfo(record.tokenName, record.tokenSymbol, record.tokenDecimal)

    override fun tags(userAddress: Address): List<String> {
        return mutableListOf(contractAddress.hex, TransactionTag.TRC20_APPROVE)
    }
}
