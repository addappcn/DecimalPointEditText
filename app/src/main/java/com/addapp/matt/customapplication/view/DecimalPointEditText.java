package com.addapp.matt.customapplication.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.Selection;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;

import com.addapp.matt.customapplication.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TODO: document your custom view class.
 * setInputType(EditorInfo.TYPE_NUMBER_FLAG_DECIMAL);
 * 保留小数点后面的位数的数字文本框
 * <com.uto.driver.views.DecimalPointEditText
    android:layout_width="300dp"
     android:layout_height="300dp"
     android:background="#ccc"
     android:paddingBottom="40dp"
     android:paddingLeft="20dp"
     app:decimalPoint="2" //小数点后面的位数
     android:text="ddd"/>
 */
public class DecimalPointEditText extends EditText {
    private String mExampleString; // TODO: use a default from R.string...
    private int mExampleColor = Color.RED; // TODO: use a default from R.color...
    private float mExampleDimension = 0; // TODO: use a default from R.dimen...
    private Drawable mExampleDrawable;
    private int mDecimalPoint = 1;

    private TextPaint mTextPaint;
    private float mTextWidth;
    private float mTextHeight;

    public DecimalPointEditText(Context context) {
        super(context);
        init(null, 0);

    }

    public DecimalPointEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public DecimalPointEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.DecimalPointEditText, defStyle, 0);

//        mExampleString = a.getString(
//                R.styleable.DecimalPointEditText_exampleString);
//        mExampleColor = a.getColor(
//                R.styleable.DecimalPointEditText_exampleColor,
//                mExampleColor);
        mDecimalPoint = a.getInt(R.styleable.DecimalPointEditText_decimalPoint,1);
        // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
        // values that should fall on pixel boundaries.
//        mExampleDimension = a.getDimension(
//                R.styleable.DecimalPointEditText_exampleDimension,
//                mExampleDimension);
//
//        if (a.hasValue(R.styleable.DecimalPointEditText_exampleDrawable)) {
//            mExampleDrawable = a.getDrawable(
//                    R.styleable.DecimalPointEditText_exampleDrawable);
//            mExampleDrawable.setCallback(this);
//        }

        a.recycle();

        // Set up a default TextPaint object
        mTextPaint = new TextPaint();
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.LEFT);

        // Update TextPaint and text measurements from attributes
//        invalidateTextPaintAndMeasurements();
    }

    @Override
    public void addTextChangedListener(TextWatcher watcher) {
        super.addTextChangedListener(watcher);
    }

    @Override
    protected void onTextChanged(CharSequence s, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(s, start, lengthBefore, lengthAfter);
        String str = s.toString().trim();
        if(!TextUtils.isEmpty(str)){
                if(str.contains(".")){
                    String subStr [];
                    subStr = str.split("\\.",2);//分割成长度为2的数组
                    if(null!=subStr){
                        if(subStr.length>1){
                            String finalStr ="";
                            subStr[1] = getNumbers(subStr[1]);
                            if(subStr[1].length()>mDecimalPoint){
                                String temp = subStr[1].substring(0,mDecimalPoint);
                                finalStr = subStr[0]+"."+ getNumbers(temp);
                                SpannableStringBuilder sBuilder=new SpannableStringBuilder(finalStr);
                                setText(sBuilder);
                                Editable editable=getText();
                                Selection.setSelection(editable, editable.length());
                            }else{
                                if(lengthBefore != lengthAfter){
                                    finalStr = subStr[0]+"."+  subStr[1];
                                    SpannableStringBuilder sBuilder=new SpannableStringBuilder(finalStr);
                                    setText(sBuilder);
                                    Editable editable=getText();
                                    Selection.setSelection(editable, editable.length());
                                }

                            }
                        }
                    }
                }
        }
    }

    // 判断一个字符串是否都为数字
    public boolean isDigit(String strNum) {
        return strNum.matches("[0-9]{1,}");
    }
    //截取数字
    private String getNumbers(String content) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            return matcher.group(0);
        }
        return "";
    }
//    private void invalidateTextPaintAndMeasurements() {
//        mTextPaint.setTextSize(mExampleDimension);
//        mTextPaint.setColor(mExampleColor);
//        mTextWidth = mTextPaint.measureText(mExampleString);
//
//        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
//        mTextHeight = fontMetrics.bottom;
//    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // TODO: consider storing these as member variables to reduce
        // allocations per draw cycle.
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;

        // Draw the text.
//        canvas.drawText(mExampleString,
//                paddingLeft + (contentWidth - mTextWidth) / 2,
//                paddingTop + (contentHeight + mTextHeight) / 2,
//                mTextPaint);
//
//        // Draw the example drawable on top of the text.
//        if (mExampleDrawable != null) {
//            mExampleDrawable.setBounds(paddingLeft, paddingTop,
//                    paddingLeft + contentWidth, paddingTop + contentHeight);
//            mExampleDrawable.draw(canvas);
//        }
    }

    /**
     * Gets the example string attribute value.
     *
     * @return The example string attribute value.
     */
    public String getExampleString() {
        return mExampleString;
    }

    /**
     * Sets the view's example string attribute value. In the example view, this string
     * is the text to draw.
     *
     * @param exampleString The example string attribute value to use.
     */
    public void setExampleString(String exampleString) {
        mExampleString = exampleString;
//        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example color attribute value.
     *
     * @return The example color attribute value.
     */
    public int getExampleColor() {
        return mExampleColor;
    }

    /**
     * Sets the view's example color attribute value. In the example view, this color
     * is the font color.
     *
     * @param exampleColor The example color attribute value to use.
     */
    public void setExampleColor(int exampleColor) {
        mExampleColor = exampleColor;
//        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example dimension attribute value.
     *
     * @return The example dimension attribute value.
     */
    public float getExampleDimension() {
        return mExampleDimension;
    }

    /**
     * Sets the view's example dimension attribute value. In the example view, this dimension
     * is the font size.
     *
     * @param exampleDimension The example dimension attribute value to use.
     */
    public void setExampleDimension(float exampleDimension) {
        mExampleDimension = exampleDimension;
//        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example drawable attribute value.
     *
     * @return The example drawable attribute value.
     */
    public Drawable getExampleDrawable() {
        return mExampleDrawable;
    }

    /**
     * Sets the view's example drawable attribute value. In the example view, this drawable is
     * drawn above the text.
     *
     * @param exampleDrawable The example drawable attribute value to use.
     */
    public void setExampleDrawable(Drawable exampleDrawable) {
        mExampleDrawable = exampleDrawable;
    }
}
