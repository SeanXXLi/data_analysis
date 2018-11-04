package com.hsbc.eep.data.migrate.analysis.table;

public class Table {

	private String name;
	
	private Integer tableLinkCounts;
	
	private boolean trasactionRequired;
	
	private boolean aggregationRequired;
	
	private boolean highIoRequired;
	
	private String recomandedStorage;

	
	
	public boolean isTrasactionRequired() {
		return trasactionRequired;
	}

	public void setTrasactionRequired(boolean trasactionRequired) {
		this.trasactionRequired = trasactionRequired;
	}

	public boolean isAggregationRequired() {
		return aggregationRequired;
	}

	public void setAggregationRequired(boolean aggregationRequired) {
		this.aggregationRequired = aggregationRequired;
	}

	public boolean isHighIoRequired() {
		return highIoRequired;
	}

	public void setHighIoRequired(boolean highIoRequired) {
		this.highIoRequired = highIoRequired;
	}

	public String getRecomandedStorage() {
		return recomandedStorage;
	}

	public void setRecomandedStorage(String recomandedStorage) {
		this.recomandedStorage = recomandedStorage;
	}

	public Integer getTableLinkCounts() {
		return tableLinkCounts;
	}

	public void setTableLinkCounts(Integer tableLinkCounts) {
		this.tableLinkCounts = tableLinkCounts;
	}

	public Table(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name ;
	}
	
	
	

}
