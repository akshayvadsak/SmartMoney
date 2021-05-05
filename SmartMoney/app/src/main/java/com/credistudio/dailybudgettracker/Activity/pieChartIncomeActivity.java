package com.credistudio.dailybudgettracker.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.credistudio.dailybudgettracker.Classes.Category;
import com.credistudio.dailybudgettracker.Classes.Expense;
import com.credistudio.dailybudgettracker.Dagger.DaggerApplication;
import com.credistudio.dailybudgettracker.Dagger.DataManager;
import com.credistudio.dailybudgettracker.R;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class pieChartIncomeActivity extends AppCompatActivity implements OnChartValueSelectedListener {

    int catIdINCOME = 0;
    ArrayList<Category> catList;
    Double completeAmount = Double.valueOf(0.0d);
    @Inject
    DataManager dataManager;
    ArrayList<Expense> IncomeList;
    /* access modifiers changed from: private */
    public InterstitialAd mInterstitialAd;
    ArrayList<Category> newCatList;
    Toolbar toolbar_chart;
    int totalvalue = 100;
    private AdView mAdView;
    public void onPointerCaptureChanged(boolean z) {
    }

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart_income);

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        this.toolbar_chart = (Toolbar) findViewById(R.id.toolbar_chart);
        setSupportActionBar(this.toolbar_chart);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.toolbar_chart.getNavigationIcon().setTint(getResources().getColor(R.color.white));
        this.toolbar_chart.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                pieChartIncomeActivity.this.onBackPressed();

            }
        });
        ((DaggerApplication) getApplication()).getComponent().inject(this);
        PieChart pieChart = (PieChart) findViewById(R.id.piechart);
        pieChart.setUsePercentValues(true);
        this.catList = new ArrayList<>();
        this.newCatList = new ArrayList<>();
        try {
            this.catList = this.dataManager.getCategoryIncome();
            this.IncomeList = this.dataManager.getIncome();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (this.IncomeList != null) {
            for (int i = 0; i < this.catList.size(); i++) {
                this.catIdINCOME = ((Category) this.catList.get(i)).getCatId();
                String str = "";
                Double valueOf = Double.valueOf(0.0d);
                int i2 = 0;
                for (int i3 = 0; i3 < this.IncomeList.size(); i3++) {
                    if (this.catIdINCOME == Integer.parseInt(((Expense) this.IncomeList.get(i3)).getCategoryId())) {
                        i2++;
                        Double.valueOf(0.0d);
                        Double valueOf2 = Double.valueOf(((Expense) this.IncomeList.get(i3)).getAmount());
                        if (valueOf.doubleValue() != 0.0d) {
                            valueOf2 = Double.valueOf(valueOf.doubleValue() + valueOf2.doubleValue());
                        }
                        Double d = valueOf2;
                        str = ((Expense) this.IncomeList.get(i3)).getCategory();
                        valueOf = d;
                    }
                }
                if (i2 > 0) {
                    Category category = new Category();
                    category.setChartValue(i2);
                    category.setChartAmount(valueOf);
                    category.setCatName(str);
                    this.newCatList.add(category);
                    StringBuilder sb = new StringBuilder();
                    sb.append(".............AA...........");
                    sb.append(valueOf);
                    sb.append("..........VV.......");
                    sb.append(i2);
                    sb.append(".....................=======================================indValue=====================================");
                    Log.e("PieChartLog", sb.toString());
                }
            }
        }
        for (int i4 = 0; i4 < this.newCatList.size(); i4++) {
            Double chartAmount = ((Category) this.newCatList.get(i4)).getChartAmount();
            if (this.completeAmount.doubleValue() == 0.0d) {
                this.completeAmount = chartAmount;
            } else {
                this.completeAmount = Double.valueOf(this.completeAmount.doubleValue() + chartAmount.doubleValue());
            }
        }
        ArrayList arrayList = new ArrayList();
        PieDataSet pieDataSet = new PieDataSet(arrayList, "");
        pieDataSet.setSliceSpace(2.0f);
        ArrayList arrayList2 = new ArrayList();
        if (this.IncomeList != null) {
            for (int i5 = 0; i5 < this.newCatList.size(); i5++) {
                arrayList.add(new Entry(Float.parseFloat(String.valueOf(amountCalculator(((Category) this.newCatList.get(i5)).getChartAmount()))), i5));
            }
            for (int i6 = 0; i6 < this.newCatList.size(); i6++) {
                arrayList2.add(((Category) this.newCatList.get(i6)).getCatName());
            }
        } else {
            arrayList.add(new Entry(100.0f, 0));
            arrayList2.add("No Data");
        }
        PieData pieData = new PieData((List<String>) arrayList2, (IPieDataSet) pieDataSet);
        pieData.setValueFormatter(new PercentFormatter());
        pieChart.setData(pieData);
        pieChart.setDescription("");
        pieChart.getLegend().setEnabled(false);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setTransparentCircleRadius(25.0f);
        pieChart.setHoleRadius(25.0f);
        pieDataSet.setColors(ColorTemplate.PASTEL_COLORS);
        pieData.setValueTextSize(13.0f);
        pieData.setValueTextColor(-12303292);
        pieChart.setOnChartValueSelectedListener(this);
        pieChart.animateXY(1400, 1400);
    }
    public Double amountCalculator(Double d) {
        return d.doubleValue() != 0.0d ? Double.valueOf((d.doubleValue() / this.completeAmount.doubleValue()) * 100.0d) : Double.valueOf(0.0d);
    }

    public void onValueSelected(Entry entry, int i, Highlight highlight) {
        if (entry != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("Value: ");
            sb.append(entry.getVal());
            sb.append(", xIndex: ");
            sb.append(entry.getXIndex());
            sb.append(", DataSet index: ");
            sb.append(i);
            Log.i("VAL SELECTED", sb.toString());
        }
    }
    public void onNothingSelected() {
        Log.i("PieChart", "nothing selected");
    }

    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, HomeActivity.class));

    }
}
