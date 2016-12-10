package com.personal.oyl.newffms.consumption.domain;

public interface ConsumptionRepos {
	/**
	 * 创建新的消费
	 * 
	 * @param param 消费实体
	 * @param operator 操作人
	 */
	void add(Consumption bean, String operator);
	
	/**
	 * 根据消费标识查询消费
	 * 
	 * @param key 消费标识
	 * @return 消费实体
	 */
	Consumption consumptionOfId(ConsumptionKey key);
	
	/**
	 * 删除消费
	 * 
	 * @param key 待删除消费标识
	 */
	void remove(ConsumptionKey key);
}
