package com.personal.oyl.newffms.common;

import java.util.Map;

public class PaginationParameter {
    private int requestPage;
    private int sizePerPage;
    private String sortField;
    private String sortDir;

    public PaginationParameter(int requestPage, int sizePerPage) {
        super();
        this.requestPage = requestPage;
        this.sizePerPage = sizePerPage;
    }

    public PaginationParameter(int requestPage, int sizePerPage, String sortField, String sortDir) {
        super();
        this.requestPage = requestPage;
        this.sizePerPage = sizePerPage;
        this.sortField = sortField;
        this.sortDir = sortDir;
    }

    public int getRequestPage() {
        return requestPage;
    }

    public void setRequestPage(int requestPage) {
        this.requestPage = requestPage;
    }

    public int getSizePerPage() {
        return sizePerPage;
    }

    public void setSizePerPage(int sizePerPage) {
        this.sizePerPage = sizePerPage;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getSortDir() {
        return sortDir;
    }

    public void setSortDir(String sortDir) {
        this.sortDir = sortDir;
    }

    public void fillMap(Map<String, Object> target) {
        target.put("start", (requestPage - 1) * sizePerPage);
        target.put("sizePerPage", sizePerPage);
        target.put("sortField", sortField);
        target.put("sortDir", sortDir);
    }
}
