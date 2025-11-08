package com.example.todolistsample

import TodoAdapter
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var todoRecyclerView: RecyclerView
    private lateinit var todoAdapter: TodoAdapter
    private val todoList = mutableListOf<TodoItem>()
    //ランチャーはフィールドなのでonCreateより上に記述
    // ★ ステップ4: 新しい画面から「結果を受け取る」ためのランチャーを準備
    private val addTodoLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result -> // <-- AddTodoActivity が閉じたら、ここが実行される！

        // 1. 結果が「成功(OK)」かどうかをチェック
        if (result.resultCode == Activity.RESULT_OK) {

            // 2. 返されたIntent(result.data)からデータを取り出す
            //    (AddTodoActivityで "NEW_TASK_NAME" として詰めたもの)
            // もしIdをint型ではなくLong型であるなら以下の一行は使わない
            val newId = todoList.size + 1
            val taskName = result.data?.getStringExtra("NEW_TASK_NAME")
            val priority = result.data?.getIntExtra("NEW_PRIORITY", 3)

            // 3. データが正しく取れたかチェック
            if (!taskName.isNullOrBlank() && priority != null) {

                // 4. ★★★ ここが「リストに要素を追加する」処理 ★★★
                val newItem = TodoItem(
                    id = newId,
                    taskName = taskName,
                    isCompleted = false,
                    priority = priority
                )

                // 5. リストに追加し、アダプターに通知
                todoList.add(newItem)

                // 2. アダプターに「アイテムが追加されたこと」を通知する
                // RecyclerViewのアダプター（todoAdapter）に対して、「リストの特定の位置に新しいデータが入ったよ」と伝えます。
                // `todoList.size - 1` は、リストの末尾に追加されたアイテムのインデックス（位置番号）を指します。
                // この命令を受け取ったアダプターは、RecyclerViewに対して「この位置に新しいビューを作って表示して！」と指示します。
                //       これにより、画面に新しいアイテムがアニメーション付きで表示されます。
                todoAdapter.notifyItemInserted(todoList.size - 1)
                //アイテムが見える位置までリストを自動スクロールさせる
                // 新しいアイテムがリストの末尾に追加された場合、そのアイテムが画面の外で見えないことがあります。
                // この命令は、RecyclerViewに対して「リストの末尾（`todoList.size - 1` の位置）まで自動でスクロールして」と指示します。
                // これにより、ユーザーは追加したアイテムをすぐに見ることができます。
                todoRecyclerView.scrollToPosition(todoList.size - 1)
            }
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //ステップ１：サンプルデータの生成
        loadSampleData()
        todoRecyclerView = findViewById(R.id.todoRecyclerView)
        todoAdapter = TodoAdapter(todoList)
        //todoAdapterマネージャー
        todoRecyclerView.adapter = todoAdapter
        todoRecyclerView.layoutManager =LinearLayoutManager(this)
        //todoRecyclerView.layoutManager =GridLayoutManager(this,2)
        //---------------------------------------------------------//

        //ステップ２アイテムの追加
        val btAdd = findViewById<Button>(R.id.btAddItem)
        btAdd.setOnClickListener(ButtonAddItemClickListener())


    }

    private fun loadSampleData() {
        //サンプルデータのを作成
        todoList.add(TodoItem(1, "UIデザインを完成させる", false, 1)) // 優先度：高
        todoList.add(TodoItem(2, "DB設計の見直し", false, 2)) // 優先度：中
        todoList.add(TodoItem(3, "牛乳を買う", true, 3)) // 優先度：低 (チェック済み)
        todoList.add(TodoItem(4, "プレゼン資料の準備", false, 2)) // 優先度：中
    }

    private inner class ButtonAddItemClickListener : OnClickListener {
        override fun onClick(v: View?) {
            val intent = Intent(this@MainActivity , AddToActivity::class.java )
            addTodoLauncher.launch(intent)
        }
    }


}