package br.grupointegrado.tads.goalsapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    var listGoals : ArrayList<ModGoal>? = null
    val controller = GoalController()

    companion object {
        val EXTRA_MENSAGEM = "br.grupointegrado.tads.goalapp.GOAL"
    }

    fun fillListView(){
        val jArray = controller.fileToJSONArray(this)

        if (jArray == null){
            controller.saveAllGoals(this, null)
        }else{
            this.listGoals = controller.jsonArrayToGoalsArray(jArray)
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, this.listGoals)
            val listView: ListView = findViewById(R.id.list_goals)
            listView.adapter = adapter

            listView.setOnItemClickListener() {
                parent: AdapterView<*>?, view: View?, position: Int, id: Long ->
                val intent = Intent(this@MainActivity, activity_goal_alter::class.java)
                intent.putExtra(EXTRA_MENSAGEM, position)
                startActivity(intent)
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_goal, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.add_goal){
            val intent = Intent(this, activity_goal_add::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        fillListView()
        super.onResume()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        fillListView()

    }
}
