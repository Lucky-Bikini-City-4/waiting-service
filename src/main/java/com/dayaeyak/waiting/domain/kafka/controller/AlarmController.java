package com.dayaeyak.waiting.domain.kafka.controller;

import com.dayaeyak.waiting.domain.kafka.dto.CustomerFromSellerCancelDto;
import com.dayaeyak.waiting.domain.kafka.dto.CustomerFromSellerDto;
import com.dayaeyak.waiting.domain.kafka.dto.CustomerWaitingDto;
import com.dayaeyak.waiting.domain.kafka.dto.SellerDto;
import com.dayaeyak.waiting.domain.kafka.service.AlarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequiredArgsConstructor
public class AlarmController {

    private final AlarmService alarmService;

    @PostMapping("/send-message-queue/rb")
    public String sendMessageQueue(@RequestParam("topic") String topic,
                                   @RequestParam("key") String key,
                                   @RequestBody CustomerFromSellerCancelDto dto) {
        alarmService.sendMessageQueue1(topic, key, dto);
        return "Message sent to Kafka topic" ;
    }


    @PostMapping("/send-message-queue/rbc")
    public String sendMessageQueue(@RequestParam("topic") String topic,
                                   @RequestParam("key") String key,
                                   @RequestBody CustomerFromSellerDto dto) {
        alarmService.sendMessageQueue2(topic, key, dto);
        return "Message sent to Kafka topic";
    }

    @PostMapping("/send-message-queue/rbcd")
    public String sendMessageQueue(@RequestParam("topic") String topic,
                                   @RequestParam("key") String key,
                                   @RequestBody CustomerWaitingDto dto) {
        alarmService.sendMessageQueue3(topic, key, dto);
        return "Message sent to Kafka topic";
    }

    @PostMapping("/send-message-queue/rbcde")
    public String sendMessageQueue(@RequestParam("topic") String topic,
                                   @RequestParam("key") String key,
                                   @RequestBody SellerDto dto) {
        alarmService.sendMessageQueue4(topic, key, dto);
        return "Message sent to Kafka topic";
    }
}
