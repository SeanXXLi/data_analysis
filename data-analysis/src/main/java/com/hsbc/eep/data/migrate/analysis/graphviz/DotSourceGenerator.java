package com.hsbc.eep.data.migrate.analysis.graphviz;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;

import com.hsbc.eep.data.migrate.analysis.dependency.ConstraintDependency;
import com.hsbc.eep.data.migrate.analysis.dependency.DynamicDependency;
import com.hsbc.eep.data.migrate.analysis.dependency.StaticDependency;
import com.hsbc.eep.data.migrate.analysis.feature.GroupByFeature;
import com.hsbc.eep.data.migrate.analysis.feature.HighIoFeature;
import com.hsbc.eep.data.migrate.analysis.feature.HighReadWriteRatioFeature;
import com.hsbc.eep.data.migrate.analysis.table.Table;

public class DotSourceGenerator {
	public List<String> generateDynamicDotSource(List<DynamicDependency> dynamicDep) {
		List<String> results = new ArrayList<>();
		for (DynamicDependency dynamicDependency : dynamicDep) {
			List<Table> relatedTables = dynamicDependency.getRelatedTables();
			String linkage = relatedTables.parallelStream().map(Table::getName).collect(Collectors.joining("->"));
			linkage = linkage.concat("[dir=\"none\",style=dashed,color=green,label=\"");
			
			linkage = linkage.concat(dynamicDependency.getOperation());
			linkage = linkage.concat("\",fontcolor = green]");
			linkage = linkage.concat(";");
			results.add(linkage);
		}
		return results;
	}

	public List<String> generateStaticDotSource(List<StaticDependency> staticDep) {
		List<String> results = new ArrayList<>();
		for (StaticDependency staticDependency : staticDep) {
			List<Table> relatedTables = staticDependency.getRelatedTables();
			String linkage = relatedTables.parallelStream().map(Table::getName).collect(Collectors.joining("->"));
			linkage = linkage.concat("[dir=\"none\",style=bold,color=red,label=\"");
			linkage = linkage.concat(staticDependency.getReference());
			linkage = linkage.concat("\",fontcolor = red]");
			linkage = linkage.concat(";");
			results.add(linkage);
		}
		return results;
	}

	public List<String> generateConstrantDotSource(List<ConstraintDependency> constraintDeps) {
		List<String> results = new ArrayList<>();
		for (ConstraintDependency constraintDep : constraintDeps) {
			List<Table> relatedTables = constraintDep.getRelatedTables();
			String linkage = relatedTables.parallelStream().map(Table::getName).collect(Collectors.joining("->"));
			linkage = linkage.concat("[dir=\"none\",style=dotted,color=blue,label=\"");
			linkage = linkage.concat(constraintDep.getConstraint());
			linkage = linkage.concat("\",fontcolor = blue]");
			linkage = linkage.concat(";");
			results.add(linkage);
		}
		return results;
	}

	@SafeVarargs
	public final List<String> mergeContentList(List<String>... graphContents) {
		List<String> content = new ArrayList<>();
		content.add(startGraph());
//		content.add("edge [decorate = true];");
		for (List<String> eachContent : graphContents) {
			content.addAll(eachContent);
		}
		content.add(endGraph());
		return content;
	}

	/**
	 * Returns a string that is used to start a graph.
	 * 
	 * @return A string to open a graph.
	 */
	public String startGraph() {
		return "digraph G {";
	}

	/**
	 * Returns a string that is used to end a graph.
	 * 
	 * @return A string to close a graph.
	 */
	public String endGraph() {
		return "}";
	}

	public List<String> generateTableDiscription(List<Table> allTables, List<DynamicDependency> dynamicDep,
			List<StaticDependency> staticDep, List<ConstraintDependency> constraintDep, List<HighReadWriteRatioFeature> rwFeatures, List<GroupByFeature> gbFeatures, List<HighIoFeature> highIoFeatures) {
		List<String> results = new ArrayList<>();
		Map<String, Integer> tableLinks = new HashMap<>();
		
		dynamicDep.stream().map(DynamicDependency::getRelatedTables)
				.forEach(tables -> tables.parallelStream().map(Table::getName).forEach(name -> {
					Integer current = tableLinks.get(name);
					if (current == null) {
						tableLinks.put(name, 1);
					} else {
						current += 1;
						tableLinks.put(name, current);
					}
				}));

		staticDep.stream().map(StaticDependency::getRelatedTables)
				.forEach(tables -> tables.parallelStream().map(Table::getName).forEach(name -> {
					Integer current = tableLinks.get(name);
					if (current == null) {
						tableLinks.put(name, 1);
					} else {
						current += 1;
						tableLinks.put(name, current);
					}
				}));
		
		constraintDep.stream().map(ConstraintDependency::getRelatedTables)
		.forEach(tables -> tables.stream().map(Table::getName).forEach(name -> {
			Integer current = tableLinks.get(name);
			if (current == null) {
				tableLinks.put(name, 1);
			} else {
				current += 1;
				tableLinks.put(name, current);
			}
		}));

		for (Entry tableLink : tableLinks.entrySet()) {

			Integer count = (Integer) tableLink.getValue();
			String color = determineColor(count);
			Integer side = count + 3;
			Integer peripheries = count + 1;
			results.add(tableLink.getKey() + " [orientation = 15, regular = true, shape = polygon, peripheries = 2, sides = 4, color = " + color + ", style = filled];");
		}
		
		List<String> allTableNames = allTables.stream().map(Table::getName).collect(Collectors.toList());
		Collection<String> IsolateTables = CollectionUtils.disjunction(allTableNames, tableLinks.keySet());
		
		for (String IsolateTable : IsolateTables) {
			results.add(IsolateTable
					+ " [orientation = 15, regular = true, shape = polygon, sides = 4, peripheries = 2, color = chartreuse, style = filled];");
		}
		for (GroupByFeature groupByFeature : gbFeatures) {
			Integer side = 5;
			results.add(groupByFeature.getTable() + " [ sides = " + side + "];");
		}

		for (HighIoFeature highIoFeature : highIoFeatures) {
			Integer peripheries = 3;
			results.add(highIoFeature.getTable() + " [ peripheries = " + peripheries + "];");
		}
		for (HighReadWriteRatioFeature readWriteFeature : rwFeatures) {
			Integer peripheries = 2;
			results.add(readWriteFeature.getTable() + " [ style = \"\"];");
		}
		return results;
	}
	

	public String determineColor(Integer linkCount) {
		String color;
		switch (linkCount) {
		case 0:
			color = "chartreuse";
			break;
		case 1:
			color = "cornflowerblue";
			break;
		case 2:
			color = "gold1";
			break;
		case 3:
			color = "orangered";
			break;
		default:
			color = "gray68";
			break;
		}
		return color;
	}
}
