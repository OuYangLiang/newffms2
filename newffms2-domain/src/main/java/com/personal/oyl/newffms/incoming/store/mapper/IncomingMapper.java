package com.personal.oyl.newffms.incoming.store.mapper;

import java.util.List;

import com.personal.oyl.newffms.incoming.domain.Incoming;
import com.personal.oyl.newffms.incoming.domain.IncomingKey;

public interface IncomingMapper {
	List<Incoming> select(Incoming param);
	
	int insert(Incoming param);
	
	int delete(IncomingKey key);
	
}
