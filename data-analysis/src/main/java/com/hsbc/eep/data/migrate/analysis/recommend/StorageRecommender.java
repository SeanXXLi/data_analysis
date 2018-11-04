package com.hsbc.eep.data.migrate.analysis.recommend;

import java.util.ArrayList;
import java.util.List;

import com.hsbc.eep.data.migrate.analysis.table.Table;

public class StorageRecommender {

	public List<Recommendation> recommand(List<Table> allTables) {
		List<Recommendation> results = new ArrayList<>();
		for (Table table : allTables) {
			String tableName = table.getName();
			if (table.isTrasactionRequired()) {
				if(table.isHighIoRequired()){
					new Recommendation(tableName, "CloudSQL");
				}else {
					new Recommendation(tableName, "Spanner");
				}
			}else {
				if(table.isAggregationRequired()){
					new Recommendation(tableName, "Bigquery");
				}else {
					new Recommendation(tableName, "Datastore");
				}
				if(table.isHighIoRequired()){
					new Recommendation(tableName, "Bigtable");
				}else {
					new Recommendation(tableName, "Datastore");
				}
			}
		}
		return results;
	}

}
