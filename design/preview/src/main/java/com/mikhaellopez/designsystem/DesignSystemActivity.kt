package com.mikhaellopez.designsystem

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.mikhaellopez.ui.base.getViewGroupParent
import com.mikhaellopez.ui.theme.BaseTheme

class DesignSystemActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BaseTheme {
                DesignSystem(viewGroupParent = getViewGroupParent())
            }
        }
    }
}
