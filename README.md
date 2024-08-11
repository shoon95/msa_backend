# msa_project

>  User - order - product의 도메인으로 이루어진 msa 구축 프로젝트입니다.

* **Eureka를 통한 서비스 등록 및 연결**:
  * MSA 환경에서 각 서비스 간의 원활한 통신을 위해 Eureka를 사용하여 서비스 등록과 디스커버리 기능을 구현했습니다. 이를 통해 여러 서비스가 서로를 인식하고 통신할 수 있도록 설정했습니다.
* **상품 서비스(Product) 복제 설정 및 로드밸런싱**:
  * 상품 서비스(Product)에는 복제를 설정하여 여러 인스턴스를 운영할 수 있도록 했습니다. 이를 통해 트래픽이 특정 인스턴스에 집중되지 않도록 로드밸런싱을 적용했습니다. 로드밸런싱은 FeignClient를 사용하여 구현하였으며, 클라이언트의 요청을 적절하게 분산 처리할 수 있도록 노력했습니다.
* **Zipkin을 통한 모니터링 및 트레이싱**:
  * 트래픽 증가로 인한 병목 현상을 모니터링하고 진단하기 위해 Zipkin을 사용하여 분산 추적을 구현했습니다. 이를 통해 Order와 Product 서비스 간의 호출을 추적하고, 문제 발생 시 병목 지점을 쉽게 식별할 수 있도록 노력했습니다.
* **인증 및 보안**:
  * Auth 서비스에서는 Spring Security를 사용하여 인증과 인가 기능을 구현했습니다. JWT(Json Web Token)를 활용하여 사용자 인증을 처리하며, 이 과정에서 Gateway에 필터를 추가하여 특정 경로 요청에 대해 JWT 검증을 수행했습니다.
* **설계 원칙**:
  * 객체 간의 느슨한 결합을 유지하여 각 서비스와 객체의 독립성을 최대화하기 위해 노력했습니다. 이를 통해 서비스와 객체 간의 의존성을 줄여, 서비스나 객체의 변경이나 확장이 다른 서비스에 영향을 주지 않도록 노력했습니다.
* **커스텀 예외 처리 및 일관된 응답 구조**:
  * 예외 처리를 명확하고 직관적으로 하기 위해 커스텀 예외 클래스를 정의하고, 이를 Global Exception Handler를 통해 관리했습니다. 성공 및 오류 응답 메시지의 형식을 통일하여, 클라이언트가 응답 데이터를 일관되게 처리할 수 있도록 노력했습니다.

API 하나를 만들 때도, Dto 하나를 만들 때도, 애노테이션 하나를 붙일 때도, 생성과 초기화를 진행 할 때마다 관습적으로 코딩을 하지 않고 최대한 유지 보수 관점, 다른 사람이 내 코드를 봤을 때 이해하기 쉽게 개발하려고 노력하였습니다. 이런 고민을 계속 하다보니 클린 아키텍처에 대해 궁금증이 많이 생기게 되었습니다. 단순 기능 구현 이외에 더 좋은 방법과 설계를 하기 위해 노력하겠습니다.

## Application

1. eureka-server : 19090
2. gateway-service : 19091
3. order-service : 19092
4. product-service(1) : 19093
5. product-service(2) : 19094
6. auth-service : 19095

## Database

DB : PostgreSql

Location : Localhost

port : 5432

username : sanghoon

password : 없음

### Databse - table

1. OrderService : msa_order(db)
   * orders
   * order_product
2. AuthService : msa_auth(db)
   * users
3. ProductService _ msa_product(db)
   * product

## API

1. [회원가입](#1.회원 가입)
2. [로그인](#2. 로그인)
3. [상품 생성](#3. 상품 생성)
4. [상품 조회](#4. 상품 조회)
5. [주문 생성](#5. 주문 생성)
6. [주문에 상품 추가](#6. 주문에 상품 추가)
7. [주문 조회](#7. 주문 조회)



### 1. 회원 가입

##### Request Systax

```
POST http://{SERVER_URL:PORT}/auth/signup \
	-H "Content-Type: application/json" \
	-d '{ \
				"username": "유저 아이디", \
				:password": "패스워드" \
			}'
```

| 메서드 | 요청 URL 예시                      |
| ------ | ---------------------------------- |
| POST   | http://Localhost:19091/auth/signup |

##### RequestHeader

* None

##### PathVariable

* None

##### RequestBody

| 파라미터 |  타입  | 필수여부 |     설명      |
| :------: | :----: | :------: | :-----------: |
| username | String |   필수   |  유저 아이디  |
| password | String |   필수   | 유저 패스워드 |

```json
{
    "username": "sjhty123",
    "password": "1234"
}

```

##### Response Syntax - success

```json
// 정상 응답
{
    "status": "success",
    "data": {
        "username": "sjhty123"
    },
    "message": "User created successfully"
}
```

##### Response syntax - error

```json
// 에러 응답 - 아이디 또는 패스워드 누락
{
    "status": "error",
    "data": null,
    "message": "Invalid username or password"
}

// 에러 응답 - 이미 존재하는 아이디
{
    "status": "error",
    "data": null,
    "message": "username already exist"
}
```

##### 

### 2. 로그인

##### Request Syntax

```
POST http://{SERVER_URL:PORT}/auth/signin \
	-H "Content-Type: application/json" \
	-d '{\
				"username": "유저 아이디", \
				"password": "유저 패스워드" \
			}'
```

| 메서드 | 요청 URL 예시                      |
| ------ | ---------------------------------- |
| POST   | http://Localhost:19091/auth/signin |

##### RequestHeader

* None

##### PathVariable

* None

##### RequestBody

| 파라미터 |  타입  | 필수여부 |     설명      |
| :------: | :----: | :------: | :-----------: |
| username | String |   필수   |  유저 아이디  |
| password | String |   필수   | 유저 패스워드 |

```json
{
    "username": "sjhty123",
    "password": "1234"
}
```

##### Response Syntax - success

```json
// 정상 응답
{
    "status": "success",
    "data": {
        "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6InNqaHR5MTIzIiwiaWF0IjoxNzIzNDAzODY4LCJleHAiOjE3MjM0MDcwNjh9.jGq9lbfZO6jXmuCn4jvTdO9eEC8Ha-FmnXxGDUPk6i4"
    },
    "message": "login success"
}
```

##### Response Syntax - error

```json
// 에러 응답 - 아이디 또는 패스워드 누락
{
    "status": "error",
    "data": null,
    "message": "Invalid username or password"
}

// 에러 응답 - 비밀번호 틀림
{
    "status": "error",
    "data": null,
    "message": "Invalid password"
}
```



### 3. 상품 생성

##### Request Systax

```
POST http://{SERVER_URL:PORT}/products \
	-H "Authorization: Bearer {ACCESS_TOKEN}" \
	-H "Content-type: application/json" \
	-d '{ \
				"name": "상품 이름", \
				"supplyPrice": 상품 가격 \
			}'
```

| 메서드 | 요청 URL 예시                   |
| ------ | ------------------------------- |
| POST   | http://localhost:19091/products |

##### RequestHeader

|   파라미터    |  타입  | 필수여부 |     설명     |
| :-----------: | :----: | :------: | :----------: |
| Authorization | String |   필수   | 인증 키(JWT) |

##### PathVariable

* None

##### RequestBody

|  파라미터   |  타입  | 필수여부 |   설명    |
| :---------: | :----: | :------: | :-------: |
|    name     | String |   필수   | 상품 이름 |
| supplyPrice |  Long  |   필수   |   가격    |

##### Response Syntax - success

```json
// 정상 응답
{
    "status": "success",
    "data": {
        "id": 2
    },
    "message": "Product added successfully"
}
```



### 4. 상품 조회

##### Request Systax

```
GET http://{SERVER_URL:PORT}/products/{PRODUCT_ID} \
	-H "Authorization: Bearer {ACCESS_TOKEN}" 
```

| 메서드 | 요청 URL 예시                     |
| ------ | --------------------------------- |
| GET    | http://localhost:19091/products/1 |

##### RequestHeader

|   파라미터    |  타입  | 필수여부 |     설명     |
| :-----------: | :----: | :------: | :----------: |
| Authorization | String |   필수   | 인증 키(JWT) |

##### PathVariable

|  파라미터  | 타입 | 필수여부 |    설명     |
| :--------: | :--: | :------: | :---------: |
| PRODUCT_ID | Long |   필수   | 상품 아이디 |

##### RequestBody

* None

##### Response Syntax - success

```json
// 정상 응답
{
    "status": "success",
    "data": {
        "id": 1,
        "name": null,
        "supplyPrice": 1234
    },
    "message": "Successfully retrieved product"
}
```

##### Response Syntax - error

```json
// 에러 응답 - 존재하지 않는 상품 ID로 조회
{
    "status": "error",
    "data": null,
    "message": "product does not exist"
}
```



### 5. 주문 생성

##### Request Systax

```
POST http://{SERVER_URL:PORT}/order \
	-H "Authorization: Bearer {ACCESS_TOKEN}" \
	-H "Content-Type: application/json" \
	-d '{\
				"name": "주문 이름", \
				"productIds": [상품 아이디, 상품 아이디] \
			}'
```

| 메서드 | 요청 URL 예시                |
| ------ | ---------------------------- |
| POST   | http://localhost:19091/order |

##### RequestHeader

|   파라미터    |  타입  | 필수여부 |     설명     |
| :-----------: | :----: | :------: | :----------: |
| Authorization | String |   필수   | 인증 키(JWT) |

##### PathVariable

* None

##### RequestBody

|  파라미터  |    타입    | 필수여부 |       설명       |
| :--------: | :--------: | :------: | :--------------: |
|    name    |   String   |   필수   |    주문 이름     |
| productIds | List<Long> |   필수   | 상품 아이디 목록 |

##### Response Syntax - success

```json
// 정상 요청
{
    "name": "테스트 주문",
    "productIds": [4,5,6]
}

// 정상 응답
{
    "status": "success",
    "data": {
        "orderName": "테스트 주문",
        "productName": [
            "product5",
            "product6",
            "product7"
        ]
    },
    "message": "Order created successfully"
}
```

##### Response Syntax - error

```json
// 에러 응답 - 존재하지 않는 상품으로 주문
{
    "status": "error",
    "data": null,
    "message": "product does not exist"
}
```



### 6. 주문에 상품 추가

##### Request Systax

```
PUT http://{SERVER_URL:PORT}/order{ORDER_ID} \
	-H "Authorization: Bearer {ACCESS_TOKEN}" \
	-H "Content-type: application/json" \
	-d '
				
			'

```

| 메서드 | 요청 URL 예시                  |
| ------ | ------------------------------ |
| GET    | http://localhost:19091/order/1 |

##### RequestHeader

|   파라미터    |  타입  | 필수여부 |     설명     |
| :-----------: | :----: | :------: | :----------: |
| Authorization | String |   필수   | 인증 키(JWT) |

##### PathVariable

| 파라미터 | 타입 | 필수여부 |    설명     |
| :------: | :--: | :------: | :---------: |
| ORDER_ID | Long |   필수   | 주문 아이디 |

##### RequestBody

* None

##### Response Syntax - success

```json
// 정상 응답
{
    "status": "success",
    "data": {
        "orderId": 1,
        "productIds": [
            1,
            2,
            3
        ]
    },
    "message": "Successfully retrieved order"
}
```



### 7. 주문 조회

##### Request Systax

```
POST http://{SERVER_URL:PORT}/order{ORDER_ID} \
	-H "Authorization: Bearer {ACCESS_TOKEN}" 
  -d '{ \
        "productId": "상품 아이디", \
   		 }'
```

| 메서드 | 요청 URL 예시                  |
| ------ | ------------------------------ |
| PUT    | http://localhost:19091/order/1 |

##### RequestHeader

|   파라미터    |  타입  | 필수여부 |     설명     |
| :-----------: | :----: | :------: | :----------: |
| Authorization | String |   필수   | 인증 키(JWT) |

##### PathVariable

| 파라미터 | 타입 | 필수여부 |    설명     |
| :------: | :--: | :------: | :---------: |
| ORDER_ID | Long |   필수   | 주문 아이디 |

##### RequestBody

| 파라미터  | 타입 | 필수여부 |    설명     |
| :-------: | :--: | :------: | :---------: |
| productId | Long |   필수   | 상품 아이디 |

##### Response Syntax - success

```json
// 정상 응답
{
    "status": "success",
    "data": {
        "name": "테스트 주문",
        "productName": "product5"
    },
    "message": "Product added to order successfully"
}
```

##### Response Syntax - error

```json
// 에러 응답 - 존재하지 않는 상품 추가
{
    "status": "error",
    "data": null,
    "message": "Product does not exist"
}
```



