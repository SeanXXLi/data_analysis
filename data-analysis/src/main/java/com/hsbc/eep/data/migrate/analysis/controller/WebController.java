package com.hsbc.eep.data.migrate.analysis.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
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
import com.hsbc.eep.data.migrate.analysis.domian.Recommendations;
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
	private List<Recommendation> recommendations;
 
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
	
	@RequestMapping(value = "/analyze")
	public String login(@ModelAttribute(value="conn") DBConnection conn,Model model) {
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
	    
	    recommendations = new StorageRecommender().recommand(allTables);
	    
		return "analyze";
	}
	
	@RequestMapping(value = "/recomm",method = {RequestMethod.GET,RequestMethod.POST})
	public String recommendation(Model model) {
		List<String> bigqueryList = new ArrayList<>();
		List<String> bigtableList = new ArrayList<>();
		List<String> datastoreList = new ArrayList<>();
		List<String> spannerList = new ArrayList<>();
		List<String> cloudSQLList = new ArrayList<>();
		for (Recommendation recommendation : recommendations) {
			if (StringUtils.equals("Bigquery", recommendation.getTargetStorage())) {
				bigqueryList.add(recommendation.getSourceTable());
			}else if(StringUtils.equals("Bigtable", recommendation.getTargetStorage())) {
				bigtableList.add(recommendation.getSourceTable());
			}else if(StringUtils.equals("Datastore", recommendation.getTargetStorage())) {
				datastoreList.add(recommendation.getSourceTable());
			}else if(StringUtils.equals("Spanner", recommendation.getTargetStorage())) {
				datastoreList.add(recommendation.getSourceTable());
			}else if(StringUtils.equals("CloudSQL", recommendation.getTargetStorage())) {
				cloudSQLList.add(recommendation.getSourceTable());
			}
		}
		
		model.addAttribute("bigqueryList",bigqueryList);
		model.addAttribute("bigtableList",bigtableList);
		model.addAttribute("datastoreList",datastoreList);
		model.addAttribute("spannerList",spannerList);
		model.addAttribute("cloudSQLList",cloudSQLList);
		return "recommendation";
	}
	
}
