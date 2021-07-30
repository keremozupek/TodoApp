package com.example.todoapp.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.room.TodoEntity

class TodoAdapter(private val listener: (TodoEntity) -> Unit) :
    RecyclerView.Adapter<TodoViewHolder>() {

    private var items = ArrayList<TodoEntity>()

    fun setItems(items: List<TodoEntity>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_view, parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val item = items[position]
        holder.textTitle.text = item.title
        holder.textDate.text = items[position].dueDate.toString()
        holder.priority.text = items[position].priority.toString()
        holder.checkBox.isChecked = items[position].done

        holder.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            listener.invoke(item)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val textTitle: TextView = itemView.findViewById(R.id.title)
    val textDate: TextView = itemView.findViewById(R.id.date)
    val priority: TextView = itemView.findViewById(R.id.priority)
    val checkBox: CheckBox = itemView.findViewById(R.id.checkBox)
}
