package com.wiacekdawid.codewars.data

import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.wiacekdawid.codewars.data.local.LocalDataSource
import com.wiacekdawid.codewars.data.local.Member
import com.wiacekdawid.codewars.data.remote.RemoteDataSource
import com.wiacekdawid.codewars.data.repository.CodewarsRepository
import com.wiacekdawid.codewars.data.repository.RepositoryResponse
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

/**
 * Created by dawidwiacek on 14/05/2018.
 */

class CodewarsRepositoryTest {

    @Mock
    private lateinit var localDataSource: LocalDataSource
    @Mock
    private lateinit var remoteDataSource: RemoteDataSource
    @Mock
    private lateinit var connectivityManager: ConnectivityManager
    @Mock
    private lateinit var memberList: HashMap<String, Member>
    @Mock
    private lateinit var network: NetworkInfo

    val SEARCHED_MEMBER_USER_NAME = "user_name"

    private lateinit var codewarsRepository: CodewarsRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        codewarsRepository = CodewarsRepository(remoteDataSource = remoteDataSource,
                localDataSource = localDataSource, connectivityManager = connectivityManager,
                memberCache = memberList)
    }

    @Test
    fun memberIsReturnedFromCache() {
        //given
        val member = Member(userName = SEARCHED_MEMBER_USER_NAME, name = "test", rank = 1, bestLanguage = "test", lastSearchTime = 1)
        Mockito.`when`(memberList[SEARCHED_MEMBER_USER_NAME]).thenReturn(member)

        //when / expected
        Mockito.`when`(codewarsRepository.getMember(SEARCHED_MEMBER_USER_NAME)).thenReturn(Single.just(RepositoryResponse(data = member)))
    }

    @Test
    fun memberIsReturnedFromLocalDataSourceOrApi() {
        //given
        Mockito.`when`(memberList[SEARCHED_MEMBER_USER_NAME]).thenReturn(null)
        Mockito.`when`(connectivityManager.activeNetworkInfo).thenReturn(network)
        Mockito.`when`(network.isConnectedOrConnecting).thenReturn(true)
        Mockito.`when`(localDataSource.getMember(SEARCHED_MEMBER_USER_NAME)).thenReturn(Single.just(RepositoryResponse(code = RepositoryResponse.ResponseCode.SUCCESS)))

        //when
        codewarsRepository.getMember(SEARCHED_MEMBER_USER_NAME).subscribe({}, {})

        //expected
        Mockito.verify(localDataSource, Mockito.atLeastOnce()).getMember(SEARCHED_MEMBER_USER_NAME)
        Mockito.verify(remoteDataSource, Mockito.atLeastOnce()).getMember(SEARCHED_MEMBER_USER_NAME)
    }

    @Test
    fun memberIsReturnedFromLocalDataSource() {
        //given
        Mockito.`when`(memberList[SEARCHED_MEMBER_USER_NAME]).thenReturn(null)
        Mockito.`when`(connectivityManager.activeNetworkInfo).thenReturn(network)
        Mockito.`when`(network.isConnectedOrConnecting).thenReturn(false)
        Mockito.`when`(localDataSource.getMember(SEARCHED_MEMBER_USER_NAME)).thenReturn(Single.just(RepositoryResponse(code = RepositoryResponse.ResponseCode.ERROR)))

        //when
        codewarsRepository.getMember(SEARCHED_MEMBER_USER_NAME).subscribe({}, {})

        //expected
        Mockito.verify(localDataSource, Mockito.atLeastOnce()).getMember(SEARCHED_MEMBER_USER_NAME)
        Mockito.verify(remoteDataSource, Mockito.never()).getMember(SEARCHED_MEMBER_USER_NAME)
    }
}