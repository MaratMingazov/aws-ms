aws cloudformation create-stack \
    --stack-name VPCStack \
    --template-url https://maratmingazovr.s3.amazonaws.com/VPC.template \
    --region us-east-1 \
    --parameters \
        ParameterKey=PrivateSubnet1CIDR,ParameterValue=10.0.10.0/24 \
        ParameterKey=PrivateSubnet2CIDR,ParameterValue=10.0.11.0/24 \
        ParameterKey=PublicSubnet1CIDR,ParameterValue=10.0.0.0/24 \
        ParameterKey=PublicSubnet2CIDR,ParameterValue=10.0.1.0/24 \
        ParameterKey=CIDR,ParameterValue=10.0.0.0/16
aws cloudformation delete-stack --stack-name VPCStack


aws cloudformation create-stack \
    --stack-name LoadBalancerStack \
    --template-url https://maratmingazovr.s3.amazonaws.com/LoadBalancer.template \
    --region us-east-1 \
    --parameters \
        ParameterKey=ImportedVPCStack,ParameterValue=VPCStack \
        ParameterKey=LoadBalancerLogsBucketName,ParameterValue=maratmingazovr
aws cloudformation delete-stack --stack-name LoadBalancerStack


aws cloudformation create-stack \
    --stack-name InstanceStack \
    --template-url https://maratmingazovr.s3.amazonaws.com/Instance.template \
    --region us-east-1 \
    --parameters \
        ParameterKey=ImportedVPCStackName,ParameterValue=VPCStack \
        ParameterKey=InstanceType,ParameterValue=t2.micro \
        ParameterKey=KeyName,ParameterValue=keyPair
aws cloudformation delete-stack --stack-name InstanceStack

aws cloudformation create-stack \
    --stack-name DBStack \
    --capabilities CAPABILITY_NAMED_IAM \
    --template-url https://maratmingazovr.s3.amazonaws.com/DB.template \
    --region us-east-1 \
    --parameters \
        ParameterKey=ImportedVPCStack,ParameterValue=VPCStack
aws cloudformation delete-stack --stack-name DBStack


aws cloudformation create-stack \
    --stack-name ECSStack \
    --capabilities CAPABILITY_NAMED_IAM \
    --template-url https://maratmingazovr.s3.amazonaws.com/ECS.template \
    --region us-east-1 \
    --parameters \
        ParameterKey=ImportedVPCStack,ParameterValue=VPCStack
aws cloudformation delete-stack --stack-name ECSStack


aws cloudformation create-stack \
    --stack-name ServiceStack \
    --capabilities CAPABILITY_NAMED_IAM \
    --template-url https://maratmingazovr.s3.amazonaws.com/Service.template \
    --region us-east-1 \
    --parameters \
        ParameterKey=ImportedVPCStack,ParameterValue=VPCStack \
        ParameterKey=ImportedECSStack,ParameterValue=ECSStack \
        ParameterKey=ImportedLoadBalancerStack,ParameterValue=LoadBalancerStack \
        ParameterKey=ImportedDBStack,ParameterValue=DBStack \
        ParameterKey=ImageUrl,ParameterValue=registry-1.docker.io/maratmingazovr/aws-ms:v.1.0.0
aws cloudformation delete-stack --stack-name ServiceStack