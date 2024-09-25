package com.wallet0x.tronkit.contracts.trc20

import com.wallet0x.tronkit.models.Address
import com.wallet0x.tronkit.contracts.ContractMethod
import java.math.BigInteger

class TransferMethod(val to: Address, val value: BigInteger) : ContractMethod() {

    override val methodSignature = Companion.methodSignature
    override fun getArguments() = listOf(to, value)

    companion object {
        const val methodSignature = "transfer(address,uint256)"
    }

}
