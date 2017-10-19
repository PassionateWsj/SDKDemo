package com.jameswong.sdkdemo

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.intfocus.syptemplatev1.TemplateOneLibraryActivity
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//                                    copyFile(p0!!.absolutePath, Environment.getExternalStorageDirectory().path + File.separator + "123456.jpg")

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

    fun copyFile(oldPath: String, newPath: String) {
        try {
            var bytesum = 0
            var byteread = 0
            val oldfile = File(oldPath)
            if (oldfile.exists()) { //文件存在时
                val inStream = FileInputStream(oldPath) //读入原文件
                val fs = FileOutputStream(newPath)
                val buffer = ByteArray(1444)
                val length: Int
                while (true) {
                    byteread = inStream.read(buffer)
                    if (byteread != -1) {
                    }
                    bytesum += byteread //字节数 文件大小
                    println(bytesum)
                    fs.write(buffer, 0, byteread)
                }
                inStream.close()
            }
        } catch (e: Exception) {
            println("复制单个文件操作出错")
            e.printStackTrace()

        }

    }
}
