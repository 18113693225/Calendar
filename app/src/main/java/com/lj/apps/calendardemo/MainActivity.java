package com.lj.apps.calendardemo;

import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.youth.banner.Banner;

public class MainActivity extends AppCompatActivity {
    MaterialCalendarView mcv;
    Banner banner;
    String[] images = new String[]{"url"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mcv = (MaterialCalendarView) findViewById(R.id.calendarView);
        banner = (Banner) findViewById(R.id.banner);
        show();
        showBanner();
    }


    private void show() {
        mcv.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(2015, 4, 3))
                .setMaximumDate(CalendarDay.from(2017, 5, 12))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();
    }

    private void showBanner() {
        banner.setImages(images);
    }
}
