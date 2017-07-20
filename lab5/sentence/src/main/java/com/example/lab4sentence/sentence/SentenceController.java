package com.example.lab4sentence.sentence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@RestController
public class SentenceController {


    @Autowired
    LoadBalancerClient loadBalancerClient;

    @GetMapping("/sentence")
    public String getSentence() {
        return getWord("SUBJECT") + " " +
                getWord("VERB") + " " +
                getWord("ARTICLE") + " " +
                getWord("ADJECTIVE") + " " +
                getWord("NOUN");
    }

    public String getWord(String service) {
        ServiceInstance serviceInstance = loadBalancerClient.choose(service);
        if (serviceInstance != null) {
            URI uri = serviceInstance.getUri();
            if (uri !=null ) {
                return (new RestTemplate()).getForObject(uri,String.class);
            }
        }
        return null;
    }

}
