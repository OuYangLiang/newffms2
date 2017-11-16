package com.personal.oyl.newffms.util;

import java.util.List;

public class BootstrapTableJsonRlt<T extends BasePojo> {
	private int total;// total records from this query
	private List<T> rows;

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
