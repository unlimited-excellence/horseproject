package infrastructure.persistence.tables

import com.axus.id.model.value.AUID
import com.axus.winelore.model.entity.WineSampleAssessment
import com.axus.winelore.model.entity.Commission
import com.axus.winelore.model.entity.WineSample
import eth.likespro.commons.models.value.Timestamp
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.timestamp
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.json.JSONObject

object WineSampleAssessmentsTable : Table("wine_sample_assessments") {
    val id = varchar("id", 36).uniqueIndex()
    val commissionId = reference("commission_id", CommissionsTable.id)
    val from = long("from") // Assuming AUID is a 36-character string
    val wineSampleId = reference("wine_sample_id", WineSamplesTable.id)
    val mark = integer("mark")
    val metadata = text("metadata") // Yes, I really save JSON in SQL!
    val createdAt = timestamp("created_at")

    override val primaryKey = PrimaryKey(id)

    init {
        uniqueIndex(commissionId, from, wineSampleId)
    }

    fun fromRow(row: ResultRow): WineSampleAssessment {
        return WineSampleAssessment(
            id = WineSampleAssessment.Id(row[id]),
            commissionId = Commission.Id(row[commissionId]),
            from = AUID(row[from]),
            wineSampleId = WineSample.Id(row[wineSampleId]),
            mark = WineSampleAssessment.Mark(row[mark]),
            metadata = JSONObject(row[metadata]),
            createdAt = Timestamp(row[createdAt]),
        )
    }

    fun copy(wineSampleAssessment: WineSampleAssessment, to: UpdateBuilder<Int>) = to.apply {
        this[id] = wineSampleAssessment.id.value
        this[commissionId] = wineSampleAssessment.commissionId.value
        this[from] = wineSampleAssessment.from.value
        this[wineSampleId] = wineSampleAssessment.wineSampleId.value
        this[mark] = wineSampleAssessment.mark.value
        this[metadata] = wineSampleAssessment.metadata.toString()
        this[createdAt] = wineSampleAssessment.createdAt.toInstant()
    }
}