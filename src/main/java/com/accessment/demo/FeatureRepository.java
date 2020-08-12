package com.accessment.demo;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FeatureRepository extends CrudRepository<Feature, Long> {
	List<Feature> findByEmailAndFeatureName(String email, String featureName);
}
