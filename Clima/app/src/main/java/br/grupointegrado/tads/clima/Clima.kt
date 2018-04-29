package br.grupointegrado.tads.clima


import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import br.grupointegrado.tads.buscadorgithub.NetworkUtils
import br.grupointegrado.tads.clima.dados.ClimaPreferencias
import br.grupointegrado.tads.clima.util.JsonUtils
import kotlinx.android.synthetic.main.activity_clima.*



class Clima : AppCompatActivity(), PrevisaoAdapter.PrevisaoItemClickListener{

    companion object {
        val EXTRA_MENSAGEM = "br.grupointegrado.tads.clima"
    }


    var previsaoAdapter : PrevisaoAdapter? = null

    override fun onItemClick(index: Int) {
        val dados = previsaoAdapter?.getDadosClima()?.get(index)
        if (dados != null){
            val intent = Intent(this, DetalhesActivity::class.java)
            intent.putExtra(EXTRA_MENSAGEM, dados)
            startActivity(intent)
        }
    }

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
                val aDados :Array<String>? = JsonUtils.getSimplesStringsDeClimaDoJson(this@Clima, result)

                if (aDados != null){
                    previsaoAdapter?.setDadosClima(aDados)
                }

                exibirResultado()
            }else{
                exibirMensagemErro()
            }
        }


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clima)

        this.previsaoAdapter = PrevisaoAdapter(this@Clima, null, this)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        rv_clima.layoutManager = layoutManager
        rv_clima.adapter = previsaoAdapter

        carregarDadosDoClima()

    }




    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.clima, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.acao_atualizar){
            carregarDadosDoClima()
            return true
        }

        if(item?.itemId == R.id.acao_abrir_mapa){
            abrirMapa()
            return true
        }
        return false
    }

    fun abrirMapa(){
        val local = "Campo Mourão, Paraná, Brasil"

        val builder = Uri.Builder().scheme("geo").path("0,0").appendQueryParameter("q", local)
        val uriLocal = builder.build()
        val intentLocal = Intent(Intent.ACTION_VIEW, uriLocal)

        if (intent.resolveActivity(packageManager) != null){
            startActivity(intentLocal)
        }
    }

    fun exibirResultado(){
        rv_clima.visibility = View.VISIBLE
        tv_mensagem_erro.visibility = View.INVISIBLE
        pb_aguarde.visibility = View.INVISIBLE
    }

    fun exibirMensagemErro(){
        tv_mensagem_erro.visibility = View.VISIBLE
        rv_clima.visibility = View.INVISIBLE
        pb_aguarde.visibility = View.INVISIBLE
    }

    fun carregarDadosDoClima(){
        val localizacao = ClimaPreferencias.getLocalizacaoSalva(this)
        buscarClimaTask().execute(localizacao)
    }

    fun exibirProgressBar(){
        pb_aguarde.visibility = View.VISIBLE
        rv_clima.visibility = View.INVISIBLE
        tv_mensagem_erro.visibility = View.INVISIBLE
    }
}
