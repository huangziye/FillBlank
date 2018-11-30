package com.hzy.fillblank

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.hzy.fillblanklibrary.AnswerRange
import com.hzy.fillblanklibrary.FillBlankView


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fillBlankView = findViewById<FillBlankView>(R.id.fillBlankView)
        val content =
            "纷纷扬扬的________下了半尺多厚。天地间________的一片。我顺着________工地走了四十多公里，" + "只听见各种机器的吼声，可是看不见人影，也看不见工点。一进灵官峡，我就心里发慌。"

        // 答案范围集合
        val rangeList = mutableListOf<AnswerRange>()
        rangeList.add(AnswerRange(5, 13))
        rangeList.add(AnswerRange(23, 31))
        rangeList.add(AnswerRange(38, 46))

        fillBlankView.setData(content, rangeList, Color.RED)
    }
}
