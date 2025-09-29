package com.dayaeyak.waiting.common.config;


import com.dayaeyak.waiting.domain.kafka.dto.CustomerFromSellerCancelDto;
import com.dayaeyak.waiting.domain.kafka.dto.CustomerFromSellerDto;
import com.dayaeyak.waiting.domain.kafka.dto.CustomerWaitingDto;
import com.dayaeyak.waiting.domain.kafka.dto.SellerDto;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    private final String SERVER = "localhost:9092";

    @Bean
    public Map<String, Object> getStringObjectMap() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, SERVER);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return configProps;
    }

    @Bean
    public Map<String, Object> producerConfigs() {
        return getStringObjectMap();
    }

    //------------------------여기까지 공통으로 들어가는 것
    //------------------------아래는 각 도메인별로 다르게 작성해주셔야 합니다.
    // 제가 위에 적어놓은 DTO 갯수만큼 2개씩 세트로 만들어 주셔야 합다


    @Bean
    public ProducerFactory<String, CustomerFromSellerCancelDto> ProducerFactoryRB() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, CustomerFromSellerCancelDto> kafkaTemplateRB() {
        return new KafkaTemplate<>(ProducerFactoryRB());
    }


    @Bean
    public ProducerFactory<String, CustomerFromSellerDto> producerFactoryRBC() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, CustomerFromSellerDto> kafkaTemplateRBC() {
        return new KafkaTemplate<>(producerFactoryRBC());
    }

    @Bean
    public ProducerFactory<String, CustomerWaitingDto> producerFactoryRBCD() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, CustomerWaitingDto> kafkaTemplateRBCD() {
        return new KafkaTemplate<>(producerFactoryRBCD());
    }

    @Bean
    public ProducerFactory<String, SellerDto> producerFactoryRBCDE() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, SellerDto> kafkaTemplateRBCDE() {
        return new KafkaTemplate<>(producerFactoryRBCDE());
    }
}
