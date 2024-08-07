#!/bin/bash


authService="auth"
gatewayService="gateway"
mailProcessService="mail-process"
mailSendService="mail-send"
userService="user"
carHistoryService="car-history"

zipkin="zipkin"
rabbitmq="rabbit"
mysql="mysql"

cd "$(pwd)/$zipkin" && kubectl apply -f ./ && cd ..

cd "$(pwd)/$rabbitmq" && kubectl apply -f ./ && cd ..

cd "$(pwd)/$mysql" && kubectl apply -f ./ && cd ..


cd "$(pwd)/$authService" && kubectl apply -f ./ && cd ..

cd "$(pwd)/$gatewayService" && kubectl apply -f ./ && cd ..

cd "$(pwd)/$mailProcessService" && kubectl apply -f ./ && cd ..

cd "$(pwd)/$mailSendService" && kubectl apply -f ./ && cd ..

cd "$(pwd)/$userService" && kubectl apply -f ./ && cd ..

cd "$(pwd)/$carHistoryService" && kubectl apply -f ./ && cd ..
