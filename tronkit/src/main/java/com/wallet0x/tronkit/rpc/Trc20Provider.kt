package com.wallet0x.tronkit.rpc

import com.wallet0x.tronkit.contracts.ContractMethodHelper
import com.wallet0x.tronkit.contracts.trc20.DecimalsMethod
import com.wallet0x.tronkit.contracts.trc20.NameMethod
import com.wallet0x.tronkit.contracts.trc20.SymbolMethod
import com.wallet0x.tronkit.decoration.TokenInfo
import com.wallet0x.tronkit.models.Address
import com.wallet0x.tronkit.network.ApiKeyProvider
import com.wallet0x.tronkit.network.Network
import com.wallet0x.tronkit.network.TronGridService
import com.wallet0x.tronkit.toBigInteger
import com.wallet0x.tronkit.toHexString

class Trc20Provider(
    private val tronGridService: TronGridService
) {
    class TokenNotFoundException : Throwable()

    suspend fun getTokenInfo(contractAddress: Address): TokenInfo {
        val name = getTokenName(contractAddress)
        val symbol = getTokenSymbol(contractAddress)
        val decimals = getDecimals(contractAddress)

        return TokenInfo(name, symbol, decimals)
    }

    suspend fun getDecimals(contractAddress: Address): Int {
        val response = tronGridService.ethCall(
            contractAddress = "0x${contractAddress.hex}",
            data = DecimalsMethod().encodedABI().toHexString()
        )
        if (response.isEmpty()) throw TokenNotFoundException()

        return response.sliceArray(IntRange(0, 31)).toBigInteger().toInt()
    }

    suspend fun getTokenSymbol(contractAddress: Address): String {
        val response = tronGridService.ethCall(
            contractAddress = "0x${contractAddress.hex}",
            data = SymbolMethod().encodedABI().toHexString()
        )

        if (response.isEmpty()) throw TokenNotFoundException()

        val argumentTypes = listOf(ByteArray::class)
        val parsedArguments = ContractMethodHelper.decodeABI(response, argumentTypes)
        val stringBytes = parsedArguments[0] as? ByteArray ?: throw TokenNotFoundException()

        return String(stringBytes)
    }

    suspend fun getTokenName(contractAddress: Address): String {
        val response = tronGridService.ethCall(
            contractAddress = "0x${contractAddress.hex}",
            data = NameMethod().encodedABI().toHexString()
        )

        if (response.isEmpty()) throw TokenNotFoundException()

        val argumentTypes = listOf(ByteArray::class)
        val parsedArguments = ContractMethodHelper.decodeABI(response, argumentTypes)
        val stringBytes = parsedArguments[0] as? ByteArray ?: throw TokenNotFoundException()

        return String(stringBytes)
    }

    companion object {
        fun getInstance(network: Network, tronGridApiKeys: List<String>): Trc20Provider {
            val apiKeyProvider = ApiKeyProvider(tronGridApiKeys)
            val tronGridService = TronGridService(network, apiKeyProvider)
            return Trc20Provider(tronGridService)
        }
    }

}
