package br.ufpe.cin.if710.rss.domain

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import br.ufpe.cin.if710.rss.R

// mantém o estado dos itens
class ItemListViewHolder(itemList: View): RecyclerView.ViewHolder(itemList) {
    var itemTitulo = itemList.findViewById<TextView>(R.id.item_titulo)
    var itemData = itemList.findViewById<TextView>(R.id.item_data)
}