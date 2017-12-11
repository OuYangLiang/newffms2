package com.personal.oyl.newffms.report;

import java.math.BigDecimal;
import java.util.List;

public class HightChartSeries {
    private String id;
    private String name;
    private String drilldown;
    private BigDecimal y;
    private String type;
    private List<HightChartSeries> data;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDrilldown() {
        return drilldown;
    }

    public void setDrilldown(String drilldown) {
        this.drilldown = drilldown;
    }

    public BigDecimal getY() {
        return y;
    }

    public void setY(BigDecimal y) {
        this.y = y;
    }

    public List<HightChartSeries> getData() {
        return data;
    }

    public void setData(List<HightChartSeries> data) {
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
