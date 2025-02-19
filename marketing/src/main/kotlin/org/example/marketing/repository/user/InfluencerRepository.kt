package org.example.marketing.repository.user

import org.example.marketing.dto.user.request.MakeNewInfluencerRequest
import org.example.marketing.entity.user.Influencer
import org.example.marketing.table.InfluencersTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository

@Repository
class InfluencerRepository {

    private fun ResultRow.toInfluencer() = Influencer(
        id = this[InfluencersTable.id].value,
        loginId = this[InfluencersTable.loginId],
        email = this[InfluencersTable.email],
        name = this[InfluencersTable.name],
        contact = this[InfluencersTable.contact],
        birthday = this[InfluencersTable.birthday],
        createdAt = this[InfluencersTable.createdAt],
    )

    fun insert(request: MakeNewInfluencerRequest): Long? = transaction {
        val newEntityId = InfluencersTable.insertAndGetId {
            it[loginId] = request.loginId
            it[password] = request.password
            it[email] = request.email
            it[name] = request.name
            it[contact] = request.contact
            it[birthday] = request.birthday
        }

        newEntityId.value
    }

    fun findAll(): List<Influencer> = transaction {
        InfluencersTable.selectAll().map { it.toInfluencer() }
    }

    fun findById(id: Long): Influencer? = transaction {
        InfluencersTable.select(InfluencersTable.columns)
            .where { InfluencersTable.id eq id }
            .map { it.toInfluencer() }
            .singleOrNull()
    }
}