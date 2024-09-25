package com.wallet0x.tronkit.contracts.trc20

import com.wallet0x.tronkit.contracts.ContractMethod


class DecimalsMethod: ContractMethod() {
    override var methodSignature = "decimals()"
    override fun getArguments() = listOf<Any>()
}
