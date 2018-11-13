package com.hsbc.eep.data.migrate.analysis.feature;

public class TransationRequiredFeature {
	private String table;
	
	private Boolean hasHighReadWriteRatio;
	
	public TransationRequiredFeature(String table, Boolean hasHighReadWriteRatio) {
		super();
		this.table = table;
		this.hasHighReadWriteRatio = hasHighReadWriteRatio;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public Boolean getHasHighReadWriteRatio() {
		return hasHighReadWriteRatio;
	}

	public void setHasHighReadWriteRatio(Boolean hasHighReadWriteRatio) {
		this.hasHighReadWriteRatio = hasHighReadWriteRatio;
	}
	
	
}
