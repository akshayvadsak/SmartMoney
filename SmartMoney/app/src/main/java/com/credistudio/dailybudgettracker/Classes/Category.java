package com.credistudio.dailybudgettracker.Classes;

public class Category {
    private int catId = 0;
    private String catName = "";
    Double chartAmount = Double.valueOf(0.0d);
    int chartValue = 0;

    public int getCatId() {
        return this.catId;
    }

    public void setCatId(int i) {
        this.catId = i;
    }

    public String getCatName() {
        return this.catName;
    }

    public void setCatName(String str) {
        this.catName = str;
    }

    public int getChartValue() {
        return this.chartValue;
    }

    public void setChartValue(int i) {
        this.chartValue = i;
    }

    public Double getChartAmount() {
        return this.chartAmount;
    }

    public void setChartAmount(Double d) {
        this.chartAmount = d;
    }
}
