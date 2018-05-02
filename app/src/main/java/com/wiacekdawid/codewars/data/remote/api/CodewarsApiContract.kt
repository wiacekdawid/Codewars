package com.wiacekdawid.codewars.data.remote.api

/**
 * Created by dawidwiacek on 29/04/2018.
 */

object CodewarsApiContract{
    const val USERNAME_PARAM = "username"
    const val PAGE_PARAM = "page"
    const val USERS_ENDPOINT = "users/{$USERNAME_PARAM}"
    const val COMPLETED_CHALLENGES_ENDPOINT = "users/{$USERNAME_PARAM}/code-challenges/completed"
    const val AUTHORED_CHALLENGES_ENDPOINT = "users/{$USERNAME_PARAM}/code-challenges/authored"
}