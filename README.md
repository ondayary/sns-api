## 서비스 소개

이 프로젝트는 회원가입, 로그인, 피드, 댓글, 좋아요, 알림 기능을 구현한 SNS(Social-Network-Service)를 제공하는 웹 사이트 입니다.

<br>

## 개발 환경

- **Java** : Java 11
- **Editor** : Intellij Ultimate
- **Build** : Gradle 7.5.1
- **Framework** : Springboot 2.7.5
- **Database** : MySQL 8.0
- **CI & CD** : GitLab
- **Server** : AWS EC2
- **Deploy** : Docker
- **Library** : SpringBoot Web, Spring Data JPA, Lombok, Spring Security, Swagger

<br>

## 실행 방법

Environment Variable에 아래의 환경변수를 설정하고 실행합니다.

| 환경 변수 명 | 예제 |
| --- | --- |
| DB_HOST | jdbc:mysql://주소:3306/스키마 이름 |
| DB_USER | root |
| DB_PASSWORD | password |
| KEY | key |

<br>

## Swagger
http://ec2-54-180-145-236.ap-northeast-2.compute.amazonaws.com:8080/swagger-ui/

<br>

## 요구사항
- 인증 / 인가 필터 구현 <br>
- 상품 조회 / 수정 API 구현 <br>
- 상품 수정 테스트 코드 작성 <br>
- Swagger를 이용하여 상품 수정 API 문서 자동화 <br>
- develop 브랜치에 push 할 경우 AWS EC2 서버에 자동으로 배포 되도록 구현

<br>

## Endpoints
|   구분    |  HTTP  |                 URI                  |      설명       |
|:-------:|:------:|:------------------------------------:|:-------------:|
|  USER   |  POST  |          /api/v1/users/join          |     회원가입      |
|  USER   |  POST  |         /api/v1/users/login          |  로그인 및 토큰 발급  |
|  POST   |  GET   |            /api/v1/posts             |  게시글 리스트 조회   |
|  POST   |  GET   |        /api/v1/posts/{postId}        |   게시글 상세 조회   |
|  POST   |  POST  |            /api/v1/posts             |    게시글 등록     |
|  POST   |  PUT   |        /api/v1/posts/{postId}        |    게시글 수정     |
|  POST   | DELETE |        /api/v1/posts/{postId}        |    게시글 삭제     |
|  POST   |  GET   |           /api/v1/posts/my           |   마이 피드 조회    |
| COMMENT |  GET   |   /api/v1/posts/{postId}/comments    | 게시글 댓글 리스트 조회 |
| COMMENT |  POST  |   /api/v1/posts/{postId}/comments    |   게시글 댓글 생성   |
| COMMENT |  PUT   | /api/v1/posts/{postId}/comments/{id} |   게시글 댓글 수정   |
| COMMENT | DELETE | /api/v1/posts/{postId}/comments/{id} |   게시글 댓글 삭제   |
|  GOOD   |  GET   |     /api/v1/posts/{postId}/likes     | 게시글 좋아요 개수 조회 |
|  GOOD   |  POST  |     /api/v1/posts/{postId}/likes     |  게시글 좋아요 생성   |
|  ALARM  |  GET   |            /api/v1/alarms            |   알람 리스트 조회   |

<br>

## Endpoint Return Example
##### 회원가입: POST`/api​/v1​/users​/join`
###### 입력 폼 (JSON 형식)
```json
{
    "userName" : "user1",
    "password" : "user1234"
}
```
###### 리턴 (JSON 형식)
```json
{
    "resultCode": "SUCCESS",
    "result": {
        "userId": 1,
        "userName": "userName"
    }
}
```
<br>

##### 로그인: POST `api​/v1​/users​/login`
###### 입력 폼 (JSON 형식)
```json
{
    "userName" : "user1",
    "password" : "user1234"
}
```
###### 리턴 (JSON 형식)
```json
{
    "resultCode": "SUCCESS",
    "result": {
        "jwt": "eyJhbGciOiJIU"
    }
}
```
<br>

#####  게시글 조회: GET `​/api​/v1​/posts`<br>
http://localhost:8080/api/v1/posts
###### 리턴 (JSON 형식)
```json
{
    "resultCode": "SUCCESS",
    "result": {
    "content": [
        {
        "id": 2,
        "title": "hello-title",
        "body": "hello-body",
        "userName": "userName",
        "createdAt": "yyyy/mm/dd hh:mm:ss",
        "lastModifiedAt": "yyyy/mm/dd hh:mm:ss"
        }
    ],
    "pageable": {
        "sort": {
        "empty": true,
        "sorted": false,
        "unsorted": true
        },
        "offset": 0,
        "pageNumber": 0,
        "pageSize": 20,
        "paged": true,
        "unpaged": false
    },
        "last": false,
        "totalPages": 1,
        "totalElements": 1,
        "size": 20,
        "number": 0,
        "sort": {
            "empty": true,
            "sorted": false,
            "unsorted": true
        },
        "first": true,
        "numberOfElements": 1,
        "empty": false
    }
}
```
<br>

##### 게시글 상세 조회: GET `/api/v1/posts/{postsId}` <br>
http://localhost:8080/api/v1/posts/1
###### 리턴 (JSON 형식)
```json
{
    "resultCode": "SUCCESS",
    "result": {
    "id": 1,
    "title": "title1",
    "body": "body",
    "userName": "user1",
    "createdAt": "yyyy/mm/dd hh:mm:ss",
    "lastModifiedAt": "yyyy/mm/dd hh:mm:ss"
    }
}
```
<br>

##### 게시글 작성: POST `/api/v1/posts`
###### 입력 폼 (JSON 형식)
```json
{
  "title" : "title1",
  "body" : "body1"
}
```
###### 리턴 (JSON 형식)
```json
{
    "resultCode": "SUCCESS",
    "result": {
    "message": "포스트 등록 완료",
    "postId": 0
    }
}
```
<br>

##### 게시글 수정: PUT `/api/v1/posts/{postId}`
###### 입력 폼 (JSON 형식)
```json
{
    "title" : "modified title",
    "body" : "modified body"
}
```
###### 리턴 (JSON 형식)
```json
{
    "resultCode": "SUCCESS",
    "result": {
    "message": "포스트 수정 완료",
    "postId": 0
    }
}
```
<br>

##### 게시글 삭제: DELETE `/api/v1/posts/{postId}`
###### 입력 폼 (JSON 형식)
```json
{
    "id" : "post id"
}
```
###### 리턴 (JSON 형식)
```json
{
    "resultCode": "SUCCESS",
    "result": {
    "message": "포스트 삭제 완료",
    "postId": 0
    }
}
```
<br>

##### 댓글 조회: GET `/api/v1/posts/{postId}/comments`
```json
{
    "resultCode": "SUCCESS",
    "result": {
    "content": [
        {
        "id": 2,
        "comment": "comment2",
        "userName": "userName1",
        "postId": 1,
        "createdAt": "yyyy/mm/dd hh:mm:ss"
        },
        {
        "id": 1,
        "comment": "comment2",
        "userName": "userName1",
        "postId": 1,
        "createdAt": "yyyy/mm/dd hh:mm:ss"
        }
    ],
    "pageable": {
      "sort": {
        "empty": true,
        "sorted": false,
        "unsorted": true
      },
      "offset": 0,
      "pageSize": 10,
      "pageNumber": 0,
      "unpaged": false,
      "paged": true
    },
    "last": true,
    "totalElements": 2,
    "totalPages": 1,
    "size": 10,
    "number": 0,
    "sort": {
      "empty": true,
      "sorted": false,
      "unsorted": true
    },
    "first": true,
    "numberOfElements": 2,
    "empty": false
    }
}
```
<br>

##### 댓글 작성: POST `/api/v1/posts/{postId}/comments`
```json
{
    "resultCode": "SUCCESS",
    "result":{
    "id": 1,
    "comment": "comment",
    "userName": "userName",
    "postId": 1,
    "createdAt": "yyyy/mm/dd hh:mm:ss"
    }
}
```
<br>

##### 댓글 수정: PUT `/api/v1/posts/{postId}/comments/{id}`
```json
{
    "resultCode": "SUCCESS",
    "result":{
    "id": 1,
    "comment": "comment",
    "userName": "userName",
    "postId": 1,
    "createdAt": "yyyy/mm/dd hh:mm:ss",
    "modifiedAt": "yyyy/mm/dd hh:mm:ss"
    }
}
```
<br>

##### 댓글 삭제: DELETE `/api/v1/posts/{postId}/comments/{id}`
```json
{
    "resultCode": "SUCCESS",
    "result":{
    "message": "댓글 삭제 완료",
    "id": 1
    }
}
```
<br>

##### 좋아요 생성: POST `/api/v1/posts/{postId}/likes`
```json
{
    "resultCode":"SUCCESS",
    "result": "좋아요를 눌렀습니다."
}
```
<br>

##### 좋아요 개수: GET `/api/v1/posts/{postId}/likes`
```json
{
    "resultCode":"SUCCESS",
    "result": 0
}
```
<br>

##### 마이 피드: GET `/api/v1/posts/my`
```json
{
    "resultCode": "SUCCESS",
    "result": {
    "content": [
        {
        "id": 2,
        "title": "title",
        "body": "body",
        "userName": "userName",
        "createdAt": "yyyy/mm/dd hh:mm:ss",
        "lastModifiedAt": "yyyy/mm/dd hh:mm:ss"
        },
        {
        "id": 1,
        "title": "title",
        "body": "body",
        "userName": "userName",
        "createdAt": "yyyy/mm/dd hh:mm:ss",
        "lastModifiedAt": "yyyy/mm/dd hh:mm:ss"
        }
    ],
    "pageable": {
      "sort": {
        "empty": true,
        "sorted": false,
        "unsorted": true
      },
      "offset": 0,
      "pageSize": 20,
      "pageNumber": 0,
      "paged": true,
      "unpaged": false
    },
    "last": true,
    "totalPages": 1,
    "totalElements": 2,
    "size": 20,
    "number": 0,
    "sort": {
      "empty": true,
      "sorted": false,
      "unsorted": true
    },
    "first": true,
    "numberOfElements": 2,
    "empty": false
    }
}

```
<br>

##### 알람 조회: GET `/api/v1/alarms`
```json
{
    "resultCode": "SUCCESS",
    "result": {
    "content": [
        {
        "id": 2,
        "alarmType": "NEW_LIKE_ON_POST",
        "fromUserId": 1,
        "targetId": 1,
        "text": "new like!",
        "createdAt": "yyyy/mm/dd hh:mm:ss"
        },
        {
        "id": 1,
        "alarmType": "NEW_COMMENT_ON_POST",
        "fromUserId": 1,
        "targetId": 1,
        "text": "new comment!",
        "createdAt": "yyyy/mm/dd hh:mm:ss"
        }
    ],
    "pageable": {
      "sort": {
        "empty": true,
        "sorted": false,
        "unsorted": true
      },
      "offset": 0,
      "pageNumber": 0,
      "pageSize": 20,
      "unpaged": false,
      "paged": true
    },
    "last": true,
    "totalPages": 1,
    "totalElements": 2,
    "size": 20,
    "number": 0,
    "sort": {
      "empty": true,
      "sorted": false,
      "unsorted": true
    },
    "first": true,
    "numberOfElements": 2,
    "empty": false
    }
}
```

## ERD
![](img/erd.png)