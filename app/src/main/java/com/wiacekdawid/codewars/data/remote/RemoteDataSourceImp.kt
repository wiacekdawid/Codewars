package com.wiacekdawid.codewars.data.remote

import com.wiacekdawid.codewars.data.local.model.AuthoredChallenge
import com.wiacekdawid.codewars.data.local.model.Member
import com.wiacekdawid.codewars.data.remote.api.CodewarsService
import com.wiacekdawid.codewars.data.remote.api.model.CompletedChallengesResponseDto
import com.wiacekdawid.codewars.data.remote.mappers.AuthoredChallengeDtoToAuthoredChallengeMapper
import com.wiacekdawid.codewars.data.remote.mappers.MemberDtoToMemberMapper
import com.wiacekdawid.codewars.data.repository.ApiResponseCodeToRepositoryResponseCodeMapper
import com.wiacekdawid.codewars.data.repository.RepositoryResponse
import io.reactivex.Single

/**
 * Created by dawidwiacek on 16/05/2018.
 */

class RemoteDataSourceImp(private val codewarsService: CodewarsService): RemoteDataSource {

    override fun getMember(searchUsername: String): Single<RepositoryResponse<Member>> {
        return codewarsService.getMember(searchUsername)
                .map {
                    var response = RepositoryResponse<Member>()
                    with(response) {
                        code = ApiResponseCodeToRepositoryResponseCodeMapper.transform(it.code())
                        data = it.body()?.let { return@let MemberDtoToMemberMapper.transform(it) }
                        message = it.message()
                    }
                    response
                }
    }



    override fun getCompletedChallenges(username: String, page: Int): Single<RepositoryResponse<CompletedChallengesResponseDto>> {
        return codewarsService.getCompletedChallenges(username, page)
                .map {
                    var response = RepositoryResponse<CompletedChallengesResponseDto>()
                    with(response) {
                        code = ApiResponseCodeToRepositoryResponseCodeMapper.transform(it.code())
                        data = it.body()
                        message = it.message()
                    }
                    response
                }
    }

    override fun getAuthoredChallenges(username: String): Single<RepositoryResponse<List<AuthoredChallenge>>> {
        return codewarsService.getAuthoredChallenges(username)
                .map {
                    var response = RepositoryResponse<List<AuthoredChallenge>>()
                    with(response) {
                        code = ApiResponseCodeToRepositoryResponseCodeMapper.transform(it.code())
                        var listOfAuthoredChallenge: MutableList<AuthoredChallenge> = mutableListOf()
                        it.body()?.data?.forEach {
                            listOfAuthoredChallenge.add(AuthoredChallengeDtoToAuthoredChallengeMapper.transform(it, username))
                        }
                        data = listOfAuthoredChallenge
                        message = it.message()
                    }
                    response
                }
    }
}