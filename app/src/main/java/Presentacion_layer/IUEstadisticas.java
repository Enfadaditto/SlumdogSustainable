package Presentacion_layer;

import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.slumdogsustainable.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class IUEstadisticas extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.estadisticas);
        BarChart barChart = findViewById(R.id.barChart);
        barChart.setDrawBarShadow(false);
        barChart.getDescription().setEnabled(false);
        barChart.setMaxVisibleValueCount(100);
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(false);
        barChart.getAxisLeft().setEnabled(false);
        barChart.getAxisRight().setEnabled(true);
        barChart.getXAxis().setEnabled(true);
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, 43));
        entries.add(new BarEntry(1, 82));
        entries.add(new BarEntry(2, 61));
        entries.add(new BarEntry(3, 12));
        entries.add(new BarEntry(4, 18));
        entries.add(new BarEntry(5, 36));
        entries.add(new BarEntry(6, 78));
        entries.add(new BarEntry(7, 52));
        entries.add(new BarEntry(8, 61));
        entries.add(new BarEntry(9, 36));
        entries.add(new BarEntry(10, 87));
        entries.add(new BarEntry(11, 95));
        entries.add(new BarEntry(12, 43));
        entries.add(new BarEntry(13, 56));
        entries.add(new BarEntry(14, 76));
        entries.add(new BarEntry(15, 61));
        entries.add(new BarEntry(16, 23));
        entries.add(new BarEntry(17, 93));



        BarDataSet dataSet = new BarDataSet(entries, "ODS");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(18f);

        BarData barData = new BarData(dataSet);
        barData.setBarWidth(0.9f);
        barData.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.valueOf((int) value);
            }
        });

        barChart.setData(barData);

        String[] xAxisLables = new String[]{"GENERAL","ODS 1","ODS 2", "ODS 3", "ODS 4", "ODS 5", "ODS 6", "ODS 7", "ODS 8", "ODS 9", "ODS 10", "ODS 11", "ODS 12", "ODS 13"
        , "ODS 14", "ODS 15", "ODS 16", "ODS 17"};

        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xAxisLables));
        barChart.getXAxis().setDrawGridLines(false);
        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getAxisRight().setDrawGridLines(false);
        barChart.getLegend().setEnabled(false);
        barChart.animateY(1000);
        barChart.setVisibleXRangeMaximum(6);
        barChart.setExtraTopOffset(5);
        barChart.getXAxis().setTextSize(14);
        barChart.getAxisRight().setEnabled(false);
        barChart.getBarData().setValueFormatter(new PercentValueFormatter());
        barChart.invalidate();
    }


    public class PercentValueFormatter extends ValueFormatter {
        private DecimalFormat mFormat;

        public PercentValueFormatter() {
            mFormat = new DecimalFormat("###,###,##0.0");
        }

        @Override
        public String getFormattedValue(float value) {
            return mFormat.format(value) + " %";
        }
    }
}
