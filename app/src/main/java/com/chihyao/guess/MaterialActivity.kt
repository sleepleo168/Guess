package com.chihyao.guess

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_material.*
import kotlinx.android.synthetic.main.content_material.*

class MaterialActivity : AppCompatActivity() {
    private val REQUEST_RECORD = 100
    val secretNumber = SecretNumber()
    val TAG = MaterialActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            replay()
        }
        counter.setText(secretNumber.count.toString())
        Log.d(TAG, "onCreate: " + secretNumber.secret)
        val count = getSharedPreferences("guess", MODE_PRIVATE)
            .getInt("REC_COUNTER", -1)
        val nick = getSharedPreferences("guess", MODE_PRIVATE)
            .getString("REC_NICKNAME", null)
        Log.d(TAG, "data: $count / $nick")
        //Room Query
        /*AsyncTask.execute {
            val list = GameDataBase.getInstance(this)?.recordDao()?.getAll()
            list?.forEach {
                Log.d(TAG, "record: ${it.nickname} ${it.counter}")
            }
        }*/
    }

    private fun replay() {
        AlertDialog.Builder(this)
            .setTitle("Replay game")
            .setPositiveButton(getString(R.string.ok), { dialog, which ->
                secretNumber.reset()
                counter.setText(secretNumber.count.toString())
                number.setText("")
            })
            .setNeutralButton("Cancel", null)
            .show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_RECORD){
            if(resultCode == RESULT_OK) {
                val nickname = data?.getStringExtra("NICK")
                Log.d(TAG, "onActivityResult: $nickname")
                replay()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: ")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: ")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: ")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: ")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
    }

    fun check(view : View){
        if (number.text.isEmpty()) {
            showError("please input a number(1-10)")
            return
        }
        val n = number.text.toString().toInt()
        if (n !in 1..10) {
            showError("number must in 1-10")
            return
        }
        println("number: $n")
        Log.d(TAG, "number:" + n)
        val diff = secretNumber.validate(n)
        var message = getString(R.string.yes_you_got_it)
        if (diff < 0) {
            message = getString(R.string.bigger)
        } else if (diff > 0) {
            message = getString(R.string.smaller)
        }
        counter.setText(secretNumber.count.toString())
//        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.dialog_title))
            .setMessage(message)
            .setPositiveButton(getString(R.string.ok), { dialog, which ->
                if (diff == 0) {
                    val intent = Intent(this, RecordActivity::class.java)
                    intent.putExtra("COUNTER", secretNumber.count)
                    startActivityForResult(intent, REQUEST_RECORD)
//                    startActivity(intent)
                }
            })
            .show()
    }

    private fun showError(errMsg : String) {
        AlertDialog.Builder(this)
            .setTitle("Error")
            .setMessage(errMsg)
            .setPositiveButton(getString(R.string.ok), null)
            .show()
    }

}