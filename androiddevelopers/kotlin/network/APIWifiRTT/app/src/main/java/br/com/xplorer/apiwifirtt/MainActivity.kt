package br.com.xplorer.apiwifirtt

import android.content.*
import android.content.pm.PackageManager
import android.net.wifi.WifiConfiguration
import android.net.wifi.rtt.WifiRttManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.support.annotation.RequiresApi
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // val rootLayout = findViewById<LinearLayout>(R.id.main_layout)
        locationWithWifiRTT()
        val dataWifi = loadWifiScan()
        Log.i("WIFI_SCAN", dataWifi)
    }

    private fun loadWifiScan() : String {
        var scanData = ""

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val wifiManager = WifiScan.getWifiManager(this)
            val configuredNetworks = wifiManager.configuredNetworks
            val op = {
                networks: WifiConfiguration ->
                    val info = String.format("BSSID: %s\nFQDN: %s\nSSID: %s\nStatus: %s"
                        , networks.BSSID
                        , networks.FQDN
                        , networks.SSID
                        , networks.status
                    )
                info
            }
            //outra forma de escrever
            //val op2 : (networks: WifiConfiguration ) -> Unit = {}

            configuredNetworks.forEach { Log.i("NETWORKS_INFO", op(it) )}

            scanData = String.format("Support P2P: %s\nSupport Device-to-AP RTT: %s\nWifi Enabled: %s"
                , wifiManager.isP2pSupported
                , wifiManager.isDeviceToApRttSupported
                , wifiManager.isWifiEnabled
            )

            val wifiInfo = wifiManager.connectionInfo

            String.format("BSSID: %s\n", wifiInfo.bssid)

        }

        return scanData
    }

    private fun locationWithWifiRTT() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val hasWifiRttSupport = hasSystemFeatureWifiRTT()

            val message = String.format("WifiAware: %s\nWifiRTT: %s\nWifiDirect: %s\nWifiPassPoint: %s"
                , hasSystemFeatureWifiAware()
                , hasWifiRttSupport
                , hasSystemFeatureWifiDirect()
                , hasSystemFeatureWifiPassPoint()
            )

            val alertDialogBuilder = AlertDialog.Builder(this)
            alertDialogBuilder.setTitle("WIFI API android 9")
            alertDialogBuilder.setMessage(message)

            //val op = DialogInterface.OnClickListener { dialog, _ -> dialog.dismiss() }
            alertDialogBuilder.setPositiveButton(R.string.ok) { dialog, _ -> dialog.dismiss()}

            if (!isFinishing) { alertDialogBuilder.create().show() }

            if (hasWifiRttSupport) {
                val wifiRttManager: WifiRttManager? = WifiScan.getWifiRttManager(this)
                //val textViewInfoRtt = findViewById<TextView>(R.id.text_view_wifi_rtt_info)
                registerWifiScanReceiver(wifiRttManager)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun registerWifiScanReceiver(wifiRttManager: WifiRttManager?) {
        val filter = IntentFilter(WifiRttManager.ACTION_WIFI_RTT_STATE_CHANGED)
        val wifiScanReceiver =  object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (wifiRttManager!!.isAvailable) {
                    val extraData =  intent!!.extras
                    extraData!!.keySet().forEach {
                            key ->
                        val value = extraData[key]
                        println(value)
                    }
                }
            }
        }
        registerReceiver(wifiScanReceiver, filter)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun hasSystemFeatureWifiAware () : Boolean = packageManager.hasSystemFeature(PackageManager.FEATURE_WIFI_AWARE)

    @RequiresApi(Build.VERSION_CODES.P)
    fun hasSystemFeatureWifiRTT () : Boolean = packageManager.hasSystemFeature(PackageManager.FEATURE_WIFI_RTT)

    private fun hasSystemFeatureWifiDirect () : Boolean = packageManager.hasSystemFeature(PackageManager.FEATURE_WIFI_DIRECT)

    @RequiresApi(Build.VERSION_CODES.O_MR1)
    fun hasSystemFeatureWifiPassPoint () : Boolean = packageManager.hasSystemFeature(PackageManager.FEATURE_WIFI_PASSPOINT)

    // fun hasSystemFeatureNFC () : Boolean = packageManager.hasSystemFeature(PackageManager.FEATURE_NFC)
}
