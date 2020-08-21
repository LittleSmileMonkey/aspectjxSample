package com.sleep.timetrace

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.StringBuilder

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv_java_method_test.setOnClickListener(this)
        tv_java_args_test.setOnClickListener(this)
        tv_java_return_test.setOnClickListener(this)
        tv_java_thread_test.setOnClickListener(this)
        tv_kotlin_method_test.setOnClickListener(this)
        tv_kotlin_args_test.setOnClickListener(this)
        tv_kotlin_return_test.setOnClickListener(this)
        tv_kotlin_thread_test.setOnClickListener(this)
        bt_click_test.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_java_method_test -> {
                javaMethodTest()
            }
            R.id.tv_java_args_test -> {
                javaArgsTest()
            }
            R.id.tv_java_return_test -> {
                javaReturnTest()
            }
            R.id.tv_java_thread_test -> {
                javaThreadTest()
            }
            R.id.tv_kotlin_method_test -> {
                kotlinMethodTest()
            }
            R.id.tv_kotlin_args_test -> {
                kotlinArgsTest()
            }
            R.id.tv_kotlin_return_test -> {
                kotlinReturnTest()
            }
            R.id.tv_kotlin_thread_test -> {
                kotlinThreadTest()
            }
            R.id.bt_click_test -> {
                startActivity(Intent(this,SingleTestActivity::class.java))
            }
            else -> {
                Log.e(TAG, "onClick invoke, id = ${v?.id}")
            }
        }
    }

    private fun javaMethodTest() {
        JavaTraceTest.javaMethodTest()
    }

    private fun javaArgsTest() {
        JavaTraceTest.javaArgsTest(
            1,
            2.0,
            false,
            "javaArgsTest",
            StringBuilder("java object test"),
            null
        )
    }

    private fun javaReturnTest() {
        JavaTraceTest.javaReturnTest(
            1,
            2.0,
            false,
            "javaArgsTest",
            StringBuilder("java object test")
        )
    }

    private fun javaThreadTest() {
        Thread(Runnable {
            try {
                JavaTraceTest.javaThreadTest()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }, "threadTest").start()
    }

    private fun kotlinMethodTest() {
        KotlinTraceTest.kotlinMethodTest()
    }

    private fun kotlinArgsTest() {
        KotlinTraceTest.kotlinArgsTest(
            1,
            2.0,
            true,
            "kotlinArgsTest",
            StringBuilder("kotlin object test"),
            null
        )
    }

    private fun kotlinReturnTest() {
        KotlinTraceTest.kotlinReturnTest(
            1,
            2.0,
            false,
            "javaArgsTest",
            StringBuilder("java object test")
        )
    }

    private fun kotlinThreadTest() {
        Thread(Runnable {
            try {
                KotlinTraceTest.kotlinThreadTest()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }, "threadTest").start()
    }
}