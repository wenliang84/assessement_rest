package com.accessment.demo;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class FeatureRepositoryTests {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private FeatureRepository featureRepository;

	@Test
	public void testH2DBQuery() {
		Feature feature = new Feature("test@email.com", "test", true);
		entityManager.persist(feature);

		List<Feature> list = featureRepository.findByEmailAndFeatureName("test@email.com", "test");
		assertThat(list).extracting(Feature::getFeatureName).containsOnly(feature.getFeatureName());
	}

}
