package com.bank.ccr.service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import java.util.*;
@Service
public class LlmClient {
  private final String mode, baseUrl, model; private final ObjectMapper mapper = new ObjectMapper();
  public LlmClient(@Value("${app.llm.mode}") String mode,@Value("${app.llm.base-url}") String baseUrl,@Value("${app.llm.model}") String model){this.mode=mode;this.baseUrl=baseUrl;this.model=model;}
  public String model(){ return "mock".equalsIgnoreCase(mode)?"mock-grounded-explainer":model; }
  public String generate(String system, String user){
    if("mock".equalsIgnoreCase(mode)) return "Grounded summary: " + user;
    RestClient client=RestClient.builder().baseUrl(baseUrl).build();
    Map<String,Object> body=Map.of("model",model,"temperature",0.1,"messages",List.of(Map.of("role","system","content",system),Map.of("role","user","content",user)));
    String raw=client.post().uri("/chat/completions").contentType(MediaType.APPLICATION_JSON).body(body).retrieve().body(String.class);
    try { JsonNode n=mapper.readTree(raw); return n.at("/choices/0/message/content").asText(); } catch(Exception e){ throw new IllegalStateException("Invalid LLM response",e); }
  }
}
