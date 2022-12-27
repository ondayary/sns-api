# 프로젝트 소개
> 종합 프로젝트를 통해서 만들어볼 서비스는 “멋사스네스(MutsaSNS)”입니다. 

## * Swagger
http://ec2-54-180-145-236.ap-northeast-2.compute.amazonaws.com:8080/swagger-ui/

## * EndPoint
### userController
회원가입 `POST ​/api​/v1​/users​/join` <br>
로그인 `POST ​/api​/v1​/users​/login`

### postController
포스트 리스트 `GET ​/api​/v1​/posts` <br>
포스트 상세 `GET ​/api​/v1​/posts​/{postsId}` <br>
포스트 작성 `POST ​/api​/v1​/posts` <br>
포스트 수정 `PUT ​/api​/v1​/posts​/{id}` <br>
포스트 삭제 `DELETE ​/api​/v1​/posts​/{postId}`
<br>

## * ERD
![](img/erd.png)

## * 요구사항
> 인증 / 인가 필터 구현 <br>
> 상품 조회 / 수정 API 구현 <br> 
> 상품 수정 테스트 코드 작성 <br>
> Swagger를 이용하여 상품 수정 API 문서 자동화 <br>
> develop 브랜치에 push 할 경우 AWS EC2 서버에 자동으로 배포 되도록 구현

### * 회원가입
POST `/api​/v1​/users​/join`
- 입력 폼(JSON 형식)
```json
{
	"userName" : "user1",
	"password" : "user1234"
}
```
- 리턴 (JSON 형식)
```json
{
  "resultCode": "SUCCESS",
  "result": {
    "userId": 5,
    "userName": "test1"
  }
}
```

### * 로그인
POST `api​/v1​/users​/login`
- 입력 폼(JSON 형식)
```json
{
  "userName" : "user1",
  "password" : "user1234"
}
```
- 리턴 (JSON 형식)
```json
{
  "jwt": "eyJhbGciOiJIU"
}
```

### * 포스트 리스트
GET `​/api​/v1​/posts`<br>
http://localhost:8080/api/v1/posts
- 리턴 (JSON 형식)
```json
{"content":
[
  {"id":4,"title":"test","body":"body","userName":"test","createdAt":"2022-12-16T16:50:37.515952"},
  {"id":3,"title":"string","body":"string","userName":"kyeongrok","createdAt":"2022-12-16T15:13:19.663287"},
  {"id":1,"title":"title1","body":"body1","userName":"yeram_test1","createdAt":null},
  {"id":2,"title":"title1","body":"body1","userName":"yeram_test1","createdAt":null}],
  "pageable":
  {"sort":{"empty":false,"unsorted":false,"sorted":true},
    "offset":0,"pageNumber":0,"pageSize":20,"paged":true,"unpaged":false},
  "last":true,"totalElements":4,"totalPages":1,"size":20,"number":0,
  "sort":{"empty":false,"unsorted":false,"sorted":true},
  "numberOfElements":4,"first":true,"empty":false}
```

### * 포스트 상세
GET `/api/v1/posts/{postsId}`
http://localhost:8080/api/v1/posts/1
- 리턴 (JSON 형식)
```json
{
  "id" : 1,
  "title" : "title1",
  "body" : "body",
  "userName" : "user1",
  "createdAt" : yyyy-mm-dd hh:mm:ss,
  "lastModifiedAt" : yyyy-mm-dd hh:mm:ss
}
```

### * 포스트 등록
POST `/api/v1/posts`
- 입력 폼(JSON 형식)
```json
{
  "title" : "title1",
  "body" : "body1"
}
```
- 리턴 (JSON 형식)
```json
{
  "resultCode":"SUCCESS",
  "result":{
    "message":"포스트 등록 완료",
    "postId":0
  }
}
```
### * 포스트 수정
PUT `/api/v1/posts/{postId}`
- 입력 폼(JSON 형식)
```json
{
  "title" : "modified title",
  "body" : "modified body"
}
```
- 리턴 (JSON 형식)
```json
{
  "resultCode":"SUCCESS",
  "result":{
    "message":"포스트 수정 완료",
    "postId":0
  }
}
```

### * 포스트 삭제
DELETE `/api/v1/posts/{postId}`
- 입력 폼(JSON 형식)
```json
{
  "id" : "post id"
}
```
- 리턴 (JSON 형식)
```json
{
  "resultCode":"SUCCESS",
  "result":{
    "message":"포스트 삭제 완료",
    "postId":0
  }
}
```