package edu.searchahouse.leadrouter.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import edu.searchahouse.leadrouter.model.Lead;
import edu.searchahouse.leadrouter.service.LeadRouterService;

@Service
public class LeadRouterServiceImpl implements LeadRouterService {
    
    private final RestTemplate restTemplate;
    
    public LeadRouterServiceImpl( final RestTemplate restTemplate ){
        this.restTemplate = restTemplate;
    }

    @Override
    public void routeLead(Lead lead, final String propertyId) {
        
        String endpoint = "http://localhost:8080/api/v1/property/" + propertyId;
        
        
    }

    
    
}
