package com.sam.sample.apiGateway.factory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ServerWebExchange;

import java.util.function.Predicate;

@Slf4j
public class MessageTypeRoutePredicateFactory extends AbstractRoutePredicateFactory<MessageTypeRoutePredicateFactory.Config> {
    ObjectMapper objectMapper = new ObjectMapper();

    public MessageTypeRoutePredicateFactory() {
        super(Config.class);
    }

    @Override
    public Predicate<ServerWebExchange> apply(Config config) {
        return (ServerWebExchange swe) -> {
            ServerHttpRequest serverHttpRequest = swe.getRequest();
            String bodyStr = swe.getAttribute("cachedRequestBodyObject");
            JsonNode rootNode;
            try {
                rootNode = objectMapper.readTree(bodyStr);
            } catch (Exception ex) {
                return false;
            }

            JsonNode messageTypeNode = rootNode.findValue("messageType");
            if(messageTypeNode == null) {
                return false;
            }
            String messageTypeStr = messageTypeNode.asText();
            return StringUtils.equals(messageTypeStr, config.getMessageType());
        };
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @Validated
    public static class Config {
        String messageType;
    }
}
