package com.bank.ccr.service;
import com.bank.ccr.dto.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.UUID;
@Service
public class DemoTradeService {
  private final KafkaTemplate<String, TradeEvent> kafka;
  private final DemoRiskRecalculationService risk;
  @Value("${app.kafka.enabled:false}") boolean kafkaEnabled;
  @Value("${app.kafka.trade-topic:ccr.trade-events}") String topic;
  public DemoTradeService(KafkaTemplate<String,TradeEvent> kafka, DemoRiskRecalculationService risk){this.kafka=kafka;this.risk=risk;}
  public DemoEventResponse book(DemoTradeRequest r){
    String eventId=UUID.randomUUID().toString();
    String tradeId="TRD-"+eventId.substring(0,8).toUpperCase();
    TradeEvent event=new TradeEvent(eventId,"TRADE_BOOKED",tradeId,r.counterpartyId(),r.product(),r.notional(),Instant.now());
    if(kafkaEnabled){
      kafka.send(topic,r.counterpartyId(),event);
      risk.rememberImpact(eventId,r.exposureImpact());
      return new DemoEventResponse(eventId,"PUBLISHED","Trade event published to Kafka; CCR recalculation will run asynchronously.");
    }
    risk.recalculate(event,r.exposureImpact());
    return new DemoEventResponse(eventId,"PROCESSED","Local demo mode: trade booked and CCR recalculated synchronously.");
  }
}
