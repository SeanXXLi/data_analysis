package com.hsbc.eep.data.migrate.analysis.domian;
import java.util.List;

import com.hsbc.eep.data.migrate.analysis.recommend.Recommendation;

import lombok.Data;

@Data
public class Recommendations {
	private List<Recommendation> recommendations;

	public Recommendations(List<Recommendation> recommendations) {
		super();
		this.recommendations = recommendations;
	}

	public List<Recommendation> getRecommendations() {
		return recommendations;
	}

	public void setRecommendations(List<Recommendation> recommendations) {
		this.recommendations = recommendations;
	}
	
}
