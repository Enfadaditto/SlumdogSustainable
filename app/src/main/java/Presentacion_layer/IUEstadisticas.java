package Presentacion_layer;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
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
import Persistence.UserRepository;


public class IUEstadisticas extends AppCompatActivity {
    BarChart barChart;

    TextView puntosText;

    RelativeLayout chartLayout;

    Spinner selector;

    TextView tiempoText;

    TextView totalGames;

    TextView gamesLost;

    TextView gamesWon;

    TextView timeSpent;

    PieChart pieChart;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.estadisticas);

        chartLayout = (RelativeLayout) findViewById(R.id.chartsLayout);
        selector = (Spinner) findViewById(R.id.selectorGrafica);
        tiempoText = findViewById(R.id.tiempoTextview);
        puntosText = (TextView) findViewById(R.id.puntosTextview);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        puntosText.setText(puntosText.getText() + Integer.toString(MainActivity.user.getPointsAchieved()));
        tiempoText.setText(tiempoText.getText() + String.format("%.2f", ((float)MainActivity.user.getTimeSpent() / 60)) + "h");

        selector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Your code here
                if(position == 0) {
                    chartLayout.removeView(pieChart);
                    chartLayout.removeView(gamesWon);
                    createDiagramChart();}
                if(position == 1) {
                    chartLayout.removeView(barChart);
                    chartLayout.removeView(gamesWon);
                    createPieChart();
                }

                if(position == 2) {
                    chartLayout.removeView(barChart);
                    chartLayout.removeView(pieChart);
                    createGameStats();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    public void createGameStats() {
        gamesWon = new TextView(this.getApplicationContext());
        gamesLost = new TextView(this.getApplicationContext());
        totalGames = new TextView(this.getApplicationContext());
        timeSpent = new TextView(this.getApplicationContext());

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );
        gamesWon.setLayoutParams(layoutParams);
        gamesLost.setLayoutParams(layoutParams);
        totalGames.setLayoutParams(layoutParams);
        timeSpent.setLayoutParams(layoutParams);
        gamesWon.setText("Juegos ganados: " + MainActivity.user.getGamesAchieved() + "\n" + "\n" +
                "Juegos perdidos: " + MainActivity.user.getGamesFailed() + "\n" + "\n" +
                "Juegos Totales: " + (MainActivity.user.getGamesFailed() + MainActivity.user.getGamesAchieved()) + "\n" + "\n" +
                "Tiempo total: " + MainActivity.user.getTimeSpent() + " minutos"


        );
        chartLayout.addView(gamesWon);

    }

    public void createPieChart() {
        pieChart = new PieChart(this.getApplicationContext());
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );
        pieChart.setLayoutParams(layoutParams);
        chartLayout.addView(pieChart);
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.setEntryLabelTextSize(12f);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setDrawEntryLabels(true);
        pieChart.setRotationAngle(0);
        pieChart.setRotationEnabled(true);
        pieChart.animateY(1000);
        try {
            addEntriesPie();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public void createDiagramChart() {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );
        barChart = new BarChart(this.getApplicationContext());
        barChart.setLayoutParams(layoutParams);
        chartLayout.addView(barChart);
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
            addEntriesBar();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
    public void addEntriesPie() throws InterruptedException {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<PieEntry> entries = new ArrayList<>();
                ODS_URepository ODS = new ODS_URepository(MainActivity.conexion);
                List <ODS_has_User> aux = ODS.getAllhits(MainActivity.user);
                entries.add(new PieEntry(aux.get(0).getRightGuesses(), "General"));
                for(int i = 1; i < 18; i++) {
                    entries.add(new PieEntry(aux.get(i).getRightGuesses(), "ODS " + i));
                }
                PieDataSet dataSet = new PieDataSet(entries, "ODS");
                dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                dataSet.setValueTextColor(Color.BLACK);
                dataSet.setValueTextSize(12f);
                PieData p = new PieData(dataSet);
                pieChart.getLegend().setEnabled(false);
                pieChart.setData(p);
                barChart.refreshDrawableState();
                barChart.invalidate();
            }
        });
        t.start();
        t.join();
    }
    public void addEntriesBar() throws InterruptedException {
        ArrayList<BarEntry> entries = new ArrayList<>();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                ODS_URepository ODS = new ODS_URepository(MainActivity.conexion);
                List<ODS_has_User.tuplaStats> aux = ODS.getPercentagebyUser(MainActivity.user);
                for(int i = 0; i < 18; i++) {
                    entries.add(new BarEntry(aux.get(i).idOds, aux.get(i).percentage));
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
