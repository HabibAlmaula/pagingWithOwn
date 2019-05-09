package com.fkammediacenter.testpsak

import com.fkammediacenter.testpsak.base.BasePaginationViewModel
import com.fkammediacenter.testpsak.daoResponse.DonaturKotakList
import com.fkammediacenter.testpsak.daoResponse.DonaturKotakResponse
import com.fkammediacenter.testpsak.source.DonaturDataSourceFactory

class MainViewModel: BasePaginationViewModel<DonaturDataSourceFactory, DonaturKotakList?>(){
    init {
        dataSourceFactory = DonaturDataSourceFactory(getListener(), null, null, null)
    }

    override fun getPageSize(): Int = 3


    /**
     * Handles a new user search
     */
    fun newSearch(donatur: String,id_fr : String,nama : String) {
        dataSourceFactory.donatur = donatur
        dataSourceFactory.id_fr = id_fr
        dataSourceFactory.nama = nama

        clearData()
    }

}