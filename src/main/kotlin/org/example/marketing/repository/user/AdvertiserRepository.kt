package org.example.marketing.repository.user

import org.example.marketing.dto.user.request.MakeNewAdvertiserRequest
import org.example.marketing.Dao.user.Advertiser
import org.example.marketing.table.AdvertisersTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository

@Repository
class AdvertiserRepository {

    private fun ResultRow.toAdvertiser() = Advertiser(
        id = this[AdvertisersTable.id].value,
        loginId = this[AdvertisersTable.loginId],
        email = this[AdvertisersTable.email],
        name = this[AdvertisersTable.name],
        contact = this[AdvertisersTable.contact],
        homepageUrl = this[AdvertisersTable.homepageUrl],
        advertiserType = this[AdvertisersTable.advertiserType],
        companyName = this[AdvertisersTable.companyName],
    )

    fun insert(request: MakeNewAdvertiserRequest): Long? = transaction {
        val newEntityId  = AdvertisersTable.insertAndGetId {
            it[loginId] = request.loginId
            it[password] = request.password
            it[email] = request.email
            it[name] = request.name
            it[contact] = request.contact
            it[homepageUrl] = request.homepageUrl
            it[advertiserType] = request.advertiserType
            it[companyName] = request.companyName
        }

        newEntityId.value
    }

    fun findAll(): List<Advertiser> = transaction {
        AdvertisersTable.selectAll().map { it.toAdvertiser() }
    }

    fun findById(id: Long): Advertiser? = transaction {
        AdvertisersTable.select(AdvertisersTable.columns)
            .where { AdvertisersTable.id eq id }
            .map { it.toAdvertiser() }
            .singleOrNull()
    }
}
