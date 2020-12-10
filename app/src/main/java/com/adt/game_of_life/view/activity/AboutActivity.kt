package com.adt.game_of_life.view.activity

import android.os.Bundle
import com.adt.game_of_life.R
import com.adt.game_of_life.view.activity.contract.BackActivity

class AboutActivity : BackActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        setTitle(R.string.about_activity_title)
    }
}
