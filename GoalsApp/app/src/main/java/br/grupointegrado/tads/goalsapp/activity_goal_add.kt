package br.grupointegrado.tads.goalsapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_goal_add.*
import java.text.SimpleDateFormat
import java.util.*

class activity_goal_add : AppCompatActivity() {

    val controller = GoalController()

    fun saveGoals(): Boolean{

        if (validateFields()){
            val formater = SimpleDateFormat("dd/MM/yyyy")
            val datestring = et_deadline_new.text.toString()
            val title:String = et_title_new.text.toString()
            val desc:String = et_desc_new.text.toString()
            var dtConclusion: Date? = null
            var completed = false
            if (check_conclusion_new.isChecked){
                completed = true
                dtConclusion = Date()

            }
            val date = formater.parse(datestring)
            val goal = ModGoal(title, desc, date, dtConclusion, completed)

            var jArray = controller.fileToJSONArray(this)
            var goalsArray = ArrayList<ModGoal>()

            if (jArray != null){
                goalsArray = controller.jsonArrayToGoalsArray(jArray)!!
            }

            goalsArray.add(goal)
            jArray = controller.goalArrayToJSON(goalsArray)
            controller.saveAllGoals(this, jArray)
            return true
        }
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goal_add)

        btn_finish_new.setOnClickListener{
            if(saveGoals()){
                finish()
            }

        }

        DataPickerUtils(this, et_deadline_new).DateDialog()

    }


    fun validateFields(): Boolean{
        if (et_title_new.text.toString().equals("")){
            Toast.makeText(this,"Informe um título para a meta!", Toast.LENGTH_SHORT).show()
            return false
        }

        if (et_desc_new.text.toString().equals("")){
            Toast.makeText(this,"Informe uma descrição para a meta!", Toast.LENGTH_SHORT).show()
            return false
        }

        if (et_deadline_new.text.toString().equals("")){
            Toast.makeText(this, "Informe uma data limite!", Toast.LENGTH_SHORT).show()
            return false
        }


        return true
    }
}
