package com.bank.ccr.service;
import com.bank.ccr.dto.*;
import com.bank.ccr.model.ExposureDriver;
import org.springframework.stereotype.Service;
import java.util.*;
@Service
public class GenAiService {
  private final ExposureService exposureService; private final LlmClient llm; private final RagService rag;
  public GenAiService(ExposureService exposureService, LlmClient llm, RagService rag){this.exposureService=exposureService;this.llm=llm;this.rag=rag;}
  public AskResponse ask(AskRequest req){
    String cp = (req.counterpartyId()==null||req.counterpartyId().isBlank())?"CP-001":req.counterpartyId();
    ExposureResponse ex=exposureService.latest(cp);
    List<ExposureDriver> ds=exposureService.drivers(cp,ex.businessDate());
    List<String> evidence=new ArrayList<>();
    evidence.add("Current exposure="+ex.currentExposure()+", PFE="+ex.pfe()+", EAD="+ex.ead()+", CVA="+ex.cva()+", collateral="+ex.collateral()+", utilization="+ex.utilizationPct()+"%");
    ds.forEach(d->evidence.add(d.getDriver()+"="+d.getAmount()));
    List<String> knowledge=rag.retrieve(req.question()); knowledge.forEach(k->evidence.add("RAG: "+k)); String context=String.join("; ", evidence);
    String system="You are a bank CCR assistant. Use only supplied evidence. Never invent official risk values. State that risk engines are authoritative.";
    String answer=llm.generate(system,"Question: "+req.question()+" Evidence: "+context);
    return new AskResponse(answer,evidence,llm.model());
  }
}
