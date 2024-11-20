package com.weather.myweatherapplication.domain.mapper

import com.weather.myweatherapplication.data.remote.entity.CityDto
import com.weather.myweatherapplication.data.remote.entity.CoordDto
import com.weather.myweatherapplication.domain.model.City
import com.weather.myweatherapplication.domain.model.Coord
import javax.inject.Inject

class CityDtoMapper @Inject constructor() : IEntityMapper<CityDto, City> {
    override fun mapFromEntity(entity: CityDto): City {
        return City(
            entity.country,
            entity.timezone,
            entity.sunrise,
            entity.sunset,
            entity.cityName,
            Coord(
                entity.coordinate.latitude,
                entity.coordinate.longitude
            )
        )
    }

    override fun entityFromModel(model: City): CityDto {
        return CityDto(
            model.country,
            model.timezone,
            model.sunrise,
            model.sunset,
            model.cityName,
            CoordDto(
                model.coordinate.latitude,
                model.coordinate.longitude
            )
        )
    }

}