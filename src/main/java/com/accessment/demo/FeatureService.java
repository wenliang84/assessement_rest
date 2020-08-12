package com.accessment.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeatureService {
    @Autowired
    FeatureRepository featureRepository;

    public void saveOrUpdate(Feature assessment)
    {
        featureRepository.save(assessment);
    }

    public Feature findByEmailAndFeatureName(String email, String featureName)
    {
        List<Feature> list = featureRepository.findByEmailAndFeatureName(email, featureName);
        if(list.size() > 0){
            return list.get(0);
        }
        return null;
    }
}
