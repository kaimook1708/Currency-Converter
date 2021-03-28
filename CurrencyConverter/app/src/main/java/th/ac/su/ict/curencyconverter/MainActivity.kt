package th.ac.su.ict.curencyconverter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    lateinit var editUSA:EditText
    lateinit var Converter:TextView
    lateinit var Bt:Button
    lateinit var Us:TextView

    var BASE_URL = "https://api.exchangeratesapi.io/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editUSA = findViewById<EditText>(R.id.Ip)
        Converter = findViewById<TextView>(R.id.Th)
        Usd = findViewById<TextView>(R.id.Us)
        val btnCt = findViewById<Button>(R.id.Bt)

        btnCt.setOnClickListener {
            getCurrentMoneyData()
        }

    } //override
    fun getCurrentMoneyData() {

        val retrofit =Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(MoneyService::class.java)
        val call = service.getCurrentMoneyData("USD")

        call.enqueue(object : Callback<MoneyResponse>{



            override fun onFailure(call: Call<MoneyResponse>?, t: Throwable?) {
                TODO("Not yet implemented")
            }
            override fun onResponse(
                call: Call<MoneyResponse>?,
                response: Response<MoneyResponse>?

            ) {

                if (response!=null){
                    if (response.code() == 200){

                        val moneyRes = response.body()
                        val cal = moneyRes.rates.THB.toDouble()
                        val enit:Double = editUSA.text.toString().toDouble()
                        val sum = (enit*cal)
                        Converter.text = "THB = ${sum.toString()}"

                        val showTHB = moneyRes.rates.THB.toString()
                        Us.text = "1 USD = ${showTHB.toString()}"

                    }
                }
            }
        })
    }
}