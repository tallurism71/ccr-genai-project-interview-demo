package com.bank.ccr.controller;
import com.bank.ccr.dto.*;
import com.bank.ccr.service.DemoTradeService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/demo") @CrossOrigin(origins="*")
public class DemoController {
  private final DemoTradeService trades;
  public DemoController(DemoTradeService trades){this.trades=trades;}
  @PostMapping("/trades") public DemoEventResponse book(@Valid @RequestBody DemoTradeRequest request){return trades.book(request);}
}
