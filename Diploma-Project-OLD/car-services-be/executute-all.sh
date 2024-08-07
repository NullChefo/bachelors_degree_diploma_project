#!/bin/bash


authService="auth-service"
gatewayService="gateway-service"
mailProcessService="mail-process-service"
mailSendService="mail-send-service"
userService="user-service"
carHistoryService="car-history-service"

cd "$(pwd)/$authService" && rm -rf ./target/  sh ./execute.sh && cd ..

cd "$(pwd)/$gatewayService" && rm -rf ./target/ && sh ./execute.sh && cd ..

cd "$(pwd)/$mailProcessService" && rm -rf ./target/ && sh ./execute.sh && cd ..

cd "$(pwd)/$mailSendService" && rm -rf ./target/ && sh ./execute.sh && cd ..

cd "$(pwd)/$userService" && rm -rf ./target/ && sh ./execute.sh && cd ..

cd "$(pwd)/$carHistoryService" && rm -rf ./target/ && sh ./execute.sh && cd ..
