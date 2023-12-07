package com.prashant.springboot;

import com.prashant.springboot.entity.WikimediaData;
import com.prashant.springboot.repository.WikimediaDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaDatabaseConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaDatabaseConsumer.class);

    private final WikimediaDataRepository dataRepository;

    public KafkaDatabaseConsumer(WikimediaDataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }
    @KafkaListener(topics = "${spring.kafka.topic.name}" , groupId = "${spring.kafka.consumer.group-id}")
    public void consume(String message){
        LOGGER.info("Message Received -> {}",message);
        WikimediaData wikimediaData = new WikimediaData();
        wikimediaData.setWikiEventData(message);

        dataRepository.save(wikimediaData);

    }

}
