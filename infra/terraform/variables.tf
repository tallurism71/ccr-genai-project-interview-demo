variable "region" { type=string default="us-east-1" }
variable "cluster_name" { type=string default="ccr-genai" }
variable "vpc_id" { type=string }
variable "private_subnet_ids" { type=list(string) }
