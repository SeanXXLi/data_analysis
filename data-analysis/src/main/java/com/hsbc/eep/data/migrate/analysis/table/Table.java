package com.hsbc.eep.data.migrate.analysis.table;

public class Table {

	private String name;
	
	private Integer tableLinkCounts = 0;
	
	private boolean trasactionRequired = false;
	
	private boolean aggregationRequired = false;
	
	private boolean highIoRequired = false;
	
	private String recomandedStorage = "CloudSQL";

	
	
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Table other = (Table) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
