package br.com.kevinlucas.whatsappmvvm.service.listener

interface ContactListener {

    /**
     * Click para edição
     */
    fun onInitTalk(idContact: String, nameContact: String)

}