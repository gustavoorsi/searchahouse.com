package edu.searchahouse;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.IntStream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableAsync;

import edu.searchahouse.model.Address;
import edu.searchahouse.model.Agent;
import edu.searchahouse.model.Lead;
import edu.searchahouse.model.Lead.Status;
import edu.searchahouse.model.Property;
import edu.searchahouse.model.Property.PropertyStatus;
import edu.searchahouse.model.Property.PropertyType;
import edu.searchahouse.repository.mongo.AgentRepository;
import edu.searchahouse.repository.mongo.LeadRepository;
import edu.searchahouse.repository.mongo.PropertyRepository;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableAsync
public class SearchahouseApplication {

	final static Map<Integer, String[]> ADDRESSES = new HashMap<Integer, String[]>();

	final static Map<Integer, String[][]> VALUES = new HashMap<Integer, String[][]>();

	//@formatter:off
	{
		VALUES.put(1, new String[][] { { "California", "Beverly Hills", "31340 Mulholland Dr." }, { "http://www.hawthorne.co.nz/p7ssm_img_1/thumbs/Hawthorne-House-1_tmb.jpg" } }); // Anderson Pamela
		VALUES.put(2, new String[][] { { "California", "Bel Air", "846 Stradella Rd." },  		  { "http://www.hawthorne.co.nz/p7ssm_img_1/fullsize/Hawthorne-House-4_fs.jpg" } }); // Eastwood Clint
		VALUES.put(3, new String[][] { { "California", "Hollywood Hills", "2705 Glen Dower Ave" },{ "http://d3ka0sx7noujy3.cloudfront.net/wp-content/uploads/2011/10/byrne-reed-house-exterior.jpg" } }); // Pitt Brad
		VALUES.put(4, new String[][] { { "New York", "S Ozone Park", "134-12 Linden Blvd." }, 	  { "http://www.thehouseplanshop.com/userfiles/photos/large/2659053724d87558b4ad09.jpg" } });
		VALUES.put(5, new String[][] { { "New York", "Brooklyn", "379 Kings Hwy" }, 			  { "https://pbs.twimg.com/profile_images/514415805652422657/VVLin2v4.png" } }); // Eastwood Clint
		VALUES.put(6, new String[][] { { "California", "Hollywood Hills", "2705 Glen Dower Ave" },{ "http://www.avaay.com/wp-content/uploads/2015/04/bizarre-house-4.jpg" } }); // Pitt Brad
	}
	//@formatter:on

	public static void main(String[] args) {
		SpringApplication.run(SearchahouseApplication.class, args);
	}

	@Profile("development")
	@Bean
	CommandLineRunner init(//
			final PropertyRepository propertyRepository, //
			final LeadRepository leadRepository, //
			final AgentRepository agentRepository//
	) {

		propertyRepository.deleteAll();
		leadRepository.deleteAll();
		agentRepository.deleteAll();

		//@formatter:off
        return (evt) -> IntStream.iterate(0, i -> i + 1).limit(3).mapToObj( Integer::toString )
				.forEach(
						index -> {
							Property property = createProperty(index);
							propertyRepository.save(property);

							Lead lead = createLead(index, Status.UNCONTACTED);
							leadRepository.save(lead);
							
							Lead lead2 = createLead("_"+index, Status.UNCONTACTED);
							leadRepository.save(lead2);

							Agent agent = createAgent(index);
							agent.addLead(lead);
							agent.addProperty(property);
							
							if( index.equals("1") ){
							    agent.addLead(lead2);
							}
							
							if( index.equals("2") ){
								Lead l1 = createLead("a", Status.CONTACTED);
								Lead l2 = createLead("b", Status.CONTACTED);
								Lead l3 = createLead("c", Status.CONTACTED);
								Lead l4 = createLead("d", Status.CONTACTED);
								leadRepository.save(l1);
								leadRepository.save(l2);
								leadRepository.save(l3);
								leadRepository.save(l4);
							    agent.addLead( l1 );
							    agent.addLead( l2 );
							    agent.addLead( l3 );
							    agent.addLead( l4 );
							    
							    Property p = propertyRepository.findPropertyByName("Property1").get();
							    agent.addProperty(p);
							}
							
							agentRepository.save(agent);
							
						});
		//@formatter:on
	}

	private Property createProperty(final String index) {
		Property property = new Property("Property" + index, "description" + index, new Address(VALUES.get(Integer.valueOf(1))[0][0], VALUES.get(Integer
				.valueOf(1))[0][1], VALUES.get(Integer.valueOf(1))[0][2]), 100000L, PropertyType.SALE, PropertyStatus.AVAILABLE);
		property.setImageUrl(VALUES.get(Integer.valueOf(1))[1][0]);

		property.setPrimaryKey(UUID.randomUUID().toString());

		return property;
	}

	private Lead createLead(final String index, final Lead.Status status) {
		Lead lead = new Lead("Lead" + index, "last name " + index, index + "lead@example.com", "012345678" + index);
		lead.setContactStatus(status);

		lead.setPrimaryKey(UUID.randomUUID().toString());

		return lead;
	}

	private Agent createAgent(final String index) {
		Agent agent = new Agent("Gustavo" + index, "Orsi" + index, index + "agent@example.com");

		agent.setPrimaryKey(UUID.randomUUID().toString());

		return agent;
	}
}
