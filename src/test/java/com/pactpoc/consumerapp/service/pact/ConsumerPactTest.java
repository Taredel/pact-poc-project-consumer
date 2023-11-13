package com.pactpoc.consumerapp.service.pact;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactBuilder;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.junit.MockServerConfig;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.consumer.junit5.ProviderType;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.pactpoc.consumerapp.dto.MessageDto;
import com.pactpoc.consumerapp.dto.SimpleDto;
import com.pactpoc.consumerapp.service.ProviderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(PactConsumerTestExt.class)
@SpringBootTest
@MockServerConfig
@PactTestFor(providerName = "ProviderService", providerType = ProviderType.ASYNCH)
public class ConsumerPactTest {

    @Autowired
    private ProviderService providerService;

    @Pact(consumer = "ConsumerService", provider = "ProviderService")
    V4Pact getSomething(PactBuilder builder) {
        DslPart body = new PactDslJsonBody()
                .integerType("simpleInteger", 10)
                .object("warningMessage", new PactDslJsonBody()
                        .stringValue("message", "Default message"))
                .asBody();
        return builder.usingLegacyDsl().given("Upon /getSomething")
                .uponReceiving("send 10")
                .method("GET")
                .path("/getSomething")
                .matchQuery("inputData", "10")
                .willRespondWith()
                .status(200)
                .body(body)
                .toPact(V4Pact.class);
    }

    @Test
    @PactTestFor(pactMethod = "getSomething")
    void getSomethingTest(MockServer mockServer) {
        SimpleDto expected = new SimpleDto(10, new MessageDto("Default message"));
        providerService.setUrl(mockServer.getUrl());
        SimpleDto actual = providerService.callProvider(10);

        assertEquals(expected, actual);
    }


    @Pact(consumer = "ConsumerService", provider = "ProviderService")
    V4Pact getSomethingElse(PactBuilder builder) {
        DslPart body = new PactDslJsonBody()
                .integerType("simpleInteger", 25)
                .object("warningMessage", new PactDslJsonBody()
                        .stringValue("message", "Default message"))
                .asBody();
        return builder.usingLegacyDsl().given("Upon /getSomething else")
                .uponReceiving("send 25")
                .method("GET")
                .path("/getSomething")
                .matchQuery("inputData", "25")
                .willRespondWith()
                .status(200)
                .body(body)
                .toPact(V4Pact.class);

    }
    @Test
    @PactTestFor(pactMethod = "getSomethingElse")
    void getSomethingElseTest(MockServer mockServer) {
        SimpleDto expected = new SimpleDto(25, new MessageDto("Default message"));
        providerService.setUrl(mockServer.getUrl());
        SimpleDto actual = providerService.callProvider(25);

        assertEquals(expected, actual);
    }

    @Pact(consumer = "ConsumerService", provider = "ProviderService")
    V4Pact getSomethingWithZero(PactBuilder builder) {
        return builder.usingLegacyDsl().given("Upon /getSomething")
                .uponReceiving("send 0")
                .method("GET")
                .path("/getSomething")
                .matchQuery("inputData", "0")
                .willRespondWith()
                .status(500)
                .toPact(V4Pact.class);

    }

    @Test
    @PactTestFor(pactMethod = "getSomethingWithZero")
    void getSomethingWithZeroTest(MockServer mockServer) {
        providerService.setUrl(mockServer.getUrl());
        SimpleDto actual = providerService.callProvider(0);

        assertNull(actual);
    }
}
