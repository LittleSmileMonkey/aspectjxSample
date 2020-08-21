package com.sleep.timetrace

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sleep.runtime.singleclick.SingleClick
import kotlinx.android.synthetic.main.activity_single_test.*
import java.lang.StringBuilder

class SingleTestActivity : AppCompatActivity(), View.OnTouchListener {

    private val TAG = SingleTestActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_test)
        JavaClickProxy.lambdaTest(tv_java_lambda_test)
        tv_kotlin_lambda_test.setOnClickListener { Log.e(TAG, "kotlin lambda test") }
        tv_java_annotation_test.setOnTouchListener(this)
        tv_kotlin_annotation_test.setOnTouchListener(this)
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            when (v?.id) {
                R.id.tv_java_annotation_test -> {
                    JavaClickProxy.annotationTest()
                }
                R.id.tv_kotlin_annotation_test -> {
                    kotlinAnnotationTest()
                }
                else -> {
                    Log.e(TAG, "no case, onClick invoke, id = ${v?.id}")
                }
            }
        }
        return false
    }

    @SingleClick
    private fun kotlinAnnotationTest() {
        Log.e(TAG, "kotlin annotation test")
    }
}