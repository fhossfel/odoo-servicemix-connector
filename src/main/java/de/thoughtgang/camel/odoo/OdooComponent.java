/*
 * Copyright (C) 2019 Thought Gang GmbH
 * 
 * This file is part of odoo-camel-connector.
 * 
 * odoo-camel-connector or any parts thereof cannot be copied, modified, distributed
 * and/or used without the express permission of the Thought Gang GmbH, Hamburg, DE.
 */
package de.thoughtgang.camel.odoo;

import java.util.Map;
import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.impl.UriEndpointComponent;


/**
 *
 * @author felix Hoßfeld &lt;felix.hossfeld@thoughtgang.de&lt;
 */
public class OdooComponent extends UriEndpointComponent {
    
    public OdooComponent() {
        super(OdooEndpoint.class);
    }

    public OdooComponent(CamelContext context) {
        super(context, OdooEndpoint.class);
    }

    @Override
    protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {
        
        Endpoint endpoint = new OdooEndpoint(uri, this);
        setProperties(endpoint, parameters);
        return endpoint;
    }

}