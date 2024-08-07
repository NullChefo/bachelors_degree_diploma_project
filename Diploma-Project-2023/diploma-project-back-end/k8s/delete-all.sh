#!/bin/bash

default="default"
#authService="auth"
gatewayService="gateway"
mailProcessService="mail-process"
mailSendService="mail-send"
userService="user"
carHistoryService="car-history"
authorizationService="authorization"

loki="loki"
zipkin="zipkin"
rabbitmq="rabbit"
mysql="mysql"
postgres="postgres"
redis="redis"

tempo="tempo"
promtail="promtail"
prometheus="prometheus"
grafana="grafana"
frontEnd="front-end"

socialMedia="social-media"

currentDirectory=$(pwd)

#kubectl delete -f https://raw.githubusercontent.com/kubernetes/dashboard/v2.0.0/aio/deploy/recommended.yaml
#sudo kubectl delete ns kubernetes-dashboard
#sudo kubectl delete clusterrolebinding kubernetes-dashboard
#sudo kubectl delete clusterrole kubernetes-dashboard

cd "$currentDirectory/$redis" && kubectl --namespace=dev delete -f ./

cd "$currentDirectory/$rabbitmq" && kubectl --namespace=dev delete -f ./ 

cd "$currentDirectory/$postgres" && kubectl --namespace=dev delete -f ./

#cd "$currentDirectory/$mysql" && kubectl --namespace=dev delete -f ./ 

#cd "$currentDirectory/$authService" && kubectl --namespace=dev delete -f ./ 

cd "$currentDirectory/$gatewayService" && kubectl --namespace=dev delete -f ./ 

cd "$currentDirectory/$mailProcessService" && kubectl --namespace=dev delete -f ./ 

cd "$currentDirectory/$mailSendService" && kubectl --namespace=dev delete -f ./ 

cd "$currentDirectory/$userService" && kubectl --namespace=dev delete -f ./ 

cd "$currentDirectory/$carHistoryService" && kubectl --namespace=dev delete -f ./ 

cd "$currentDirectory/$authorizationService" && kubectl --namespace=dev delete -f ./

cd "$currentDirectory/$socialMedia" && kubectl --namespace=dev delete -f ./

cd "$currentDirectory/$frontEnd" && kubectl --namespace=dev delete -f ./

cd "$currentDirectory/$grafana" && kubectl delete -f ./
cd "$currentDirectory/$tempo" && kubectl delete -f ./
cd "$currentDirectory/$promtail" && kubectl delete -f ./
cd "$currentDirectory/$prometheus" && kubectl delete -f ./
cd "$currentDirectory/$zipkin" && kubectl delete -f ./
cd "$currentDirectory/$loki" && kubectl delete -f ./

#cd "$currentDirectory/$default" && kubectl delete -f ./
