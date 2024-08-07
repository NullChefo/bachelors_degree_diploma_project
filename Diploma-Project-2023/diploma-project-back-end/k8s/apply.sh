#!/bin/bash

default="default"
#authService="auth"
gatewayService="gateway"
mailProcessService="mail-process"
mailSendService="mail-send"
userService="user"
# carHistoryService="car-history"
authorizationService="authorization"
frontEnd="front-end"

socialMedia="social-media"


currentDirectory=$(pwd)

cd "$currentDirectory/$default" && kubectl apply -f ./

#cd "$currentDirectory/$userService" && kubectl --namespace=dev apply -f ./

cd "$currentDirectory/$authorizationService" && kubectl --namespace=dev apply -f ./

sleep 10

cd "$currentDirectory/$gatewayService" && kubectl --namespace=dev apply -f ./

# cd "$currentDirectory/$mailProcessService" && kubectl --namespace=dev apply -f ./

cd "$currentDirectory/$mailSendService" && kubectl --namespace=dev apply -f ./

# cd "$currentDirectory/$carHistoryService" && kubectl --namespace=dev apply -f ./

# cd "$currentDirectory/$frontEnd" && kubectl --namespace=dev apply -f ./

cd "$currentDirectory/$socialMedia" && kubectl --namespace=dev apply -f ./

kubectl autoscale -n dev deployment mail-send --cpu-percent=50 --min=1 --max=2
