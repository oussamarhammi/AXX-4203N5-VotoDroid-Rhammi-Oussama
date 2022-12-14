package org.sabourin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;


import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import org.sabourin.bd.BD;
import org.sabourin.databinding.ResultatBinding;
import org.sabourin.databinding.VoteBinding;
import org.sabourin.modele.VDQuestion;
import org.sabourin.modele.VDVote;
import org.sabourin.service.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Resultat extends AppCompatActivity {
    private ResultatBinding binding;
    BarChart chart;
    private Service service;
    private BD maBD;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            maBD =  Room.databaseBuilder(getApplicationContext(), BD.class, "BDQuestions")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
            service = new Service(maBD);


            binding = ResultatBinding.inflate(getLayoutInflater());
            View view = binding.getRoot();
            setContentView(view);


            chart = findViewById(R.id.chart);
            binding.textView6.setText(getIntent().getStringExtra("texte1"));
            long monId = getIntent().getLongExtra("idtexte1",-1);
            VDQuestion questionCourante =new VDQuestion();
            questionCourante.idQuestion = monId;
                String s= String.valueOf(service.moyenneVotes(questionCourante));
            binding.textView.setText(s);
            setData(service.distributionVotes(questionCourante));


            /* Settings for the graph - Change me if you want*/
            chart.setMaxVisibleValueCount(6);
            chart.setPinchZoom(false);
            chart.setDrawGridBackground(false);

            XAxis xAxis = chart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setDrawGridLines(false);
            xAxis.setGranularity(1); // only intervals of 1 day
            xAxis.setLabelCount(7);
            xAxis.setValueFormatter(new DefaultAxisValueFormatter(0));

            YAxis leftAxis = chart.getAxisLeft();
            leftAxis.setLabelCount(8, false);
            leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
            leftAxis.setSpaceTop(15f);
            leftAxis.setGranularity(1);
            leftAxis.setAxisMinimum(0f);
            leftAxis.setValueFormatter(new DefaultAxisValueFormatter(0));
            chart.getDescription().setEnabled(false);
            chart.getAxisRight().setEnabled(false);
            binding.button3.setOnClickListener(view3 -> {
                {
                    Intent i = new Intent(Resultat.this,MainActivity.class);
                    startActivity(i);

                }
            });


            /* Data and function call to bind the data to the graph */

        }

    /**
     * methode fournie par le prof pour s??parer
     * - la configuration dans le onCreate
     * - l'ajout des donn??es dans le setDate
     * @param datas
     */
    private void setData(Map<Integer, Integer> datas) {
        List<BarEntry> values = new ArrayList<>();
        /* Every bar entry is a bar in the graphic */
        for (Map.Entry<Integer, Integer> key : datas.entrySet()){
            values.add(new BarEntry(key.getKey() , key.getValue()));
        }

        BarDataSet set1;
        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(values, "Notes");

            set1.setDrawIcons(false);

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setBarWidth(.9f);
            chart.setData(data);
        }
    }



        }

