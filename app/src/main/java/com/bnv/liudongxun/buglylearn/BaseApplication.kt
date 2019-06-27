package com.bnv.liudongxun.buglylearn

import android.app.Application
import android.content.Intent
import android.util.Log
import com.tencent.bugly.Bugly
import com.tencent.bugly.beta.Beta
import com.tencent.bugly.beta.UpgradeInfo
import com.tencent.bugly.beta.download.DownloadListener
import com.tencent.bugly.beta.download.DownloadTask
import com.tencent.bugly.beta.upgrade.UpgradeListener
import com.tencent.bugly.beta.upgrade.UpgradeStateListener
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.startActivity
import java.util.*

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Beta.upgradeListener=upgradeListener  //todo 设置了这个监听SDK就不会处理升级策略了,既不会弹出升级框了
        Beta.upgradeStateListener=upgradeStateListener
        Beta.autoCheckUpgrade=false
     //   Beta.smallIconId=R.drawable.icon_car
        Beta.largeIconId=R.drawable.icon_car
        Bugly.init(getApplicationContext(), "df157270ca", false)//todo Beta设置一定要放到  Bugly.init之前否则无效

      //  Log.d("test","onCreate1")
    }

    /**
     * Bugly全量更新使用注意
     * 1.Beta设置的属性一定要在Bugly.init()之前否则无效
     * 2.Beta.upgradeListener=upgradeListener 如果设置了upgradeListener监听SDK就不会自动升级策略了,既不会弹出升级框了
     */
    /**
     * 常用api
     * 1.设置SDK加载的延迟时间,默认延时3s,如果Bugly SDK加载影响了我们的APP启动速度可以设置延迟
     * Beta.initDelay = 1 * 1000;
     * 2.开始下载状态栏会有提示,有一个图标
     *  Beta.smallIconId = R.drawable.ic_launcher;
     *  Beta.largeIconId = R.drawable.ic_launcher;//设置无效暂时不知道为啥
     * 3.获取升级策略Beta.getUpgradeInfo()
     * ·详细升级策略信息包含哪些具体在下面
     * 4.可以通过startDownload开始下载APK
     * public static DownloadTask startDownload() 开始下载
     * 5. public static void cancelDownload() 取消下载
     */

    /**
     * UpgradeInfo内容字段如下
     *public String id = "";//唯一标识
    public String title = "";//升级提示标题
    public String newFeature = "";//升级特性描述
    public long publishTime = 0;//升级发布时间,ms
    public int publishType = 0;//升级类型 0测试 1正式
    public int upgradeType = 1;//升级策略 1建议 2强制 3手工
    public int popTimes = 0;//提醒次数
    public long popInterval = 0;//提醒间隔
    public int versionCode;
    public String versionName = "";
    public String apkMd5;//包md5值
    public String apkUrl;//APK的CDN外网下载地址
    public long fileSize;//APK文件的大小
    pubilc String imageUrl; // 图片url
     */

    /**
     * SDK向服务器请求是否有新的升级策略
     * 1.SDK默认程序启动自动向服务器请求是否有新的策略
     * ,如果不希望SDK执行升级查询可以设置Beta.autoCheckUpgrade=false 默认为true
     */
    val upgradeListener:UpgradeListener=object :UpgradeListener{
        /**
         *po: 0正常 -1 请求失败
         * p1:更新策略,包含了具体的更新信息
         * p2:true手动请求 false自动请求 如果我们通过SDK自动查询升级策略 那么这里是false,如果我们通过  Beta.checkUpgrade()手动查询升级策略这里返回false
         * p3:true不弹窗 false 弹窗
         * return:true SDK不会弹窗,升级策略交app处理
         */
        override fun onUpgrade(p0: Int, p1: UpgradeInfo?, p2: Boolean, p3: Boolean) {
              Log.d("test","p0="+p0+" p1="+p1+" p2="+p2+" p3="+p3)
            if(p1!=null){//发现新版本
                Log.d("test","find new version ")
                startActivity(Intent(this@BaseApplication, UpgradeActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
            }
        }

    }

    /**
     * 下载回调 ,不能在application注册这个监听,要在Activity里注册 否则无效
     *
    public static void unregisterDownloadListener()记得要接注册     */
    val downListener:DownloadListener=object : DownloadListener{
        /**
         * 下载失败回调
         * p1:错误码
         * p2;错误信息
         */
        override fun onFailed(p0: DownloadTask?, p1: Int, p2: String?) {
            Log.d("test","onFailed  p1="+p1+" p2="+p2)
        }
        /**
         * 可以理解为下载进度,下载过程中会一直回调
         */
        override fun onReceive(p0: DownloadTask?) {
            Log.d("test","onReceive ")
        }

        override fun onCompleted(p0: DownloadTask?) {
            Log.d("test","onCompleted ")
        }

    }

    /**
     * 查询升级策略时候的一些回调
     */
    val upgradeStateListener=object :UpgradeStateListener{
        /**
         * 更新中
         */
        override fun onUpgrading(p0: Boolean) {
            Log.d("test","onUpgrading p0="+p0)
        }
        /**
         * 更新成功(不是指下载apk成功,是指获取后台升级策略成功)
         */
        override fun onUpgradeSuccess(p0: Boolean) {
            Log.d("test","onUpgradeSuccess p0="+p0)
        }
        /**
         * 下载完成(下载apk)
         */
        override fun onDownloadCompleted(p0: Boolean) {
            Log.d("test","onDownloadCompleted p0="+p0)
        }
        /**
         * 没有要更新版本,可以理解成当前已经是最新版本
         */
        override fun onUpgradeNoVersion(p0: Boolean) {
            Log.d("test","onUpgradeNoVersion p0="+p0)
        }

        /**
         * 更新失败,这里不是指下载apk失败 是指获取后台升级策略失败
         */
        override fun onUpgradeFailed(p0: Boolean) {
            Log.d("test","onUpgradeFailed p0="+p0)
        }

    }
}