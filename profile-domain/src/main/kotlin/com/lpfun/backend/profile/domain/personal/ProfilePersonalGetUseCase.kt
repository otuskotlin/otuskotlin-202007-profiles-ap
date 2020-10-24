package com.lpfun.backend.profile.domain.personal

import com.lpfun.backend.common.model.dsl.personal.profilePersonalData
import com.lpfun.backend.common.model.profile.base.ProfileContextStatus
import com.lpfun.backend.common.model.profile.base.stub.ProfileStubGet
import com.lpfun.backend.common.model.profile.personal.ProfilePersonalContext
import com.lpfun.backend.profile.domain.cor.IExec
import com.lpfun.backend.profile.domain.cor.cor

class ProfilePersonalGetUseCase : IExec<ProfilePersonalContext> {
    override suspend fun execute(ctx: ProfilePersonalContext) = chain.execute(ctx.apply {

    })

    companion object {
        val chain = cor<ProfilePersonalContext> {
            execute {
                responseProfileStatus = ProfileContextStatus.RUNNING
            }

            // Обработка стабов
            processor {
                condition { stubCaseGet != ProfileStubGet.NONE }
                handler {
                    condition { stubCaseGet == ProfileStubGet.SUCCESS }
                    exec {
                        responseProfile = profilePersonalData {
                            id = requestProfile.profileId
                            name {
                                first = "John"
                                second = "Junior"
                                last = "Smith"
                                display = "John Smith"
                            }
                            contacts {
                                phone = "+1234"
                                email = "mail@mail.com"
                            }
                            location {
                                country = "Test Country"
                                city = "Test City"
                            }
                        }
                        responseProfileStatus = ProfileContextStatus.FINISHING
                    }
                }
            }

            // Валидация

            // Обращение к БД и обработка

            // Подготовка ответа
            execute {
                responseProfileStatus = ProfileContextStatus.SUCCESS
            }

        }
    }
}