package com.hsbc.eep.data.migrate.analysis.collector;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.hsbc.eep.data.migrate.analysis.feature.TransationRequiredFeature;
import com.hsbc.eep.data.migrate.analysis.table.Table;

public class TransactionCollector {

	public void collect(List<Table> allTables, List<TransationRequiredFeature> rwFeatures) {
		Set<String> changeTables = new HashSet<>();
		rwFeatures.stream().forEach(rf -> {changeTables.add(rf.getTable());});
		allTables.stream().forEach(t -> {if (changeTables.contains(t.getName())) {
			t.setTrasactionRequired(true);
		}});
	}

}
