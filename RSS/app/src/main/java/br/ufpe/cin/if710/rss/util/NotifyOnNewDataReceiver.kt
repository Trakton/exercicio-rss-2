package br.ufpe.cin.if710.rss.util

import android.app.ActivityManager
import android.app.ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat
import android.util.Log
import br.ufpe.cin.if710.rss.ui.MainActivity

class NotifyOnNewDataReceiver: BroadcastReceiver() {
    override fun onReceive(c: Context?, i: Intent?) {
        if (!isInForeground(c)) {
            val notificationIntent = Intent(c, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(c, 0, notificationIntent, 0)
            val notification = NotificationCompat.Builder(c!!.applicationContext, NOTIFICATION_CHANNEL_ID)
                    .setSmallIcon(android.R.drawable.stat_sys_warning)
                    .setContentTitle("Feed atualizado")
                    .setContentText("Você tem novos itens no seu feed rss")
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .build()
            val notificationService = c.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationService.notify(NOTIFICATION_ID, notification)
        }
    }

    private fun isInForeground(c: Context?): Boolean {
        val activityManager = c?.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val runningProcesses = activityManager.runningAppProcesses ?: return false
        return runningProcesses.any { it.importance == IMPORTANCE_FOREGROUND && it.processName.equals(c.packageName) }
    }

    companion object {
        val NOTIFICATION_CHANNEL_ID = "br.cin.ufpe.if710.notifications"
        val NOTIFICATION_ID = 714
    }
}