package com.example.mcorvodiaz.moneymon.model

import java.io.Serializable

data class Operation(val id: String = "0",
                     val person: String,
                     val description: String,
                     val date: Long,
                     val cost: Double,
                     val category: String) : Serializable