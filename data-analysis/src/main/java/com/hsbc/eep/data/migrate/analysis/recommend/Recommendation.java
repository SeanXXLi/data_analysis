package com.hsbc.eep.data.migrate.analysis.recommend;

public class Recommendation {
	private String sourceTable;
	
	private String targetStorage;

	public Recommendation(String sourceTable, String targetStorage) {
		super();
		this.sourceTable = sourceTable;
		this.targetStorage = targetStorage;
	}

	public String getSourceTable() {
		return sourceTable;
	}

	public void setSourceTable(String sourceTable) {
		this.sourceTable = sourceTable;
	}

	public String getTargetStorage() {
		return targetStorage;
	}

	public void setTargetStorage(String targetStorage) {
		this.targetStorage = targetStorage;
	}

	@Override
	public String toString() {
		return "Recommendation [sourceTable=" + sourceTable + ", targetStorage=" + targetStorage + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sourceTable == null) ? 0 : sourceTable.hashCode());
		result = prime * result + ((targetStorage == null) ? 0 : targetStorage.hashCode());
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
		Recommendation other = (Recommendation) obj;
		if (sourceTable == null) {
			if (other.sourceTable != null)
				return false;
		} else if (!sourceTable.equals(other.sourceTable))
			return false;
		if (targetStorage == null) {
			if (other.targetStorage != null)
				return false;
		} else if (!targetStorage.equals(other.targetStorage))
			return false;
		return true;
	}
	
	
}
