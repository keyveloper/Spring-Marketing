package org.example.marketing.repository.board

import org.example.marketing.dao.board.AdvertisementEntity
import org.example.marketing.dao.board.AdvertisementLocationEntity
import org.example.marketing.dto.board.request.FindAdvertisementsLocations
import org.example.marketing.dto.board.request.SaveAdvertisementLocation
import org.example.marketing.table.AdvertisementLocationsTable
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.and
import org.springframework.stereotype.Component

@Component
class AdvertisementLocationRepository {

    fun save(saveDto: SaveAdvertisementLocation): Long {
        val advertisementLocation = AdvertisementLocationEntity.new {
            saveDto.advertisementId
            saveDto.city
            saveDto.district
            saveDto.latitude
            saveDto.longitude
        }

        return advertisementLocation.id.value
    }

    fun findAllByLocations(findDto: FindAdvertisementsLocations): List<AdvertisementLocationEntity> {
        val cities = findDto.cities.filterNotNull()
        val districts = findDto.districts.filterNotNull()

        return AdvertisementLocationEntity.find {
            var condition : Op<Boolean> = Op.TRUE
            if (cities.isNotEmpty()) {
                condition = condition and (AdvertisementLocationsTable.city inList cities)
            }
            if (districts.isNotEmpty()) {
                condition = condition and (AdvertisementLocationsTable.district inList districts)
            }
            condition
        }.toList()
    }


}