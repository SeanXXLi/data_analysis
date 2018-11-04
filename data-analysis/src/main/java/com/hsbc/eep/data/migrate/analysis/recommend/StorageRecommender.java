package com.hsbc.eep.data.migrate.analysis.recommend;

import java.util.List;

import com.hsbc.eep.data.migrate.analysis.dependency.ConstraintDependency;
import com.hsbc.eep.data.migrate.analysis.dependency.DynamicDependency;
import com.hsbc.eep.data.migrate.analysis.dependency.StaticDependency;
import com.hsbc.eep.data.migrate.analysis.feature.GroupByFeature;
import com.hsbc.eep.data.migrate.analysis.feature.ReadWriteFeature;
import com.hsbc.eep.data.migrate.analysis.table.Table;

public class StorageRecommender {

	public List<Recommendation> recommand(List<Table> allTables, List<DynamicDependency> dynamicDep,
			List<StaticDependency> staticDep, List<ConstraintDependency> constraintDep, List<ReadWriteFeature> rwFeatures, List<GroupByFeature> gbFeatures) {
		// TODO Auto-generated method stub
		return null;
	}

}
