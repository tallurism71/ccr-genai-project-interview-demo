# ADLC controls and pain points

| Stage | Typical CCR/GenAI pain point | Control in this reference |
|---|---|---|
| Requirements | Ambiguous risk methodology and data lineage | API/tool contracts; deterministic engine boundary |
| Design | Duplicate GenAI stacks | Reuse enterprise orchestrator/model gateway/RAG |
| Development | Boilerplate and inconsistent patterns | Copilot-assisted code with mandatory PR review |
| Data | Missing/stale counterparty, netting, collateral data | Domain APIs, event contracts, reconciliation hooks |
| Testing | Unit tests miss financial correctness | Golden CCR datasets + deterministic calculation validation |
| GenAI testing | Hallucination/tool misuse | Grounded evidence, RAG evaluation, tool-selection tests |
| Security | LLM bypasses entitlements | OIDC/RBAC at API/tool boundary |
| Release | Large risky changes | Containers, CI gates, EKS rolling deployment |
| Operations | Hard to explain AI output | Request/tool/model/source audit and observability |
