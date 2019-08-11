package com.cloudidev.jadwalshalat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.cloudidev.jadwalshalat.Entities.Models.Quran
import com.cloudidev.jadwalshalat.Modules.Database.DatabaseHelper
import com.cloudidev.jadwalshalat.Modules.Database.DatabasePresenter
import com.cloudidev.jadwalshalat.Modules.Database.DatabaseView
import com.cloudidev.jadwalshalat.Support.Utils.Adapter
import com.cloudidev.jadwalshalat.ViewHolders.VerseViewHolder
import kotlinx.android.synthetic.main.activity_list_verse.*

class ListVerseActivity : AppCompatActivity(), DatabaseView{


    lateinit var  adapter: Adapter<Quran, VerseViewHolder>
    lateinit var bundle: Bundle

    var presenter = DatabasePresenter(DatabaseHelper(this), this)
    var surahTitle = ""
    var surahId = 1
    var verseId = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_verse)
        setToolbar()


        bundle = intent.extras!!
        surahTitle = bundle.getString("surahTitle").toString()
        surahId = bundle.getInt("surahId")
        verseId = bundle.getInt("verseId")

        presenter.getDataBySurahId(surahId)

        text_title_surah.text = surahTitle

    }

    fun setToolbar(){
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun successGetDataBySurahId(list: List<Quran>) {
        setList(list)
    }

    fun setList(list: List<Quran>) {


        val manager = LinearLayoutManager(this)
        adapter = object : Adapter<Quran, VerseViewHolder>(R.layout.list_verse, VerseViewHolder::class.java,
                Quran::class.java, list) {
            override fun bindView(holder: VerseViewHolder, tipeData: Quran, position: Int) {
                holder.onBind(tipeData)

            }
        }

        list_verse.layoutManager = manager
        list_verse.adapter = adapter
    }
}