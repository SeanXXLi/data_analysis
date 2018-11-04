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
import com.hsbc.eep.data.migrate.analysis.table.Table;

public class DotSourceGenerator {
	public List<String> generateDynamicDotSource(List<DynamicDependency> dynamicDep) {
		List<String> results = new ArrayList<>();
		for (DynamicDependency dynamicDependency : dynamicDep) {
			List<Table> relatedTables = dynamicDependency.getRelatedTables();
			String linkage = relatedTables.parallelStream().map(Table::getName).collect(Collectors.joining("->"));
			linkage = linkage.concat("[dir=\"none\",style=dotted,label=\"");
			linkage = linkage.concat(dynamicDependency.getOperation());
			linkage = linkage.concat("\"]");
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
			linkage = linkage.concat("[dir=\"none\",style=bold,label=\"");
			linkage = linkage.concat(staticDependency.getReference());
			linkage = linkage.concat("\"]");
			linkage = linkage.concat(";");
			results.add(linkage);
		}
		return results;
	}

	public List<String> generateConstrantDotSource(List<ConstraintDependency> constraintDep) {
		List<String> content = new ArrayList<>();
		content.add("");
		return content;
	}

	@SafeVarargs
	public final List<String> mergeContentList(List<String>... graphContents) {
		List<String> content = new ArrayList<>();
		content.add(startGraph());
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
			List<StaticDependency> staticDep, List<ConstraintDependency> constraintDep) {
		List<String> results = new ArrayList<>();
		Map<String, Integer> tableLinks = new HashMap<>();
		dynamicDep.parallelStream().map(DynamicDependency::getRelatedTables)
				.forEach(tables -> tables.parallelStream().map(Table::getName).forEach(name -> {
					Integer current = tableLinks.get(name);
					if (current == null) {
						tableLinks.put(name, 1);
					} else {
						current += 1;
						tableLinks.put(name, current);
					}
				}));

		staticDep.parallelStream().map(StaticDependency::getRelatedTables)
				.forEach(tables -> tables.parallelStream().map(Table::getName).forEach(name -> {
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
			results.add(tableLink.getKey() + " [orientation = 15, regular = true, shape = polygon, sides = " + side
					+ ", peripheries = " + peripheries + ", color = " + color + ", style = filled];");
		}
		List<String> allTableNames = allTables.stream().map(Table::getName).collect(Collectors.toList());
		Collection<String> IsolateTables = CollectionUtils.disjunction(allTableNames, tableLinks.keySet());
		for (String IsolateTable : IsolateTables) {
			results.add(IsolateTable
					+ " [orientation = 15, regular = true, shape = polygon, sides = 3, peripheries = 1, color = chartreuse, style = filled];");
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
