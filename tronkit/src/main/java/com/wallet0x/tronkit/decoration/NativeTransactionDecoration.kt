package com.wallet0x.tronkit.decoration

import com.wallet0x.tronkit.models.Address
import com.wallet0x.tronkit.models.Contract
import com.wallet0x.tronkit.models.TransactionTag
import com.wallet0x.tronkit.models.TransferAssetContract
import com.wallet0x.tronkit.models.TransferContract

class NativeTransactionDecoration(
    val contract: Contract
) : TransactionDecoration() {

    override fun tags(userAddress: Address): List<String> {
        val tags = mutableListOf<String>()

        when (contract) {
            is TransferContract -> {
                tags.add(TransactionTag.TRX_COIN)

                if (contract.ownerAddress == userAddress) {
                    tags.add(TransactionTag.TRX_COIN_OUTGOING)
                    tags.add(TransactionTag.OUTGOING)
                }
                if (contract.toAddress == userAddress) {
                    tags.add(TransactionTag.TRX_COIN_INCOMING)
                    tags.add(TransactionTag.INCOMING)
                }
            }

            is TransferAssetContract -> {
                tags.add(TransactionTag.TRC10)

                if (contract.ownerAddress == userAddress) {
                    tags.add(TransactionTag.trc10Outgoing(contract.assetName))
                    tags.add(TransactionTag.OUTGOING)
                }
                if (contract.toAddress == userAddress) {
                    tags.add(TransactionTag.trc10Incoming(contract.assetName))
                    tags.add(TransactionTag.INCOMING)
                }
            }

            else -> {}
        }

        return tags
    }

}
