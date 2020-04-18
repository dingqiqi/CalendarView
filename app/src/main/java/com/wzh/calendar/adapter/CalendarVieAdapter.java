package com.wzh.calendar.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wzh.calendar.R;
import com.wzh.calendar.bean.DateModel;
import com.wzh.calendar.view.CalendarView;

import java.util.List;

public class CalendarVieAdapter extends RecyclerView.Adapter<CalendarVieAdapter.Holder> {

    private Context mContext;
    private List<DateModel> mList;

    private String mSelectDate;

    private CalendarView.ItemClickListener itemClickListener;

    public CalendarVieAdapter(Context mContext, List<DateModel> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    public void setSelectDate(String mSelectDate) {
        if (TextUtils.isEmpty(mSelectDate)) {
            return;
        }
        this.mSelectDate = mSelectDate;
    }

    public void setItemClickListener(CalendarView.ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Holder(LayoutInflater.from(mContext).inflate(R.layout.item_month_date_layout, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int i) {
        final DateModel info = mList.get(i);

        if (TextUtils.isEmpty(info.date)) {
            holder.mTvDate.setText("");
            holder.mIvMark.setVisibility(View.INVISIBLE);
            holder.mTvDate.setBackgroundColor(Color.TRANSPARENT);
        } else {
            holder.mTvDate.setText(info.day);
            holder.mIvMark.setVisibility(View.VISIBLE);

            // 选中
            if (info.date.equals(mSelectDate)) {
                holder.mTvDate.setBackgroundResource(R.drawable.bg_calendar_circle_shape);
                holder.mTvDate.setTextColor(ContextCompat.getColor(mContext, R.color.color_ffffff));
            } else {
                if (info.isToday) {
                    holder.mTvDate.setBackgroundColor(Color.TRANSPARENT);
                    // 红色
                    holder.mTvDate.setTextColor(Color.parseColor("#E83434"));
                } else {
                    holder.mTvDate.setBackgroundColor(Color.TRANSPARENT);
                    // 灰色
                    holder.mTvDate.setTextColor(Color.parseColor("#B0B0B0"));
                }
            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(view, info, i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    static class Holder extends RecyclerView.ViewHolder {

        TextView mTvDate;
        ImageView mIvMark;

        Holder(@NonNull View itemView) {
            super(itemView);
            mTvDate = itemView.findViewById(R.id.tv_date);
            mIvMark = itemView.findViewById(R.id.iv_mark);
        }
    }
}
