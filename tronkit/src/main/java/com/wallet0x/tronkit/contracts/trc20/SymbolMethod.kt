package com.wallet0x.tronkit.contracts.trc20

import com.wallet0x.tronkit.contracts.ContractMethod


class SymbolMethod: ContractMethod() {
    override var methodSignature = "symbol()"
    override fun getArguments() = listOf<Any>()
}
