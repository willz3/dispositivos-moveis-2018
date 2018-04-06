package br.grupointegrado.tads.goalsapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_goal_alter.*
import java.text.SimpleDateFormat
import java.util.*

class activity_goal_alter : AppCompatActivity() {


    val controller = GoalController()
    var listGoals: ArrayList<ModGoal>? = null

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.complete_alter_goal, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.complete){
            if(validateFields()){
                saveModificationGoal()
                finish()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    fun saveModificationGoal(){
        val formater = SimpleDateFormat("dd/MM/yyyy")
        val position = this.intent.getIntExtra(MainActivity.EXTRA_MENSAGEM, 0)
        val title: String = et_title_alter.text.toString()
        val desc: String = et_desc_alter.text.toString()
        val deadline: Date = formater.parse(et_deadline_alter.text.toString())
        val completed: Boolean = check_conclusion_alter.isChecked
        var dtConclusion: Date? = null
        if (completed == true){
            dtConclusion = Date()
        }
        val goal = ModGoal(title, desc, deadline, dtConclusion,completed)
        this.listGoals!![position] = goal
        val listFinal = this.listGoals
        val jArray = controller.goalArrayToJSON(listFinal!!)
        controller.saveAllGoals(this, jArray)
    }

    fun fillListGoal(){
        val jArray = controller.fileToJSONArray(this)
        this.listGoals = controller.jsonArrayToGoalsArray(jArray!!)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goal_alter)
        disableFields()
        buttonsDefaultState()
        fillListGoal()
        fillFields()

        btn_alter_alter.setOnClickListener {
            buttonAlterState()
            enableFields()
        }

        btn_cancel_alter.setOnClickListener {
            disableFields()
            fillFields()
            buttonsDefaultState()
        }

        btn_delete_alter.setOnClickListener {
            deleteGoal()
        }

        DataPickerUtils(this, et_deadline_alter).DateDialog()
    }

    fun deleteGoal(){
        val position = this.intent.getIntExtra(MainActivity.EXTRA_MENSAGEM, 0)
        val goal = this.listGoals!![position]
        this.listGoals!!.remove(goal)
        val jArray = controller.goalArrayToJSON(this.listGoals!!)
        controller.saveAllGoals(this, jArray)
        finish()
    }

    fun fillFields(){
        val position = this.intent.getIntExtra(MainActivity.EXTRA_MENSAGEM, 0)
        val formater = SimpleDateFormat("dd/MM/yyyy")
        val list = this.listGoals
        if (list != null){
            var goal = list[position]
            et_title_alter.setText(goal.title)
            et_desc_alter.setText(goal.desc)
            et_deadline_alter.setText(formater.format(goal.deadline))
            check_conclusion_alter.isChecked = goal.completed
        }

    }

    fun validateFields(): Boolean{
        if (et_title_alter.text == null){
            Toast.makeText(this, "Informe um titulo para concluir!", Toast.LENGTH_SHORT).show()
            return false
        }
        if (et_deadline_alter.text == null){
            Toast.makeText(this, "Informe uma data limite para concluir!", Toast.LENGTH_SHORT).show()
            return false
        }

        if (et_desc_alter.text == null ){
            Toast.makeText(this, "Informe uma descrição para concluir!", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }


    fun disableFields(){
        et_title_alter.isEnabled = false
        et_deadline_alter.isEnabled = false
        et_desc_alter.isEnabled = false
        check_conclusion_alter.isEnabled = false
    }

    fun enableFields(){
        et_title_alter.isEnabled = true
        et_deadline_alter.isEnabled = true
        et_desc_alter.isEnabled = true
        check_conclusion_alter.isEnabled = true
    }

    fun buttonsDefaultState(){
        btn_alter_alter.isEnabled = true
        btn_delete_alter.isEnabled = true
        btn_cancel_alter.isEnabled = false
    }

    fun buttonAlterState(){
        btn_alter_alter.isEnabled = false
        btn_delete_alter.isEnabled = false
        btn_cancel_alter.isEnabled = true
    }
}
