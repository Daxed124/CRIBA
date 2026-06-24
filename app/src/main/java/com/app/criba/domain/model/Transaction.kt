package com.app.criba.domain.model

data class Transaction(
    val id: Long = 0,
    val cycleId: Long,
    val type: TransactionType,
    val amount: Double,
    val category: ExpenseCategory? = null,
    val description: String,
    val date: Long, // epoch millis
    val isSynced: Boolean = false
)

enum class TransactionType(val displayName: String) {
    INGRESO("Ingreso"),
    GASTO("Gasto")
}

enum class ExpenseCategory(val displayName: String) {
    FERTILIZANTE("Fertilizante"),
    MANO_OBRA("Mano de obra"),
    SEMILLAS("Semillas"),
    MAQUINARIA("Maquinaria"),
    RIEGO("Riego"),
    TRANSPORTE("Transporte"),
    OTROS("Otros")
}
