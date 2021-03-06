package com.lpfun.backend.common.model.profile

enum class ProfileContextStatus {
    NONE,
    ERROR,
    SUCCESS;

    val isError
        get() = this == ERROR
}