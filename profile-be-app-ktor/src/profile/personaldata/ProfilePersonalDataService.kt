package com.lpfun.profile.personaldata

import com.lpfun.backend.common.model.profile.LocationModel
import com.lpfun.backend.common.model.profile.ProfileContext
import com.lpfun.backend.common.model.profile.ProfileContextStatus
import com.lpfun.backend.common.model.profile.ProfilePersonalData
import com.lpfun.backend.kmp.profile.setQuery
import com.lpfun.base.request
import com.lpfun.transport.multiplatform.profile.personal.*
import kotlinx.datetime.LocalDate
import java.util.*

class ProfilePersonalDataService {

    private var profilePersonal = ProfilePersonalData(
        profileId = "12345",
        firstName = "John",
        middleName = "Junior",
        lastName = "Smith",
        displayName = "John Smith",
        phone = "+1234",
        email = "mail@mail.com",
        bday = LocalDate(2000, 1, 1),
        locationModel = LocationModel(
            country = "Test Country",
            city = "Test City"
        )
    )

    fun get(paramsList: List<Pair<String, List<String>>>) = ProfileContext().request<KmpProfilePersonalDataResponse> {
        val id = paramsList.firstOrNull { it.first == "id" }?.second?.get(0) ?: throw IllegalArgumentException()
        setQuery(KmpProfilePersonalDataGet(profileId = id))
            .apply {
                responseProfile =
                    if (id == profilePersonal.profileId) profilePersonal else throw IllegalArgumentException()
                responseProfileStatus = ProfileContextStatus.SUCCESS
            }
    }

    fun create(query: KmpProfilePersonalDataCreate) = ProfileContext().request<KmpProfilePersonalDataResponse> {
        setQuery(query)
            .apply {
                profilePersonal =
                    (requestProfile as ProfilePersonalData).copy(profileId = UUID.randomUUID().toString())
                responseProfile = profilePersonal
                responseProfileStatus = ProfileContextStatus.SUCCESS
            }
    }

    fun update(query: KmpProfilePersonalDataUpdate) = ProfileContext().request<KmpProfilePersonalDataResponse> {
        setQuery(query).apply {
            requestProfileId = profilePersonal.profileId
            responseProfile = requestProfile
            responseProfileStatus = ProfileContextStatus.SUCCESS
        }
    }

    fun delete(query: KmpProfilePersonalDataDelete) = ProfileContext().request<KmpProfilePersonalDataResponse> {
        setQuery(query).apply {
            responseProfile = ProfilePersonalData()
            responseProfileStatus = ProfileContextStatus.SUCCESS
        }
    }
}


