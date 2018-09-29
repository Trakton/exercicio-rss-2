package br.ufpe.cin.if710.rss.util

import android.app.IntentService
import android.content.Intent
import android.util.Log
import br.ufpe.cin.if710.rss.db.SQLiteRSSHelper
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class GetRssFeedService(): IntentService("GetRssFeedService") {
    override fun onHandleIntent(intent: Intent?) {
        var ins: InputStream? = null
        var rssFeed = ""
        val dbHelper = SQLiteRSSHelper.getInstance(this)
        try {
            val url = URL(intent!!.data.toString())
            val conn = url.openConnection() as HttpURLConnection
            ins = conn.inputStream
            val out = ByteArrayOutputStream()
            val buffer = ByteArray(1024)
            var count = ins.read(buffer)
            while (count != -1) {
                out.write(buffer, 0, count)
                count = ins.read(buffer)
            }
            val response = out.toByteArray()
            rssFeed = String(response, charset("UTF-8"))
        } finally {
            ins?.close()
        }
        val items = ParserRSS.parse(rssFeed)
        var addedItems = 0
        items.forEach{
            val id = dbHelper.insertItem(it)
            Log.i("[XABLITO]", id.toString())
            if (id != -1L) addedItems += 1
        }
        sendBroadcast(Intent(COMPLETED_DOWNLOAD))
        Log.i("[XABLITOo]", addedItems.toString())
        if (addedItems > 0)
            sendBroadcast(Intent(NEW_ITEMS_ADDED))
    }

    companion object {
        val COMPLETED_DOWNLOAD = "br.ufpe.cin.if710.rss.download_complete"
        val NEW_ITEMS_ADDED = "br.ufpe.cin.if710.rss.new_items"
    }
}