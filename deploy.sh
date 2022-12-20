#!/bin/sh
DATASOURCE_URL=$1
DATASOURCE_USERNAME=$2
DATASOURCE_PASSWORD=$3
JWT_SECRET=$4
PORT=$5
GITLAB_USER=$6
PROJECT_NAME=$7

if [ $# != 7 ]; then
  echo "파라미터를 확인해주세요."
  echo "DATASOURCE_URL:$1"
  echo "DATASOURCE_USERNAME:$2"
  echo "DATASOURCE_PASSWORD:$3"
  echo "JWT_SECRET:$4"
  echo "ACCESS_PORT:$5"
  echo "GITLAB_USER:$6"
  echo "PROJECT_NAME:$7"
  exit
fi

CONTAINER=$(docker ps -aq --filter "name=$PROJECT_NAME")

if [ -n "$CONTAINER" ]; then
  echo "컨테이너 종료"

  docker stop $CONTAINER

  echo "컨테이너 삭제"

  docker rm $CONTAINER
fi

IMAGE=$(docker images --filter=reference="registry.gitlab.com/$GITLAB_USER/$PROJECT_NAME" -q)

if [ -n "$IMAGE" ]; then
  echo "이미지 삭제"

  docker rmi $IMAGE
fi

USING_PORT_ID=$(docker ps -aq --filter "expose=$PORT")

if [ -n "$USING_PORT_ID" ]; then
  echo "포트번호 $PORT 컨테이너 종료"

  docker stop $USING_PORT_ID

  echo "포트번호 $PORT 컨테이너 삭제"

  docker rm $USING_PORT_ID
fi

echo "docker pull start." &&
  docker pull registry.gitlab.com/$GITLAB_USER/$PROJECT_NAME&&
  echo "docker pull done." &&
  echo "docker container run" &&
  docker run --name $PROJECT_NAME -p $PORT:8080 -e SPRING_DATASOURCE_URL=$DATASOURCE_URL -e SPRING_DATASOURCE_PASSWORD=$DATASOURCE_PASSWORD -e SPRING_DATASOURCE_USERNAME=$DATASOURCE_USERNAME -e JWT_SECRET=$JWT_SECRET registry.gitlab.com/$GITLAB_USER/$PROJECT_NAME:latest