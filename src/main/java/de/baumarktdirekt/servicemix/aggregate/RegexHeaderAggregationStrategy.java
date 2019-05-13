/*
 * Copyright (C) 2019 baumarkt direkt GmbH
 * 
 * This file is part of bmd-odoo-servicemix.
 * 
 * bmd-odoo-servicemix or any parts thereof cannot be copied, modified, distributed
 * and/or used without the express permission of the baumarkt direkt GmbH, Hamburg, DE.
 */
package de.baumarktdirekt.servicemix.aggregate;

import java.util.regex.Pattern;
import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

/**
 *
 * @author Felix Hoßfeld <felix.hossfel@baumarktdirekt.de>
 */
public class RegexHeaderAggregationStrategy implements AggregationStrategy {
    
    private final Pattern pattern;
    
    public RegexHeaderAggregationStrategy(String regex) {
        
        this.pattern = Pattern.compile(regex);
        
    }

    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        
        
        newExchange.getIn().getHeaders().forEach((key, value) -> { if (pattern.matcher(key).matches()) oldExchange.getIn().setHeader(key, value); });
        
        return oldExchange;        
        
    }
    
    
    
}
