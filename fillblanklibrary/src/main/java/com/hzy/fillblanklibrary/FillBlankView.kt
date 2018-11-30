package com.hzy.fillblanklibrary

import android.content.Context
import android.graphics.drawable.PaintDrawable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.*

/**
 * 填空题
 * Created by ziye_huang on 2018/11/29.
 */
open class FillBlankView constructor(context: Context?, attrs: AttributeSet?) :
    RelativeLayout(context, attrs) {

    private lateinit var mContentTextView: TextView
    private lateinit var mSpannableStringBuilder: SpannableStringBuilder
    //答案范围集合
    private lateinit var mAnswerRangeList: MutableList<AnswerRange>
    //答案集合
    private lateinit var mAnswerList: MutableList<String>

    init {
        initView()
    }

    private fun initView() {
        var view = LayoutInflater.from(context).inflate(R.layout.fill_blank_layout, this)
        mContentTextView = view.findViewById(R.id.tv_content)
    }

    /**
     * 设置数据
     * @param originContext 源数据
     * @param answerRangeList 答案范围集合
     * @param underLineColor 填空题的字体及下划线颜色
     */
    fun setData(originContext: String, answerRangeList: MutableList<AnswerRange>, underLineColor: Int) {
        mAnswerRangeList = answerRangeList
        mAnswerList = mutableListOf()
        for (index in mAnswerRangeList) {
            mAnswerList.add("")
        }
        mSpannableStringBuilder = SpannableStringBuilder(originContext)
        //设置下划线
        for (range in answerRangeList) {
            var underLineColorSpan = ForegroundColorSpan(underLineColor);
            mSpannableStringBuilder.setSpan(
                underLineColorSpan,
                range.start,
                range.end,
                Spanned.SPAN_INCLUSIVE_INCLUSIVE
            )
        }

        //设置填空处点击事件
        for (index in answerRangeList.indices) {
            val blankClickableSpan = BlankClickableSpan(context, mContentTextView, index)
            val range = answerRangeList[index]
            mSpannableStringBuilder.setSpan(
                blankClickableSpan,
                range.start,
                range.end,
                Spanned.SPAN_INCLUSIVE_INCLUSIVE
            )
        }

        //设置此方法后，点击事件才能生效
        mContentTextView.movementMethod = LinkMovementMethod.getInstance()
        mContentTextView.text = mSpannableStringBuilder
    }

    /**
     * 空白处点击事件
     * Created by ziye_huang on 2018/11/30.
     */
    inner class BlankClickableSpan(
        private val context: Context,
        private val contentView: TextView,
        val position: Int
    ) : ClickableSpan() {

        override fun onClick(widget: View) {
            val view = LayoutInflater.from(context).inflate(R.layout.input_layout, null)
            val etInput = view.findViewById<EditText>(R.id.et_answer)
            val btnFillBlank = view.findViewById<Button>(R.id.btn_fill_blank)

            //显示原有答案
            etInput.setText(mAnswerList[position])
            etInput.setSelection(mAnswerList[position].length)

            val popupWindow = PopupWindow(view, LayoutParams.MATCH_PARENT, dp2px(40f))
            //获取焦点
            popupWindow.isFocusable = true
            // 为了防止弹出菜单获取焦点之后，点击Activity的其他组件没有响应
            popupWindow.setBackgroundDrawable(PaintDrawable())
            // 设置PopupWindow在软键盘的上方
            popupWindow.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
            // 弹出PopupWindow
            popupWindow.showAtLocation(contentView, Gravity.BOTTOM, 0, 0)

            btnFillBlank.setOnClickListener {
                // 填写答案
                fillAnswer(etInput.text.toString(), position)
                popupWindow.dismiss()
            }

            // 显示软键盘
            val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)

        }

        override fun updateDrawState(ds: TextPaint) {
            //设置不显示下划线
            ds.isUnderlineText = false
        }

    }

    /**
     * 填写答案
     *
     * @param answer   当前填空处答案
     * @param position 填空位置
     */
    private fun fillAnswer(answer: String, position: Int) {
        var answer = " $answer "

        // 替换答案
        val range = mAnswerRangeList[position]
        mSpannableStringBuilder.replace(range.start, range.end, answer)

        // 更新当前的答案范围
        val currentRange = AnswerRange(range.start, range.start + answer.length)
        mAnswerRangeList[position] = currentRange

        // 答案设置下划线
        mSpannableStringBuilder.setSpan(
            UnderlineSpan(),
            currentRange.start, currentRange.end, Spanned.SPAN_INCLUSIVE_INCLUSIVE
        )

        // 将答案添加到集合中
        mAnswerList[position] = answer.replace(" ", "")

        // 更新内容
        mContentTextView.text = mSpannableStringBuilder

        for (i in 0 until mAnswerRangeList.size) {
            if (i > position) {
                // 获取下一个答案原来的范围
                val oldNextRange = mAnswerRangeList.get(i)
                val oldNextAmount = oldNextRange.end - oldNextRange.start
                // 计算新旧答案字数的差值
                val difference = currentRange.end - range.end

                // 更新下一个答案的范围
                val nextRange = AnswerRange(
                    oldNextRange.start + difference,
                    oldNextRange.start + difference + oldNextAmount
                )
                mAnswerRangeList[i] = nextRange
            }
        }
    }

    private fun dp2px(dp: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics
        )
            .toInt()
    }

}
