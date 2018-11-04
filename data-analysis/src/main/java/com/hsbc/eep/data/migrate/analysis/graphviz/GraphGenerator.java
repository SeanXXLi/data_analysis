package com.hsbc.eep.data.migrate.analysis.graphviz;

import java.io.File;
import java.util.List;

import com.hsbc.eep.data.migrate.analysis.dependency.ConstraintDependency;
import com.hsbc.eep.data.migrate.analysis.dependency.DynamicDependency;
import com.hsbc.eep.data.migrate.analysis.dependency.StaticDependency;
import com.hsbc.eep.data.migrate.analysis.feature.GroupByFeature;
import com.hsbc.eep.data.migrate.analysis.feature.ReadWriteFeature;
import com.hsbc.eep.data.migrate.analysis.table.Table;

public class GraphGenerator {
	public void generateDependencyGraph(List<Table> allTables, List<DynamicDependency> dynamicDep, List<StaticDependency> staticDep,
			List<ConstraintDependency> constraintDep, List<ReadWriteFeature> rwFeatures, List<GroupByFeature> gbFeatures) {
		GraphViz gv = new GraphViz();
		DotSourceGenerator generator = new DotSourceGenerator();
		List<String> dynamicContent = generator.generateDynamicDotSource(dynamicDep);
		List<String> staticContent = generator.generateStaticDotSource(staticDep);
		List<String> constraintContent = generator.generateConstrantDotSource(constraintDep);
		List<String> tableDis = generator.generateTableDiscription(allTables,dynamicDep,staticDep,constraintDep);
		List<String> content = generator.mergeContentList(dynamicContent, staticContent, constraintContent,tableDis);
		for (String line : content ) {
			  gv.addln(line);
		}
		  System.out.println(gv.getDotSource());
		  
		  String type = "gif";
//	      String type = "dot";
//	      String type = "fig";    // open with xfig
//	      String type = "pdf";
//	      String type = "ps";
//	      String type = "svg";    // open with inkscape
//	      String type = "png";
//	      String type = "plain";
		  File out = new File("D:\\graph\\out." + type);   // Linux
//	      File out = new File("c:/eclipse.ws/graphviz-java-api/out." + type);    // Windows
		  gv.writeGraphToFile( gv.getGraph( gv.getDotSource(), type ), out );
	}
}
