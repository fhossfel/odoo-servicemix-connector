/*
 * Copyright (C) 2019 baumarkt direkt GmbH
 * 
 * This file is part of bmd-odoo-servicemix.
 * 
 * bmd-odoo-servicemix or any parts thereof cannot be copied, modified, distributed
 * and/or used without the express permission of the baumarkt direkt GmbH, Hamburg, DE.
 */
package de.baumarktdirekt.servicemix.aggregate;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

/**
 *
 * @author Felix Hoßfeld <felix.hossfel@baumarktdirekt.de>
 */
public class SetHeaderAggregationStrategy implements AggregationStrategy {
    
    private final String headerName;
    
    public SetHeaderAggregationStrategy(String headername) {
        
        this.headerName = headername;
        
    }

    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        
        oldExchange.getIn().setHeader(headerName, newExchange.getIn().getBody());
        
        return oldExchange;
        
    }
    
    
    
}
