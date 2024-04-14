To configure github action authenticate to AWS using OIDC temp credential instead of simple IAM user access key ID and secret access key is a best practice.

to begin, refer to https://github.com/aws-actions/configure-aws-credentials

then see this for steps to configure your IdP for github action in IAM, then create role for the IdP to assume.
https://docs.github.com/en/actions/deployment/security-hardening-your-deployments/configuring-openid-connect-in-amazon-web-services