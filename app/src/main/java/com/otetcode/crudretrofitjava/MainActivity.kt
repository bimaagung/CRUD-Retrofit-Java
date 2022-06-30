package com.otetcode.crudretrofitjava

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.otetcode.crudretrofitjava.Adapater.ArticleAdapter
import com.otetcode.crudretrofitjava.Model.Article
import com.otetcode.crudretrofitjava.Model.Person
import com.otetcode.crudretrofitjava.Network.ArticleApiClient
import com.otetcode.crudretrofitjava.Network.PersonApiClient
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.card_layout.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
//
//    val client by lazy {
//        ArticleApiClient.create()
//    }

    val client1 by lazy {
        PersonApiClient.create()
    }

    var disposable: Disposable? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //showArticles()

        // Uncomment to test different functions
       // showArticle(1)
        //showPerson(5)

//        val person = Person(1,"Riani", "Lestari", "", "")
//        postPerson(person)
         repeatPerson(5)

        // val article = Article(1,101, "Test Article", "Have fun posting")
         //postArticle(article)
    }

    private fun showPerson(id: Int) {

        disposable = client1.getPerson(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> Log.v("PERSON ID ${result.id}: ", " " +result.first_name+result.last_name) },
                { error -> Log.e("ERROR", error.message) }
            )

    }

    private fun postPerson(person: Person) {

        disposable = client1.addPerson(person)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> Log.v("POSTED PERSON", "" + person ) },
                { error -> Log.e("ERROR", error.message ) }
            )


    }

    private fun repeatPerson(id:Int){
        Observable.interval(15, TimeUnit.SECONDS).startWith(1)
            .flatMap { client1.getPerson(id) }
            .repeat()
            .subscribe(
                {
                    setupCoba(it)
                },
                { error -> Log.e("ERROR", error.message ) })
    }


    /* get list of articles */
//    private fun showArticles() {
//
//        disposable = client.getArticles()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(
//                { result -> setupRecycler(result) },
//                { error -> Log.e("ERROR", error.message) }
//            )
//
//    }
//
//    private fun showArticle(id: Int) {
//
//        disposable = client.getArticle(id)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(
//                { result -> Log.v("ARTICLE ID ${id}: ", "" + result.title) },
//                { error -> Log.e("ERROR", error.message) }
//            )
//
//    }
//
//    private fun postArticle(article: Article) {
//
//        disposable = client.addArticle(article)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(
//                { result -> Log.v("POSTED ARTICLE", "" + article ) },
//                { error -> Log.e("ERROR", error.message ) }
//            )
//    }

    fun setupCoba(person: Person){
        coba.text = person.first_name
    }
//
//    fun setupRecycler(articleList: List<Article>) {
//        articles_recycler.setHasFixedSize(true)
//        val layoutManager = LinearLayoutManager(this)
//        layoutManager.orientation = LinearLayoutManager.VERTICAL
//        articles_recycler.layoutManager = layoutManager
//        articles_recycler.adapter = ArticleAdapter(articleList){
//            Log.v("Article", it.id.toString())
//        }
//    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }
}
