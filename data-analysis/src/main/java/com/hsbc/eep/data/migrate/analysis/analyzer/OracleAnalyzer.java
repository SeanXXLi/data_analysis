package com.hsbc.eep.data.migrate.analysis.analyzer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.hsbc.eep.data.migrate.analysis.dependency.ConstraintDependency;
import com.hsbc.eep.data.migrate.analysis.dependency.DynamicDependency;
import com.hsbc.eep.data.migrate.analysis.dependency.StaticDependency;
import com.hsbc.eep.data.migrate.analysis.domian.Employee;
import com.hsbc.eep.data.migrate.analysis.feature.GroupByFeature;
import com.hsbc.eep.data.migrate.analysis.feature.HighIoFeature;
import com.hsbc.eep.data.migrate.analysis.feature.HighReadWriteRatioFeature;
import com.hsbc.eep.data.migrate.analysis.table.Table;

@Service
public class OracleAnalyzer {
    @Autowired
    private JdbcTemplate jdbcTemplate;

	public List<DynamicDependency> getDynamicDependencies() {
		List<DynamicDependency> results = new ArrayList<>();
//		results.add(new DynamicDependency(Arrays.asList(new Table("A"),new Table("B")),"SELECT","12354"));
		return results;
	}

	public List<StaticDependency> getStaticDependencies() {
		List<StaticDependency> results = new ArrayList<>();
        String sql = "with s as ( select name,REFERENCED_NAME from dba_dependencies where owner = 'HR' and REFERENCED_OWNER = 'HR' and REFERENCED_TYPE = 'TABLE') select name,listagg(REFERENCED_NAME,',') within group (order by REFERENCED_NAME) as all_tables from s group by name";
        List<StaticDependency> staticDeps = jdbcTemplate.query(sql, new RowMapper(){
 
            public StaticDependency mapRow(ResultSet rs, int rowNum) throws SQLException {
            	List<Table> tl = new ArrayList<>();
            	String tables = rs.getString("all_tables");
            	String[] split = tables.split(",");
            	for (String table : split) {
					Table t = new Table(table);
					tl.add(t);
				}
            	StaticDependency sd = new StaticDependency(tl,rs.getString("NAME"));
                return sd;
            }
 
        });
        for (StaticDependency staticDependency : staticDeps) {
        	if (staticDependency.getRelatedTables().size()>1) {		
        		results.add(staticDependency);
			}
		}
//		results.add(new StaticDependency(Arrays.asList(new Table("A"),new Table("C")),"X_PKG"));
		return results;
	}

	public List<ConstraintDependency> getConstraintDependencies() {
		List<ConstraintDependency> results = new ArrayList<>();
		String sql = "select a.table_name as owner, b.table_name as refer, a.constraint_name from DBA_CONSTRAINTS A, DBA_CONSTRAINTS B where A.OWNER='HR' and B.OWNER='HR' and A.CONSTRAINT_TYPE= 'R' and A.R_CONSTRAINT_NAME = B.CONSTRAINT_NAME order by A.TABLE_NAME, B.TABLE_NAME";
        List<ConstraintDependency> constraintDep = jdbcTemplate.query(sql, new RowMapper(){
 
            public ConstraintDependency mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new ConstraintDependency(Arrays.asList(new Table(rs.getString("OWNER")),new Table(rs.getString("REFER"))),rs.getString("CONSTRAINT_NAME"));
            }
 
        });
        results.addAll(constraintDep);
//		results.add(new ConstraintDependency(Arrays.asList(new Table("C"),new Table("E")),"X_FK"));
		return results;
	}

	public List<Table> getAllTables() {
		List<Table> results = new ArrayList<>();
        String sql = "select table_name from all_tables where owner = 'HR'";
        List<Table> alltables = jdbcTemplate.query(sql, new RowMapper(){
 
            public Table mapRow(ResultSet rs, int rowNum) throws SQLException {
            	Table table = new Table(rs.getString("TABLE_NAME"));
                return table;
            }
 
        });
        results.addAll(alltables);
//		results.addAll(Arrays.asList(new Table("A"),new Table("B"),new Table("C"),new Table("D"),new Table("E"),new Table("G")));
		return results;
	}

	public List<HighReadWriteRatioFeature> getReadWriteFeature() {
		List<HighReadWriteRatioFeature> results = new ArrayList<>();
//		results.addAll(Arrays.asList(new HighReadWriteRatioFeature("C",true)));
		return results;
	}

	public List<GroupByFeature> getAggregationFeature() {
		List<GroupByFeature> results = new ArrayList<>();
//		results.addAll(Arrays.asList(new GroupByFeature("A",true),new GroupByFeature("B",true)));
		return results;
	}

	public List<HighIoFeature> getHighIoFeature() {
		List<HighIoFeature> results = new ArrayList<>();
//		results.addAll(Arrays.asList(new HighIoFeature("D",true),new HighIoFeature("E",true)));
		return results;
	}


}
