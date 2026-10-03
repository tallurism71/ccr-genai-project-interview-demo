terraform { required_version=">= 1.6" required_providers { aws={source="hashicorp/aws" version="~> 5.0"} } }
provider "aws" { region=var.region }
# Reference scaffold: production banks normally consume approved enterprise VPC/EKS/MSK/RDS modules.
resource "aws_ecr_repository" "backend" { name="ccr-genai-backend" image_scanning_configuration { scan_on_push=true } }
resource "aws_ecr_repository" "frontend" { name="ccr-genai-frontend" image_scanning_configuration { scan_on_push=true } }
resource "aws_cloudwatch_log_group" "app" { name="/bank/ccr-genai" retention_in_days=90 }
output "backend_ecr" { value=aws_ecr_repository.backend.repository_url }
output "frontend_ecr" { value=aws_ecr_repository.frontend.repository_url }
