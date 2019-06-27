package com.bnv.liudongxun.buglylearn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.tencent.bugly.beta.Beta
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
   //     EventBus.getDefault().register(this)
     //   startActivity<UpgradeActivity>()
        mainactivity_upgradebtn.setOnClickListener {
            Beta.checkUpgrade()
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN, priority = 1, sticky = true)
    fun appupdate(event: Int) {
        Log.d("test","appupdate event="+event)
        if (event == 1) {
            Log.d("test","appupdate ")
            AppUpdateDialogView.showDialog(this)
        }
    }
}
