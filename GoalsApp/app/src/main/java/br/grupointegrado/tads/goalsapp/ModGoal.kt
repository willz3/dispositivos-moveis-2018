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


}