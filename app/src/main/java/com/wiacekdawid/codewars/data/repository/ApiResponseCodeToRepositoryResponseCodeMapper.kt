package com.wiacekdawid.codewars.data.repository

/**
 * Created by dawidwiacek on 10/05/2018.
 */

object ApiResponseCodeToRepositoryResponseCodeMapper {

    private val SUCCESS_PREFIX = "2"
    private val SERVER_ERROR_PREFIX = "3"
    private val NOT_FOUND = "404"

    fun transform(apiResponseCode: Int): RepositoryResponse.ResponseCode {
        var repositoryResponse = RepositoryResponse.ResponseCode.SUCCESS

        with(apiResponseCode.toString()) {
            if(equals(NOT_FOUND)) {
                repositoryResponse = RepositoryResponse.ResponseCode.NO_DATA
            }
            else if(startsWith(SUCCESS_PREFIX)) {
                repositoryResponse = RepositoryResponse.ResponseCode.SUCCESS
            }
            else if(startsWith(SERVER_ERROR_PREFIX)) {
                repositoryResponse = RepositoryResponse.ResponseCode.SERVER_ERROR
            }
            else {
                repositoryResponse = RepositoryResponse.ResponseCode.ERROR
            }
        }
        return repositoryResponse
    }
}