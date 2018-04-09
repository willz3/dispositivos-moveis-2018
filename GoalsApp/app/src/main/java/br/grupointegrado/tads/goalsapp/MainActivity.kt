package br.grupointegrado.tads.goalsapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var listGoals : ArrayList<ModGoal>? = null
    val controller = GoalController()


    fun fillListView(){
        val jArray = controller.fileToJSONArray(this)

        if (jArray == null){
            controller.saveAllGoals(this, null)
        }else{
            this.listGoals = controller.jsonArrayToGoalsArray(jArray)
            val adapter = myAdapter(this@MainActivity, this.listGoals!!)
            val layoutManager = LinearLayoutManager(this@MainActivity, LinearLayout.VERTICAL, false)
            rv_goal.adapter = adapter
            rv_goal.layoutManager = layoutManager
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
