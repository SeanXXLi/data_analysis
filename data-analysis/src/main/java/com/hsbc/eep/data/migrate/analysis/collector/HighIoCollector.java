package com.hsbc.eep.data.migrate.analysis.collector;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.hsbc.eep.data.migrate.analysis.feature.HighIoFeature;
import com.hsbc.eep.data.migrate.analysis.table.Table;

public class HighIoCollector {

	public void collect(List<Table> allTables, List<HighIoFeature> highIoFeatures) {
		Set<String> changeTables = new HashSet<>();
		highIoFeatures.stream().forEach(hf -> {changeTables.add(hf.getTable());});
		allTables.stream().forEach(t -> {if (changeTables.contains(t.getName())) {
			t.setTrasactionRequired(true);
		}});
	}

}
