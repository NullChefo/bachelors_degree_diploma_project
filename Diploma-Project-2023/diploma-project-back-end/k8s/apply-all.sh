#!/bin/bash

default="default"
#authService="auth"
gatewayService="gateway"
mailProcessService="mail-process"
mailSendService="mail-send"
userService="user"
# carHistoryService="car-history"
authorizationService="authorization"
# zipkin="zipkin"
rabbitmq="rabbit"
#mysql="mysql"
postgres="postgres"
redis="redis"

loki="loki"
tempo="tempo"
promtail="promtail"
prometheus="prometheus"
grafana="grafana"
frontEnd="front-end"

socialMedia="social-media"

currentDirectory=$(pwd)

kubectl apply -f https://raw.githubusercontent.com/kubernetes/dashboard/v2.0.0/aio/deploy/recommended.yaml

sleep 5

cd "$currentDirectory/$default" && kubectl  apply -f ./

sleep 5

cd "$currentDirectory/$postgres" && kubectl --namespace=dev apply -f ./

sleep 10

cd "$currentDirectory/$grafana" && kubectl apply -f ./

sleep 10

cd "$currentDirectory/$rabbitmq" && kubectl --namespace=dev apply -f ./
cd "$currentDirectory/$tempo" && kubectl apply -f ./
cd "$currentDirectory/$promtail" && kubectl apply -f ./
cd "$currentDirectory/$prometheus" && kubectl apply -f ./
cd "$currentDirectory/$loki" && kubectl apply -f ./

cd "$currentDirectory/$redis" && kubectl --namespace=dev apply -f ./

#cd "$currentDirectory/$zipkin" && kubectl apply -f ./

echo "Waiting for containers"

sleep 20

#cd "$currentDirectory/$userService" && kubectl --namespace=dev apply -f ./

cd "$currentDirectory/$authorizationService" && kubectl --namespace=dev apply -f ./

cd "$currentDirectory/$gatewayService" && kubectl --namespace=dev apply -f ./

# cd "$currentDirectory/$mailProcessService" && kubectl --namespace=dev apply -f ./

cd "$currentDirectory/$mailSendService" && kubectl --namespace=dev apply -f ./

cd "$currentDirectory/$socialMedia" && kubectl --namespace=dev apply -f ./

# cd "$currentDirectory/$frontEnd" && kubectl --namespace=dev apply -f ./

#cd "$currentDirectory/$carHistoryService" && kubectl --namespace=dev apply  -f ./

echo -e "Your token:\n"

kubectl -n kubernetes-dashboard describe secret $(kubectl -n kubernetes-dashboard get secret | grep admin-user | awk '{print $1}')
