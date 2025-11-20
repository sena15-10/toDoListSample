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

    // ▼▼▼ (以前のコード) ここにあったView変数は、フラグメント側で持つので不要になりました ▼▼▼
    // private lateinit var tilTaskName: TextInputLayout
    // private lateinit var etTaskName: TextInputEditText
    // private lateinit var spinnerPriority: Spinner
    // ▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲

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
        // TODO: ２．以下のコードに書き換えることする 
        // =================================================================
        //  ★ 新しい実装：フラグメントを表示して連携する
        // =================================================================

        // 1. フラグメントを表示する (初回のみ)
        if (savedInstanceState == null) {
            // 新規作成なので引数は null で作成
            val fragment = TaskInputFragment.newInstance(null)

            supportFragmentManager.beginTransaction()
                // ※ activity_add_to.xml に配置した FragmentContainerView のIDを指定してください
                // まだコンテナを作っていない場合は、ConstraintLayoutなど親のID(R.id.mainなど)でも一旦動きますが、
                // 正しくは <FragmentContainerView android:id="@+id/container" ... /> を作るべきです。
                .replace(R.id.TaskInputFragment, fragment, "INPUT_FRAGMENT")
                .commit()
        }

        // 2. 保存ボタンの処理
        btnSave = findViewById(R.id.btnSave)
        btnSave.setOnClickListener {
            // (A) フラグメントを見つける
            val fragment = supportFragmentManager.findFragmentByTag("INPUT_FRAGMENT") as? TaskInputFragment

            // (B) フラグメントに「入力データちょうだい」と頼む
            val todoItem = fragment?.getTaskInput()

            // (C) データが返ってきたら（入力OKなら）、メイン画面に結果を返す
            if (todoItem != null) {
                val intent = Intent()
                intent.putExtra("NEW_TASK_NAME", todoItem.taskName)
                intent.putExtra("NEW_PRIORITY", todoItem.priority)

                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }

        // =================================================================
        //  ▼ (以前のコード) 以下の処理は全てフラグメント(TaskInputFragment)の中に移動しました
        // =================================================================
        /*
        btnSave = findViewById(R.id.btnSave)
        btnSave.setOnClickListener(SaveBtnClickListener())
        */
    }

    /*
    // ▼▼▼ (以前のコード) 内部クラスで行っていた処理もフラグメントへ移動済みです ▼▼▼
    private inner class SaveBtnClickListener : OnClickListener{
        override fun onClick(v: View?) {
            tilTaskName = findViewById(R.id.tilTaskName)
            etTaskName = findViewById(R.id.etTaskName)
            spinnerPriority = findViewById(R.id.PriorityList) 
            
            val taskName = etTaskName.text.toString()
            if (taskName == "") {
                tilTaskName.error = "タスク名を入力してください"
                return
            } else {
                tilTaskName.error = null
            }

            val selectedPriorityText = spinnerPriority.selectedItem.toString()
            val priority = when (selectedPriorityText) {
                "低" -> 1
                "中" -> 2
                "高" -> 3
                else -> 2
            }
            
            val intent = Intent()
            intent.putExtra("NEW_TASK_NAME",taskName)
            intent.putExtra("NEW_TASK_PRIORITY",priority)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
    ▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲
    */
}