package com.bnv.liudongxun.buglylearn

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.tencent.bugly.beta.Beta
import com.tencent.bugly.beta.download.DownloadListener
import com.tencent.bugly.beta.download.DownloadTask

class UpgradeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //  Log.d("test","onCreate  UpgradeActivity")
        setContentView(R.layout.activity_upgrade)
        AppUpdateDialogView.showDialog(this)
        Beta.registerDownloadListener(downListener)
    }

    /**
     * 下载回调         Beta.registerDownloadListener(downListener)
     *     public static void unregisterDownloadListener()记得要接注册
     * 不能在application注册这个监听,要在Activity里注册 否则无效
     */
    val downListener: DownloadListener =object : DownloadListener {
        override fun onFailed(p0: DownloadTask?, p1: Int, p2: String?) {
            Log.d("test","onFailed ")
        }

        override fun onReceive(p0: DownloadTask?) {
            Log.d("test","onReceive ")
        }

        override fun onCompleted(p0: DownloadTask?) {
            Log.d("test","onCompleted ")
        }

    }
}