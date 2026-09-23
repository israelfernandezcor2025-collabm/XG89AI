package com.xg89.ai

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : ComponentActivity() {

    private lateinit var status: TextView
    private lateinit var lastEvent: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        status = findViewById(R.id.status)
        lastEvent = findViewById(R.id.lastEvent)

        requestPermissionsIfNeeded()

        findViewById<Button>(R.id.listenButton).setOnClickListener {
            XG89VoiceManager.startListening(this)
        }

        findViewById<Button>(R.id.startServiceButton).setOnClickListener {
            ContextCompat.startForegroundService(
                this,
                Intent(this, XG89VoiceService::class.java)
            )
            status.text = "Estado: puente físico activo"
        }

        XG89VoiceService.onButtonEvent = { event ->
            runOnUiThread {
                lastEvent.text = "Último botón: $event"
                status.text = "BOTÓN FÍSICO RECIBIDO"
            }
        }
    }

    private fun requestPermissionsIfNeeded() {
        val permissions = mutableListOf(
            Manifest.permission.RECORD_AUDIO
        )

        if (Build.VERSION.SDK_INT >= 31) {
            permissions += Manifest.permission.BLUETOOTH_CONNECT
            permissions += Manifest.permission.BLUETOOTH_SCAN
        }

        if (Build.VERSION.SDK_INT >= 33) {
            permissions += Manifest.permission.POST_NOTIFICATIONS
        }

        val missing = permissions.filter {
            ContextCompat.checkSelfPermission(
                this,
                it
            ) != PackageManager.PERMISSION_GRANTED
        }

        if (missing.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                missing.toTypedArray(),
                100
            )
        }
    }
}
