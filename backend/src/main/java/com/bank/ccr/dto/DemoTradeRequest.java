package com.bank.ccr.dto;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
public record DemoTradeRequest(
  @NotBlank String counterpartyId,
  @NotBlank String product,
  @NotNull @Positive BigDecimal notional,
  @NotNull BigDecimal exposureImpact
) {}
