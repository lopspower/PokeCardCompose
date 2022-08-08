package com.mikhaellopez.domain.model

enum class TestModeEnum {
    UNIT_TEST,
    NONE;

    companion object {
        fun TestModeEnum.isTest(): Boolean = this == UNIT_TEST
    }
}