package com.hsbc.eep.data.migrate.analysis.dependency;

import java.util.List;

import com.hsbc.eep.data.migrate.analysis.table.Table;

public class StaticDependency {

	private List<Table> relatedTables;
	private String reference;

	public StaticDependency(List<Table> relatedTables, String reference) {
		this.relatedTables = relatedTables;
		this.reference = reference;
	}

	public List<Table> getRelatedTables() {
		return relatedTables;
	}

	public void setRelatedTables(List<Table> relatedTables) {
		this.relatedTables = relatedTables;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	@Override
	public String toString() {
		return "StaticDependency [relatedTables=" + relatedTables + ", reference=" + reference + "]";
	}

	
	
}
