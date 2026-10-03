package com.bank.ccr.service;
import com.bank.ccr.dto.ExposureResponse;
import com.bank.ccr.model.ExposureDriver;
import com.bank.ccr.model.ExposureSnapshot;
import com.bank.ccr.repository.ExposureDriverRepository;
import com.bank.ccr.repository.ExposureSnapshotRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
@Service
public class ExposureService {
  private final ExposureSnapshotRepository snapshots; private final ExposureDriverRepository drivers;
  public ExposureService(ExposureSnapshotRepository snapshots, ExposureDriverRepository drivers){this.snapshots=snapshots;this.drivers=drivers;}
  public ExposureResponse latest(String cp){
    ExposureSnapshot s=snapshots.findByCounterpartyIdOrderByBusinessDateDesc(cp).stream().findFirst().orElseThrow();
    BigDecimal pct=s.getApprovedLimit().signum()==0?BigDecimal.ZERO:s.getCurrentExposure().multiply(BigDecimal.valueOf(100)).divide(s.getApprovedLimit(),2,RoundingMode.HALF_UP);
    return new ExposureResponse(cp,s.getBusinessDate(),s.getCurrentExposure(),s.getPfe(),s.getEad(),s.getCva(),s.getCollateral(),s.getApprovedLimit(),pct);
  }
  public List<ExposureDriver> drivers(String cp, LocalDate date){return drivers.findByCounterpartyIdAndBusinessDateOrderByAmountDesc(cp,date);}
}
