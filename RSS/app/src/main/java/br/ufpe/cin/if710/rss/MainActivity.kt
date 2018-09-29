package br.ufpe.cin.if710.rss

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.io.IOException

class MainActivity : Activity() {
    private lateinit var RSS_FEED : String

    val layoutManager = LinearLayoutManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        RSS_FEED = getString(R.string.rssfeed)
    }

    // estamos triggando o getRssFeed e definindo o callback a ser executado
    override fun onStart() {
        super.onStart()
        try {
            GetRssFeedAsynTask { itemsRSS: List<ItemRSS> ->
                val recyclerAdapter = ItemRSSListAdapter(itemsRSS)
                conteudoRSS.apply {
                    setHasFixedSize(true)
                    layoutManager = this@MainActivity.layoutManager
                    adapter = recyclerAdapter
                }
            }.execute(RSS_FEED)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    // adapter para executar o mapeamento do formar saído do fetch para a tela
    inner class ItemRSSListAdapter(private val itemsRSS: List<ItemRSS>): RecyclerView.Adapter<ItemListViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemListViewHolder {
            val view = layoutInflater.inflate(R.layout.itemlista, parent, false)
            return ItemListViewHolder(view)
        }

        override fun getItemCount(): Int {
            return itemsRSS.size
        }

        override fun onBindViewHolder(holder: ItemListViewHolder, i: Int) {
            val item = itemsRSS[i]
            holder.itemData.text = item.pubDate
            holder.itemTitulo.text = item.title
            holder.itemTitulo.onClick {
                val uri = Uri.parse(itemsRSS[i].link)
                startActivity(Intent(Intent.ACTION_VIEW, uri))
            }
        }

    }
}
