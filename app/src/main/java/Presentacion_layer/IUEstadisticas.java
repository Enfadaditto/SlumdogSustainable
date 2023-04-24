package Presentacion_layer;

import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.TextView;

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
import com.slumdogsustainable.MainActivity;
import com.slumdogsustainable.R;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import Domain_Layer.ODS_has_User;
import Persistence.ODS_URepository;


public class IUEstadisticas extends AppCompatActivity {
    BarChart barChart;

    TextView puntosText;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.estadisticas);
        barChart = findViewById(R.id.barChart);
        puntosText = (TextView) findViewById(R.id.puntosTextview);
        puntosText.setText(puntosText.getText() + Integer.toString(MainActivity.user.getPointsAchieved()));
        barChart.setDrawBarShadow(false);
        barChart.getDescription().setEnabled(false);
        barChart.setMaxVisibleValueCount(100);
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(false);
        barChart.getAxisLeft().setEnabled(false);
        barChart.getAxisRight().setEnabled(true);
        barChart.getXAxis().setEnabled(true);
        barChart.animateY(1000);
        try {
            addEntries();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void addEntries() throws InterruptedException {
        ArrayList<BarEntry> entries = new ArrayList<>();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                ODS_URepository ODS = new ODS_URepository(MainActivity.conexion);
                List<ODS_has_User.tuplaStats> aux = ODS.getPercentagebyUser(MainActivity.user);
                for(int i = 0; i < 18; i++) {
                    entries.add(new BarEntry(aux.get(i).idOds, (int) aux.get(i).percentage));

                }
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

                barChart.setVisibleXRangeMaximum(6);
                barChart.setExtraTopOffset(5);
                barChart.getXAxis().setTextSize(14);
                barChart.getAxisRight().setEnabled(false);
                barChart.getBarData().setValueFormatter(new PercentValueFormatter());
                barChart.getAxisLeft().setAxisMinimum(0f);
                barChart.getAxisLeft().setAxisMaximum(100f);
                barChart.notifyDataSetChanged();

                barChart.refreshDrawableState();
                barChart.invalidate();
            }
        });
        t.start();
        t.join();
        /*
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
        entries.add(new BarEntry(17, 93));*/
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
