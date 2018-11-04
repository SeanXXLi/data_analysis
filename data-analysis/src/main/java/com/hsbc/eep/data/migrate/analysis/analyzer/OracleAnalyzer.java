package com.hsbc.eep.data.migrate.analysis.analyzer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.hsbc.eep.data.migrate.analysis.dependency.ConstraintDependency;
import com.hsbc.eep.data.migrate.analysis.dependency.DynamicDependency;
import com.hsbc.eep.data.migrate.analysis.dependency.StaticDependency;
import com.hsbc.eep.data.migrate.analysis.feature.GroupByFeature;
import com.hsbc.eep.data.migrate.analysis.feature.HighIoFeature;
import com.hsbc.eep.data.migrate.analysis.feature.ReadWriteFeature;
import com.hsbc.eep.data.migrate.analysis.table.Table;

public class OracleAnalyzer {


	public List<DynamicDependency> getDynamicDependencies() {
		List<DynamicDependency> results = new ArrayList<>();
		results.add(new DynamicDependency(Arrays.asList(new Table("A"),new Table("B")),"SELECT","12354"));
		return results;
	}

	public List<StaticDependency> getStaticDependencies() {
		List<StaticDependency> results = new ArrayList<>();
		results.add(new StaticDependency(Arrays.asList(new Table("A"),new Table("C")),"X_PKG"));
		return results;
	}

	public List<ConstraintDependency> getConstraintDependencies() {
		List<ConstraintDependency> results = new ArrayList<>();
		results.add(new ConstraintDependency(Arrays.asList(new Table("C"),new Table("E")),"X_FK"));
		return results;
	}

	public List<Table> getAllTables() {
		List<Table> results = new ArrayList<>();
		results.addAll(Arrays.asList(new Table("A"),new Table("B"),new Table("C"),new Table("D"),new Table("E")));
		return results;
	}

	public List<ReadWriteFeature> getReadWriteFeature() {
		List<ReadWriteFeature> results = new ArrayList<>();
		return results;
	}

	public List<GroupByFeature> getAggregationFeature() {
		List<GroupByFeature> results = new ArrayList<>();
		return results;
	}

	public List<HighIoFeature> getHighIoFeature() {
		// TODO Auto-generated method stub
		return null;
	}


}
