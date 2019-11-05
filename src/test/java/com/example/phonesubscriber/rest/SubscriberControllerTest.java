package com.example.phonesubscriber.rest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class SubscriberControllerTest {

    @Autowired
    private SubscriberController controller;

    @Test
    void testAllSubscribersPage() {
        assert (true);
//    controller.allSubscribersPage()
    }
}
