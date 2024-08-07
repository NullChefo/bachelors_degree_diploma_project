#!/bin/bash

default="default"
#authService="auth"
gatewayService="gateway"
mailProcessService="mail-process"
mailSendService="mail-send"
userService="user"
carHistoryService="car-history"
authorizationService="authorization"
frontEnd="front-end"

socialMedia="social-media"

currentDirectory=$(pwd)

cd "$currentDirectory/$gatewayService" && kubectl --namespace=dev delete -f ./

cd "$currentDirectory/$mailProcessService" && kubectl --namespace=dev delete -f ./

cd "$currentDirectory/$mailSendService" && kubectl --namespace=dev delete -f ./

cd "$currentDirectory/$userService" && kubectl --namespace=dev delete -f ./

# cd "$currentDirectory/$carHistoryService" && kubectl --namespace=dev delete -f ./

cd "$currentDirectory/$authorizationService" && kubectl --namespace=dev delete -f ./

cd "$currentDirectory/$frontEnd" && kubectl --namespace=dev delete -f ./

cd "$currentDirectory/$socialMedia" && kubectl --namespace=dev delete -f ./
