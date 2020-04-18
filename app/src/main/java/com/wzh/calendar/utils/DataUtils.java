package com.wzh.calendar.utils;

import com.wzh.calendar.bean.DateModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DataUtils {
    /**
     * 获取当前日期一月的日期
     *
     * @param date 日期
     * @return 当月包含的日期列表
     */
    public static List<DateModel> getMonth(String date) {
        List<DateModel> result = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(new SimpleDateFormat("yyyy-MM", Locale.getDefault()).parse(date));
        } catch (Exception e) {
            e.printStackTrace();
        }
        int max = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        for (int i = 1; i <= max; i++) {
            DateModel entity = new DateModel();
            entity.date = getValue(cal.get(Calendar.YEAR)) + "-" + getValue(cal.get(Calendar.MONTH) + 1)
                    + "-" + getValue(cal.get(Calendar.DATE));
            entity.million = cal.getTimeInMillis();
            entity.weekNum = cal.get(Calendar.DAY_OF_WEEK);
            entity.day = getValue(cal.get(Calendar.DATE));
            entity.weekName = getWeekName(entity.weekNum);
            entity.isToday = isToday(entity.date);
            cal.add(Calendar.DATE, 1);
            result.add(entity);
        }

        //为了用空的值填补第一个之前的日期
        //先获取在本周内是周几
        int weekNum = result.get(0).weekNum - 1;
        for (int j = 0; j < weekNum; j++) {
            DateModel entity = new DateModel();
            result.add(0, entity);
        }

        return result;
    }

    /**
     * 根据美式周末到周一 返回
     *
     * @param weekNum 周几
     * @return 星期
     */
    private static String getWeekName(int weekNum) {
        String name = "";
        switch (weekNum) {
            case 1:
                name = "星期日";
                break;
            case 2:
                name = "星期一";
                break;
            case 3:
                name = "星期二";
                break;
            case 4:
                name = "星期三";
                break;
            case 5:
                name = "星期四";
                break;
            case 6:
                name = "星期五";
                break;
            case 7:
                name = "星期六";
                break;
            default:
                break;
        }
        return name;
    }

    /**
     * 是否是今天
     *
     * @param date 日期
     * @return true 今天
     */
    private static boolean isToday(String date) {
        try {
            SimpleDateFormat dateFormat = mDateFormat.get();
            Date time = dateFormat.parse(date);
            String nowDate = dateFormat.format(new Date());
            String timeDate = dateFormat.format(time);
            if (nowDate.equals(timeDate)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 个位数补0操作
     *
     * @param num 入参
     * @return 出参
     */
    private static String getValue(int num) {
        return String.valueOf(num > 9 ? num : ("0" + num));
    }


    private static ThreadLocal<SimpleDateFormat> mDateFormat = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        }
    };

    /**
     * 获取系统当前日期
     */
    public static String getCurrDate(String format) {
        try {
            return new SimpleDateFormat(format, Locale.getDefault()).format(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 格式化日期
     */
    public static String formatDate(String date, String format) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.getDefault());
            Date curDate = formatter.parse(date);
            return formatter.format(curDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取前/后 几个月的一个日期  切换月的时候用
     *
     * @param currentData 当前日期
     * @param monthNum    变化的月数
     * @return 日期
     */
    public static String getSomeMonthDay(String currentData, int monthNum) {
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(new SimpleDateFormat("yyyy-MM", Locale.getDefault()).parse(currentData));
        } catch (Exception e) {
            e.printStackTrace();
        }
        c.set(Calendar.MONTH, c.get(Calendar.MONTH) + monthNum);
        Date day = c.getTime();
        return mDateFormat.get().format(day);
    }
}
