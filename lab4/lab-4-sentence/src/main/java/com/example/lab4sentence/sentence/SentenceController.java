package com.example.lab4sentence.sentence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@RestController
public class SentenceController {


    @Autowired
    DiscoveryClient discoveryClient;

    @GetMapping("/sentence")
    public String getSentence() {
        return getWord("LAB-4-SUBJECT") + " " +
                getWord("LAB-4-VERB") + " " +
                getWord("LAB-4-ARTICLE") + " " +
                getWord("LAB-4-ADJECTIVE") + " " +
                getWord("LAB-4-NOUN");
    }

    public String getWord(String service) {
        List<ServiceInstance> list = discoveryClient.getInstances(service);
        if (list != null && list.size() > 0 ) {
            URI uri = list.get(0).getUri();
            if (uri !=null ) {
                return (new RestTemplate()).getForObject(uri,String.class);
            }
        }
        return null;
    }

}