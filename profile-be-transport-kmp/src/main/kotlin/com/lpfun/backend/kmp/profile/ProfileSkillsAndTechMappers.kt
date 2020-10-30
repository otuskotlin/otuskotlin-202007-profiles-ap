package com.lpfun.backend.kmp.profile

import com.lpfun.backend.common.model.profile.base.stub.ProfileStubCreate
import com.lpfun.backend.common.model.profile.base.stub.ProfileStubDelete
import com.lpfun.backend.common.model.profile.base.stub.ProfileStubGet
import com.lpfun.backend.common.model.profile.base.stub.ProfileStubUpdate
import com.lpfun.backend.common.model.profile.skills.*
import com.lpfun.transport.multiplatform.profile.skills.*
import com.lpfun.transport.multiplatform.profile.skills.model.KmpDataBaseModel
import com.lpfun.transport.multiplatform.profile.skills.model.KmpOperatingSystemModel
import com.lpfun.transport.multiplatform.profile.skills.model.KmpProfileSkillsAndTech
import com.lpfun.transport.multiplatform.profile.skills.model.KmpSpecializationModel

fun ProfileSkillsContext.setQuery(get: KmpProfileSkillsAndTechGet) = this.apply {
    requestProfileId = get.profileId ?: ""
    stubCaseGet = when (get.debug?.stub) {
        KmpProfileSkillsAndTechGet.StubCase.RUNNING -> ProfileStubGet.SUCCESS
        else -> ProfileStubGet.NONE
    }
}

fun ProfileSkillsContext.setQuery(save: KmpProfileSkillsAndTechSave) = this.apply {
    requestProfile = save.toModel()
    when (save) {
        is KmpProfileSkillsAndTechCreate -> stubCaseCreate = when (save.debug?.stub) {
            KmpProfileSkillsAndTechCreate.StubCase.RUNNING -> ProfileStubCreate.SUCCESS
            else -> ProfileStubCreate.NONE
        }
        is KmpProfileSkillsAndTechUpdate -> stubCaseUpdate = when (save.debug?.stub) {
            KmpProfileSkillsAndTechUpdate.StubCase.RUNNING -> ProfileStubUpdate.SUCCESS
            else -> ProfileStubUpdate.NONE
        }
    }
}

fun ProfileSkillsContext.setQuery(delete: KmpProfileSkillsAndTechDelete) = this.apply {
    requestProfileId = delete.profileId ?: ""
    stubCaseDelete = when (delete.debug?.stub) {
        KmpProfileSkillsAndTechDelete.StubCase.RUNNING -> ProfileStubDelete.SUCCESS
        else -> ProfileStubDelete.NONE
    }
}

fun ProfileSkillsContext.resultItem() = KmpProfileSkillsAndTechResponse(
    data = responseProfile.toKmp(),
    status = kmpStatus(),
    errors = errors.map { it.toKmp() }
)

fun ProfileSkillsAndTech.toKmp() = KmpProfileSkillsAndTech(
    profileId = profileId,
    specialization = specialization.toKmp(),
    operatingSystems = operatingSystems.toKmp(),
    dataBases = dataBases.toKmp()
)

@JvmName("toKmpDB")
private fun MutableSet<DataBaseModel>.toKmp(): MutableSet<KmpDataBaseModel>? {
    val set = mutableSetOf<KmpDataBaseModel>()
    forEach {
        set.add(
            KmpDataBaseModel(
                id = it.id,
                dataBase = it.dataBase
            )
        )
    }
    return set
}

@JvmName("toKmpOS")
private fun MutableSet<OperatingSystemModel>.toKmp(): MutableSet<KmpOperatingSystemModel>? {
    val set = mutableSetOf<KmpOperatingSystemModel>()
    forEach {
        set.add(
            KmpOperatingSystemModel(
                id = it.id,
                operatingSystem = it.operatingSystem
            )
        )
    }
    return set
}

private fun SpecializationModel.toKmp() = KmpSpecializationModel(
    category = category,
    subCategory = subCategory
)

private fun KmpProfileSkillsAndTechSave.toModel() = ProfileSkillsAndTech(
    profileId = if (this is KmpProfileSkillsAndTechUpdate) id ?: "" else "",
    specialization = specialization.toModel(),
    operatingSystems = operatingSystems.toModel(),
    dataBases = dataBases.toModel()
)

@JvmName("toModelDB")
private fun MutableSet<KmpDataBaseModel>?.toModel(): MutableSet<DataBaseModel> {
    val set = mutableSetOf<DataBaseModel>()
    this?.forEach {
        set.add(
            DataBaseModel(
                id = it.id ?: "",
                dataBase = it.dataBase ?: ""
            )
        )
    }
    return set
}

@JvmName("toModelOS")
private fun MutableSet<KmpOperatingSystemModel>?.toModel(): MutableSet<OperatingSystemModel> {
    val set = mutableSetOf<OperatingSystemModel>()
    this?.forEach {
        set.add(
            OperatingSystemModel(
                id = it.id ?: "",
                operatingSystem = it.operatingSystem ?: ""
            )
        )
    }
    return set
}

private fun MutableSet<String>?.toModel() = this ?: mutableSetOf()

private fun KmpSpecializationModel?.toModel() = SpecializationModel(
    category = this?.category.toModelString(),
    subCategory = this?.subCategory.toModelString()
)