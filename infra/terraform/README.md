# Terraform deployment scaffold
This intentionally creates only application-owned ECR repositories and logging. In a bank, VPC, EKS, MSK, RDS/Aurora, KMS, IAM, Secrets Manager, ingress/WAF and private DNS should normally come from approved enterprise modules. Supply those outputs to the Kubernetes deployment rather than creating shadow infrastructure.
