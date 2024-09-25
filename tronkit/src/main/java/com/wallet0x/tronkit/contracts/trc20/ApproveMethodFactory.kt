package com.wallet0x.tronkit.contracts.trc20

import com.wallet0x.tronkit.models.Address
import com.wallet0x.tronkit.contracts.ContractMethodFactory
import com.wallet0x.tronkit.contracts.ContractMethodHelper
import com.wallet0x.tronkit.toBigInteger

object ApproveMethodFactory : ContractMethodFactory {

    override val methodId = ContractMethodHelper.getMethodId(ApproveMethod.methodSignature)

    override fun createMethod(inputArguments: ByteArray): ApproveMethod {
        val address = Address.fromRawWithoutPrefix(inputArguments.copyOfRange(12, 32))
        val value = inputArguments.copyOfRange(32, 64).toBigInteger()

        return ApproveMethod(address, value)
    }

}
