package com.api.assemblyvotingmanager.controllers;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.api.assemblyvotingmanager.dto.TopicEndSessionMessageDto;
import com.api.assemblyvotingmanager.services.TopicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.aws.autoconfigure.context.ContextStackAutoConfiguration;
import org.springframework.cloud.aws.messaging.config.SimpleMessageListenerContainerFactory;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.core.SqsMessageHeaders;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.messaging.MessageHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.AbstractMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication(exclude = {ContextStackAutoConfiguration.class})
@RestController
public class AwsSqsApplicationController {
    Logger logger = LoggerFactory.getLogger(AwsSqsApplicationController.class);
    final TopicService topicService;
    final Integer SECONDS = 60;
    @Autowired
    private QueueMessagingTemplate queueMessagingTemplate;

    @Value("${cloud.aws.end-point.uri}")
    private String endpoint;

    public AwsSqsApplicationController(TopicService topicService) {
        this.topicService = topicService;
    }

    @PostMapping("/send")
    public void sendMessageToQueue(@RequestBody TopicEndSessionMessageDto topicEndSessionMessage) {
        this.sendMessageDelayed(topicEndSessionMessage.getTopicId().toString(), endpoint, topicEndSessionMessage.getSessionEnd());
    }

    public void sendMessageDelayed(Object payload, String queueName, long delaySeconds) {
        Map<String, Object> headers = Stream.of(
                new AbstractMap.SimpleEntry<>(MessageHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE),
                new AbstractMap.SimpleEntry<>(SqsMessageHeaders.SQS_DELAY_HEADER, (int) delaySeconds))
                .collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));

        this.queueMessagingTemplate.convertAndSend(queueName,payload, headers);
    }

    @Bean
    public SimpleMessageListenerContainerFactory simpleMessageListenerContainerFactory(
            AmazonSQSAsync amazonSQSAsync
    ) {
        SimpleMessageListenerContainerFactory factory = new SimpleMessageListenerContainerFactory();
        factory.setAmazonSqs(amazonSQSAsync);
        factory.setWaitTimeOut(1); // less than 10 sec
        return factory;
    }

    @SqsListener("assembly-voting-manager-queue")
    public void loadMessageFromSQS(String message)  {
        String convertMessage = message.substring(1, message.length() - 1);

        UUID topicId = UUID.fromString(convertMessage);

        topicService.endVoteSession(topicId);

        logger.info("message from SQS Queue {}", message);
    }

    public void sendEndVoteSessionMessage(UUID topicId, Integer sessionEnd) {
        var topicEndSessionMessageDto = new TopicEndSessionMessageDto();
        topicEndSessionMessageDto.setSessionEnd(sessionEnd * SECONDS);
        topicEndSessionMessageDto.setTopicId(topicId);

        logger.info("minutes to end session {}", sessionEnd * SECONDS);
        this.sendMessageDelayed(topicId.toString(), endpoint, sessionEnd * SECONDS);
    }

    public static void main(String[] args) {
        SpringApplication.run(AwsSqsApplicationController.class, args);
    }
}
