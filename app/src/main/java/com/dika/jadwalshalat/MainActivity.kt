package com.dika.jadwalshalat

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import org.json.JSONObject
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    private var listKota: MutableList<Kota>? = null
    private var mKotaAdapter: ArrayAdapter<Kota>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listKota = ArrayList<Kota>()
        mKotaAdapter = ArrayAdapter<Kota>(this, android.R.layout.simple_spinner_item, listKota as ArrayList<Kota>)

        mKotaAdapter!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        kota.adapter = mKotaAdapter
        kota.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val kota = mKotaAdapter!!.getItem(position)
                if (kota != null) {
                    loadJadwal(kota.id)
                }
            }
        }

        loadKota()
    }

    private fun loadJadwal(id: Int?) {
        try {
            val id_kota = id.toString()

            val current = SimpleDateFormat("yyyy-MM-dd")
            val tanggal = current.format(Date())

            val url = "https://api.banghasan.com/sholat/format/json/jadwal/kota/$id_kota/tanggal/$tanggal"
            val task = ClientAsyncTask(this, object : ClientAsyncTask.OnPostExecuteListener {
                override fun onPostExecute(result: String) {
                    Log.d("JadwalData", result)
                    try {
                        val jsonObj = JSONObject(result)
                        val objJadwal = jsonObj.getJSONObject("jadwal")
                        val obData = objJadwal.getJSONObject("data")

                        Log.d("dataJadwal", obData.toString())

                        tv_tanggal.text = obData.getString("tanggal")
                        tv_subuh.text = obData.getString("subuh")
                        tv_dzuhur.text = obData.getString("dzuhur")
                        tv_ashar.text = obData.getString("ashar")
                        tv_maghrib.text = obData.getString("maghrib")
                        tv_isya.text = obData.getString("isya")

                        Log.d("dataJadwal", obData.toString())

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
            })
            task.execute(url)
        }catch (e:Exception) {
            e.printStackTrace()
        }
    }

    fun loadKota () {
        try {
            var url = "https://api.banghasan.com/sholat/format/json/kota"
            val task = ClientAsyncTask(this, object :
                ClientAsyncTask.OnPostExecuteListener {
                override fun onPostExecute(result: String) {

                    Log.d("KotaData", result)
                    try {
                        val jsonObj = JSONObject(result)
                        val jsonArray = jsonObj.getJSONArray("kota")
                        var kota: Kota? = null
                        for (i in 0 until jsonArray.length()) {
                            val obj = jsonArray.getJSONObject(i)

                            kota = Kota()
                            kota!!.id = obj.getInt("id")
                            kota!!.nama = obj.getString("nama")
                            listKota!!.add(kota)
                        }

                        mKotaAdapter!!.notifyDataSetChanged()
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
            })
            task.execute(url)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
