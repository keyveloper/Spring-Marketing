package org.example.marketing.repository.board

import org.example.marketing.dao.board.Advertisement
import org.jetbrains.exposed.sql.ResultRow
import org.springframework.stereotype.Repository

@Repository
class AdvertisementRepository {

    private fun ResultRow.toAdvertisement() = Advertisement(

    )

    fun insert()

    fun delete()

    fun update()
