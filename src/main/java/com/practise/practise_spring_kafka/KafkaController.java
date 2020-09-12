package com.practise.practise_spring_kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.*;

import javax.websocket.SendResult;

@Slf4j
@RestController
public class KafkaController {
    @Autowired
    private KafkaTemplate kafkaTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping(value = "/send")
    public ResponseEntity<String> postValues(@RequestBody SampleObject message, @RequestParam String topic) {
        log.info("Sending Values");
        if (topic == null) {
            topic = "test-topic";
        }
        ListenableFuture<SendResult> future = kafkaTemplate.send(topic, message);
        future.addCallback(new ListenableFutureCallback<SendResult>() {

            @Override
            public void onSuccess(SendResult result) {
                System.out.println("Sent message=[" + message +
                        "] with offset=[" + result + "]");
            }

            @Override
            public void onFailure(Throwable ex) {
                System.out.println("Unable to send message=["
                        + message + "] due to : " + ex.getMessage());
            }
        });
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/getValuesInTopic")
    @KafkaListener(topics = "test-topic", groupId = "foo")
    public ResponseEntity<SampleObject> getValuesInTopic(SampleObject message) throws JsonProcessingException {
        log.info("Received message=" + objectMapper.writeValueAsString(message));
        return new ResponseEntity<>(message, HttpStatus.OK);
    }


}
