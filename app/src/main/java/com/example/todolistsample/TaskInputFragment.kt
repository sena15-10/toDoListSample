package com.example.todolistsample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class TaskInputFragment : Fragment() {

    // 編集中のデータを一時的に持っておく変数
    private var editingTodo: TodoItem? = null

    // 画面のパーツを入れる変数
    private lateinit var tilTaskName: TextInputLayout
    private lateinit var etTaskName: TextInputEditText
    private lateinit var spinnerPriority: Spinner

    // 1. フラグメントが作られたときに呼ばれる
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Activityから渡された「荷物（arguments）」があるかチェック
        val args = arguments
        if (args != null) {
            // "id" という荷物が入っていたら、それは「編集モード」ということ
            if (args.containsKey("id")) {
                val id = args.getInt("id")
                val name = args.getString("task_name")
                val priority = args.getInt("priority")

                // バラバラのデータを TodoItem の形に戻す (nameがnullなら空文字にする)
                editingTodo = TodoItem(id, name ?: "", false, priority)
            }
        }
    }

    // 2. 画面の見た目（レイアウト）を作る
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_task_input, container, false)
    }

    // 3. 画面ができた後に、初期設定をする
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 画面のパーツを見つけて変数に入れる
        tilTaskName = view.findViewById(R.id.tilTaskName)
        etTaskName = view.findViewById(R.id.etTaskName)
        spinnerPriority = view.findViewById(R.id.PriorityList)

        // もし「編集モード（データがある状態）」なら、画面に文字を表示する
        if (editingTodo != null) {
            // 名前を入れる
            etTaskName.setText(editingTodo!!.taskName)

            // 優先度（1,2,3）をスピナーの位置（0,1,2）に直して選択させる
            val priority = editingTodo!!.priority
            val spinnerIndex = priority - 1
            spinnerPriority.setSelection(spinnerIndex)
        }
    }

    // ★ Activityが「入力されたデータちょうだい！」と言ってきたら動く機能
    fun getTaskInput(): TodoItem? {
        // 入力された文字を取得
        val taskName = etTaskName.text.toString()

        // 空っぽだったらエラーを出して null を返す
        if (taskName.isBlank()) {
            tilTaskName.error = "タスク名を入力してください"
            return null
        } else {
            tilTaskName.error = null
        }

        // 選ばれている優先度を取得（0,1,2 なので +1 して 1,2,3 にする）
        val priority = spinnerPriority.selectedItemPosition + 1

        // 新しい TodoItem を作って返す
        // 編集モードならそのIDを使い、新規なら 0 を使う
        val idToUse: Int
        if (editingTodo != null) {
            idToUse = editingTodo!!.id
        } else {
            idToUse = 0
        }

        return TodoItem(idToUse, taskName, false, priority)
    }

    // ★ Activityからこのフラグメントを作るための便利機能
    companion object {
        fun newInstance(todoItem: TodoItem?): TaskInputFragment {
            val fragment = TaskInputFragment()

            // もしデータが渡されたら、荷物（Bundle）に詰める
            if (todoItem != null) {
                val args = Bundle()
                // ここで "id" や "task_name" という名前をつけて保存
                args.putInt("id", todoItem.id)
                args.putString("task_name", todoItem.taskName)
                args.putInt("priority", todoItem.priority)

                fragment.arguments = args
            }

            return fragment
        }
    }
}