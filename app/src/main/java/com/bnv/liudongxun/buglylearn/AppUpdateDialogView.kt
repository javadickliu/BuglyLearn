package com.bnv.liudongxun.buglylearn

import android.app.Dialog
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.tencent.bugly.beta.Beta
import org.jetbrains.anko.find
import java.math.BigDecimal

object AppUpdateDialogView {
    fun showDialog(context: AppCompatActivity){
        val mdialog = Dialog(context, R.style.dialog)
        mdialog.requestWindowFeature(Window.FEATURE_NO_TITLE)//去掉标题栏
        mdialog.setCancelable(false)
        mdialog.setCanceledOnTouchOutside(false)
        val win = mdialog.window
        win!!.decorView.setPadding(100, 0, 100, 0)
        val lp = win.attributes
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        win.attributes = lp
        val contentview = context.layoutInflater.inflate(R.layout.upgrade_dialog,null)
        /***** 获取升级信息 *****/
        val upgradeInfo = Beta.getUpgradeInfo()
        if(upgradeInfo != null){
            contentview.find<TextView>(R.id.tv_title).text = upgradeInfo.title
            val info = StringBuilder()
            info.append("版本: ").append(upgradeInfo.versionName).append("\n")
            info.append("包大小: ").append(bytes2kb(upgradeInfo.fileSize)).append("\n")
            info.append("发布时间: ").append(TimeUtil.getStringByFormat(upgradeInfo.publishTime,TimeUtil.dateFormatYMDHM))
            contentview.find<TextView>(R.id.tv_beta_upgrade_info).text = info
            contentview.find<TextView>(R.id.tv_beta_upgrade_feature).text = upgradeInfo.newFeature
            val cancelView = contentview.find<TextView>(R.id.tv_beta_cancel_button)
            if(upgradeInfo.upgradeType == 2){
                cancelView.visibility = View.GONE
            }else{
                cancelView.visibility = View.VISIBLE
            }
            val prograssBar_download = contentview.find<ProgressBar>(R.id.prograssBar_download)
            prograssBar_download.max = 100
            cancelView.setOnClickListener {
                Beta.cancelDownload()
                mdialog.dismiss()
            }
            contentview.find<TextView>(R.id.tv_beta_confirm_button).setOnClickListener {
                Beta.startDownload()
                Toast.makeText(context,"下载中，请稍后",Toast.LENGTH_SHORT).show()
                mdialog.dismiss()
//                contentview.find<LinearLayout>(R.id.ll_download_progress).visibility = View.VISIBLE
//                Beta.registerDownloadListener(object :DownloadListener{
//                    override fun onFailed(p0: DownloadTask?, p1: Int, p2: String?) {
//
//                    }
//
//                    override fun onReceive(p0: DownloadTask?) {
//                        val percent = (p0?.savedLength?.div(p0.totalLength))?.toFloat() ?: 0f
//                        val progress = Math.round(percent * 100)
//                        prograssBar_download.progress = progress
//                        if(progress == 100) {
//                            mdialog.dismiss()
//                        }
//                    }
//
//                    override fun onCompleted(p0: DownloadTask?) {
//
//                    }
//
//                })
            }
        }
        mdialog.setContentView(contentview)
        mdialog.show()
    }
    /**
     * byte(字节)根据长度转成kb(千字节)和mb(兆字节)
     *
     * @param bytes
     * @return
     */
    fun bytes2kb(bytes: Long): String {
        val filesize = BigDecimal(bytes)
        val megabyte = BigDecimal(1024 * 1024)
        var returnValue = filesize.divide(megabyte, 2, BigDecimal.ROUND_UP).toFloat()
        if (returnValue > 1)
            return "${returnValue}MB"
        val kilobyte = BigDecimal(1024)
        returnValue = filesize.divide(kilobyte, 2, BigDecimal.ROUND_UP).toFloat()
        return "${returnValue}KB"
    }

}