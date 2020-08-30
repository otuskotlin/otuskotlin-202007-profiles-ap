package com.lpfun.backend.kmp.profile

import com.lpfun.backend.common.model.profile.AdditionalEducationModel
import com.lpfun.backend.common.model.profile.EducationModel
import com.lpfun.backend.common.model.profile.ProfileEducation
import com.lpfun.transport.multiplatform.profile.education.KmpProfileEducationSave
import com.lpfun.transport.multiplatform.profile.education.model.KmpAdditionalEducationModel
import com.lpfun.transport.multiplatform.profile.education.model.KmpEducationModel
import org.junit.Test
import kotlin.test.assertEquals

internal class ProfileEducationMappersTest {

    @Test
    fun kmpToModel() {
        val kmpProfileEducation = KmpProfileEducationSave(
                id = "121",
                mainEducation = getTestKmpEducationModel(),
                additionalEducation = getTestKmpAdditionalModel()
        )

        val profileEducationModel = kmpProfileEducation.toModel()

        assertEquals("121", profileEducationModel.id)
        assertEquals(
                getTestEducationModel(),
                profileEducationModel.mainEducation
        )
        assertEquals(
                getTestAdditionalEducationModel(),
                profileEducationModel.additionalEducation)
    }

    @Test
    fun modelToKmp() {
        val profileEducation = ProfileEducation(
                id = "123",
                mainEducation = getTestEducationModel(),
                additionalEducation = getTestAdditionalEducationModel()
        )
        val kmpProfileEducation = profileEducation.toKmp()
        assertEquals("123", kmpProfileEducation.id)
        assertEquals(getTestKmpEducationModel(), kmpProfileEducation.mainEducation)
        assertEquals(getTestKmpAdditionalModel(), kmpProfileEducation.additionalEducation)
    }

    private fun getTestKmpEducationModel() = mutableListOf(
            KmpEducationModel(
                    university = "MGU",
                    department = "IT",
                    specialty = "Programmer",
                    yearOfCompletion = "2020"
            )
    )

    private fun getTestKmpAdditionalModel() = mutableListOf(
            KmpAdditionalEducationModel(
                    nameOfInstitution = "OTUS",
                    courseName = "Kotlin",
                    yearOfCompletion = "2020"
            )
    )

    private fun getTestEducationModel() = mutableListOf(
            EducationModel(
                    university = "MGU",
                    department = "IT",
                    specialty = "Programmer",
                    yearOfCompletion = "2020"
            )
    )

    private fun getTestAdditionalEducationModel() = mutableListOf(
            AdditionalEducationModel(
                    nameOfInstitution = "OTUS",
                    courseName = "Kotlin",
                    yearOfCompletion = "2020"
            )
    )
}