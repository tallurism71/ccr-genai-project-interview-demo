package com.bank.ccr.dto;
import java.util.List;
public record AskResponse(String answer, List<String> evidence, String model) {}
