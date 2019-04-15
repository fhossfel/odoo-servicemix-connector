package de.thoughtgang.camel.odoo;

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

public class OdooCamelRouteTest extends CamelBlueprintTestSupport {

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

        return "/OSGI-INF/blueprint/test.xml";

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
        context.getRouteDefinition("odoo-servicemix-test").adviceWith(context, new AdviceWithRouteBuilder() {

            @Override
            public void configure() throws Exception {
                
              interceptSendToEndpoint("stream:out").to("mock:result").skipSendToOriginalEndpoint();

            }

        });
        

        Exchange senderExchange = new DefaultExchange(context, ExchangePattern.InOut);
        senderExchange.getIn().setBody("<methodCall xmlns:ex=\"http://ws.apache.org/xmlrpc/namespaces/extensions\">\n" +
                                       "   <methodName>list</methodName>\n" +
                                       "   <params/>\n" +
                                       "</methodCall>");
        inputEndpoint.send(senderExchange);

        resultEndpoint.expectedMessageCount(1);
        resultEndpoint.assertIsSatisfied();
        
        for (Exchange exchange : resultEndpoint.getExchanges()) {
            
            System.out.println("------------> " + exchange.getIn().getBody(String.class));
        }
        /*for (Exchange exchange : resultEndpoint.getExchanges()) {

            String actual = exchange.getIn().getBody(String.class);
            String lagerort = exchange.getIn().getHeader("LAGERORT", String.class);
            String expectedFilename = "120".equals(lagerort) ? "kss/P_KSS_BESTAENDE_HWL.xml" : "kss/P_KSS_BESTAENDE_NoLo.xml";

            String expected = Util.readRessource(expectedFilename);

            Util.assertXmlEqual(expected, actual, n -> !"CREDATE".equals(n.getNodeName())
                    && !"CRETIME".equals(n.getNodeName())
                    && !"BELEGNUMMER".equals(n.getNodeName())
                    && !"BELEGDATUM".equals(n.getNodeName())
                    && !"SERIAL".equals(n.getNodeName()));

        } */

    }

}
