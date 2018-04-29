package br.grupointegrado.tads.clima

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


class PrevisaoAdapter : RecyclerView.Adapter<PrevisaoAdapter.PrevisaoViewHolder> {

    private var listClima: Array<String>?
    private var itemClickListener: PrevisaoItemClickListener
    val context: Context


    constructor(context: Context ,listClima:Array<String>?, itemClickListener: PrevisaoItemClickListener){
        this.listClima = listClima
        this.context = context
        this.itemClickListener = itemClickListener
    }

    override fun getItemCount(): Int {
        val dados = this.listClima
        if (dados != null){
            return dados.size
        }
        return 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrevisaoViewHolder {
        val itemView = LayoutInflater.from(this.context).inflate(R.layout.previsao_lista_item, parent,false)
        return PrevisaoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PrevisaoViewHolder, position: Int) {
        val dados = this.listClima
        if (dados != null){
            val aDados = dados[position]
            holder.tvDadosPrevisao.text = aDados
        }
    }

    fun setDadosClima(dadosClima: Array<String>){
        this.listClima = dadosClima
        notifyDataSetChanged()
    }

    interface PrevisaoItemClickListener{
        fun onItemClick(index: Int)
    }

    fun getDadosClima() : Array<String>?{
        return listClima

    }

    inner class PrevisaoViewHolder : RecyclerView.ViewHolder{

        var tvDadosPrevisao: TextView

        constructor(itemView: View?) : super(itemView){
            tvDadosPrevisao = itemView!!.findViewById(R.id.tv_dados_previsao)

            itemView.setOnClickListener({
                itemClickListener.onItemClick(adapterPosition)
            })
        }
    }
}