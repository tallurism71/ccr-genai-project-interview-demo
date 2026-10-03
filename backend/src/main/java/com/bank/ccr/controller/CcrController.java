package com.bank.ccr.controller;
import com.bank.ccr.dto.*;
import com.bank.ccr.model.Counterparty;
import com.bank.ccr.model.ExposureDriver;
import com.bank.ccr.repository.CounterpartyRepository;
import com.bank.ccr.service.ExposureService;
import com.bank.ccr.service.GenAiService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
@RestController @RequestMapping("/api") @CrossOrigin(origins="*")
public class CcrController {
  private final CounterpartyRepository cps; private final ExposureService exposure; private final GenAiService genai;
  public CcrController(CounterpartyRepository cps, ExposureService exposure, GenAiService genai){this.cps=cps;this.exposure=exposure;this.genai=genai;}
  @GetMapping("/counterparties") public List<Counterparty> counterparties(){return cps.findAll();}
  @GetMapping("/counterparties/{id}/exposure") public ExposureResponse exposure(@PathVariable String id){return exposure.latest(id);}
  @GetMapping("/counterparties/{id}/drivers") public List<ExposureDriver> drivers(@PathVariable String id,@RequestParam LocalDate date){return exposure.drivers(id,date);}
  @PostMapping("/genai/ask") public AskResponse ask(@Valid @RequestBody AskRequest request){return genai.ask(request);}
}
