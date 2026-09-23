package com.xg89.ai

import android.content.Context
import android.content.Intent
import android.speech.RecognizerIntent
import java.util.Locale

object XG89VoiceManager {

    fun startListening(context: Context) {

        val intent = Intent(
            RecognizerIntent.ACTION_RECOGNIZE_SPEECH
        ).apply {

            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE,
                Locale("es", "MX")
            )

            putExtra(
                RecognizerIntent.EXTRA_PROMPT,
                "Habla con XG89 AI"
            )
        }

        try {

            context.startActivity(
                intent.addFlags(
                    Intent.FLAG_ACTIVITY_NEW_TASK
                )
            )

        } catch (_: Exception) {
        }
    }
}
