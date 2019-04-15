/*
 * Copyright (C) 2019 Thought Gang GmbH
 * 
 * This file is part of odoo-camel-connector.
 * 
 * odoo-camel-connector or any parts thereof cannot be copied, modified, distributed
 * and/or used without the express permission of the Thought Gang GmbH, Hamburg, DE.
 */
package de.thoughtgang.camel.odoo;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.impl.DefaultConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Felix Hoßfeld &lt;felix.hossfeld@thoughtgang.de&lt;
 */
public class OdooConsumer extends DefaultConsumer {
    
    private static final Logger LOG = LoggerFactory.getLogger(OdooConsumer.class);
     
    private OdooEndpoint endpoint;

    public OdooConsumer(OdooEndpoint endpoint, Processor processor) {
        
        super(endpoint, processor);
        this.endpoint = endpoint;
    }

    public void process(Exchange exchange) throws Exception {
        System.out.println(exchange.getIn().getBody());    
    }
    
}
