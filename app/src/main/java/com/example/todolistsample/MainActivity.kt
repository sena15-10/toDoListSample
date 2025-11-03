package com.example.todolistsample

import TodoAdapter
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
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


    }

    private fun loadSampleData() {
        //サンプルデータのを作成
        todoList.add(TodoItem(1, "UIデザインを完成させる", false, 1)) // 優先度：高
        todoList.add(TodoItem(2, "DB設計の見直し", false, 2)) // 優先度：中
        todoList.add(TodoItem(3, "牛乳を買う", true, 3)) // 優先度：低 (チェック済み)
        todoList.add(TodoItem(4, "プレゼン資料の準備", false, 2)) // 優先度：中
    }
}