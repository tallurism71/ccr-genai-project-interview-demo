package com.bank.ccr.service;
import com.bank.ccr.dto.TradeEvent;
import com.bank.ccr.model.*;
import com.bank.ccr.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.*;
import java.time.LocalDate;
import java.util.concurrent.ConcurrentHashMap;
@Service
public class DemoRiskRecalculationService {
  private final ExposureSnapshotRepository snapshots; private final ExposureDriverRepository drivers;
  private final ConcurrentHashMap<String,BigDecimal> pendingImpacts=new ConcurrentHashMap<>();
  public DemoRiskRecalculationService(ExposureSnapshotRepository snapshots, ExposureDriverRepository drivers){this.snapshots=snapshots;this.drivers=drivers;}
  public void rememberImpact(String eventId,BigDecimal impact){pendingImpacts.put(eventId,impact);}
  public BigDecimal consumeImpact(String eventId,BigDecimal notional){BigDecimal v=pendingImpacts.remove(eventId); return v!=null?v:notional.multiply(new BigDecimal("0.03"));}
  @Transactional public void recalculate(TradeEvent event,BigDecimal impact){
    ExposureSnapshot previous=snapshots.findByCounterpartyIdOrderByBusinessDateDesc(event.counterpartyId()).stream().findFirst().orElseThrow();
    LocalDate d=LocalDate.now();
    BigDecimal current=previous.getCurrentExposure().add(impact);
    BigDecimal pfe=previous.getPfe().add(impact.multiply(new BigDecimal("1.20")));
    BigDecimal ead=previous.getEad().add(impact.multiply(new BigDecimal("1.10")));
    BigDecimal cva=previous.getCva().add(impact.multiply(new BigDecimal("0.012")));
    snapshots.save(new ExposureSnapshot(event.counterpartyId(),d,current,pfe,ead,cva,previous.getCollateral(),previous.getApprovedLimit()));
    drivers.save(new ExposureDriver(event.counterpartyId(),d,"New "+event.product()+" trade "+event.tradeId(),impact));
  }
}
