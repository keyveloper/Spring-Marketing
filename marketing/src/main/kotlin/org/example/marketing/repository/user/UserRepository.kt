package org.example.marketing.repository.user

import org.example.marketing.entity.user.Advertiser
import org.example.marketing.table.AdvertisersTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository

@Repository
class UserRepository {

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
