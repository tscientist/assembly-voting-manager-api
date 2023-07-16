package com.api.assemblyvotingmanager.sqs;

import com.api.assemblyvotingmanager.dto.TopicDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.aws.autoconfigure.context.ContextStackAutoConfiguration;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.core.SqsMessageHeaders;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.http.MediaType;
import org.springframework.messaging.MessageHeaders;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication(exclude = {ContextStackAutoConfiguration.class})
@RestController
public class AwsSqsApplication {
    Logger logger = LoggerFactory.getLogger(AwsSqsApplication.class);

    @Autowired
    private QueueMessagingTemplate queueMessagingTemplate;

    @Value("${cloud.aws.end-point.uri}")
    private String endpoint;

    @PostMapping("/send")
    public void sendMessageToQueue(@RequestBody String teste) {
        this.sendMessageDelayed(teste, endpoint, 15);
    }

    public void sendMessageDelayed(Object payload, String queueName, long delaySeconds) {
        Map<String, Object> headers = Stream.of(
                new AbstractMap.SimpleEntry<>(MessageHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE),
                new AbstractMap.SimpleEntry<>(SqsMessageHeaders.SQS_DELAY_HEADER, (int) delaySeconds))
                .collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));

        this.queueMessagingTemplate.convertAndSend(queueName,payload, headers);
    }
    @SqsListener("assembly-voting-manager-queue")
    public void loadMessageFromSQS(String message)  {
        logger.info("message from SQS Queue {}",message);
    }

    public static void main(String[] args) {
        SpringApplication.run(AwsSqsApplication.class, args);
    }
}
