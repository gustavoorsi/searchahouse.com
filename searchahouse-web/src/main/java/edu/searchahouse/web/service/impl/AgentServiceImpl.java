package edu.searchahouse.web.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.stereotype.Service;

import edu.searchahouse.web.model.Agent;
import edu.searchahouse.web.model.Property;
import edu.searchahouse.web.model.Property.PropertyType;
import edu.searchahouse.web.service.AgentService;

@Service
public class AgentServiceImpl implements AgentService {

    @Override
    public Collection<Agent> findTopAgents(int n) {
        
        Collection<Agent> agents = new ArrayList<Agent>();
        agents.add( new Agent("Gustavo", "1") );
        agents.add( new Agent("Fabian", "2") );
        agents.add( new Agent("Diego", "3") );
        
        return agents;
    }

    @Override
    public Agent findById(String id) {
        
        Agent agent = new Agent("gustavoooo", "1");
        
        Collection<Property> properties = new ArrayList<Property>();
        properties.add( new Property("House 1", "Located in beverly Hills", PropertyType.RENT) );
        properties.add( new Property("House 2", "Located in New York", PropertyType.SALE) );
        properties.add( new Property("House 3", "Located in Miami", PropertyType.RENT) );
        properties.add( new Property("House 4", "Located in Las Vegas", PropertyType.RENT) );
        
        agent.setProperties(properties);
        
        return agent;
    }
    
    
    
    

}
