package com.wzh.calendar.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wzh.calendar.adapter.CalendarVieAdapter;
import com.wzh.calendar.bean.DateModel;
import com.wzh.calendar.R;
import com.wzh.calendar.utils.DataUtils;

import java.util.ArrayList;
import java.util.List;

public class CalendarView extends LinearLayout implements View.OnClickListener {
    private ImageView mIvPreMonth;
    private ImageView mIvNextMonth;
    private TextView mTvCutMonth;
    private TextView mTvPlan;
    private String mSelectDate;

    private ArrayList<DateModel> mList = new ArrayList<>();
    private String mDataFormat_YM = "yyyy-MM";
    private String mDataFormat_YMD = "yyyy-MM-dd";

    private CalendarVieAdapter mAdapter;

    public CalendarView(Context context) {
        this(context, null);
    }

    public CalendarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(LinearLayout.VERTICAL);
        LayoutInflater.from(getContext()).inflate(R.layout.view_calendar_layout, this);

        mIvPreMonth = findViewById(R.id.tv_previous_month);
        mIvPreMonth.setOnClickListener(this);
        mIvNextMonth = findViewById(R.id.tv_next_month);
        mIvNextMonth.setOnClickListener(this);

        mTvPlan = findViewById(R.id.tv_plan);
        mTvPlan.setOnClickListener(this);

        mTvCutMonth = findViewById(R.id.now_month);

        RecyclerView mRecyclerView = findViewById(R.id.rv_date);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 7, GridLayoutManager.VERTICAL, false));
        mAdapter = new CalendarVieAdapter(getContext(), mList);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setItemClickListener(new ItemClickListener<DateModel>() {
            @Override
            public void onItemClick(View view, DateModel data, int position) {
                if (data != null) {
                    mSelectDate = data.date;
                    mTvCutMonth.setText(DataUtils.formatDate(mSelectDate, mDataFormat_YM));
                    mAdapter.setSelectDate(mSelectDate);
                    mAdapter.notifyDataSetChanged();
                }

                if (onSelectListener != null) {
                    onSelectListener.onSelected(data);
                }
            }
        });
    }

    public void setSelectDate(String selectDate) {
        if (!TextUtils.isEmpty(mSelectDate) && mSelectDate.equals(selectDate)) {
            return;
        }
        if (TextUtils.isEmpty(selectDate)) {
            this.mSelectDate = DataUtils.getCurrDate(mDataFormat_YMD);
        } else {
            this.mSelectDate = selectDate;
        }

        List<DateModel> list = DataUtils.getMonth(mSelectDate);
        if (list == null || list.isEmpty()) {
            return;
        }
        mList.clear();
        mList.addAll(list);
        mTvCutMonth.setText(DataUtils.formatDate(mSelectDate, mDataFormat_YM));
        mAdapter.setSelectDate(mSelectDate);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == mIvPreMonth.getId()) {
            setSelectDate(DataUtils.getSomeMonthDay(mSelectDate, -1));
        } else if (id == mIvNextMonth.getId()) {
            setSelectDate(DataUtils.getSomeMonthDay(mSelectDate, +1));
        } else if (id == mTvPlan.getId()) {
        }
    }

    /**
     * 选中日期之后的回调
     */
    private OnSelectListener onSelectListener;

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        this.onSelectListener = onSelectListener;
    }

    public interface OnSelectListener {
        void onSelected(DateModel date);
    }

    public interface ItemClickListener<T> {
        void onItemClick(View view, T data, int position);
    }
}
