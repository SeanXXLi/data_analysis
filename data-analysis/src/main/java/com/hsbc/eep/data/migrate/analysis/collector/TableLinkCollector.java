package com.hsbc.eep.data.migrate.analysis.collector;

import java.util.List;

import com.hsbc.eep.data.migrate.analysis.dependency.ConstraintDependency;
import com.hsbc.eep.data.migrate.analysis.dependency.DynamicDependency;
import com.hsbc.eep.data.migrate.analysis.dependency.StaticDependency;
import com.hsbc.eep.data.migrate.analysis.table.Table;

public class TableLinkCollector {

	public void collect(List<Table> allTables, List<DynamicDependency> dynamicDep, List<StaticDependency> staticDep,
			List<ConstraintDependency> constraintDep) {
		
		dynamicDep.parallelStream().map(DynamicDependency::getRelatedTables).forEach(tl -> {
			tl.parallelStream().forEach(t -> {
				if (allTables.contains(t)) {
					allTables.parallelStream().filter(at->at.getName().equals(t.getName())).forEach(at->{at.setTableLinkCounts(at.getTableLinkCounts()+1);});;
				}
			});
		});
		;
		
		staticDep.parallelStream().map(StaticDependency::getRelatedTables).forEach(tl -> {
			tl.parallelStream().forEach(t -> {
				if (allTables.contains(t)) {
					allTables.parallelStream().filter(at->at.getName().equals(t.getName())).forEach(at->{at.setTableLinkCounts(at.getTableLinkCounts()+1);});;
				}
			});
		});
		;
		
		constraintDep.parallelStream().map(ConstraintDependency::getRelatedTables).forEach(tl -> {
			tl.parallelStream().forEach(t -> {
				if (allTables.contains(t)) {
					allTables.parallelStream().filter(at->at.getName().equals(t.getName())).forEach(at->{at.setTableLinkCounts(at.getTableLinkCounts()+1);});;
				}
			});
		});
		;
		
	}

}
