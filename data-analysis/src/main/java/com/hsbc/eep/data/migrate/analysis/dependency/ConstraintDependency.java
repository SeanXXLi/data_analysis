package com.hsbc.eep.data.migrate.analysis.dependency;

import java.util.List;

import com.hsbc.eep.data.migrate.analysis.table.Table;

public class ConstraintDependency {
	
	private List<Table> relatedTables;
	
	private String Constraint;

	public List<Table> getRelatedTables() {
		return relatedTables;
	}

	public void setRelatedTables(List<Table> relatedTables) {
		this.relatedTables = relatedTables;
	}

	public String getConstraint() {
		return Constraint;
	}

	public void setConstraint(String constraint) {
		Constraint = constraint;
	}

	public ConstraintDependency(List<Table> relatedTables, String constraint) {
		super();
		this.relatedTables = relatedTables;
		Constraint = constraint;
	}
	
	

}
