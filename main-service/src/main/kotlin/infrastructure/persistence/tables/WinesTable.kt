package infrastructure.persistence.tables

import com.axus.id.model.value.AUID
import com.axus.winelore.model.entity.Wine
import eth.likespro.commons.models.value.Iteration
import eth.likespro.commons.models.value.Timestamp
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.timestamp
import org.jetbrains.exposed.sql.statements.UpdateBuilder

object WinesTable: Table("wines") {
    val id = varchar("id", 36).uniqueIndex()
    val producer = long("producer")
    val name = text("name")
    val iteration = long("iteration")
    val color = enumerationByName("color", 30, Wine.Color::class)
    val type = enumerationByName("type", 30, Wine.Type::class)
    val createdAt = timestamp("created_at")

    override val primaryKey = PrimaryKey(id)

    init {
        uniqueIndex(producer, name, iteration)
    }

    fun fromRow(row: ResultRow): Wine = Wine(
        id = Wine.Id(row[id]),
        producer = AUID(row[producer]),
        name = Wine.Name(row[name]),
        iteration = Iteration(row[iteration]),
        color = row[color],
        type = row[type],
        createdAt = Timestamp(row[createdAt])
    )

    fun copy(wine: Wine, to: UpdateBuilder<Int>) {
        to[id] = wine.id.value
        to[producer] = wine.producer.value
        to[name] = wine.name.value
        to[iteration] = wine.iteration.value
        to[color] = wine.color
        to[type] = wine.type
        to[createdAt] = wine.createdAt.toInstant()
    }
}