package br.grupointegrado.tads.goalsapp

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

class myAdapter : RecyclerView.Adapter<myAdapter.ViewHolder> {

    companion object {
        val EXTRA_MENSAGEM = "br.grupointegrado.tads.goalapp.GOAL"
    }
    val formater = SimpleDateFormat("dd/MM/yyyy")

    val context: Context
    var listGoals: ArrayList<ModGoal>
    constructor(context: Context, listGoals : ArrayList<ModGoal>){
        this.context = context
        this.listGoals = listGoals
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(this.context).inflate(R.layout.item_rv_layout, parent, false)
        val viewHolder = ViewHolder(itemView)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val goal = this.listGoals[position]
        val dtGoal = Calendar.getInstance()
        dtGoal.time = goal.deadline
        val timeNow = Calendar.getInstance()
        timeNow.time = Date()
        val days = dtGoal.get(Calendar.DAY_OF_YEAR) - timeNow.get(Calendar.DAY_OF_YEAR)
        val years = dtGoal.get(Calendar.YEAR) - timeNow.get(Calendar.YEAR)


        holder!!.tv_title!!.text = goal.title

        if ((goal.completed) && (goal.dtConclusion != null)){
            holder.tv_date!!.text = "Meta completada em: " + formater.format(goal.dtConclusion).toString()
        }else if(years < 0 || days < 0){
            val aux = (365*years - days*-1) * - 1
            holder.tv_date!!.text = "Meta excedida em: $aux dia(s)!"
        }else if(years > 0){
            val aux = (365*years - days)
            holder.tv_date!!.text = "$aux Dia(s) restante(s) para concluir a meta!"
        }else if(years == 0 && days == 0){
            holder.tv_date!!.text = "Último dia para concluir a meta!"

        }else{
            holder.tv_date!!.text = "$days Dia(s) para concluir a meta!"
        }

        holder.btn_edit!!.setOnClickListener{
            val intent = Intent(this.context, activity_goal_alter::class.java)
            intent.putExtra(EXTRA_MENSAGEM, position)
            this.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return this.listGoals.size
    }

    inner class ViewHolder : RecyclerView.ViewHolder {

        val btn_edit: ImageButton?
        val tv_title: TextView?
        val tv_date: TextView?


        constructor(itemView: View?) : super(itemView) {
            tv_title = itemView?.findViewById<TextView>(R.id.tv_title)
            tv_date = itemView?.findViewById<TextView>(R.id.tv_date)
            btn_edit = itemView?.findViewById<ImageButton>(R.id.btn_alter)
        }

    }
}