package com.hsbc.eep.data.migrate.analysis;

import java.io.File;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

import com.hsbc.eep.data.migrate.analysis.analyzer.OracleAnalyzer;
import com.hsbc.eep.data.migrate.analysis.collector.AggregationCollector;
import com.hsbc.eep.data.migrate.analysis.collector.HighIoCollector;
import com.hsbc.eep.data.migrate.analysis.collector.TableLinkCollector;
import com.hsbc.eep.data.migrate.analysis.collector.TransactionCollector;
import com.hsbc.eep.data.migrate.analysis.dependency.ConstraintDependency;
import com.hsbc.eep.data.migrate.analysis.dependency.DynamicDependency;
import com.hsbc.eep.data.migrate.analysis.dependency.StaticDependency;
import com.hsbc.eep.data.migrate.analysis.feature.GroupByFeature;
import com.hsbc.eep.data.migrate.analysis.feature.HighIoFeature;
import com.hsbc.eep.data.migrate.analysis.feature.HighReadWriteRatioFeature;
import com.hsbc.eep.data.migrate.analysis.graphviz.GraphGenerator;
import com.hsbc.eep.data.migrate.analysis.graphviz.GraphViz;
import com.hsbc.eep.data.migrate.analysis.recommend.Recommendation;
import com.hsbc.eep.data.migrate.analysis.recommend.StorageRecommender;
import com.hsbc.eep.data.migrate.analysis.table.Table;

/**
 * Hello world!
 *
 */
@EnableEurekaServer
@SpringBootApplication
public class App {
	 public static void main(String[] args)
	   {
		 SpringApplication.run(App.class, args);
//		 App p = new App();
//	      p.start();
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
		    List<HighReadWriteRatioFeature> rwFeatures = analyzer.getReadWriteFeature();
		    List<GroupByFeature> aggregationFeatures = analyzer.getAggregationFeature();
		    List<HighIoFeature> highIoFeatures = analyzer.getHighIoFeature();
		    
		    new GraphGenerator().generateDependencyGraph(allTables,dynamicDep, staticDep, constraintDep,rwFeatures,aggregationFeatures,highIoFeatures);
		    
		    new TableLinkCollector().collect(allTables,dynamicDep, staticDep, constraintDep);
		    new TransactionCollector().collect(rwFeatures);
		    new AggregationCollector().collect(aggregationFeatures);
		    new HighIoCollector().collect(highIoFeatures);
		    
		    List<Recommendation> recommends = new StorageRecommender().recommand(allTables);
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
