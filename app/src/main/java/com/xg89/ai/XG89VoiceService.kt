package com.xg89.ai

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.media.session.MediaSession
import android.os.IBinder
import android.view.KeyEvent

class XG89VoiceService : Service() {

    private lateinit var mediaSession: MediaSession

    companion object {
        var onButtonEvent: ((String) -> Unit)? = null

        private const val CHANNEL = "xg89_voice"
        private const val NOTIFICATION_ID = 8901
    }

    override fun onCreate() {
        super.onCreate()

        createChannel()

        startForeground(
            NOTIFICATION_ID,
            notification()
        )

        mediaSession = MediaSession(
            this,
            "XG89_AI"
        )

        mediaSession.setCallback(
            object : MediaSession.Callback() {

                override fun onMediaButtonEvent(
                    intent: Intent
                ): Boolean {

                    val event =
                        intent.getParcelableExtra<KeyEvent>(
                            Intent.EXTRA_KEY_EVENT
                        )

                    if (event?.action == KeyEvent.ACTION_DOWN) {

                        val name =
                            KeyEvent.keyCodeToString(
                                event.keyCode
                            )

                        onButtonEvent?.invoke(name)

                        when (event.keyCode) {

                            KeyEvent.KEYCODE_HEADSETHOOK,
                            KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE,
                            KeyEvent.KEYCODE_MEDIA_PLAY -> {

                                XG89VoiceManager.startListening(
                                    this@XG89VoiceService
                                )

                                return true
                            }
                        }
                    }

                    return super.onMediaButtonEvent(intent)
                }
            }
        )

        mediaSession.isActive = true
    }

    private fun createChannel() {

        val manager =
            getSystemService(
                NotificationManager::class.java
            )

        manager.createNotificationChannel(
            NotificationChannel(
                CHANNEL,
                "XG89 AI",
                NotificationManager.IMPORTANCE_LOW
            )
        )
    }

    private fun notification(): Notification {

        return Notification.Builder(
            this,
            CHANNEL
        )
            .setContentTitle("XG89 AI")
            .setContentText("Botón físico activo")
            .setSmallIcon(
                android.R.drawable.ic_btn_speak_now
            )
            .setOngoing(true)
            .build()
    }

    override fun onDestroy() {

        if (::mediaSession.isInitialized) {
            mediaSession.release()
        }

        super.onDestroy()
    }

    override fun onBind(
        intent: Intent?
    ): IBinder? = null
}
