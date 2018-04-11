package br.grupointegrado.tads.telaverde2

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class ListaVerdeAdapter : RecyclerView.Adapter<ListaVerdeAdapter.NumeroViewHolder> {

    val context: Context
    val lista: List<Int>
    val itemClickListener: ItemClickListener

    var count = 0

    constructor(context: Context, list: List<Int>, itemClickListener: ItemClickListener){
        this.context = context;
        this.lista = list
        this.itemClickListener = itemClickListener
    }


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): NumeroViewHolder {
        val numeroListItem = LayoutInflater.from(this.context).inflate(R.layout.numero_list_item, parent, false)
        val numeroViewHolder = NumeroViewHolder(numeroListItem)
        val tvViewHolder = numeroListItem.findViewById<TextView>(R.id.tv_viewholder_index)
        tvViewHolder.text = "ViewHolder $count"
        val cor = ColorUtils.getViewHolderBackgroundColor(this.context, count)
        numeroListItem.setBackgroundColor(cor)

        count++

        return numeroViewHolder
    }

    override fun onBindViewHolder(holder: NumeroViewHolder?, position: Int) {
        val numero = this.lista.get(position)

        holder?.tvItemNumero?.text = numero.toString()
//        val cor = ColorUtils.getViewHolderBackgroundColor(context, position)
//        holder?.tvItemNumero?.setBackgroundColor(cor)

    }

    override fun getItemCount(): Int {
        return this.lista.size
    }


    inner class NumeroViewHolder : RecyclerView.ViewHolder, View.OnClickListener, View.OnLongClickListener {

        val tvItemNumero: TextView?
        val tvViewHolderIndex: TextView?

        constructor(itemView: View?) : super(itemView) {
            tvItemNumero = itemView?.findViewById<TextView>(R.id.tv_item_numero)
            tvViewHolderIndex = itemView?.findViewById<TextView>(R.id.tv_viewholder_index)

            itemView?.setOnClickListener(this)
            itemView?.setOnLongClickListener(this)
        }

        override fun onClick(p0: View?) {
            val posicaoCliclada = adapterPosition
            itemClickListener.onItemClick(posicaoCliclada)
        }

        override fun onLongClick(p0: View?): Boolean {
            val posicaoClicada = adapterPosition
            itemClickListener.onLongItemClick(posicaoClicada)
            return true
        }

    }

     interface ItemClickListener{
        fun onItemClick(position: Int)
         fun onLongItemClick(position: Int)
    }

}