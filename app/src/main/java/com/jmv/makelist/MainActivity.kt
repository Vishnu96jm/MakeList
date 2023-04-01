package com.jmv.makelist

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class MainActivity : AppCompatActivity(), TodoListAdapter.TodoListClickListener {

    lateinit var todoListRecyclerView: RecyclerView
    lateinit var fab : View
    lateinit var toolbar : Toolbar

    private val listDataManager: ListDataManager = ListDataManager(this)

    companion object{
        const val INTENT_LIST_KEY = "list"
        const val LIST_DETAIL_REQUEST_CODE = 123
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        fab = findViewById(R.id.fab)

        val lists = listDataManager.readLists()

        todoListRecyclerView = findViewById(R.id.lists_recyclerview)
        todoListRecyclerView.layoutManager = LinearLayoutManager(this)
        todoListRecyclerView.adapter = TodoListAdapter(lists, this)

        /*fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }*/

        fab.setOnClickListener { _ ->
            showCreateTodoListDialog()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    //to receive the results of the activity.
    /*This method is going to be called for every activity that is launched that will
    return a result.*/
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == LIST_DETAIL_REQUEST_CODE) {
            data?.let {
                val list = data.getParcelableExtra<TaskList>(INTENT_LIST_KEY)!!
                listDataManager.saveList(list)
                updateLists()
            }
        }
    }

    //another way of refreshing RecyclerView
    private fun updateLists() {
        val lists = listDataManager.readLists()
        todoListRecyclerView.adapter = TodoListAdapter(lists, this)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showCreateTodoListDialog() {

        val dialogTitle = getString(R.string.name_of_list)
        val positiveButtonTitle = getString(R.string.create_list)
        val myDialog = AlertDialog.Builder(this)
        val todoTitleEditText = EditText(this)
        todoTitleEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_WORDS

        myDialog.setTitle(dialogTitle)
        myDialog.setView(todoTitleEditText)

        myDialog.setPositiveButton(positiveButtonTitle, object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface, p1: Int) {
                val adapter: TodoListAdapter = todoListRecyclerView.adapter as TodoListAdapter

                //      adapter.addNewItem(todoTitleEditText.text.toString())


                //create an empty TaskList passing in EditText as title
                val list = TaskList(todoTitleEditText.text.toString())
                listDataManager.saveList(list)
                adapter.addList(list)

                dialog.dismiss()

                showTaskListItems(list)

            }
        })

        /*myDialog.setPositiveButton(positiveButtonTitle) {
                dialog, _ ->
            val adapter = todoListRecyclerView.adapter as TodoListAdapter

            //   adapter.addNewItem(todoTitleEditText.text.toString())

            //create an empty TaskList passing in EditText as title
            val list = TaskList(todoTitleEditText.text.toString())
            listDataManager.saveList(list)
            adapter.addList(list)

            dialog.dismiss()

            showTaskListItems(list)
        }*/

        myDialog.create().show()
    }

    //method to open DetailActivity passing a TaskList object to it
    private fun showTaskListItems(list: TaskList) {
        val taskListItem = Intent(this, DetailActivity::class.java)
        /*TaskList isn't supported by putExtra. Implement Parcelable interface in your objects*/
        taskListItem.putExtra(INTENT_LIST_KEY, list)
        startActivity(taskListItem)

        /*startActivityForResult starts your DetailActivity as intended, however, it adds the
        * expectation that the MainActivity will hear back from the DetailActivity. The second
        * parameter is a request code that lets you know which result you're dealing with.
        *
        * You could be dealing with multiple activities that are passing back multiple results. So,
        * having a unique way to be able to identify results is helpful.*/
        // startActivityForResult(taskListItem, LIST_DETAIL_REQUEST_CODE)
    }

    override fun listItemClicked(list: TaskList) {
        showTaskListItems(list)
    }

}