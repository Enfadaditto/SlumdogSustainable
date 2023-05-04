package Presentacion_layer;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.slumdogsustainable.MainActivity;
import com.slumdogsustainable.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import Domain_Layer.ODS_has_User;
import Persistence.ODS_URepository;
import Persistence.SingletonConnection;


public class IUEstadisticas extends AppCompatActivity {
    BarChart barChart;

    TextView puntosText;

    RelativeLayout chartLayout;

    Spinner selector;

    TextView tiempoText;

    TextView totalGames;

    TextView retosAcertados;

    TextView retosFallados;

    TextView tiempoPromedio;

    TextView gamesLost;

    TextView gamesAbandoned;

    TextView gamesWon;

    TextView timeSpent;

    PieChart pieChart;

    ScrollView Scroll;

    List<Integer> retosStats;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.estadisticas);

        chartLayout = findViewById(R.id.chartsLayout);
        selector = findViewById(R.id.selectorGrafica);
        tiempoText = findViewById(R.id.tiempoTextview);
        puntosText = findViewById(R.id.puntosTextview);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        puntosText.setText(puntosText.getText() + Integer.toString(MainActivity.user.getPointsAchieved()));
        tiempoText.setText(tiempoText.getText() + String.format("%.2f", (MainActivity.user.getTimeSpent() / 60)) + "h");

        selector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Your code here
                if (position == 0) {
                    chartLayout.removeView(pieChart);
                    chartLayout.removeView(gamesWon);
                    chartLayout.removeView(totalGames);
                    chartLayout.removeView(gamesLost);
                    chartLayout.removeView(timeSpent);
                    chartLayout.removeView(tiempoPromedio);
                    chartLayout.removeView(retosFallados);
                    chartLayout.removeView(retosAcertados);
                    chartLayout.removeView(Scroll);
                    createDiagramChart();
                }
                if (position == 1) {
                    chartLayout.removeView(barChart);
                    chartLayout.removeView(gamesWon);
                    chartLayout.removeView(totalGames);
                    chartLayout.removeView(gamesLost);
                    chartLayout.removeView(timeSpent);
                    chartLayout.removeView(tiempoPromedio);
                    chartLayout.removeView(retosFallados);
                    chartLayout.removeView(retosAcertados);
                    chartLayout.removeView(Scroll);
                    createPieChart();
                }

                if (position == 2) {
                    chartLayout.removeView(barChart);
                    chartLayout.removeView(pieChart);
                    chartLayout.removeView(Scroll);
                    createGameStats();
                }
                if (position == 3) {
                    chartLayout.removeView(barChart);
                    chartLayout.removeView(pieChart);
                    chartLayout.removeView(gamesWon);
                    chartLayout.removeView(totalGames);
                    chartLayout.removeView(gamesLost);
                    chartLayout.removeView(timeSpent);
                    chartLayout.removeView(tiempoPromedio);
                    chartLayout.removeView(retosFallados);
                    chartLayout.removeView(retosAcertados);
                    createODShelp();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    public void addwithlayout() {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        layoutParams.setMargins(36, 50, 0, 0);
        RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams1.addRule(RelativeLayout.BELOW, gamesWon.getId());
        layoutParams1.setMargins(36, 100, 0, 0);
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams2.addRule(RelativeLayout.BELOW, gamesAbandoned.getId());
        layoutParams2.setMargins(36, 100, 0, 0);
        RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams3.addRule(RelativeLayout.BELOW, totalGames.getId());
        layoutParams3.setMargins(36, 100, 0, 0);
        RelativeLayout.LayoutParams layoutParams4 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams4.addRule(RelativeLayout.BELOW, timeSpent.getId());
        layoutParams4.setMargins(36, 100, 0, 0);
        RelativeLayout.LayoutParams layoutParams5 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams5.addRule(RelativeLayout.BELOW, tiempoPromedio.getId());
        layoutParams5.setMargins(36, 100, 0, 0);
        RelativeLayout.LayoutParams layoutParams6 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams6.addRule(RelativeLayout.BELOW, retosAcertados.getId());
        layoutParams6.setMargins(36, 100, 0, 0);
        RelativeLayout.LayoutParams layoutParams7 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams7.addRule(RelativeLayout.BELOW, gamesLost.getId());
        layoutParams7.setMargins(36, 100, 0, 0);

        chartLayout.addView(gamesWon, layoutParams);
        chartLayout.addView(gamesLost, layoutParams1);
        chartLayout.addView(gamesAbandoned, layoutParams7);
        chartLayout.addView(totalGames, layoutParams2);
        chartLayout.addView(timeSpent, layoutParams3);
        chartLayout.addView(tiempoPromedio, layoutParams4);
        chartLayout.addView(retosAcertados, layoutParams5);
        chartLayout.addView(retosFallados, layoutParams6);
    }
    public void createGameStats() {
        gamesWon = new TextView(this);
        gamesLost = new TextView(this);
        gamesAbandoned = new TextView(this);
        totalGames = new TextView(this);
        timeSpent = new TextView(this);
        tiempoPromedio = new TextView(this);
        retosAcertados = new TextView(this);
        retosFallados = new TextView(this);

        gamesWon.setId(View.generateViewId());
        gamesLost.setId(View.generateViewId());
        gamesAbandoned.setId(View.generateViewId());
        totalGames.setId(View.generateViewId());
        timeSpent.setId(View.generateViewId());
        tiempoPromedio.setId(View.generateViewId());
        retosAcertados.setId(View.generateViewId());
        retosFallados.setId(View.generateViewId());


        try {
            getStatsRetos();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        gamesWon.setText("Partidas ganadas: " + MainActivity.user.getGamesAchieved());
        gamesLost.setText("Partidas perdidas: " + MainActivity.user.getGamesFailed());
        gamesAbandoned.setText("Partidas abandonadas: " + MainActivity.user.getGamesAbandoned());
        totalGames.setText("Partidas totales: " + (MainActivity.user.getGamesFailed() + MainActivity.user.getGamesAchieved() + MainActivity.user.getGamesAbandoned()));
        timeSpent.setText("Tiempo total: " + MainActivity.user.getTimeSpent() + " minutos");
        float tiempoProm = MainActivity.user.getTimeSpent() / (MainActivity.user.getGamesFailed() + MainActivity.user.getGamesAchieved());
        tiempoPromedio.setText("Tiempo promedio: " + String.format("%.02f", tiempoProm) + " minutos");
        retosAcertados.setText("Retos acertados: " + retosStats.get(0));
        retosFallados.setText("Retos fallados: " + retosStats.get(1));

        gamesWon.setTextSize(16);
        gamesLost.setTextSize(16);
        gamesAbandoned.setTextSize(16);
        totalGames.setTextSize(16);
        timeSpent.setTextSize(16);
        tiempoPromedio.setTextSize(16);
        retosAcertados.setTextSize(16);
        retosFallados.setTextSize(16);

        addwithlayout();

    }

    public void getStatsRetos() throws InterruptedException {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<PieEntry> entries = new ArrayList<>();
                ODS_URepository ODS = new ODS_URepository(SingletonConnection.getSingletonInstance());
                retosStats = ODS.getNumberHitsandFails();
            }
        });
        t.start();
        t.join();
    }

    public void createODShelp() {
        Scroll = new ScrollView(this);
        Scroll.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        LinearLayout dropdownView = new LinearLayout(this);
        LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dropdownView.setOrientation(LinearLayout.VERTICAL);
        dropdownView.setLayoutParams(linearParams);
        LinearLayout.LayoutParams params1;

        for (int j = 1; j < 18; j++) {
            params1 = new LinearLayout.LayoutParams(700, 700);
            params1.gravity = Gravity.CENTER_HORIZONTAL;
            ImageView v1 = new ImageView(this);
            int imagenId = getResources().getIdentifier("ods_" + j, "drawable", getPackageName());
            v1.setId(j);
            v1.setLayoutParams(params1);
            v1.setImageResource(imagenId);
            v1.setOnClickListener(this::onImageClick);
            dropdownView.addView(v1);
        }
        Scroll.addView(dropdownView);
        chartLayout.addView(Scroll);
    }

    public void onImageClick(View v) {
        Uri uri = Uri.parse(getResources().getStringArray(R.array.linkODS)[v.getId()]);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
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
                ODS_URepository ODS = new ODS_URepository(SingletonConnection.getSingletonInstance());
                List<ODS_has_User> aux = ODS.getAllODS_user(MainActivity.user);
                entries.add(new PieEntry(aux.get(0).getRightGuesses(), "General"));
                for (int i = 1; i < 18; i++) {
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
                ODS_URepository ODS = new ODS_URepository(SingletonConnection.getSingletonInstance());
                List<ODS_has_User.tuplaStats> aux = ODS.getPercentagebyUser(MainActivity.user);
                for (int i = 0; i < 18; i++) {
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

                String[] xAxisLables = new String[]{"GENERAL", "ODS 1", "ODS 2", "ODS 3", "ODS 4", "ODS 5", "ODS 6", "ODS 7", "ODS 8", "ODS 9", "ODS 10", "ODS 11", "ODS 12", "ODS 13"
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
        private final DecimalFormat mFormat;

        public PercentValueFormatter() {
            mFormat = new DecimalFormat("###,###,##0.0");
        }

        @Override
        public String getFormattedValue(float value) {
            return mFormat.format(value) + " %";
        }
    }
}
