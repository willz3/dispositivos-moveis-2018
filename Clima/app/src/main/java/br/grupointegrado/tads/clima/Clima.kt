package br.grupointegrado.tads.clima


import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import br.grupointegrado.tads.buscadorgithub.NetworkUtils
import br.grupointegrado.tads.clima.dados.ClimaPreferencias
import br.grupointegrado.tads.clima.util.JsonUtils
import kotlinx.android.synthetic.main.activity_clima.*
import org.json.JSONObject



class Clima : AppCompatActivity() {

    inner class buscarClimaTask : AsyncTask<String, Void, String>{
        constructor(){}

        override fun onPreExecute() {
            exibirProgressBar()
        }

        override fun doInBackground(vararg p0: String): String? {
            try{
                val local = p0.first()
                val url = NetworkUtils.construirUrl(local)

                if (url != null){
                    val resultado = NetworkUtils.obterRespostaDaUrlHttp(url)
                    return resultado
                }
            }catch (e: Exception){
                e.printStackTrace()
            }

            return null
        }

        override fun onPostExecute(result: String?) {
            if (result != null){
                tv_dados_clima.text = ""
                val aDados:Array<String>? = JsonUtils.getSimplesStringsDeClimaDoJson(this@Clima, result)

                if (aDados != null){
                    for (i in aDados){
                        tv_dados_clima.append("$i \n\n")
                    }
                }

                exibirResultado()
            }else{
                exibirMensagemErro()
            }
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.clima, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.acao_atualizar){
            tv_dados_clima.text = ""
            carregarDadosDoClima()
            return true
        }
        return false
    }

    fun exibirResultado(){
        tv_dados_clima.visibility = View.VISIBLE
        tv_mensagem_erro.visibility = View.INVISIBLE
        pb_aguarde.visibility = View.INVISIBLE
    }

    fun exibirMensagemErro(){
        tv_mensagem_erro.visibility = View.VISIBLE
        tv_dados_clima.visibility = View.INVISIBLE
        pb_aguarde.visibility = View.INVISIBLE
    }

    fun carregarDadosDoClima(){
        val localizacao = ClimaPreferencias.getLocalizacaoSalva(this)
        buscarClimaTask().execute(localizacao)
    }

    fun exibirProgressBar(){
        pb_aguarde.visibility = View.VISIBLE
        tv_dados_clima.visibility = View.INVISIBLE
        tv_mensagem_erro.visibility = View.INVISIBLE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clima)

        carregarDadosDoClima()

    }
}
