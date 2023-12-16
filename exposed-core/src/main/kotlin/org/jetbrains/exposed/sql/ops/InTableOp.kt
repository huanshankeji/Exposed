package org.jetbrains.exposed.sql.ops

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ExpressionWithColumnType
import org.jetbrains.exposed.sql.Query
import org.jetbrains.exposed.sql.Table

// for MySQL only
class InTableOp(
    expr: Column<*>,
    /** Returns the query to check against. */
    val table: Table,
    /** Returns `true` if the check is inverted, `false` otherwise. */
    val isInTable: Boolean = true
) {
    fun
}
