#!/bin/bash

default="default"

currentDirectory=$(pwd)

kubectl delete -f https://raw.githubusercontent.com/kubernetes/dashboard/v2.0.0/aio/deploy/recommended.yaml
sudo kubectl delete ns kubernetes-dashboard
sudo kubectl delete clusterrolebinding kubernetes-dashboard
sudo kubectl delete clusterrole kubernetes-dashboard

cd "$currentDirectory/$default" && kubectl delete -f ./
