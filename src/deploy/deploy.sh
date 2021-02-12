cd `dirname $0`

sam package --template-file sam.native.yaml --output-template-file packaged.yaml --s3-bucket cocosoft --s3-prefix rateconv

sam deploy --template-file packaged.yaml --capabilities CAPABILITY_IAM --stack-name rateconv