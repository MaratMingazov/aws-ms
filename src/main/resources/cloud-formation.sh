aws cloudformation create-stack \
    --stack-name WebServerVPCStack \
    --template-url https://maratmingazovr.s3.amazonaws.com/VPC.template \
    --region us-east-1 \
    --parameters \
        ParameterKey=PrivateSubnet1CIDR,ParameterValue=10.0.10.0/24 \
        ParameterKey=PrivateSubnet2CIDR,ParameterValue=10.0.11.0/24 \
        ParameterKey=PublicSubnet1CIDR,ParameterValue=10.0.0.0/24 \
        ParameterKey=PublicSubnet2CIDR,ParameterValue=10.0.1.0/24 \
        ParameterKey=VPCCIDR,ParameterValue=10.0.0.0/16


aws cloudformation delete-stack --stack-name WebServerVPCStack

aws cloudformation create-stack \
    --stack-name WebServerInstanceStack \
    --template-url https://maratmingazovr.s3.amazonaws.com/Instance.template \
    --region us-east-1 \
    --parameters \
        ParameterKey=ImportedStackName,ParameterValue=WebServerVPCStack \
        ParameterKey=InstanceType,ParameterValue=t2.micro \
        ParameterKey=KeyName,ParameterValue=keyPair

aws cloudformation delete-stack --stack-name WebServerInstanceStack