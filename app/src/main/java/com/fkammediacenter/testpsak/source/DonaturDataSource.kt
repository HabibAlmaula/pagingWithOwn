package com.fkammediacenter.testpsak.source

import android.annotation.SuppressLint
import com.fkammediacenter.testpsak.base.BaseDataSource
import com.fkammediacenter.testpsak.daoResponse.DonaturKotakList
import com.fkammediacenter.testpsak.utils.api.BaseApiService
import com.fkammediacenter.testpsak.utils.api.DonaturMapper
import com.fkammediacenter.testpsak.utils.api.UtilsApi
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DonaturDataSource(var donatur: String,
                               var id_fr: String,
                               var nama : String) : BaseDataSource<DonaturKotakList?>(){
    private val baseApiService : BaseApiService = UtilsApi.getAPIService()



    @SuppressLint("CheckResult")
    override fun loadInitialData(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, DonaturKotakList?>) {
        if (nama == "" || nama == null){
            baseApiService.getAllDonaturKotak(donatur, id_fr, 0, params.requestedLoadSize)
                .onErrorResumeNext { throwable -> Single.error(throwable)}
                .flatMap { response ->
                    if (!response.isSuccessful) {
                        Single.error(Throwable(response.code().toString()))
                    } else Single.just(response) }
                .map { response -> response.body() }
                .map { list -> list.donaturlist?.let { DonaturMapper.Instance.mapList(it) } }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this::addDisposable)
                .subscribe(
                    { items -> items?.let { submitInitialData(it, params, callback) } },
                    { error -> submitInitialError(error) }
                )
        }else
          baseApiService.searchDonaturKotak(donatur,id_fr,nama,0, params.requestedLoadSize)
              .onErrorResumeNext { throwable -> Single.error(throwable)}
              .flatMap { response ->
                  if (!response.isSuccessful) {
                      Single.error(Throwable(response.code().toString()))
                  } else Single.just(response) }
              .map { response -> response.body() }
              .map { list -> list.donaturlist?.let { DonaturMapper.Instance.mapList(it) } }
              .subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread())
              .doOnSubscribe(this::addDisposable)
              .subscribe(
                  { items -> items?.let { submitInitialData(it, params, callback) } },
                  { error -> submitInitialError(error) }
              )
    }

    @SuppressLint("CheckResult")
    override fun loadAditionalData(params: LoadParams<Int>, callback: LoadCallback<Int, DonaturKotakList?>) {
        if (nama =="" || nama == null){
            baseApiService.getAllDonaturKotak(donatur,id_fr,params.key, params.requestedLoadSize)
                .onErrorResumeNext { throwable -> Single.error(throwable)}
                .flatMap { response ->
                    if (!response.isSuccessful) {
                        Single.error(Throwable(response.code().toString()))
                    } else Single.just(response) }
                .map { response -> response.body() }
                .map { list -> list.donaturlist?.let { DonaturMapper.Instance.mapList(it) } }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this::addDisposable)
                .subscribe(
                    { items -> items?.let { submitData(it, params, callback) } },
                    { error -> submitError(error) }
                )
        }else
            baseApiService.searchDonaturKotak(donatur,id_fr,nama, params.key, params.requestedLoadSize)
                .onErrorResumeNext { throwable -> Single.error(throwable)}
                .flatMap { response ->
                    if (!response.isSuccessful) {
                        Single.error(Throwable(response.code().toString()))
                    } else Single.just(response) }
                .map { response -> response.body() }
                .map { list -> list.donaturlist?.let { DonaturMapper.Instance.mapList(it) } }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this::addDisposable)
                .subscribe(
                    { items -> items?.let { submitData(it, params, callback) } },
                    { error -> submitError(error) }
                )

    }

}