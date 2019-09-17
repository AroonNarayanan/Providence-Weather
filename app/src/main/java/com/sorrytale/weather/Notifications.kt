package com.sorrytale.weather

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Looper
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.RemoteInput

class Notifications {
    companion object {
        fun createNotificationChannel(c: Context) {
            val name = "All Notifications"
            val descriptionText = "All Notifications"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("channel_all", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                c.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        fun sendTextNotificationWithDelay(
            context: Context,
            sender: String,
            message: String,
            notifcationId: Int,
            delay: Long,
            intentDestination: Class<*>,
            replyIcon: Int,
            messageIcon: Int
        ) {
            android.os.Handler(Looper.getMainLooper()).postDelayed({
                sendTextNotification(
                    context,
                    sender,
                    message,
                    notifcationId,
                    intentDestination,
                    replyIcon,
                    messageIcon
                )
            }, delay)
        }

        fun sendStaticNotificationWithDelay(
            context: Context,
            sender: String,
            message: String,
            notifcationId: Int,
            delay: Long,
            messageIcon: Int
        ) {
            android.os.Handler(Looper.getMainLooper()).postDelayed({
                sendStaticNotification(
                    context,
                    sender,
                    message,
                    notifcationId,
                    messageIcon
                )
            }, delay)
        }


        fun sendTextNotification(
            context: Context,
            sender: String,
            message: String,
            notificationId: Int,
            intentDestination: Class<*>,
            replyIcon: Int,
            messageIcon: Int
        ) {
            val remoteInput = RemoteInput.Builder("key_text_reply").run {
                setLabel("Reply")
                build()
            }
            val replyIntent =
                PendingIntent.getService(
                    context,
                    0,
                    Intent(context, intentDestination).apply {
                        putExtra("origin", "reply")
                        putExtra("notificationId", notificationId)
                    },
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
            val replyAction =
                NotificationCompat.Action.Builder(replyIcon, "Reply", replyIntent)
                    .addRemoteInput(remoteInput).build()
            val builder = NotificationCompat.Builder(context, "channel_all")
                .setSmallIcon(messageIcon)
                .setContentTitle(sender)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .addAction(replyAction)
                .setAutoCancel(true)

            with(NotificationManagerCompat.from(context)) {
                // notificationId is a unique int for each notification that you must define
                notify(notificationId, builder.build())
            }
        }

        fun sendStaticNotification(
            context: Context,
            sender: String,
            message: String,
            notificationId: Int,
            messageIcon: Int
        ) {
            with(NotificationManagerCompat.from(context)) {
                notify(
                    notificationId,
                    buildStaticNotification(context, sender, message, messageIcon)
                )
            }
        }

        fun buildStaticNotification(
            context: Context,
            sender: String,
            message: String,
            messageIcon: Int
        ): Notification {
            val builder = NotificationCompat.Builder(context, "channel_all")
                .setSmallIcon(messageIcon)
                .setContentTitle(sender)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setAutoCancel(true)
            return builder.build()
        }
    }
}