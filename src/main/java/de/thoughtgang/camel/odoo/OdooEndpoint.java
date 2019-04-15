/*
 * Copyright (C) 2019 Thought Gang GmbH
 * 
 * This file is part of odoo-camel-connector.
 * 
 * odoo-camel-connector or any parts thereof cannot be copied, modified, distributed
 * and/or used without the express permission of the Thought Gang GmbH, Hamburg, DE.
 */
package de.thoughtgang.camel.odoo;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.api.management.ManagedResource;
import org.apache.camel.impl.DefaultEndpoint;
import org.apache.camel.spi.Metadata;
import org.apache.camel.spi.UriEndpoint;
import org.apache.camel.spi.UriParam;
import org.apache.camel.spi.UriPath;

/**
 *
 * @author Felix Hoßfeld &lt;felix.hossfeld@thoughtgang.de&lt;
 */
@ManagedResource(description = "Odoo Endpoint")
@UriEndpoint(scheme = "odoo", title = "Square", syntax="square:name", label = "Square")
public class OdooEndpoint extends DefaultEndpoint {
    
    @UriPath @Metadata(required = "true")
    private String url;
    
    @UriParam
    private String method;
    
    @UriParam
    private String entity;

    @UriParam
    private String filter;

    @UriParam
    private String fields;
    
    public OdooEndpoint() {
    }

    public OdooEndpoint(String uri, OdooComponent component) {
        super(uri, component);
    }

    public Producer createProducer() throws Exception {
        return new OdooProducer(this);
    }

    public Consumer createConsumer(Processor processor) throws Exception {
    	throw new UnsupportedOperationException("The Odoo endpoint doesn't support consumers.");
    }

    public boolean isSingleton() {
        return true;
    }

    /**
     * Some description of this option, and what it does
     */

    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }

    String getMethod() {
        return method;
    }

    void setMethod(String method) {
        this.method = method;
    }

    String getEntity() {
        return entity;
    }

    void setEntity(String entity) {
        this.entity = entity;
    }

    String getFilter() {
        return filter;
    }

    void setFilter(String filter) {
        this.filter = filter;
    }

    String getFields() {
        return fields;
    }

    void setFields(String fields) {
        this.fields = fields;
    }

}