package com.hsbc.eep.data.migrate.analysis.feature;

public class GroupByFeature {
	private String table;
	
	private Boolean hasGroupByFeature;

	public GroupByFeature(String table, Boolean hasGroupByFeature) {
		super();
		this.table = table;
		this.hasGroupByFeature = hasGroupByFeature;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public Boolean getHasGroupByFeature() {
		return hasGroupByFeature;
	}

	public void setHasGroupByFeature(Boolean hasGroupByFeature) {
		this.hasGroupByFeature = hasGroupByFeature;
	}

}
