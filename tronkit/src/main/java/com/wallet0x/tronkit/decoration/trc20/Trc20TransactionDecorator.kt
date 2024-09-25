package com.wallet0x.tronkit.decoration.trc20

import com.wallet0x.tronkit.models.Address
import com.wallet0x.tronkit.contracts.trc20.ApproveMethod
import com.wallet0x.tronkit.contracts.ContractMethodFactories
import com.wallet0x.tronkit.contracts.trc20.ApproveMethodFactory
import com.wallet0x.tronkit.contracts.trc20.TransferMethod
import com.wallet0x.tronkit.contracts.trc20.TransferMethodFactory
import com.wallet0x.tronkit.decoration.Event
import com.wallet0x.tronkit.decoration.ITransactionDecorator
import com.wallet0x.tronkit.decoration.TransactionDecoration
import com.wallet0x.tronkit.hexStringToByteArray
import com.wallet0x.tronkit.models.InternalTransaction
import com.wallet0x.tronkit.models.TriggerSmartContract

class Trc20TransactionDecorator(
    private val userAddress: Address
) : ITransactionDecorator {

    private val factories = ContractMethodFactories()

    init {
        factories.registerMethodFactories(listOf(TransferMethodFactory, ApproveMethodFactory))
    }

    override fun decoration(
        contract: TriggerSmartContract,
        internalTransactions: List<InternalTransaction>,
        events: List<Event>
    ): TransactionDecoration? {
        val contractMethod = factories.createMethodFromInput(contract.data.hexStringToByteArray())

        return when {
            contractMethod is TransferMethod && contract.ownerAddress == userAddress -> {
                val tokenInfo =
                    (events.firstOrNull { it is Trc20TransferEvent && it.contractAddress == contract.contractAddress } as? Trc20TransferEvent)?.tokenInfo
                OutgoingTrc20Decoration(
                    contractAddress = contract.contractAddress,
                    to = contractMethod.to,
                    value = contractMethod.value,
                    sentToSelf = contractMethod.to == userAddress,
                    tokenInfo = tokenInfo
                )
            }

            contractMethod is ApproveMethod -> {
                ApproveTrc20Decoration(
                    contractAddress = contract.contractAddress,
                    spender = contractMethod.spender,
                    value = contractMethod.value
                )
            }

            else -> null
        }
    }

}
