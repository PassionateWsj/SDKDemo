package com.jameswong.sdkdemo

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.intfocus.syptemplatev1.TemplateOneLibraryActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun enterTemple(view: View) {
        val intent = Intent(this, TemplateOneLibraryActivity::class.java)
//        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        intent.putExtra(URLs.kBannerName, "模板一报表")
        intent.putExtra(URLs.kObjectId, "2")
        intent.putExtra(URLs.kObjectType, "2")
        intent.putExtra(URLs.kGroupId, "165")
        intent.putExtra(URLs.kLink, "/mobile/v2/group/%@/template/1/report/2")
        intent.putExtra(URLs.kTemplatedId, "1")
        startActivity(intent)
    }
}
