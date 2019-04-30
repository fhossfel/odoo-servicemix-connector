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
public class CopyHeadersAggregationStrategy implements AggregationStrategy {
    
    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        
        newExchange.getIn().getHeaders().forEach((key, value) -> { oldExchange.getIn().setHeader(key, value); });
        
        return oldExchange;
        
    }
    
}
