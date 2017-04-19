## 构建eureka docker镜像   

    # 本地docker构建
    DOCKER_HOST=unix:///var/run/docker.sock mvn clean package docker:build docker:push
    
    # 远程docker构建
    DOCKER_HOST=tcp://<host>:2375 mvn clean package docker:build docker:push

## Debug Docker cluster

    HOST_IP_ADDRESS="${给EUREKA客户端访问的IP地址}"

    if [[ -z "${HOST_IP_ADDRESS}" ]]; then HOST_IP_ADDRESS="127.0.0.1"; fi
    EUREKA_PEER1_IP_ADDRESS="${HOST_IP_ADDRESS}"
    EUREKA_PEER1_PORT="8761"
    EUREKA_PEER2_IP_ADDRESS="${HOST_IP_ADDRESS}"
    EUREKA_PEER2_PORT="8762"
    EUREKA_PEER3_IP_ADDRESS="${HOST_IP_ADDRESS}"
    EUREKA_PEER3_PORT="8763"

    PEER="peer1"
    EUREKA_INSTANCE_NONSECUREPORT="${EUREKA_PEER1_PORT}"

    PEER="peer2"
    EUREKA_INSTANCE_NONSECUREPORT="${EUREKA_PEER2_PORT}"

    PEER="peer3"
    EUREKA_INSTANCE_NONSECUREPORT="${EUREKA_PEER3_PORT}"

    docker run --rm -it \
    -v $(pwd):/root/app \
    -p ${EUREKA_INSTANCE_NONSECUREPORT}:8761 \
    --name eureka-${PEER} \
    --hostname eureka-${PEER}.infra.yixinonline.org \
    -e SPRING_PROFILES_ACTIVE=cloud,${PEER} \
    -e EUREKA_INSTANCE_HOSTNAME=eureka-${PEER}.infra.yixinonline.org \
    -e EUREKA_INSTANCE_PREFER_IP_ADDRESS=false \
    -e EUREKA_INSTANCE_IP_ADDRESS=${HOST_IP_ADDRESS} \
    -e CLOUD_CLIENT_HOSTNAME=eureka-${PEER}.infra.yixinonline.org \
    -e CLOUD_CLIENT_IP_ADDRESS=${HOST_IP_ADDRESS} \
    -e SERVER_PORT=8761 \
    -e EUREKA_INSTANCE_NONSECUREPORT=${EUREKA_INSTANCE_NONSECUREPORT} \
    -e EUREKA_PEER1_HOST=eureka-peer1.infra.yixinonline.org \
    -e EUREKA_PEER1_PORT=${EUREKA_PEER1_PORT} \
    -e EUREKA_PEER2_HOST=eureka-peer2.infra.yixinonline.org \
    -e EUREKA_PEER2_PORT=${EUREKA_PEER2_PORT} \
    -e EUREKA_PEER3_HOST=eureka-peer3.infra.yixinonline.org \
    -e EUREKA_PEER3_PORT=${EUREKA_PEER3_PORT} \
    --add-host eureka-peer1.infra.yixinonline.org:${EUREKA_PEER1_IP_ADDRESS} \
    --add-host eureka-peer2.infra.yixinonline.org:${EUREKA_PEER2_IP_ADDRESS} \
    --add-host eureka-peer3.infra.yixinonline.org:${EUREKA_PEER3_IP_ADDRESS} \
    registry.docker.local/java:oracle-8u101-jdk-alpine \
    "${PEER}"
