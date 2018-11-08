package com.hsbc.eep.data.migrate.analysis.feature;

public class HighIoFeature {
	private String table;
	
	private Boolean hasHighIoFeature;

	public HighIoFeature(String table, Boolean hasHighIoFeature) {
		super();
		this.table = table;
		this.hasHighIoFeature = hasHighIoFeature;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public Boolean getHasHighIoFeature() {
		return hasHighIoFeature;
	}

	public void setHasHighIoFeature(Boolean hasHighIoFeature) {
		this.hasHighIoFeature = hasHighIoFeature;
	}
	
	
}
