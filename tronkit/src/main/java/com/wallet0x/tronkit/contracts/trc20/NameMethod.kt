package com.wallet0x.tronkit.contracts.trc20

import com.wallet0x.tronkit.contracts.ContractMethod


class NameMethod: ContractMethod() {
    override var methodSignature = "name()"
    override fun getArguments() = listOf<Any>()
}
