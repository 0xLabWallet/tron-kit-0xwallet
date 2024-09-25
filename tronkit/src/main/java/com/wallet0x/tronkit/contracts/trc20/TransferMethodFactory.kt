package com.wallet0x.tronkit.contracts.trc20

import com.wallet0x.tronkit.models.Address
import com.wallet0x.tronkit.contracts.ContractMethodFactory
import com.wallet0x.tronkit.contracts.ContractMethodHelper
import com.wallet0x.tronkit.toBigInteger

object TransferMethodFactory : ContractMethodFactory {

    override val methodId = ContractMethodHelper.getMethodId(TransferMethod.methodSignature)

    override fun createMethod(inputArguments: ByteArray): TransferMethod {
        val address = Address.fromRawWithoutPrefix(inputArguments.copyOfRange(12, 32))
        val value = inputArguments.copyOfRange(32, 64).toBigInteger()

        return TransferMethod(address, value)
    }

}
