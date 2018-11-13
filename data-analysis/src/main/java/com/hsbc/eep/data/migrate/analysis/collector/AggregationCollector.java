package com.hsbc.eep.data.migrate.analysis.collector;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.hsbc.eep.data.migrate.analysis.feature.GroupByFeature;
import com.hsbc.eep.data.migrate.analysis.table.Table;

public class AggregationCollector {

	public void collect(List<Table> allTables, List<GroupByFeature> aggregationFeatures) {
		Set<String> changeTables = new HashSet<>();
		aggregationFeatures.stream().forEach(af -> {changeTables.add(af.getTable());});
		allTables.stream().forEach(t -> {if (changeTables.contains(t.getName())) {
			t.setTrasactionRequired(true);
		}});
	}

}
