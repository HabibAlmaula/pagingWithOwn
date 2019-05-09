package com.fkammediacenter.testpsak.utils.api

import com.fkammediacenter.testpsak.daoResponse.DonaturKotakList
import com.fkammediacenter.testpsak.daoResponse.DonaturKotakResponse
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

@Mapper
interface DonaturMapper{
    companion object {
        val Instance = Mappers.getMapper(DonaturMapper::class.java)
    }

    fun map(donatur : DonaturKotakResponse) : DonaturKotakResponse
    fun mapList(donatur : List<DonaturKotakList>) : List<DonaturKotakList>
}