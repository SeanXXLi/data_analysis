package com.hsbc.eep.data.migrate.analysis.dependency;

import java.util.List;

import com.hsbc.eep.data.migrate.analysis.table.Table;

public class DynamicDependency {

	
	
	private List<Table> relatedTables;
	private String operation;
	private String sql;

	public DynamicDependency(List<Table> relatedTables, String operation, String sql) {
		this.relatedTables = relatedTables;
		this.operation = operation;
		this.sql = sql;
	}

	public List<Table> getRelatedTables() {
		return relatedTables;
	}

	public void setRelatedTables(List<Table> relatedTables) {
		this.relatedTables = relatedTables;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

}
