# End-to-End Interview Demo Runbook

## Story
A risk analyst monitors ABC Bank. A new $100M interest-rate swap is booked. The trade event flows through Kafka, the CCR service recalculates exposure, and the GenAI assistant explains the updated exposure using governed risk data and RAG context.

## Run
```bash
docker compose up --build
```
Open http://localhost:3000.

## Demo sequence
1. Point out the starting Current Exposure, PFE, EAD, CVA, collateral and limit utilization.
2. Click **Book Trade & Recalculate**. This publishes `TRADE_BOOKED` to `ccr.trade-events` when Kafka is enabled.
3. The `TradeEventConsumer` invokes the deterministic demo recalculation service and persists a new exposure snapshot and driver.
4. Refresh occurs automatically. Show the increased exposure and the new trade driver.
5. Click **Explain Exposure Change**. The GenAI service retrieves current CCR metrics, exposure drivers and RAG knowledge, then sends only that governed context to the model adapter.
6. Emphasize the control boundary: the risk engine calculates; GenAI explains.

## Architecture talking points
- React/TypeScript analyst experience.
- Spring Boot domain APIs and GenAI adapter.
- Kafka/MSK-compatible event contract decouples trade capture from risk calculation.
- PostgreSQL is the local operational store; enterprise deployments can use the bank-approved data platform.
- Existing bank orchestration framework owns model routing, generic guardrails and workflow orchestration.
- CCR team owns domain tools, risk APIs, approved RAG content and domain evaluations.
- LLM endpoint is model-independent; local mock mode keeps the demo runnable without a GPU.
- OIDC/RBAC profile is available for enterprise integration.

## Production substitutions
Replace the demo recalculation formula with validated CCR/PFE/EAD/CVA engines; use durable event idempotency; source collateral/netting from authoritative systems; connect enterprise orchestration/RAG; add schema registry, DLQ/replay, tracing, data-quality controls and model-risk approvals.
