package com.bank.ccr.repository;
import com.bank.ccr.model.ExposureSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.Optional;
import java.util.List;
public interface ExposureSnapshotRepository extends JpaRepository<ExposureSnapshot,Long> {
  Optional<ExposureSnapshot> findByCounterpartyIdAndBusinessDate(String counterpartyId, LocalDate businessDate);
  List<ExposureSnapshot> findByCounterpartyIdOrderByBusinessDateDesc(String counterpartyId);
}
