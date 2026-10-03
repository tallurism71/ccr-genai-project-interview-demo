# CCR GenAI enterprise architecture

## Runtime flow
Risk Analyst -> React UI -> Enterprise API/SSO -> Bank GenAI Orchestrator -> CCR GenAI Adapter/Tools -> deterministic CCR services + Enterprise RAG -> Enterprise Model Gateway -> self-hosted approved LLM -> grounded response.

## Event flow
Trading source -> Kafka/MSK `ccr.trade-events` -> validation/projection -> deterministic exposure recalculation -> exposure snapshot/driver services -> analyst tools.

## Control boundaries
1. LLM never owns official exposure, PFE, EAD, CVA, netting, collateral, or limit values.
2. Tool APIs enforce authorization independently of model instructions.
3. RAG should index only approved/versioned CCR content and return source metadata/citations.
4. Material actions remain in existing approval workflows; initial GenAI tools are read-only.
5. Enterprise platform owns model routing, generic guardrails, orchestration, audit, and common RAG infrastructure; CCR team owns domain tools, data contracts, domain evaluations and approved content.
