package com.hsbc.eep.data.migrate.analysis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hsbc.eep.data.migrate.analysis.analyzer.OracleAnalyzer;
import com.hsbc.eep.data.migrate.analysis.collector.AggregationCollector;
import com.hsbc.eep.data.migrate.analysis.collector.HighIoCollector;
import com.hsbc.eep.data.migrate.analysis.collector.TableLinkCollector;
import com.hsbc.eep.data.migrate.analysis.collector.TransactionCollector;
import com.hsbc.eep.data.migrate.analysis.dependency.ConstraintDependency;
import com.hsbc.eep.data.migrate.analysis.dependency.DynamicDependency;
import com.hsbc.eep.data.migrate.analysis.dependency.StaticDependency;
import com.hsbc.eep.data.migrate.analysis.domian.DBConnection;
import com.hsbc.eep.data.migrate.analysis.feature.GroupByFeature;
import com.hsbc.eep.data.migrate.analysis.feature.HighIoFeature;
import com.hsbc.eep.data.migrate.analysis.feature.HighReadWriteRatioFeature;
import com.hsbc.eep.data.migrate.analysis.graphviz.GraphGenerator;
import com.hsbc.eep.data.migrate.analysis.recommend.Recommendation;
import com.hsbc.eep.data.migrate.analysis.recommend.StorageRecommender;
import com.hsbc.eep.data.migrate.analysis.service.TestService;
import com.hsbc.eep.data.migrate.analysis.table.Table;

@Controller
public class WebController {
	
	@Autowired 
	private OracleAnalyzer analyzer;
 
//	@RequestMapping(value = "/",method = RequestMethod.GET)
//	public String index() {
//		
//		return "backindex.html";
//	}
	@RequestMapping(value = "/",method = {RequestMethod.GET,RequestMethod.POST})
	public String connectionForm(Model model) {
		model.addAttribute("conn",new DBConnection());
		return "connection";
	}
	
	@RequestMapping(value = "/connect")
	public String login(@ModelAttribute DBConnection conn,Model model) {
		model.addAttribute("conn",new DBConnection());
//		List list = service.getList();
//		System.out.println(list);
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
		return "result";
	}
	
}
