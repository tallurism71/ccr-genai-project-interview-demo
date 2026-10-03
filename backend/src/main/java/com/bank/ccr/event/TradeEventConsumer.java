package com.bank.ccr.event;
import com.bank.ccr.dto.TradeEvent;
import com.bank.ccr.service.DemoRiskRecalculationService;
import org.slf4j.*;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
@Component
public class TradeEventConsumer {
  private static final Logger log=LoggerFactory.getLogger(TradeEventConsumer.class);
  private final DemoRiskRecalculationService risk;
  public TradeEventConsumer(DemoRiskRecalculationService risk){this.risk=risk;}
  @KafkaListener(topics="${app.kafka.trade-topic:ccr.trade-events}",groupId="${spring.kafka.consumer.group-id:ccr-risk}",autoStartup="${app.kafka.enabled:false}")
  public void consume(TradeEvent e){
    log.info("CCR trade event received id={} type={} cp={}",e.tradeId(),e.eventType(),e.counterpartyId());
    risk.recalculate(e,risk.consumeImpact(e.eventId(),e.notional()));
  }
}
