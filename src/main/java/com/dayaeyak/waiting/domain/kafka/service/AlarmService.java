package com.dayaeyak.waiting.domain.kafka.service;


import com.dayaeyak.waiting.domain.kafka.dto.CustomerFromSellerCancelDto;
import com.dayaeyak.waiting.domain.kafka.dto.CustomerFromSellerDto;
import com.dayaeyak.waiting.domain.kafka.dto.CustomerWaitingDto;
import com.dayaeyak.waiting.domain.kafka.dto.SellerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlarmService {

    private final KafkaTemplate<String, CustomerFromSellerCancelDto> kafkaTemplateRB;
    private final KafkaTemplate<String, CustomerFromSellerDto> kafkaTemplateRBC;
    private final KafkaTemplate<String, CustomerWaitingDto> kafkaTemplateRBCD;
    private final KafkaTemplate<String, SellerDto> kafkaTemplateRBCDE;


    public void sendMessageQueue1(String topic, String key, CustomerFromSellerCancelDto dto) {
        kafkaTemplateRB.send(topic, key, dto);
    }

    public void sendMessageQueue2(String topic, String key, CustomerFromSellerDto dto) {
        kafkaTemplateRBC.send(topic, key, dto);
    }

    public void sendMessageQueue3(String topic, String key, CustomerWaitingDto dto) {
        kafkaTemplateRBCD.send(topic, key, dto);
    }

    public void sendMessageQueue4(String topic, String key, SellerDto dto) {
//        System.out.println("topic "+topic);
//        System.out.println("key "+key);
//        System.out.println("dto "+dto.toString());
        kafkaTemplateRBCDE.send(topic, key, dto);
    }
}
