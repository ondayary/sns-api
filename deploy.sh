#!/bin/sh
DATASOURCE_URL=$1
DATASOURCE_USERNAME=$2
DATASOURCE_PASSWORD=$3
JWT_SECRET=$4
PORT=$5
GITLAB_USER=$6
PROJECT_NAME=$7

if [ $# != 7 ]; then # $:변수 #:숫자 "변수의 숫자가 7이 아닐경우에 다음 것을 실행해라"
  echo "파라미터를 확인해주세요."
  echo "DATASOURCE_URL:$1"
  echo "DATASOURCE_USERNAME:$2"
  echo "DATASOURCE_PASSWORD:$3"
  echo "JWT_SECRET:$4"
  echo "ACCESS_PORT:$5"
  echo "GITLAB_USER:$6"
  echo "PROJECT_NAME:$7" # println
  exit # 함수종료
fi #finally

CONTAINER=$(docker ps -aq --filter "name=$PROJECT_NAME")

if [ -n "$CONTAINER" ]; then # container가 존재하면 다음을 실행해라
  echo "컨테이너 종료"

  docker stop $CONTAINER

  echo "컨테이너 삭제"

  docker rm $CONTAINER
fi

IMAGE=$(docker images --filter=reference="registry.gitlab.com/$GITLAB_USER/$PROJECT_NAME" -q)

if [ -n "$IMAGE" ]; then # -n: 존재한다면
  echo "이미지 삭제"

  docker rmi $IMAGE
fi

USING_PORT_ID=$(docker ps -aq --filter "expose=$PORT") # 사용되는 포트 찾아가서 종료, 삭제

if [ -n "$USING_PORT_ID" ]; then
  echo "포트번호 $PORT 컨테이너 종료"

  docker stop $USING_PORT_ID

  echo "포트번호 $PORT 컨테이너 삭제"

  docker rm $USING_PORT_ID
fi

echo "docker pull start." && # 예전:git pull start 바뀐이유: gitlab runner가 image를 만들어주기 때문
  docker pull registry.gitlab.com/$GITLAB_USER/$PROJECT_NAME &&
  echo "docker pull done." && # &&: 앞에 작업이 끝나야 다음 작업으로 넘어간다.(체인으로 엮는다) 안끝나면? 이프로세서 자체가 종료되어 버린다.
  echo "docker container run" && # 버전: latest로 한 개 고정
  docker run --name $PROJECT_NAME -p $PORT:8080 -e SPRING_DATASOURCE_URL=$DATASOURCE_URL -e SPRING_DATASOURCE_PASSWORD=$DATASOURCE_PASSWORD -e SPRING_DATASOURCE_USERNAME=$DATASOURCE_USERNAME -e JWT_SECRET=$JWT_SECRET registry.gitlab.com/$GITLAB_USER/$PROJECT_NAME:latest