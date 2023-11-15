package org.jetbrains.exposed.sql.ops

import org.jetbrains.exposed.sql.IColumnType
import org.jetbrains.exposed.sql.OneDimArrayColumnType
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.QueryBuilder
import org.jetbrains.exposed.sql.toColumnType
import kotlin.reflect.KClass

class AllAnyOp<T>(val opType: OpType, val elementType: IColumnType, val list: Iterable<T>, val toTypedArray: List<T>.() -> Any) : Op<T>() {
    // TODO remove
    /*
    init {
        require(list.any())
        val type = list.asSequence().filter { it !== null }::class
        require(list.all { it === null || it!!::class == type })
    }
     */

    enum class OpType {
        All, Any
    }

    override fun toQueryBuilder(queryBuilder: QueryBuilder) = queryBuilder {
        +when (opType) {
            OpType.All -> "ALL"
            OpType.Any -> "ANY"
        }
        +'('
        // +"ARRAY[" // This syntax is for PostgresSQL.
        val array = list.toList().toTypedArray()
        registerArgument(OneDimArrayColumnType(elementType), array)
        // +"]"
        +')'
    }
}

// TODO remove
/*
inline fun <reified T> AllAnyOp(opType: AllAnyOp.OpType, list: Iterable<T>) =
    AllAnyOp(opType, (T::class as KClass<*>).toColumnType(), list)
*/

inline fun <reified T> AllAnyOp(opType: AllAnyOp.OpType, list: Iterable<T>) =
    AllAnyOp(opType, (T::class as KClass<*>).toColumnType(), list) { this.toTypedArray() }
