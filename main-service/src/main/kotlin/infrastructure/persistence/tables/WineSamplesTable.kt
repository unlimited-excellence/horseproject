package infrastructure.persistence.tables

import com.axus.winelore.model.entity.Commission
import com.axus.winelore.model.entity.Wine
import com.axus.winelore.model.entity.Competition
import com.axus.winelore.model.entity.WineSample
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.statements.UpdateBuilder

object WineSamplesTable : Table("wine_samples") {
    val id = varchar("id", 36).uniqueIndex()
    val commissionId = varchar("commission_id", 36)
    val wineId = varchar("wine_id", 36)
    val code = text("code")
    val previousWineSampleId = varchar("previous_wine_sample_id", 36)

    override val primaryKey = PrimaryKey(id)
    init {
        uniqueIndex(commissionId, code)
        uniqueIndex(commissionId, previousWineSampleId)
    }

    fun fromRow(row: ResultRow) = WineSample(
        id = WineSample.Id(row[id]),
        commissionId = Commission.Id(row[commissionId]),
        wineId = Wine.Id(row[wineId]),
        code = WineSample.Code(row[code]),
        previousWineSampleId = WineSample.Id(row[previousWineSampleId])
    )

    fun copy(wineSample: WineSample, to: UpdateBuilder<Int>) {
        to[id] = wineSample.id.value
        to[commissionId] = wineSample.commissionId.value
        to[wineId] = wineSample.wineId.value
        to[code] = wineSample.code.value
        to[previousWineSampleId] = wineSample.previousWineSampleId.value
    }
}