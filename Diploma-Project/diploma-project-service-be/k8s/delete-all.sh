#!/bin/bash


authService="auth"
gatewayService="gateway"
mailProcessService="mail-process"
mailSendService="mail-send"
userService="user"
carHistoryService="car-history"
authorizationService="authorization"

zipkin="zipkin"
rabbitmq="rabbit"
mysql="mysql"
postgres="postgres"


# cd "$(pwd)/$zipkin" && kubectl delete -f ./ && cd ..

# cd "$(pwd)/$rabbitmq" && kubectl delete -f ./ && cd ..

# cd "$(pwd)/$mysql" && kubectl delete -f ./ && cd ..

# cd "$(pwd)/postgres" && kubectl delete -f ./ && cd ..


cd "$(pwd)/$authService" && kubectl delete -f ./ && cd ..

cd "$(pwd)/$gatewayService" && kubectl delete -f ./ && cd ..

cd "$(pwd)/$mailProcessService" && kubectl delete -f ./ && cd ..

cd "$(pwd)/$mailSendService" && kubectl delete -f ./ && cd ..

cd "$(pwd)/$userService" && kubectl delete -f ./ && cd ..

cd "$(pwd)/$carHistoryService" && kubectl delete -f ./ && cd ..

cd "$(pwd)/$authorizationService" && kubectl delete -f ./ && cd ..
