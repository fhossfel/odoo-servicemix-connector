package de.thoughtgang.camel.odoo;

import de.thoughtgang.camel.odoo.util.Util;
import java.util.Dictionary;
import java.util.Map;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.impl.DefaultExchange;
import org.apache.camel.test.blueprint.CamelBlueprintTestSupport;
import org.apache.camel.util.KeyValueHolder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CreateStockPickingTest extends CamelBlueprintTestSupport {

    @Produce(uri="direct:start")
    protected ProducerTemplate inputEndpoint;

    @BeforeEach
    public void setup() throws Exception {

        super.setUp();

    }

    @AfterEach
    public void tearDown() throws Exception {

        super.tearDown();

    }

    @AfterAll
    public static void tearDownAfterClass() throws Exception {

        CamelBlueprintTestSupport.tearDownAfterClass();

    }

    @Override
    protected String getBlueprintDescriptor() {

        return "/OSGI-INF/blueprint/receiving_goods.xml";

    }
    
    @Override
    protected String getBundleFilter() {
        
        return "(!(Bundle-SymbolicName= org.test.junit.bundleTest))";
    
    }

    @Override
    protected void addServicesOnStartup(Map<String, KeyValueHolder<Object, Dictionary>> services) {

    }
    
    @Test
    public void testCamelRoute() throws Exception {

        MockEndpoint resultEndpoint = context.getEndpoint("mock:result", MockEndpoint.class);

        // Define mock
        context.getRouteDefinition("create.stock.picking").adviceWith(context, new AdviceWithRouteBuilder() {

            @Override
            public void configure() throws Exception {
                
              interceptSendToEndpoint("stream:out").skipSendToOriginalEndpoint().to("mock:result"); //

            }
        });

        String payload = Util.readRessource("test-data/we_00268350.xml");

        Exchange senderExchange = new DefaultExchange(context, ExchangePattern.InOut);
        senderExchange.getIn().setBody(payload);
        inputEndpoint.send(senderExchange);

        resultEndpoint.expectedMessageCount(1);
        resultEndpoint.assertIsSatisfied();
        
        for (Exchange exchange : resultEndpoint.getExchanges()) {
            
            System.out.println("------------> " + exchange.getIn().getBody(String.class));
            exchange.getIn().getHeaders().forEach((k,v) -> { System.err.println("------------> Header \"" + k + "\": \"" + v + "\""); });
        }

    }

}
