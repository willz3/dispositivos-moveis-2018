package br.grupointegrado.tads.buscadorgithub

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.net.URL

class MainActivity : AppCompatActivity() {

    inner class GithubBuscaTask : AsyncTask<URL, Void, String>{

        constructor(){
        }

        override fun onPreExecute() {
            exibirProgressBar();
        }

        override fun doInBackground(vararg p0: URL?): String? {
            try{
                val url = p0.first();
                val resultado = NetworkUtils.obterRespostaDaUrlHttp(url!!);
                return resultado;
            }catch (ex : Exception){
                ex.printStackTrace();
            }
            return null;
        }

        override fun onPostExecute(resultado: String?) {
            if (resultado != null && !resultado.isEmpty()){
                tv_github_resultado.text = resultado;
                exibirResultado();
            }else{
                exibirMensagemErro();
            }

        }

    }

    fun exibirResultado(){
        tv_mensagem_erro.visibility = View.INVISIBLE;
        pb_aguarde.visibility = View.INVISIBLE;
        tv_github_resultado.visibility = View.VISIBLE;
    }

    fun pegarjson(){
        var dadosjson = """{
                  "temperatura":{
                      "temp_min": 11.34,
                      "temp_max": 19.01
                   },
                   "clima":{
                    "id": 801,
                    "condicao": "Nuvens",
                    "descricao": "poucas nuvens"
                   },
                   "pressao": 1023.51,
                   "umidade": 87
              }"""
        val objetoPrevisao = JSONObject(dadosjson);
        val clima = objetoPrevisao.getJSONObject("clima");
        val condicao = clima.getString("condicao");
        Log.d("Resultado","Condição: $condicao");

    }

    fun exibirMensagemErro(){

        tv_mensagem_erro.visibility = View.VISIBLE;
        pb_aguarde.visibility = View.INVISIBLE;
        tv_github_resultado.visibility = View.INVISIBLE;
    }

    fun exibirProgressBar(){
        tv_mensagem_erro.visibility = View.INVISIBLE;
        pb_aguarde.visibility = View.VISIBLE;
        tv_github_resultado.visibility = View.INVISIBLE;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pegarjson();

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu);
        return true;
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.action_buscar){
            buscarNoGitHub();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    fun buscarNoGitHub(){

        var texto: String = et_busca.text.toString();
        var url: URL? = NetworkUtils.construirUrl(texto);
        tv_url.text = url.toString();
        var gitHubBuscarTask = GithubBuscaTask();
        gitHubBuscarTask.execute(url);
    }
}
