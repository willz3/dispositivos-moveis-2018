package br.grupointegrado.tads.telaverde2

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ListaVerdeAdapter.ItemClickListener {

    var toast: Toast? = null

    fun exibirMensagem(mensagem: String){
        toast?.cancel()
        toast = Toast.makeText(this, mensagem, Toast.LENGTH_SHORT)
        toast?.show()
    }

    override fun onItemClick(position: Int) {
        val numeroClicado = lista.get(position)
        exibirMensagem("Clique em $numeroClicado")
    }

    override fun onLongItemClick(position: Int) {
        val numeroClicado = lista.get(position)
        exibirMensagem("Clique longo em $numeroClicado")
    }

    val lista = listOf<Int>(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17);

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = ListaVerdeAdapter(this, lista, this)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        rv_num.adapter = adapter
        rv_num.layoutManager = layoutManager
    }
}
