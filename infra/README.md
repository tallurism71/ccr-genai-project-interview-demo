# AWS deployment mapping

This project is containerized so a bank platform team can deploy it to its approved AWS runtime.

Suggested mapping:
- Frontend: S3/CloudFront or container platform
- Backend: EKS or ECS/Fargate
- Database: Aurora PostgreSQL
- Events: MSK/Kafka
- Object knowledge sources: S3 / enterprise document platform
- Model inference: enterprise model gateway to self-hosted inference
- Orchestration: existing bank enterprise GenAI orchestration framework
- Secrets: AWS Secrets Manager / enterprise secret platform
- Encryption: KMS
- Logs/metrics: enterprise observability plus CloudWatch where approved

Do not embed proprietary bank orchestration APIs here. Add a bank-specific adapter module that implements the tool contracts documented in the root README.
