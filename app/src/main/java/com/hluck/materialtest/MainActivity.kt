package com.hluck.materialtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.GridLayout
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.hluck.materialtest.databinding.ActivityMainBinding
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private val fruits = mutableListOf(Fruit("Apple", R.drawable.apple), Fruit("Banana", R.drawable.banana), Fruit("Orange", R.drawable.orange), Fruit("Watermelon", R.drawable.watermelon), Fruit("Pear", R.drawable.pear), Fruit("Grape", R.drawable.grape), Fruit("Pineapple", R.drawable.pineapple), Fruit("Strawberry", R.drawable.strawberry), Fruit("Cherry", R.drawable.cherry), Fruit("Mango", R.drawable.mango))
    private val fruitList = ArrayList<Fruit>()

    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.let {
            //让导航按钮显示出来
            it.setDisplayHomeAsUpEnabled(true)
            //设置导航按钮图标
            it.setHomeAsUpIndicator(R.drawable.ic_menu)
        }

        binding.navView.setCheckedItem(R.id.navCall)
        binding.navView.setNavigationItemSelectedListener {
            binding.drawerLayout.closeDrawers()
            true
        }

        binding.fab.setOnClickListener {
            Snackbar.make(it,"Data deleted",Snackbar.LENGTH_SHORT).setAction("Undo"){
                showMsg("Data restored")
            }.show()
//            showMsg("FAB clicked!")
        }

        initFruits()
        val gridLayoutManager = GridLayoutManager(this, 2)
        binding.recycleView.layoutManager = gridLayoutManager
        val fruitAdapter = FruitAdapter(this, fruitList)
        binding.recycleView.adapter = fruitAdapter

        binding.swiperefreshlayout.setColorSchemeResources(R.color.colorPrimary)
        binding.swiperefreshlayout.setOnRefreshListener {
            refreshFruits(adapter = fruitAdapter)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> binding.drawerLayout.openDrawer(GravityCompat.START)
            R.id.backup -> showMsg("backup")
            R.id.delete -> showMsg("delete")
            R.id.settings -> showMsg("settings")
        }
        return true
    }

    fun showMsg(msg: String){
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show()
    }

    /**
     * 初始化水果列表数据
     */
    private fun initFruits(){
        fruitList.clear()
        repeat(50) {
            val index = (0 until fruits.size).random()
            fruitList.add(fruits[index])
        }
    }

    /**
     * 刷新水果视图列表
     * @param adapter FruitAdapter
     */
    private fun refreshFruits(adapter: FruitAdapter){
        thread {
            Thread.sleep(2000)
            runOnUiThread {
                initFruits()
                adapter.notifyDataSetChanged()
                binding.swiperefreshlayout.isRefreshing = false
            }
        }
    }
}