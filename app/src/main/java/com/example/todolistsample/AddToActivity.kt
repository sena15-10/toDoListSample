package com.example.todolistsample

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class AddToActivity : AppCompatActivity() {
    // 1. レイアウトの部品（ビュー）を入れるための変数を宣言
    private lateinit var tilTaskName: TextInputLayout
    private lateinit var etTaskName: TextInputEditText
    private lateinit var spinnerPriority: Spinner // ★ IDを PriorityList から変更した場合
    private lateinit var btnSave: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_to)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //todoアイテムを作成する

        btnSave = findViewById(R.id.btnSave)

    }
    private inner class SaveBtnClickListener : OnClickListener{
        override fun onClick(v: View?) {
            tilTaskName = findViewById(R.id.tilTaskName)
            etTaskName = findViewById(R.id.etTaskName)
            spinnerPriority = findViewById(R.id.PriorityList) // ★ IDを PriorityList から変更した場合
            val taskName = etTaskName.text.toString()
            if (taskName == "") {
                //タスク名を書いていなかった場合エラーメッセージを設定
                tilTaskName.error = "タスク名を入力してください"
                return
            } else {
                //エラーメッセージを消す
                tilTaskName.error = null
            }
            //Spinnerで選択された項目(文字列)を取得
            val selectedPriorityText = spinnerPriority.selectedItem.toString()
            //選択された文字列をデータ用の数値に変換する
            val priority = when (selectedPriorityText) {
                "低" -> 1
                "中" -> 2
                "高" -> 3
                else -> 2 //念のため
            }
            //10. 返送用の「荷物(Intent)」を作成する
            val intent = Intent()

            //11.荷物にデータを詰める
            intent.putExtra("NEW_TASK_NAME",taskName)
            intent.putExtra("NEW_TASK_PRIORITY",priority)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}