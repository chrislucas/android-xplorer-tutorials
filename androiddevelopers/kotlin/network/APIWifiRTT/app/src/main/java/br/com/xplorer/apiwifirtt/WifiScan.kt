package br.com.xplorer.apiwifirtt

import android.content.Context
import android.net.wifi.WifiManager
import android.net.wifi.rtt.WifiRttManager
import android.os.Build
import android.support.annotation.RequiresApi

object WifiScan  {

    fun getWifiManager(ctx: Context) : WifiManager = ctx.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

    @RequiresApi(Build.VERSION_CODES.P)
    fun getWifiRttManager(ctx: Context) : WifiRttManager? = ctx.applicationContext.getSystemService(Context.WIFI_RTT_RANGING_SERVICE) as WifiRttManager?
}