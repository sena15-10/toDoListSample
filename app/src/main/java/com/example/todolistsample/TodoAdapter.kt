// TodoAdapter.kt
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat

import androidx.recyclerview.widget.RecyclerView
import com.example.todolistsample.R
import com.example.todolistsample.TodoItem

// ① TodoAdapterクラス本体
class TodoAdapter(val todoList: MutableList<TodoItem>):
    RecyclerView.Adapter<TodoAdapter.TodoViewHolder>()  {
//ViewHolder: 各リストアイテムのビュー要素を保持する入れ物
    class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //list_item_todo.xmlの各ビュー要素を取得
        val dateTextView: TextView = itemView.findViewById(R.id.taskMeText)
        val checkBox: CheckBox = itemView.findViewById(R.id.todoCheckBox)
        val priorityIndicator: ImageView = itemView.findViewById(R.id.priorityIndicator)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view  = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_todo, parent, false)
        return TodoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        // Get the data model for this position
        val currentTodo = todoList[position]
        // Set the view elements for the data model
        holder.dateTextView.text = currentTodo.taskName
        holder.checkBox.isChecked = currentTodo.isCompleted
        holder.priorityIndicator.setImageResource(R.drawable.ic_priority)
        val context = holder.itemView.context
        val iconColor = when ( currentTodo.priority) {
            1 -> ContextCompat.getColor(context, R.color.priority_high_color)
            2 -> ContextCompat.getColor(context, R.color.priority_medium_color)
            else -> ContextCompat.getColor(context, R.color.priority_low_color)
        }
        holder.priorityIndicator.imageTintList = ColorStateList.valueOf(iconColor)


    }


}