package com.bank.ccr.model;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
@Entity
public class ExposureDriver {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  private String counterpartyId;
  private LocalDate businessDate;
  private String driver;
  private BigDecimal amount;
  protected ExposureDriver() {}
  public ExposureDriver(String counterpartyId, LocalDate businessDate, String driver, BigDecimal amount){this.counterpartyId=counterpartyId;this.businessDate=businessDate;this.driver=driver;this.amount=amount;}
  public String getCounterpartyId(){return counterpartyId;} public LocalDate getBusinessDate(){return businessDate;} public String getDriver(){return driver;} public BigDecimal getAmount(){return amount;}
}
