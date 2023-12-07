package com.prashant.springboot;

import com.launchdarkly.eventsource.MessageEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.launchdarkly.eventsource.background.BackgroundEventHandler;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import java.util.concurrent.CompletableFuture;

public class WikimediaChangeHandler implements BackgroundEventHandler {
    public static final Logger LOGGER= LoggerFactory.getLogger(WikimediaChangeHandler.class);

    private KafkaTemplate<String, String> kafkaTemplate;
    private String topic;

    public WikimediaChangeHandler(KafkaTemplate<String, String> kafkaTemplate, String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    @Override
    public void onOpen() throws Exception {

    }

    @Override
    public void onClosed() throws Exception {

    }

    @Override
    public void onMessage(String s, MessageEvent messageEvent) throws Exception {
        System.out.println("Topic "+topic);
        LOGGER.info(String.format("event data -> %s", messageEvent.getData()));
        CompletableFuture<SendResult<String,String>> future=kafkaTemplate.send(topic, messageEvent.getData());

        future.whenComplete((result,ex)->{
           if (ex != null){
               LOGGER.info("Sent Message to {} , Partition {} Offset {}",result.getRecordMetadata().topic(),result.getRecordMetadata().partition(),result.getRecordMetadata().offset());
           }
           else {
               LOGGER.error("Error {}:",ex.getMessage());
           }
        });

    }

    @Override
    public void onComment(String s) throws Exception {

    }

    @Override
    public void onError(Throwable throwable) {

    }
}
