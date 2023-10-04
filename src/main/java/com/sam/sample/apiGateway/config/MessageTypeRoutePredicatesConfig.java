package com.sam.sample.apiGateway.config;

import com.sam.sample.apiGateway.factory.MessageTypeRoutePredicateFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Predicate;

@Configuration
public class MessageTypeRoutePredicatesConfig {
    @Bean
    public MessageTypeRoutePredicateFactory messageTypeRoutePredicateFactory() {
        return new MessageTypeRoutePredicateFactory();
    }

    @Bean
    public Predicate globalApplyPredicate() {
        return o -> true;
    }
}
