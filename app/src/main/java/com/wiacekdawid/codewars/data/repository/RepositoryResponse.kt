package com.wiacekdawid.codewars.data.repository

/**
 * Created by dawidwiacek on 10/05/2018.
 */

class RepositoryResponse<T>(var code: ResponseCode = ResponseCode.SUCCESS,
                            var message: String? = null,
                            var data: T? = null) {

    enum class ResponseCode {
        SUCCESS, NO_DATA, ERROR, SERVER_ERROR
    }
}