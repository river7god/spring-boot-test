#!/bin/bash

# 停止并删除现有容器（如果存在）
docker stop rozwork-admin-container || true
docker rm rozwork-admin-container || true

# 从 Docker Hub 拉取最新的镜像
docker pull rozwork-admin:latest
