package com.personal.oyl.newffms.util;

import java.util.List;

public class JqGridJsonRlt <T extends BasePojo> {
    private int page;//request page
    private int records;// total records from this query
    private int total;//total pages from this query
    private List<T> rows;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRecords() {
        return records;
    }

    public void setRecords(int records) {
        this.records = records;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
