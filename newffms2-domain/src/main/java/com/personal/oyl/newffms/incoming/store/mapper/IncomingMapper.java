package com.personal.oyl.newffms.incoming.store.mapper;

import java.util.List;
import java.util.Map;

import com.personal.oyl.newffms.incoming.domain.Incoming;
import com.personal.oyl.newffms.incoming.domain.IncomingKey;

public interface IncomingMapper {
    List<Incoming> select(Incoming param);

    int insert(Incoming param);

    int delete(IncomingKey key);

    int updateStatus(Map<String, Object> param);

    int updateInfo(Map<String, Object> param);

    int getCountOfSummary(Map<String, Object> param);

    List<Incoming> getListOfSummary(Map<String, Object> param);
}
