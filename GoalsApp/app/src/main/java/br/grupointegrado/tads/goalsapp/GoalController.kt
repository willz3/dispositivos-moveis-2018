package br.grupointegrado.tads.goalsapp

import android.content.Context
import android.widget.Toast
import org.json.JSONArray
import org.json.JSONObject
import java.io.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class GoalController{

    companion object {
        val OWN_FILE_NAME = "dados"
        val OWN_TITLE = "title"
        val OWN_DESC = "description"
        val OWN_DEADLINE = "deadline"
        val OWN_DT_FINAL = "dtConclusion"
        val OWN_COMPLETED = "completed"

    }


    fun fileToJSONArray(context: Context): JSONArray?{
        try {
            val file: FileInputStream = context.openFileInput(OWN_FILE_NAME)
            var stringBuilder = StringBuilder()
            var line = file.bufferedReader().readLine()

            while (line != null){
                stringBuilder.append(line).append("\n")
                line = file.bufferedReader().readLine()
            }

            if(stringBuilder.length>0){
                return JSONArray(stringBuilder.toString())
            }else{
                return null
            }

        }catch (ex: Exception){
            ex.printStackTrace()
            return null
        }
    }


    fun jsonArrayToGoalsArray(jArray: JSONArray): ArrayList<ModGoal>?{
        try{
            val goalsArray = ArrayList<ModGoal>()
            val formater = SimpleDateFormat("dd/MM/yyyy")
            var deadline: Date? = null
            var dtConclusion: Date? = null

            for (i in 0..(jArray.length() -1)){
                val aux = jArray.getJSONObject(i)
                deadline = formater.parse(aux.getString(OWN_DEADLINE))
                dtConclusion = if (aux.getString(OWN_DT_FINAL).equals("")) null else formater.parse(aux.getString(OWN_DT_FINAL))
                goalsArray.add(ModGoal(aux.getString(OWN_TITLE), aux.getString(OWN_DESC),deadline, dtConclusion, aux.getBoolean(OWN_COMPLETED)))
            }

            return goalsArray
        }catch (ex: Exception){
            ex.printStackTrace()
            return null
        }
    }

    fun goalArrayToJSON(gArray: ArrayList<ModGoal>): JSONArray?{
        val formater = SimpleDateFormat("dd/MM/yyyy")
        var jArray = JSONArray()
        for (i in gArray){
            val goalJson = JSONObject();
            goalJson.put("title", i.title)
            goalJson.put("description", i.desc)
            goalJson.put("deadline", formater.format(i.deadline))
            goalJson.put("dtConclusion", if(i.dtConclusion == null)"" else formater.format(i.dtConclusion))
            goalJson.put("completed", i.completed)
            jArray.put(goalJson)
        }
        return jArray

    }

    fun saveAllGoals(context: Context ,jArray: JSONArray?){
        try{
            val fileStream: FileOutputStream = context.openFileOutput(OWN_FILE_NAME, Context.MODE_PRIVATE);
            if (jArray == null){
                fileStream.write("".toByteArray())
                fileStream.close()
                return
            }
            val stringArray = jArray.toString()
            fileStream.write(stringArray.toByteArray())
            fileStream.close()


        }catch (e: IOException){
            e.printStackTrace()

        }
    }

}