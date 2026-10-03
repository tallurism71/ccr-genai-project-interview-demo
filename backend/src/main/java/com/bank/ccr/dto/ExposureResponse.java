package com.bank.ccr.dto;
import java.math.BigDecimal;
import java.time.LocalDate;
public record ExposureResponse(String counterpartyId, LocalDate businessDate, BigDecimal currentExposure, BigDecimal pfe, BigDecimal ead, BigDecimal cva, BigDecimal collateral, BigDecimal approvedLimit, BigDecimal utilizationPct) {}
