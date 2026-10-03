package com.bank.ccr.model;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
@Entity
public class ExposureSnapshot {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  private String counterpartyId;
  private LocalDate businessDate;
  private BigDecimal currentExposure;
  private BigDecimal pfe;
  private BigDecimal ead;
  private BigDecimal cva;
  private BigDecimal collateral;
  private BigDecimal approvedLimit;
  protected ExposureSnapshot() {}
  public ExposureSnapshot(String counterpartyId, LocalDate businessDate, BigDecimal currentExposure, BigDecimal pfe, BigDecimal ead, BigDecimal cva, BigDecimal collateral, BigDecimal approvedLimit){this.counterpartyId=counterpartyId;this.businessDate=businessDate;this.currentExposure=currentExposure;this.pfe=pfe;this.ead=ead;this.cva=cva;this.collateral=collateral;this.approvedLimit=approvedLimit;}
  public Long getId(){return id;} public String getCounterpartyId(){return counterpartyId;} public LocalDate getBusinessDate(){return businessDate;} public BigDecimal getCurrentExposure(){return currentExposure;} public BigDecimal getPfe(){return pfe;} public BigDecimal getEad(){return ead;} public BigDecimal getCva(){return cva;} public BigDecimal getCollateral(){return collateral;} public BigDecimal getApprovedLimit(){return approvedLimit;}
}
