package com.top.bryon.lr.ui.widget;

import android.content.Context;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.top.bryon.lr.R;

//TODO 还有问题
public class ExpandableTextView extends LinearLayout {

    private TextView contentView;
    private TextView contentExpandView;
    private int CUSTOM_MAX_TEXT_VIEW_LINE = 5;
    private boolean isOpen = false;
    private int DEFAULT_MAX_TEXT_VIEW_LINE = 1000;
    private boolean isInitialized = true;

    public ExpandableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public void initView() {
        contentView = new TextView(getContext());
        contentExpandView = new TextView(getContext());
        contentExpandView.setText(R.string.expand_open);
        contentExpandView.setVisibility(View.GONE);
        contentExpandView.setTextColor(getResources().getColor(R.color.notice_text_expand_color));
        contentExpandView.setTextSize(getResources().getDimension(R.dimen.notice_text_expand_btn_size));
        contentView.setEllipsize(TruncateAt.END);

        contentExpandView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (isOpen) {
                    contentExpandView.setText(R.string.expand_open);
                    isOpen = false;
                } else {
                    contentExpandView.setText(R.string.expand_close);
                    isOpen = true;
                }
            }
        });
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.RIGHT;

        addView(contentView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        addView(contentExpandView, lp);
    }

    public void setText(String data) {
        initData();
        if (contentView != null) {
            contentView.setText(data);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (isInitialized) {
            if (contentView.getLineCount() > CUSTOM_MAX_TEXT_VIEW_LINE)
                contentExpandView.setVisibility(View.VISIBLE);
            else
                contentExpandView.setVisibility(View.GONE);
            isInitialized = false;
        }
        if (contentExpandView.getVisibility() == View.VISIBLE) {
            if (isOpen) {
                contentView.setMaxLines(DEFAULT_MAX_TEXT_VIEW_LINE);
            } else {
                contentView.setMaxLines(CUSTOM_MAX_TEXT_VIEW_LINE);
            }
        }
    }

    public void initData() {
        isInitialized = true;
        isOpen = false;
        contentView.setMaxLines(DEFAULT_MAX_TEXT_VIEW_LINE);
        contentExpandView.setText(R.string.expand_open);
    }


    public void setTextExpendColor(int colorInt) {
        contentExpandView.setTextColor(colorInt);
    }
}
