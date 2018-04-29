package br.grupointegrado.tads.clima

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ShareCompat
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_detalhes.*

class DetalhesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes)

        if (intent.hasExtra(Clima.EXTRA_MENSAGEM)){
            tv_exibir_previsao.text = intent.getStringExtra(Clima.EXTRA_MENSAGEM)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detalhes, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == R.id.btn_menu_compartilhar){
            compartilharDadosClima()
        }
        return false
    }

    fun compartilharDadosClima(){
        if (intent.hasExtra(Clima.EXTRA_MENSAGEM)){
            val dados = intent.getStringExtra(Clima.EXTRA_MENSAGEM)
            val intentCompartilhar = ShareCompat.IntentBuilder.from(this)
                    .setType("text/plain")
                    .setChooserTitle("Dados do Clima")
                    .setText(dados).intent
            if (intentCompartilhar.resolveActivity(packageManager) != null){
                startActivity(intentCompartilhar)
            }
        }
    }
}
