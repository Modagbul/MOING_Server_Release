DOCKER_APP_NAME=meetup
DOCKER_USERNAME=modagbul

# 현재 실행 중인 컨테이너를 확인 (blue 또는 green)
EXIST_BLUE=$(docker ps --filter name=${DOCKER_APP_NAME}-blue --filter status=running -q)
EXIST_GREEN=$(docker ps --filter name=${DOCKER_APP_NAME}-green --filter status=running -q)

# 둘 다 실행 중이지 않을 경우 blue 실행
if [ -z "$EXIST_BLUE" ] && [ -z "$EXIST_GREEN" ]; then
    echo "No containers running. Starting blue up"
    docker run -d --name ${DOCKER_APP_NAME}-blue -p 8081:8080 ${DOCKER_USERNAME}/moing:blue
    BEFORE_COMPOSE_COLOR="green"
    AFTER_COMPOSE_COLOR="blue"
elif [ -z "$EXIST_BLUE" ]; then
    echo "blue up"
    docker run -d --name ${DOCKER_APP_NAME}-blue -p 8081:8080 ${DOCKER_USERNAME}/moing:blue
    BEFORE_COMPOSE_COLOR="green"
    AFTER_COMPOSE_COLOR="blue"
else
    echo "green up"
    docker run -d --name ${DOCKER_APP_NAME}-green -p 8082:8080 ${DOCKER_USERNAME}/moing:green
    BEFORE_COMPOSE_COLOR="blue"
    AFTER_COMPOSE_COLOR="green"
fi

sleep 10

# 새로운 컨테이너가 제대로 실행되었는지 확인
EXIST_AFTER=$(docker ps --filter name=${DOCKER_APP_NAME}-${AFTER_COMPOSE_COLOR} --filter status=running -q)
if [ -n "$EXIST_AFTER" ]; then
    # nginx.config를 컨테이너에 맞게 변경해주고 reload 한다
    cp ./nginx.${AFTER_COMPOSE_COLOR}.conf /etc/nginx/nginx.conf
    sudo nginx -s reload

    # 이전 컨테이너 종료
    docker stop ${DOCKER_APP_NAME}-${BEFORE_COMPOSE_COLOR}
    docker rm ${DOCKER_APP_NAME}-${BEFORE_COMPOSE_COLOR}
    echo "$BEFORE_COMPOSE_COLOR down"
else
    # Docker logs
    LOGS=$(docker logs ${DOCKER_APP_NAME}-${AFTER_COMPOSE_COLOR} 2>&1)
    echo "Error: ${DOCKER_APP_NAME}-${AFTER_COMPOSE_COLOR} failed to start."
    echo "$LOGS"
fi
