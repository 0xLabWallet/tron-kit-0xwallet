package com.wallet0x.tronkit.transaction

import com.google.protobuf.ByteString
import com.wallet0x.tronkit.TronKit.TransactionError
import com.wallet0x.tronkit.models.Contract
import com.wallet0x.tronkit.models.TransferContract
import com.wallet0x.tronkit.models.TriggerSmartContract
import com.wallet0x.tronkit.network.BroadcastTransactionResponse
import com.wallet0x.tronkit.network.CreatedTransaction
import com.wallet0x.tronkit.network.TronGridService
import org.tron.protos.Protocol.Transaction

class TransactionSender(
    private val tronGridService: TronGridService
) {
    private fun isValidCreatedTransaction(createdTransaction: CreatedTransaction, contract: Contract): Boolean {
        val rawData = Transaction.raw.parseFrom(ByteString.fromHex(createdTransaction.raw_data_hex))
        val createdContractProto = if (rawData.contractCount == 1) rawData.getContract(0) else null
        val originalContractProto = contract.proto

        return createdContractProto != null &&
                createdContractProto.type == originalContractProto.type &&
                createdContractProto.hasParameter() &&
                createdContractProto.parameter == originalContractProto.parameter
    }

    suspend fun createTransaction(contract: Contract, feeLimit: Long?): CreatedTransaction {
        val createdTransaction = when (contract) {
            is TransferContract -> {
                tronGridService.createTransaction(
                    fromAddress = contract.ownerAddress,
                    toAddress = contract.toAddress,
                    amount = contract.amount
                )
            }

            is TriggerSmartContract -> {
                tronGridService.triggerSmartContract(
                    ownerAddress = contract.ownerAddress,
                    contractAddress = contract.contractAddress,
                    functionSelector = contract.functionSelector ?: throw TransactionError.NoFunctionSelector(contract),
                    parameter = contract.parameter ?: throw TransactionError.NoParameter(contract),
                    feeLimit = feeLimit ?: throw TransactionError.NoFeeLimit(contract),
                    callValue = contract.callValue?.toLong() ?: 0,
                )
            }

            else -> {
                throw TransactionError.NotSupportedContract(contract)
            }
        }

        if (isValidCreatedTransaction(createdTransaction, contract)) {
            return createdTransaction
        } else {
            throw TransactionError.InvalidCreatedTransaction(createdTransaction.raw_data_hex)
        }
    }

    suspend fun broadcastTransaction(createdTransaction: CreatedTransaction, signer: Signer): BroadcastTransactionResponse {
        val signature = signer.sign(createdTransaction)
        return tronGridService.broadcastTransaction(createdTransaction, signature)
    }

}
