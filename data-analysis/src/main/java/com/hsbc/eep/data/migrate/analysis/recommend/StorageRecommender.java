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
				if(table.isHighIoRequired()&&table.getTableLinkCounts()<5){
					results.add(new Recommendation(tableName, "Spanner"));
				}else {
					results.add(new Recommendation(tableName, "CloudSQL"));
				}
			}else {
				if(table.isAggregationRequired()){
					results.add(new Recommendation(tableName, "Bigquery"));
				}else {
					if(table.isHighIoRequired()&&table.getTableLinkCounts()<2){
						results.add(new Recommendation(tableName, "Bigtable"));
					}else {
						results.add(new Recommendation(tableName, "Datastore"));
					}
				}
				
			}
		}
		return results;
	}

}
