package br.com.xplorer.xplorerokhttplib.retrofit.entities

data class GithubRepo(val id: Long, val name: String, val url: String) {
    override fun toString(): String {
        return "ID: $id, Name: $name, URL: $url"
    }
}
