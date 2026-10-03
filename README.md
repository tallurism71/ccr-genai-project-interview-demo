# CCR GenAI Reference Project

Deployable reference implementation for a bank Counterparty Credit Risk analyst assistant. It assumes the bank already owns an enterprise GenAI orchestration framework. The included `/api/genai/ask` path is a local adapter/demo seam; replace or route it through the bank framework in production.

## Components
- React + TypeScript analyst UI
- Java 17 / Spring Boot CCR APIs and GenAI adapter
- PostgreSQL operational store with seeded CCR sample data
- Deterministic exposure/PFE/EAD/CVA values and exposure drivers
- Pluggable LLM adapter: `mock` or OpenAI-compatible self-hosted inference (for example vLLM)
- Docker Compose for local deployment
- Health endpoint: `/actuator/health`

## Run locally
Prerequisite: Docker Desktop / Docker Engine with Compose.

```bash
docker compose up --build
```

Open:
- UI: http://localhost:3000
- API: http://localhost:8080/api/counterparties/CP-001/exposure
- Health: http://localhost:8080/actuator/health

## Example APIs

```bash
curl http://localhost:8080/api/counterparties/CP-001/exposure

curl -X POST http://localhost:8080/api/genai/ask \
  -H 'Content-Type: application/json' \
  -d '{"question":"Why did exposure increase today?","counterpartyId":"CP-001"}'
```

## Connect a self-hosted model
Set the backend environment variables:

```text
LLM_MODE=openai-compatible
LLM_BASE_URL=http://your-vllm-host:8000/v1
LLM_MODEL=your-approved-model
```

The backend calls `/chat/completions`. In a bank deployment, the preferred flow is:

```text
UI -> Bank Enterprise Orchestration Framework -> CCR Tool/Adapter APIs
                                           -> Enterprise RAG
                                           -> Enterprise Model Gateway
```

Do not allow the model to become the authoritative calculator for exposure, PFE, EAD, CVA, collateral, netting, or limit values.

## Enterprise orchestration integration contract
Register governed tools that map to these CCR endpoints/capabilities:
- `getCurrentExposure(counterpartyId)`
- `getExposureDrivers(counterpartyId, businessDate)`
- `getPFE(counterpartyId)`
- `getEAD(counterpartyId)`
- `getCVA(counterpartyId)`
- `getLimitUtilization(counterpartyId)`

The enterprise framework should own authentication context propagation, authorization, prompt policy, guardrails, model routing, RAG, observability and audit.

## Production hardening backlog
This sample intentionally avoids pretending to implement bank controls that depend on your institution. Before production add:
- Enterprise SSO/OIDC and service identity
- mTLS/private API connectivity
- Fine-grained RBAC/ABAC and entitlements
- Secrets manager and KMS-backed encryption
- Immutable audit events and SIEM integration
- Schema migrations with Flyway/Liquibase instead of `ddl-auto`
- Enterprise observability and trace IDs
- Resilience: retries, circuit breakers, timeouts, bulkheads
- RAG source citations and approved-document versioning
- Prompt-injection / data-loss controls from the bank framework
- Golden-dataset CCR validation and GenAI evaluation suites
- CI/CD gates: SAST, SCA, secrets scan, container scan, IaC scan
- Kafka/MSK event ingestion and authoritative upstream source adapters

## Project structure

```text
ccr-genai-project/
  backend/          Spring Boot CCR services
  frontend/         React analyst UI
  infra/            placeholder for bank-specific AWS/IaC modules
  docker-compose.yml
  README.md
```

## Enterprise extension added
This version adds production-oriented integration seams while preserving local startup:
- Kafka-compatible trade event consumer (`ccr.trade-events`); local Compose uses Apache Kafka, AWS maps to MSK.
- Collateral and netting-set domain models/APIs.
- Enterprise RAG adapter with `mock` and `enterprise` modes; production endpoint is configured with `RAG_BASE_URL`.
- Spring Security with local open mode and OIDC resource-server mode. Production roles: `CCR_ANALYST` / `CCR_ADMIN` (adapt JWT authority mapping to your IdP claims).
- Kubernetes deployment reference for EKS.
- Terraform scaffold for application-owned ECR/logging, intentionally expecting approved bank modules for VPC/EKS/MSK/RDS/KMS/IAM.
- CI workflow example.

### New APIs
`GET /api/counterparties/{id}/collateral`
`GET /api/counterparties/{id}/netting-sets`

### Production configuration
Set `SECURITY_MODE=oidc`, `OIDC_ISSUER_URI`, `KAFKA_ENABLED=true`, `KAFKA_BOOTSTRAP_SERVERS` (MSK brokers), `RAG_MODE=enterprise`, `RAG_BASE_URL`, plus the existing model-gateway variables. Prefer workload identity/IRSA and Secrets Manager over static AWS credentials.
For OIDC deployment also set Spring's standard `SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_ISSUER_URI` to the bank IdP issuer. The sample intentionally does not ship a local identity provider.

## End-to-end demo
The UI now includes an interview-ready scenario: **book IRS trade → Kafka event → CCR recalculation → exposure driver → GenAI explanation**.
See `docs/DEMO-RUNBOOK.md` for the walkthrough and production substitutions.
