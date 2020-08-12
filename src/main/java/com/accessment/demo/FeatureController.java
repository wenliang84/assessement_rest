package com.accessment.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class FeatureController {

    @Autowired
    FeatureService featureService;

    @GetMapping("/feature")
    public FeatureResponse retrieve(@RequestParam String email, @RequestParam String featureName) {
        Feature feature = featureService.findByEmailAndFeatureName(email, featureName);
        if(feature==null){
            return new FeatureResponse(false);
        } else{
            return new FeatureResponse(feature.isEnable());
        }

    }

    @PostMapping("/feature")
    public ResponseEntity update(@RequestBody FeatureRequest request) {
        try {
            Feature feature = featureService.findByEmailAndFeatureName(request.getEmail(), request.getFeatureName());
            if (feature == null) {
                feature = new Feature(request.getEmail(), request.getFeatureName(), request.isEnable());
            }else{
                feature.setEnable(request.isEnable());
            }
            featureService.saveOrUpdate(feature);
            return new ResponseEntity(HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity(HttpStatus.NOT_MODIFIED);
        }
    }



}
