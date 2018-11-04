package com.hsbc.eep.data.migrate.analysis;

import java.io.File;
import java.util.List;

import com.hsbc.eep.data.migrate.analysis.analyzer.OracleAnalyzer;
import com.hsbc.eep.data.migrate.analysis.dependency.ConstraintDependency;
import com.hsbc.eep.data.migrate.analysis.dependency.DynamicDependency;
import com.hsbc.eep.data.migrate.analysis.dependency.StaticDependency;
import com.hsbc.eep.data.migrate.analysis.feature.GroupByFeature;
import com.hsbc.eep.data.migrate.analysis.feature.ReadWriteFeature;
import com.hsbc.eep.data.migrate.analysis.graphviz.GraphGenerator;
import com.hsbc.eep.data.migrate.analysis.graphviz.GraphViz;
import com.hsbc.eep.data.migrate.analysis.recommend.Recommendation;
import com.hsbc.eep.data.migrate.analysis.recommend.StorageRecommender;
import com.hsbc.eep.data.migrate.analysis.table.Table;

/**
 * Hello world!
 *
 */
public class App {
	 public static void main(String[] args)
	   {
		 App p = new App();
	      p.start();
//	      p.start2();
	   }

	/**
	    * Construct a DOT graph in memory, convert it
	    * to image and store the image in the file system.
	    */
	   private void start()
	   {
		    OracleAnalyzer analyzer = new OracleAnalyzer();
		    List<Table> allTables =  analyzer.getAllTables();
		    List<DynamicDependency>dynamicDep = analyzer.getDynamicDependencies();
		    List<StaticDependency>staticDep = analyzer.getStaticDependencies();
		    List<ConstraintDependency>constraintDep = analyzer.getConstraintDependencies();
		    List<ReadWriteFeature> rwFeatures = analyzer.getReadWriteFeature();
		    List<GroupByFeature> gbFeatures = analyzer.getGroupByFeature();
		    new GraphGenerator().generateDependencyGraph(allTables,dynamicDep, staticDep, constraintDep,rwFeatures,gbFeatures);
		    List<Recommendation> recommends = new StorageRecommender().recommand(allTables,dynamicDep,staticDep,constraintDep,rwFeatures,gbFeatures);
	   }






	   
	   /**
	    * Read the DOT source from a file,
	    * convert to image and store the image in the file system.
	    */
	   private void start2()
	   {
	 //     String dir = "/home/jabba/eclipse2/laszlo.sajat/graphviz-java-api";     // Linux
	 //     String input = dir + "/sample/simple.dot";
	    String input = "D:\\graph\\g.arrow.txt";    // Windows
	    
	    GraphViz gv = new GraphViz();
	    gv.readSource(input);
	    System.out.println(gv.getDotSource());
	     
	      String type = "gif";
//	    String type = "dot";
//	    String type = "fig";    // open with xfig
//	    String type = "pdf";
//	    String type = "ps";
//	    String type = "svg";    // open with inkscape
//	    String type = "png";
//	      String type = "plain";
	    File out = new File("D:\\graph\\simple." + type);   // Linux
//	    File out = new File("c:/eclipse.ws/graphviz-java-api/tmp/simple." + type);   // Windows
	    gv.writeGraphToFile( gv.getGraph( gv.getDotSource(), type ), out );
	   }
}
