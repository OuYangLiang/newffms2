package com.personal.oyl.newffms.util;

import java.util.List;

public class BootstrapTableJsonRlt {
    private int total;// total records from this query
    private List<?> rows;
    
    public BootstrapTableJsonRlt(int total, List<?> rows) {
        this.total = total;
        this.rows = rows;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }
}
