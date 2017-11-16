package com.personal.oyl.newffms.util;

import java.sql.SQLException;
import java.util.List;

public interface PaginatingService <T extends BasePojo> {
    public int getCountOfSummary(T param) throws SQLException;
    
    public List<T> getListOfSummary(T param) throws SQLException;
}
