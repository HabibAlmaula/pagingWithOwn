package com.fkammediacenter.testpsak.source

import androidx.paging.DataSource
import com.fkammediacenter.testpsak.base.OnDataSourceLoading
import com.fkammediacenter.testpsak.daoResponse.DonaturKotakList

class DonaturDataSourceFactory(var loading : OnDataSourceLoading,
                               var donatur: String?,
                               var id_fr: String?,
                               var nama : String? ): DataSource.Factory<Int, DonaturKotakList?>(){

    lateinit var source: DonaturDataSource

    override fun create(): DataSource<Int, DonaturKotakList?>? {
        if (::source.isInitialized && source!=null) source.invalidate()
        if (donatur !=null && id_fr != null && nama !=null){
            source = DonaturDataSource(donatur!!, id_fr!!, nama!!)
            source.onDataSourceLoading = loading
            return source
        }

        return null
    }


}