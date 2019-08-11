package com.cloudidev.jadwalshalat.Modules.Database

import com.cloudidev.jadwalshalat.Entities.Models.Quran

class DatabaseInteractor (var db: DatabaseHelper): DatabaseInput {

    lateinit var output: DatabaseOutput

    override fun getAllData() {

    }

    override fun updateData(data: Quran) {
//        db.insertData(data)
    }

    override fun getDataById(id: Int) {

    }

    override fun getDataBySurahId(id: Int) {
        val list = db.getDataBySurahId(id)
    }
}