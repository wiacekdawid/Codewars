package com.wiacekdawid.codewars.data.remote.api

/**
 * Created by dawidwiacek on 29/04/2018.
 */

object CodewarsApiContract{
    private const val API_VERSION = "api/v1/"
    const val USERNAME_PARAM = "userName"
    const val PAGE_PARAM = "page"
    const val USERS_ENDPOINT = API_VERSION + "users/{$USERNAME_PARAM}"
    const val COMPLETED_CHALLENGES_ENDPOINT = API_VERSION + "users/{$USERNAME_PARAM}/code-challenges/completed"
    const val AUTHORED_CHALLENGES_ENDPOINT = API_VERSION + "users/{$USERNAME_PARAM}/code-challenges/authored"
}