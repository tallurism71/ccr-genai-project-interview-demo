package com.bank.ccr.repository;
import com.bank.ccr.model.ExposureDriver;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
public interface ExposureDriverRepository extends JpaRepository<ExposureDriver,Long> {
  List<ExposureDriver> findByCounterpartyIdAndBusinessDateOrderByAmountDesc(String counterpartyId, LocalDate businessDate);
}
