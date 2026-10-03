package com.bank.ccr.config;
import com.bank.ccr.model.*;
import com.bank.ccr.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.math.BigDecimal;
import java.time.LocalDate;
@Configuration
public class SeedData {
  @Bean CommandLineRunner seed(CounterpartyRepository cp, ExposureSnapshotRepository snapshots, ExposureDriverRepository drivers){return args->{
    if(cp.count()>0) return;
    cp.save(new Counterparty("CP-001","ABC Bank","LEI-ABC-001","ABC Financial Group","US"));
    LocalDate d=LocalDate.now();
    snapshots.save(new ExposureSnapshot("CP-001",d,new BigDecimal("150000000"),new BigDecimal("185000000"),new BigDecimal("172000000"),new BigDecimal("2400000"),new BigDecimal("32000000"),new BigDecimal("200000000")));
    drivers.save(new ExposureDriver("CP-001",d,"Interest Rate Swaps",new BigDecimal("14000000")));
    drivers.save(new ExposureDriver("CP-001",d,"FX Derivatives",new BigDecimal("8000000")));
    drivers.save(new ExposureDriver("CP-001",d,"Collateral Reduction",new BigDecimal("5000000")));
    drivers.save(new ExposureDriver("CP-001",d,"New Trades",new BigDecimal("3000000")));
    drivers.save(new ExposureDriver("CP-001",d,"Other / Offsetting Movements",new BigDecimal("-5000000")));
  };}
}
