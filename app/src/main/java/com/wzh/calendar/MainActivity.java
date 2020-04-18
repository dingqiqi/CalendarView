package com.wzh.calendar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.TextView;

import com.wzh.calendar.bean.DateModel;
import com.wzh.calendar.view.CalendarView;

/**
 * author：Administrator on 2017/4/10 16:03
 * description:文件说明
 * version:版本
 */
public class MainActivity extends FragmentActivity {
    private TextView info;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        info = findViewById(R.id.info);
        CalendarView dataView = findViewById(R.id.calendarView);
        dataView.setSelectDate("");

        dataView.setOnSelectListener(new CalendarView.OnSelectListener() {
            @Override
            public void onSelected(DateModel date) {
                info.setText("日期：" + date.date + "\n" +
                        "周几：" + date.weekName + "\n" +
                        "今日：" + date.isToday + "\n" +
                        "时间戳：" + date.million + "\n");
                Log.e("wenzhiao--------------", date.toString());
            }
        });

    }

}
