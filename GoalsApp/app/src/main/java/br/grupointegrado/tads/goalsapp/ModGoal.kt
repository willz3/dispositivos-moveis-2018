package br.grupointegrado.tads.goalsapp

import java.text.SimpleDateFormat
import java.util.*

class ModGoal{

    constructor(title: String, desc: String, deadline: Date, completed: Boolean){
        this.title = title
        this.desc = desc
        this.deadline = deadline
        this.dtConclusion = null
        this.completed = completed
    }

    constructor(title: String, desc: String, deadline: Date, dtConclusion: Date?, completed: Boolean){
        this.title = title
        this.desc = desc
        this.deadline = deadline
        this.dtConclusion = dtConclusion
        this.completed = completed
    }

    var title: String
    var desc: String
    var deadline: Date
    var dtConclusion: Date?
    var completed: Boolean
    override fun toString(): String {
        var sb = StringBuilder()
        var days = ((deadline.time - Date().time)/(1000*60*60*24))
        val formater = SimpleDateFormat("dd/MM/yyyy")

        sb.append("Título: $title").append("\n")

        if ((completed) && dtConclusion != null){
            sb.append("Meta concluida em: " + formater.format(dtConclusion))
        }
        else if (days < 0){
            sb.append("Tempo limite excedido em: " + days*-1 + "dia(s)!")
        }else{
            sb.append("$days Dia(s) restante(s)!")
        }

        return sb.toString()
    }


}