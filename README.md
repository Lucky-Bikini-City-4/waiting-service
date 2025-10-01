### TunaHub - 물류 관리 및 배송 시스템

<div align="center">
  <h1>TunaHub - 물류 관리 및 배송 시스템</h1>
  <p>🚚 MSA 기반 B2B 물류 관리 및 배송 시스템 🚚</p>
</div>

<img width="1792" height="1024" alt="image" src="https://github.com/user-attachments/assets/9c2467cc-1df1-4ace-8a85-576941fe3b21" />

<br/>

<div align="center">
  <a href="">홈페이지</a>
  &nbsp; | &nbsp;
  <!-- <a href="">Swagger</a>
   &nbsp; | &nbsp; -->
  <a href="https://www.notion.so/teamsparta/2402dc3ef5148024a920c918967274c7?source=copy_link">Notion</a>
</div>

---

## 0. 목차

1. [프로젝트 소개](#1)
2. [팀원 소개](#2)
3. [개발 일정](#3)
4. [기술 스택](#4)
5. [라이브러리 사용 이유](#5)
6. [컨벤션](#6)
7. [브랜치 및 디렉토리 구조](#7)
8. [인프라 구조도] 
9. [erd]
10. [주요 기능 소개](#8)
11. [상세 담당 업무](#9)
12. [주요 코드 ](#10)
13. [트러블 슈팅](#11)
14. [프로젝트 회고](#12)
15. [시작 가이드](#13)

<br />

## <span id="1">🚩 1. 프로젝트 소개 : 물류 관리 및 배송 시스템 </span>

본 프로젝트는 MSA(Microservices Architecture) 기반의 국내 물류 관리 및 배송 시스템을 개발하는 것을 목표로 합니다.
스파르타 물류라는 가상의 물류 회사를 사례로, 허브(물류센터)와 업체(생산/수령 업체) 간의 물류 이동 전 과정을 디지털화하고 자동화된 관리 기능을 제공합니다.

### 🚛 서비스 시나리오

- 허브 관리: 전국 17개 주요 도시에 허브(물류센터)를 구축 및 관리
- 업체 관리: 각 허브에 소속된 생산업체·수령업체 등록 및 관리
- 배송 프로세스:

  1. 주문 발생 시 출발 허브에서 목적지 허브로 물품 이동
  2. 허브 배송 담당자가 경로에 따라 운송
  3. 최종 목적지 허브에서 업체 배송 담당자가 수령 업체로 전달

- 슬랙 연동: 배송 담당자에게 매일 아침 자동으로 당일 배송지와 경로를 안내
- 메시징 시스템 연계(Kafka, RabbitMQ): 일부 서비스 간 통신을 메시징 기반으로 전환하여 비동기적 확장성 확보

<br>

<!-- Top Button -->
<p style='background: black; width: 32px; height: 32px; border-radius: 50%; display: flex; justify-content: center; align-items: center; margin-left: auto;'><a href="#top" style='color: white; '>▲</a></p>

<br>

## <span id="2">🏃 2. 팀원 소개</span>

<div align="center">

|                                   <img src="https://img.shields.io/badge/Project_Leader-FF5733" />                                    |                              <img src="https://img.shields.io/badge/Documentation_Leader-%2310069F%20" />                              |                                                                                                                                        |                                                                                                                                        |                                                                                                                                       |
| :-----------------------------------------------------------------------------------------------------------------------------------: | :------------------------------------------------------------------------------------------------------------------------------------: | :------------------------------------------------------------------------------------------------------------------------------------: | :------------------------------------------------------------------------------------------------------------------------------------: | :-----------------------------------------------------------------------------------------------------------------------------------: |
|                                        <img src="https://github.com/" width="120px;" alt=""/>                                         |                                         <img src="https://github.com/" width="120px;" alt=""/>                                         |                                         <img src="https://github.com/" width="120px;" alt=""/>                                         |                                         <img src="https://github.com/" width="120px;" alt=""/>                                         |                                        <img src="https://github.com/" width="120px;" alt=""/>                                         |
| <img width="100" height="100" alt="57593704" src="https://github.com/user-attachments/assets/d823ce17-0831-4fa5-ad2f-913d9e5f947e" /> | <img width="100" height="100" alt="110345442" src="https://github.com/user-attachments/assets/6f24accf-5a16-4f32-be96-23a15779e85e" /> | <img width="100" height="100" alt="185685157" src="https://github.com/user-attachments/assets/59a4a457-c0e4-490a-a004-7e54c8ae47d6" /> | <img width="100" height="100" alt="172256008" src="https://github.com/user-attachments/assets/7853db03-4ce0-4715-9d5b-30e2299b87f4" /> | <img width="100" height="100" alt="33851859" src="https://github.com/user-attachments/assets/ab692188-3580-4bee-aa0a-c8aeba36c322" /> |
|                                                 [이다예](https://github.com/dayaelee)                                                 |                                                 [최우탁](https://github.com/legoChoi)                                                  |                                                 [정성운](https://github.com/Hoody-rj)                                                  |                                                 [황선영](https://github.com/dinahland)                                                 |                                               [이강혁](https://github.com/GangHyeokLee)                                               |
|                                                              배송 도메인                                                              |                                                           상품 / 주문 도메인                                                           |                                                       슬랙 알림 / 업체 배송 관리                                                       |                                                               허브 관리                                                                |                                                         Gateway / 유저 / 업체                                                         |

</div>

<br>

<!-- Top Button -->
<p style='background: black; width: 32px; height: 32px; border-radius: 50%; display: flex; justify-content: center; align-items: center; margin-left: auto;'><a href="#top" style='color: white; '>▲</a></p>

<br>

## <span id="3">📅 3. 개발 일정</span>

> 프로젝트 개발 기간: 2025.08.11 - 2025.08.23 (13일)

<br>

<!-- Top Button -->
<p style='background: black; width: 32px; height: 32px; border-radius: 50%; display: flex; justify-content: center; align-items: center; margin-left: auto;'><a href="#top" style='color: white; '>▲</a></p>

<br>

## <span id="4">📚 4. 기술 스택</span>

### Development

![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
<br>![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)![Redis](https://img.shields.io/badge/redis-%23DD0031.svg?style=for-the-badge&logo=redis&logoColor=white)
<br>![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)

### Design & Infra

![Figma](https://img.shields.io/badge/figma-%23F24E1E.svg?style=for-the-badge&logo=figma&logoColor=white)

Zipkin 3.5.1

### Environment

![IntelliJ IDEA](https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)![Git](https://img.shields.io/badge/git-%23F05033.svg?style=for-the-badge&logo=git&logoColor=white)![GitHub](https://img.shields.io/badge/github-%23121011.svg?style=for-the-badge&logo=github&logoColor=white)

<br>

<!-- Top Button -->
<p style='background: black; width: 32px; height: 32px; border-radius: 50%; display: flex; justify-content: center; align-items: center; margin-left: auto;'><a href="#top" style='color: white; '>▲</a></p>

<br>

## <span id="5">❓ 5. 라이브러리 및 외부 API 사용 이유</span>

> Spring Boot 3.5.4
- Tomcat 내장 서버와 자동 설정을 제공해 빠른 개발 및 배포 환경 구성 가능
- MSA 구조에서 독립적인 서비스 개발에 용이

<br>

> Gradle 8.4
- 빌드 및 의존성 관리 자동화 

<br>

> Hibernate 6.6.22
- 객체와 관계형 데이터베이스 매핑 프레임워크
- SQL을 직접 작성하지 않고 엔티티 기반 데이터 관리 가능
<br>

> JPA 3.5.2
- 자바 표준 ORM 스펙
- 데이터베이스 독립성을 보장하고, Hibernate 구현체와 함께 사용해 유지보수성 높이기 위함

<br>

> Querydsl

- JPQL의 컴파일 시점 오류 확인 불가
- 쿼리 가독성과 타입 안정성을 보장하여 유지보수 용이

<br>

> Spring Cloud (Gateway, Eureka) 2025.0.0
- Gateway: API 라우팅 및 필터링, 인증/인가 기능 추가 가능
- Eureka: 서비스 디스커버리 기능을 통해 MSA 환경에서 동적으로 서비스 위치 탐색 가능

<br>

> Spring Security 6.5.2
- 인증과 권한 관리
- JWT, OAuth2 기반 인증/인가 기능 확장 가능

<br>

> OpenFeign 4.3.0
- MSA 간 통신을 단순화 하는 HTTP Client
- REST API 호출 시 인터페이스 선언만으로 구현 가능하며, 서비스 간 연동이 편리하고 유지보수성이 높음

<br>

> JWT 0.12.3
- 인증/인가를 위한 토큰 기반 인증 방식 채택
- 세션 저장소 없이 Stateless 서버 환경에서 확장성과 보안성을 동시에 확보하기 위함

<br>

> lombok 1.18.38
- 반복적인 보일러프레이트 코드를 자동 생성하기 위함
- 코드 가독성을 높이고 개발 생산성 향상

<br>

> Naver 지도 API
- 배송 경로 탐색 및 예상 소요 시간 계산을 위해 사용
- 허브와 업체 간의 위치 기반 물류 이동 경로를 자동화하여, 배송 최적화 기능을 구현하기 위함
<br>

<br>

<!-- Top Button -->
<p style='background: black; width: 32px; height: 32px; border-radius: 50%; display: flex; justify-content: center; align-items: center; margin-left: auto;'><a href="#top" style='color: white; '>▲</a></p>

<br>

## <span id="6">🤝 6. 컨벤션</span>

### 코드 컨벤션

1. 다른 사람이 맡은 코드는 임의로 고치지 말고 꼭 담당자랑 얘기하기
2. 코드 리팩토링 하기 전에는 리팩토링 담당자가 팀 전체에 공유하기
3. PR 올리기 전 두 가지 꼭 하기
   - 코드 정렬: `Ctrl`+`Alt`+`L`
   - 불필요한 import 제거: `Ctrl`+`Alt`+`O`(알파벳 O)
4. DTO 명명 시 유의 사항

   Request, Response 각각 따로 작성하기

   예시)

   - CreateUserRequestDto
   - ReadUserResponseDto
   - UpdateUserResponseDto

5. API 명명 시 유의 사항

   - 자바 컨벤션 따르기(카멜케이스)
   - 줄임말 사용 금지

6. ERD 명명 시 유의 사항
   - 카멜케이스 대신 언더바 사용하기
   - 줄임말 금지
   - 스프링에서 자바컨벤션 따르면 자동으로 스네이크 케이스로 바꿔줌

<br>

### 커밋 컨벤션

| **타입** | **설명**                                          | **예시**                                              |
| -------- | ------------------------------------------------- | ----------------------------------------------------- |
| feat     | 기능 구현                                         | feat - 페인페이지 레이아웃 구현                     |
| rename   | 파일/폴더 이름 변경 및 이동                       | rename - `src/old-folder`를 `src/new-folder`로 이동 |
| script   | 라이브러리 추가                                   | script - `supabase` 라이브러리 추가                 |
| fix      | 버그 수정                                         | fix - `supabase` env 미연결 문제 해결               |
| chore    | 빌드 업무 수정, 패키지 매니저 설정 수정           | chore - .env 설정 변경                              |
| refactor | 코드 리팩토링                                     | refactor - 함수 분리 및 코드 정리                   |
| style    | 코드 포맷팅, 세미콜론 누락, 코드 변경이 없는 경우 | style - 버튼 스타일 수정                            |
| test     | 테스트 코드, 리팩토링 테스트 코드 추가            | test - 유저 로그인 기능 테스트 추가                 |
| docs     | 문서 수정                                         | docs - API 문서 업데이트                            |

<br>

### Pull Request for 코드 리뷰

1. Pull Request Template 사용하기
2. Pull Request Template 작성 시 유의 사항
   - 팀장님 포함 리뷰어를 전체로 지목하기 (단, 바쁘면 융통성 발휘하기)
   - 최소 단위로 commit 및 push하기
   - 최소 단위로 PR 하기
3. PR 전에 반드시 Postman 실행하여 잘 작동하는지 테스트하기
4. 최소 단위가 헷갈릴 때는 ‘검토할 상대방에게 부담되지 않는가?’ 스스로 질문하고 PR 올리기
5. PR이 지연되면, `dev`에서 새 브랜치를 생성한 다음 직전 브랜치를 `pull`하기

<br>

### branch

1. `release` + 버전(메인.마이너.픽스) → 배포 브랜치
2. `dev` → 개발 테스트 브랜치
3. 형식

   ```java
   ex. 기능 추가: feat/{기능 설명}
   ex. 버그 수정: fix/{짧은 설명}
   ex. 문서 관리: docs/readme
   ex. 리팩토링:  refactor/{리팩터링 짧은 설명}
   ex. 테스트 코드: test/{짧은 설명}
   ```

4. **추가로 설명을 써야 할 때에는 하이픈(-) 사용하기**
   - 하이픈 (hypen) (-) (**O**)
   - 언더바 (under bar) (\_) (**X**)
   - 대시 (dash) (—) (**X**)

<!-- Top Button -->
<p style='background: black; width: 32px; height: 32px; border-radius: 50%; display: flex; justify-content: center; align-items: center; margin-left: auto;'><a href="#top" style='color: white; '>▲</a></p>

<br>

## 7.<span id="7"> 🗂️ 브랜치 및 디렉토리 구조</span>

> 브랜치

- `main`: 배포용 브랜치
- `dev`: 개발용 브랜치
- `feat/{기능 설명}`: 개발용 - 기능 개발 브랜치


<br>

> 배송 도메인 디렉토리 구조

  ```
  📂 delivery/src
  ├── 📂 main
  │   ├── 📂 java/com/tf4/delivery
  │   │   ├── 📂 common                   # 공통 유틸, 예외 처리, 설정 클래스
  │   │   │   ├── 📂 config               # 애플리케이션 전역 설정
  │   │   │   └── 📂 event                # 이벤트 관련 클래스
  │   │   ├── 📂 controller               # 컨트롤러 계층
  │   │   ├── 📂 dto                      # 데이터 전송 객체
  │   │   │   ├── 📂 request              # 요청 DTO
  │   │   │   └── 📂 response             # 응답 DTO
  │   │   ├── 📂 entity                   # JPA 엔티티 클래스
  │   │   ├── 📂 repository               # 데이터 접근 계층
  │   │   │   ├── 📂 feign                # 외부 서비스 연동용 인터페이스
  │   │   │   └── 📂 jpa                  # JPA Repository 인터페이스
  │   │   ├── 📂 service                  # 비즈니스 로직 계층
  │   │   ├── 📂 sepc                     # 검색 조건 클래스
  │   │   └── 📄 DeliveryApplication.java # 메인 App
  │   └── 📂 resources
  │       └── 📄 application.yml          # 설정 파일
  ├── 📂 test                             # 테스트 코드
  └── 📄 build.gradle                     # 프로젝트 종속성 및 빌드 설정 파일

  ```

<br>

> 상품 / 주문 도메인 디렉토리 구조

  <details>
  <summary>Order</summary>

  <img src="https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdna%2FoNJ0s%2FbtsP0kVtl0e%2FAAAAAAAAAAAAAAAAAAAAADM3LCZeBIdOc5jyhbO8V6yYYZd-1D2YjPrSTM_QLyl_%2Fimg.png%3Fcredential%3DyqXZFxpELC7KVnFOS48ylbz2pIh7yKj8%26expires%3D1756652399%26allow_ip%3D%26allow_referer%3D%26signature%3DyXgSlq6KJHf4wJ9asK8sHiN33ck%253D" width="300" height="500" />

  </details>

  <details>
  <summary>Product</summary>

  <img src="https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdna%2Fbt3DBS%2FbtsPYNYDHku%2FAAAAAAAAAAAAAAAAAAAAAME3BC2QgCGwb4NFkaS2lKaoVr_Dktz8DoaAWSidXjqG%2Fimg.png%3Fcredential%3DyqXZFxpELC7KVnFOS48ylbz2pIh7yKj8%26expires%3D1756652399%26allow_ip%3D%26allow_referer%3D%26signature%3DYZZyl%252FtdBHZ7T%252FRXNeWgIFzOqUk%253D" width="300" height="500" />

  </details>

<br>

> 허브 도메인 디렉토리 구조

작성필 

> 유저 도메인 디렉토리 구조

작성필

> 슬랙 알람 도메인 디렉토리 구조

작성필

> 업체 도메인 디렉토리 구조

작성필

<br>

<!-- Top Button -->
<p style='background: black; width: 32px; height: 32px; border-radius: 50%; display: flex; justify-content: center; align-items: center; margin-left: auto;'><a href="#top" style='color: white; '>▲</a></p>

<br>

## <span id="8">8. 💻 인프라 구조도</span>

![인프라 설계 (1)](https://github.com/user-attachments/assets/38608894-f9ec-45b5-bdd1-d9123bdebf57)

<br>
<!-- Top Button -->
<p style='background: black; width: 32px; height: 32px; border-radius: 50%; display: flex; justify-content: center; align-items: center; margin-left: auto;'><a href="#top" style='color: white; '>▲</a></p>

## <span id="9">9. 💻 ERD 설계</span>

<img width="3080" height="2522" alt="MSA-DeliverySystem" src="https://github.com/user-attachments/assets/49ce7366-15aa-4df1-8baf-9a269dc86d7e" />

<br>

<!-- Top Button -->
<p style='background: black; width: 32px; height: 32px; border-radius: 50%; display: flex; justify-content: center; align-items: center; margin-left: auto;'><a href="#top" style='color: white; '>▲</a></p>

## <span id="10">10. 💻 주요 기능 소개</span>

프로젝트의 주요 기능을 GIF를 첨부하여 설명해주세요.
(와이어프레임 대표 이미지 추가 예정)


### 인증/인가 회원가입/로그인
설명 한두줄 작성 부탁 드립니다. 
와이어프레임 대표 이미지 추가 부탁드립니다. 

### 사용자 관리

설명 한두줄 작성 부탁 드립니다. 
와이어프레임 대표 이미지 추가 부탁드립니다. 

### 주문 관리 

설명 한두줄 작성 부탁 드립니다. 
와이어프레임 대표 이미지 추가 부탁드립니다. 


| 기능                               | 메서드 | 엔드포인트                    | Query Params     | status |
| ---------------------------------- | ------ | ----------------------------- | ---------------- | ------ |
| 주문 생성                          | POST   | /orders                       |                  | 201    |
| 주문 단건 조회                     | GET    | /orders/{orderId}             |                  | 200    |
| 허브 주문 목록 조회 (페이지네이션) | GET    | /orders/hubs/{hubId}          | page, size, sort | 200    |
| 업체 주문 목록 조회 (페이지네이션) | GET    | /orders/companies/{companyId} | page, size, sort | 200    |
| 주문 수정                          | PATCH  | /orders/{orderId}             |                  | 204    |
| 주문 취소                          | PATCH  | /orders/{orderId}/cancel      |                  | 204    |
| 주문 삭제                          | DELETE | /orders/{orderId}             |                  | 200    |



### 상품 관리 



설명 한두줄 작성 부탁 드립니다. 
와이어프레임 대표 이미지 추가 부탁드립니다. 

| 기능                                    | 메서드 | 엔드포인트                      | Query Params                  | status |
| --------------------------------------- | ------ | ------------------------------- | ----------------------------- | ------ |
| 상품 생성                               | POST   | /products                       |                               | 201    |
| 상품 단건 조회                          | GET    | /products/{productId}           |                               | 200    |
| 상품 전체 목록 조회 (페이지네이션)      | GET    | /products                       | page, size, sort, productName | 200    |
| 허브 등록 상품 목록 조회 (페이지네이션) | GET    | /products/hubs/{hubId}          | page, size, sort, productName | 200    |
| 업체 등록 상품 목록 조회 (페이지네이션) | GET    | /products/companies/{companyId} | page, size, sort, productName | 200    |
| 상품 수정                               | PATCH  | /products                       |                               | 204    |
| 상품 삭제                               | DELETE | /products                       |                               | 200    |


### 배송 관리 

설명 한두줄 작성 부탁 드립니다. 
와이어프레임 대표 이미지 추가 부탁드립니다. 

### 허브간 이동 경로 관리 

설명 한두줄 작성 부탁 드립니다. 
와이어프레임 대표 이미지 추가 부탁드립니다. 

### 업체간 이동 경로 관리 

설명 한두줄 작성 부탁 드립니다. 
와이어프레임 대표 이미지 추가 부탁드립니다. 

### 허브 정보 관리

설명 한두줄 작성 부탁 드립니다. 
와이어프레임 대표 이미지 추가 부탁드립니다. 

### 허브 간 이동 경로 관리

설명 한두줄 작성 부탁 드립니다. 
와이어프레임 대표 이미지 추가 부탁드립니다. 

### 업체 관리 

설명 한두줄 작성 부탁 드립니다. 
와이어프레임 대표 이미지 추가 부탁드립니다. 

### 슬랙 알림 전송

설명 한두줄 작성 부탁 드립니다. 
와이어프레임 대표 이미지 추가 부탁드립니다. 



<br>

<!-- Top Button -->
<p style='background: black; width: 32px; height: 32px; border-radius: 50%; display: flex; justify-content: center; align-items: center; margin-left: auto;'><a href="#top" style='color: white; '>▲</a></p>

<br>

## <span id="11">11. 📄 상세 담당 업무</span>

### 1) 이강혁

- **🎨 사용자 관리**

  - 로고 디자인 및 이미지 제작

- **💻 Gateway**

  - 로그인 화면
  - 검색 화면
  - 채팅 화면

- **🧑‍💻 업체 관리**

  - 로딩 페이지
    - 회원가입 후 로그인 모달이 올라오는 로딩페이지
  - 팔로워 목록 및 팔로워 취소&팔로우
    - 팔로워 목록을 getFollowerList로 서버에 요청하여 리스트 출력

- **♻️ 리팩토링**
  - 관련 설명

### 2) 주문 관리 (담당자: 최우탁)

- **🎨 주문 관리(Order)**

  - 주문 생성

    - **POST** `/orders`
    - 권한 검증 → 주문 데이터 생성(PENDING) → 재고 차감(PREPARED) → 결제 처리(PAID, 가정) → 배송 생성(READY_FOR_SHIPMENT)
    - 권한
      - 허브 담당자: 주문하려는 상품의 담당 업체가 본인 허브 소속 업체이면 주문 불가능
      - 배송 담당자: 주문하려는 상품의 담당 업체가 본인 소속 허브의 소속 업체이면 주문 불가능
      - 업체 담당자: 주문하려는 상품의 담당 업체가 본인 업체이면 주문 불가능

  - 주문 단건 조회

    - **GET** `/orders/{orderId}`
    - 조회 → 권한 검증 → 응답
    - 권한
      - 허브 담당자: 본인 허브 소속 업체의 주문 내역만 조회 가능
      - 배송 담당자/업체 담당자: 본인의 주문 내역만 조회 가능 (createdBy)

  - 허브 주문 목록 조회 (페이지네이션)

    - **GET** `/orders/hubs/{hubId}?page=&size=&sort=`
    - 권한 검증 → 조회
    - 권한
      - 허브 담당자: 본인 허브의 경우에만 주문 내역 조회 가능
      - 배송 담당자/업체 담당자: 조회 불가능

  - 업체 주문 목록 조회 (페이지네이션)

    - **GET** `/orders/companies/{companyId}?page=&size=&sort=`
    - 권한 검증 → 조회
    - 권한
      - 허브 담당자: 본인 허브의 경우에만 주문 내역 조회 가능
      - 배송 담당자/업체 담당자: 조회 불가능

  - 주문 수정/취소/삭제

    - **PATCH** `/orders/{orderId}`
    - **PATCH** `/orders/{orderId}/cancel`
    - **DELETE** `/orders/{orderId}`
    - 권한 검증 → 수정/삭제
    - 권한
      - 허브 담당자: 본인 허브 소속 업체의 주문만 수정/삭제 가능
      - 배송 담당자/업체 담당자: 수정/삭제 불가능

  - 유저 권한별 각 기능 유효성 검증
      <details>
          <summary>
              주문 생성
          </summary>

    ![주문_생성](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdna%2FxPINg%2FbtsP4XSCxSS%2FAAAAAAAAAAAAAAAAAAAAAG3ESMtMPZZUjmi_ZdQ9NwREYadL5FTRktzr09W8QqI3%2Fimg.png%3Fcredential%3DyqXZFxpELC7KVnFOS48ylbz2pIh7yKj8%26expires%3D1756652399%26allow_ip%3D%26allow_referer%3D%26signature%3D5RQHDbC5tbqrJP99tSOOM13DldA%253D)

      </details>
      <details>
          <summary>
              주문 단건 조회
          </summary>

    ![주문_단건_조회](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdna%2FYUyMM%2FbtsP42M6yUs%2FAAAAAAAAAAAAAAAAAAAAAFtvhHyTD1SoIVRVKkI7FYjbjipnyLIGJaoOQAkHFwio%2Fimg.png%3Fcredential%3DyqXZFxpELC7KVnFOS48ylbz2pIh7yKj8%26expires%3D1756652399%26allow_ip%3D%26allow_referer%3D%26signature%3DE1uE4nSANqi9pkdUro5zhup7jCQ%253D)

      </details>
      <details>
          <summary>
              허브 주문 목록 조회
          </summary>

    ![허브_주문_목록_조회](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdna%2FSx1JW%2FbtsP3zygfB5%2FAAAAAAAAAAAAAAAAAAAAAFd-X_oZDXYSbyb7TEpAEcYIKXNHreH9YjDFQJatRZNV%2Fimg.png%3Fcredential%3DyqXZFxpELC7KVnFOS48ylbz2pIh7yKj8%26expires%3D1756652399%26allow_ip%3D%26allow_referer%3D%26signature%3Dm31hYum7%252BBoLusHd13TL145wdIw%253D)

      </details>
      <details>
          <summary>
              업체 주문 목록 조회
          </summary>

    ![업체_주문_목록_조회](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdna%2FcXccmB%2FbtsP4jaNFQz%2FAAAAAAAAAAAAAAAAAAAAALMtrwH1BsyE6SIlsnGzyL9IOusKKlfQYwE_O5HeyKTn%2Fimg.png%3Fcredential%3DyqXZFxpELC7KVnFOS48ylbz2pIh7yKj8%26expires%3D1756652399%26allow_ip%3D%26allow_referer%3D%26signature%3DyY6pur%252B7O%252B%252BshwyRWk90l9aPVXQ%253D)

      </details>
      <details>
          <summary>
              주문 수정/삭제
          </summary>

    ![주문_수정](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdna%2FbN4LQE%2FbtsP4JAhXSG%2FAAAAAAAAAAAAAAAAAAAAAMUSR8Er9AvpScmUnCaOTKrMCOvRfQwT7C4hlsTWR3kL%2Fimg.png%3Fcredential%3DyqXZFxpELC7KVnFOS48ylbz2pIh7yKj8%26expires%3D1756652399%26allow_ip%3D%26allow_referer%3D%26signature%3D39sfLxpMiYSjgpYO%252FE%252BVPnrF4%252Fo%253D)

      </details>

<br>

- **💻 상품 관리(Product)**

  - 상품 생성

    - **POST** `/products`
    - 권한 검증 → 상품 생성
    - 권한
      - 허브 담당자: 등록하려는 상품의 담당 업체가 본인 허브 소속 업체이면 상품 등록 가능
      - 업체 담당자: 등록하려는 상품의 담당 업체가 본인 업체이면 상품 등록 가능

  - 상품 단건 조회

    - **GET** `/products/{productId}`
    - 조회 → 권한 검증 → 응답
    - 권한
      - 허브 담당자: 조회하려는 상품의 담당 업체가 본인 허브 소속 업체이면 조회 가능
      - 업체 담당자: 조회하려는 상품의 담당 업체가 본인 업체이면 조회 가능
      - 배송 담당자: 조회 불가능

  - 주문용 상품 전체 조회 (페이지네이션)

    - **GET** `/products?page=&size=&sort=&productName=`
    - 조회 → 응답

  - 허브 등록 상품 목록 조회 (페이지네이션)

    - **GET** `/products/hubs/{hubId}?page=&size=&sort=&productName=`
    - 권한 검증 → 조회 → 응답
    - 권한
      - 허브 담당자: 본인 허브 소속 업체들의 등록 상품 목록 조회 가능
      - 배송 담당자/업체 담당자: 조회 불가능

  - 업체 등록 상품 목록 조회 (페이지네이션)

    - **GET** `/products/companies/{companyId}?page=&size=&sort=&productName=`
    - 권한 검증 → 조회 → 응답
    - 권한
      - 허브 담당자: 본인 허브 소속 업체의 등록 상품 목록 조회 가능
      - 배송 담당자: 조회 불가능
      - 업체 담당자: 본인 업체의 등록 상품 목록 조회 가능

  - 상품 수정

    - **PATCH** `/products/{productId}`
    - 권한 검증 → 수정
    - 권한
      - 허브 담당자: 본인 허브 소속 업체의 등록 상품 수정 가능
      - 업체 담당자: 본인 업체의 등록 상품 수정 가능
      - 배송 담당자: 수정 불가능

  - 상품 삭제

    - **DELETE** `/products/{productId}`
    - 권한 검증 → 삭제
    - 권한
      - 허브 담당자: 본인 허브 소속 업체의 등록 상품 삭제 가능
      - 배송 담당자/업체 담당자: 삭제 불가능

  - 유저 권한별 각 기능 유효성 검증
      <details>
          <summary>
              상품 생성
          </summary>

    ![상품_생성](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdna%2FbkOHf3%2FbtsP418xBfU%2FAAAAAAAAAAAAAAAAAAAAANTAYV6Flmf0aYQFKiT4bxZ1rYOAtrKdJiaVmSiT-Djf%2Fimg.png%3Fcredential%3DyqXZFxpELC7KVnFOS48ylbz2pIh7yKj8%26expires%3D1756652399%26allow_ip%3D%26allow_referer%3D%26signature%3DHfO4qIDI98YUiZOjXkxT9a%252FovSc%253D)

      </details>
      <details>
          <summary>
              상품 단건 조회
          </summary>

    ![상품_단건_조회](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdna%2FcENDh5%2FbtsP0TdqBr5%2FAAAAAAAAAAAAAAAAAAAAANf3fNevha4wHkfxJJ67djEjfy5lv1rKUe3KXfPKcgpX%2Fimg.png%3Fcredential%3DyqXZFxpELC7KVnFOS48ylbz2pIh7yKj8%26expires%3D1756652399%26allow_ip%3D%26allow_referer%3D%26signature%3D3U%252FQRnYrfBsrAEKrd0mYSjTZ5bU%253D)

      </details>
      <details>
          <summary>
              허브 등록 상품 목록 조회
          </summary>

    ![허브_등록_상품_목록_조회](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdna%2Fb4gF5b%2FbtsP4e1BM6F%2FAAAAAAAAAAAAAAAAAAAAAK6eFEKK3E7u75yOHEyw7wyeLkgoXhoURZRjijhMSXAu%2Fimg.png%3Fcredential%3DyqXZFxpELC7KVnFOS48ylbz2pIh7yKj8%26expires%3D1756652399%26allow_ip%3D%26allow_referer%3D%26signature%3DiF7Tes%252FSHgq6KK4yk8skVfxcetU%253D)

      </details>
      <details>
          <summary>
              업체 등록 상품 목록 조회
          </summary>

    ![업체_등록_상품_목록_조회](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdna%2FcBTWKX%2FbtsP4qnocyX%2FAAAAAAAAAAAAAAAAAAAAAB0R89YUWV3XmIA38WrNvcG-_dHulc9O1Eb-FpLAxjyY%2Fimg.png%3Fcredential%3DyqXZFxpELC7KVnFOS48ylbz2pIh7yKj8%26expires%3D1756652399%26allow_ip%3D%26allow_referer%3D%26signature%3DGajD4ta0IdFS47DK2W%252BrkfmgUK4%253D)

      </details>
      <details>
          <summary>
              상품 수정
          </summary>

    ![상품_수정](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdna%2FxCLnK%2FbtsP4624ifa%2FAAAAAAAAAAAAAAAAAAAAADOQQIn41TP2pwZ0BqZHCZWjLL1KsOUAGAr106eHNrTi%2Fimg.png%3Fcredential%3DyqXZFxpELC7KVnFOS48ylbz2pIh7yKj8%26expires%3D1756652399%26allow_ip%3D%26allow_referer%3D%26signature%3DmT3cGgJRQjY1AKMHSh2Xe6G8poI%253D)

      </details>
      <details>
          <summary>
              상품 삭제
          </summary>

    ![상품_삭제](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdna%2Fb11bVy%2FbtsP4iQtGIB%2FAAAAAAAAAAAAAAAAAAAAAI8faBKIc_q6REmCQGIDYSxiT_pvo8IPVVOMuqHk75wi%2Fimg.png%3Fcredential%3DyqXZFxpELC7KVnFOS48ylbz2pIh7yKj8%26expires%3D1756652399%26allow_ip%3D%26allow_referer%3D%26signature%3DF%252FQTJXHI3PKAvxOUBfWsQ8drl0A%253D)

      </details>

<br>

- **💻 공통(Common)**

- DTO의 `record` 타입 사용

  - 클라이언트의 요청에 대한 불변성과 안정성을 명시

- `Pageable`의 `sort` 쿼리 - 정렬 조건 타입 안정화

  - 정해진 정렬 조건만 허용 - 예기치 못한 쿼리 또는 에러 발생 방지
  - 동일한 `property` 정렬 시 예외 발생
  - `SortType` ENUM

    ```
    @Getter
    @RequiredArgsConstructor
    public enum SortType {

        CREATED_ASC("createdAt", Sort.Direction.ASC),
        CREATED_DESC("createdAt", Sort.Direction.DESC),
        UPDATED_ASC("updatedAt", Sort.Direction.ASC),
        UPDATED_DESC("updatedAt", Sort.Direction.DESC),
        PRICE_ASC("price", Sort.Direction.ASC),
        PRICE_DESC("price", Sort.Direction.DESC),
        ;

        private final String value;
        private final Sort.Direction direction;

        public static void validate(Sort sort) {
            Set<String> check = new HashSet<>();

            for (Sort.Order order : sort) {
                if (!match(order)) {
                    throw new CustomRuntimeException(ProductException.UNSUPPORTED_SORT_TYPE);
                }

                if (!check.add(order.getProperty().toLowerCase())) {
                    throw new CustomRuntimeException(ProductException.DUPLICATED_SORT_TYPE);
                }
            }
        }

        private static boolean match(Sort.Order order) {
            return Arrays.stream(SortType.values())
                    .anyMatch(sortType -> sortType.value.equalsIgnoreCase(order.getProperty()) &&
                       sortType.getDirection().equals(order.getDirection()));
        }
    }
    ```

- `Querydsl`의 `getOrderSpecifier()`을 통한 동적 정렬 조건 쿼리 구현
  - 클라이언트가 여러 개의 `sort` 쿼리를 넘기도록 하여 `.orderBy()`에 적용

    ```
    private OrderSpecifier<?>[] getOrderSpecifiers(Sort sort) {
      List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();

      for (Sort.Order sortOrder : sort) {
          Order order = sortOrder.isAscending() ? Order.ASC : Order.DESC; // sort 쿼리 내 정렬 방향을 Order 객체로 파싱
          PathBuilder<Product> pathBuilder
                  = new PathBuilder<>(product.getType(), product.getMetadata()); // sort 쿼리 내 정렬 값을 QClass 내 필드값에 해당하는 값으로 매핑

          orderSpecifiers.add(new OrderSpecifier<>(order, pathBuilder.getString(sortOrder.getProperty())));
      }

      return orderSpecifiers.toArray(new OrderSpecifier[0]);
    }
    ```

<!-- - **♻️ 리팩토링**
  - 관련 설명 -->

  <br>

### 3) 🏢 허브 관리 (담당자: 황선영)

 - ###  🏢허브 정보 관리

    - **허브 생성**

      - 허브 이름, 주소 입력 → 새로운 허브 생성
      - 허브 이름 중복 검증
      - **Naver Maps API** 연동 → 위도, 경도 자동 저장

    - **허브 조회**

      - 단건 조회: 허브 ID로 단건 조회, **Redis 캐싱** 적용
      - 목록 조회: 페이지네이션 (생성일시 기준 정렬)
      - `size=0`: 전체 허브 조회 (생성일시 기준 정렬)

    - **허브 수정**

      - 허브 이름 변경 시 중복 검증
      - 주소 변경 시 **위도, 경도 재조회 + 모든 경로 자동 재계산**
      - 변경 사유 필수 입력 (변경 이력 관리)
      - 캐시 무효화 적용

    - **허브 삭제**

      - 소프트 삭제 (deletedAt, deletedBy)
      - 캐시 무효화 적용

    - **관리자 등록**

      - 허브마다 허브 관리자 지정 가능
      - Auth 서비스 연동으로 사용자 권한 검증
      - HUB 권한을 가진 사용자만 등록 가능

    - **관리자 조회**

      - 허브 관리자의 사용자 ID로 담당 허브 조회
      - 해당 관리자 ID에 맞는 허브가 없을 시 예외 처리

- ###  🚚 허브 간 이동 경로 관리

  - **허브 간 경로 정보 조회**

    - 출발 허브 ID, 도착 허브 ID 기반 검색
    - **Redis 캐싱** 적용

  - **허브 간 이동 경로 자동 생성**

    - 새 허브 생성 시 기존 모든 허브와 양방향 경로 생성
    - **Naver Maps API** 기반 최적 경로 탐색
    - 이동 거리(km), 소요 시간(초) 자동 저장

  - **경로 자동 업데이트**

    - 허브 주소 변경 시 모든 연결 경로 자동 업데이트
    - **Naver Maps API** 호출로 실시간 교통 정보 반영
    - 캐시 무효화 적용

  - **경로 자동 삭제**

    - 허브 삭제 시 모든 연결 경로 자동 삭제
    - 소프트 삭제(deletedAt, deletedBy)
    - 캐시 무효화 적용


  - **경로 수동 업데이트**

    - MASTER 권한자가 특정 경로 재계산 가능
    - 업데이트 사유 필수 입력


<!-- - **♻️ 리팩토링**
  - 관련 설명 -->

<br>


### 4) 🏢 배송 관리 (담당자: 이다예)

- **💻 배송 관리**


  - **주문시 배송 자동 생성**

    - 배송 담당자 자동 지정
    - 허브간 배송 경로 기록을 생성하여 예상 거리와 배송 시간을 기입
  - **배송 수정**
  - **배송 삭제**
  - **배송 조회**

    - 라우터 초기 셋팅
    - 게시물 등록
      - 토글 Open, Close에 따라 인풋창 높이 자동 조절
      - api 전송 한계로 인해 한 공간에 저장하여 보낼 수 있게, 데이터를 연산자로 구분하여 한줄로 전송
        이미지 추가 및 삭제 가능
    - 게시글 삭제 / 신고
      - userId를 통해 유저를 구별하여 타인의 경우 신고 기능, 본인일 경우 삭제 기능 구현
- **🎨 배송 담당자 관리**
  - **배송 담당자 등록**
  - **배송 담당자 수정**
  - **배송 담당자 삭제**
  - **배송 담당자 조회** 
- **🎨 허브 간 배송 경로 관리**
  - **배송 담당자 자동 등록**
  - **배송 건당 허브 배송담당자 할당 기능** 
    - 담당자 배정 기록 테이블의 순번 기록 컬럼 활용
    - 할당 로직
      - 허브타입의 할당 가능한 배송 담당자를 추림
      - 순번 기록 컬럼의 값을 가져옴
      - 1번의 값의 순번들에 대해 2번 값보다 크면서 제일 작은 순번을 가진 배송 담당자를 할당
        
        => 삭제된 담당자는 자동으로 건너뜀

        => 할당 가능한 순번을 초과하면 1번 값 중 맨 처음 값을 가진 담당자 할당
  - **배송 담당자 수정**
  - **배송 담당자 삭제**
  - **배송 담당자 조회**

- **🎨 검색**
  - **단일 조건**
  - **복합 검색**
  - **다중 조건 검색** 
<!-- - **♻️ 리팩토링**
  - 관련 설명 -->

<br>

### 5) 정성운

- **🎨 상품 관리**

  - 전체적인 UI 디자인

- **💻 주문 관리**

  - 공통 헤더 네브바
  - 공통 푸터 네브바
  - 삭제 / 신고 모달창

- **👩‍💻 구현 기능**

  - 라우터 초기 셋팅
  - 게시물 등록
    - 토글 Open, Close에 따라 인풋창 높이 자동 조절
    - api 전송 한계로 인해 한 공간에 저장하여 보낼 수 있게, 데이터를 연산자로 구분하여 한줄로 전송
      이미지 추가 및 삭제 가능
  - 게시글 삭제 / 신고
    - userId를 통해 유저를 구별하여 타인의 경우 신고 기능, 본인일 경우 삭제 기능 구현

- **♻️ 리팩토링**
  - 관련 설명

<br>

<!-- Top Button -->
<p style='background: black; width: 32px; height: 32px; border-radius: 50%; display: flex; justify-content: center; align-items: center; margin-left: auto;'><a href="#top" style='color: white; '>▲</a></p>

<br>

## <span id="12">✨ 12. 주요 코드</span>

<details>
<summary> [황선영] 허브 생성 및 자동 경로 계산 </summary>

<div>
설명 - 간단 작성 부탁드립니다. 

```java
    @Transactional
    public HubCreateResponseDto createHub(String roles, HubCreateRequestDto requestDto) {
        // 권한 확인
        validateMasterRole(roles);

        //허브 이름 중복 검증
        checkDuplicateHubName(requestDto.hubName());

        // 위도, 경도는 네이버 지도 API 호출로 설정
        Map<String, Double> coordinates = naverMapsService.getCoordinates(requestDto.hubAddress());
        Double latitude =   coordinates.get("latitude");
        Double longitude =   coordinates.get("longitude");

        Hub hub = Hub.builder()
                .hubName(requestDto.hubName())
                .hubAddress(requestDto.hubAddress())
                .hubLatitude(latitude)
                .hubLongitude(longitude)
                .build();

        Hub savedHub = hubRepository.save(hub);

        // 허브 경로 서비스 호출, 허브 간 이동 경로 자동 생성
        hubRouteService.createHubRoutesAutomatically(savedHub);

        return new HubCreateResponseDto(savedHub.getHubId());
    }
```
```java
    @Transactional
    public void createHubRoutesAutomatically(Hub newHub){
        // newHub를 제외한 기존 허브 모두 조회
        List<Hub> hubList = hubRepository.findAllByHubIdNot(newHub.getHubId());

        // newHub를 출발지로 설정, 모든 허브와 연결
        for(Hub goalHub : hubList){
            createHubRoute(newHub, goalHub);
        }

        // newHub를 도착지로 설정, 모든 허브와 연결
        for(Hub startHub : hubList){
            createHubRoute(startHub, newHub);
        }
    }
```
```java
    /*출발허브, 도착허브 인스턴스를 매개변수로 받아서 허브 경로 생성 */
    private void createHubRoute(Hub startHub, Hub goalHub){
        // Naver Maps API 호출하여 이동 거리와 시간 검색
        Map<String, Number> directions = naverMapsService.getDirections(startHub, goalHub);
        Double distance = directions.get("distance").doubleValue();
        Long transitTime = directions.get("transitTime").longValue();

        HubRoute hubRoute = HubRoute.builder()
                .originHubId(startHub.getHubId())
                .destinationHubId(goalHub.getHubId())
                .transitTime(transitTime)
                .distance(distance)
                .build();

        hubRouteRepository.save(hubRoute);
    }
```

</div>
</details>

<br>

<details>
<summary> 주요 코드에 대한 설명을 입력하세요. </summary>

<div>
설명

```jsx

```

</div>
</details>

<br>

<!-- Top Button -->
<p style='background: black; width: 32px; height: 32px; border-radius: 50%; display: flex; justify-content: center; align-items: center; margin-left: auto;'><a href="#top" style='color: white; '>▲</a></p>

<br>

## <span id="13">🚦 13. 트러블 슈팅</span>

<details>
<summary>[최우탁] OpenFeign Invalid HTTP method: PATCH </summary>

<div>

1. 문제 상황 <br />
   ![openfeign_trouble_1](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdna%2FsenN2%2FbtsP228uae0%2FAAAAAAAAAAAAAAAAAAAAAFPM1kh3cVJlaIyepRMGq-26xrPuLBlypU56BVkH8Kex%2Fimg.png%3Fcredential%3DyqXZFxpELC7KVnFOS48ylbz2pIh7yKj8%26expires%3D1756652399%26allow_ip%3D%26allow_referer%3D%26signature%3D1Adhf2iRqBIxWFno4%252BOSC6ThSec%253D)
   ![openfeign_trouble_2](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdna%2FcsXI7X%2FbtsP11WPACl%2FAAAAAAAAAAAAAAAAAAAAAFnLL7tOaohP5ePtMOrW6aTPrfonpf0_FuxwVNiufRCa%2Fimg.png%3Fcredential%3DyqXZFxpELC7KVnFOS48ylbz2pIh7yKj8%26expires%3D1756652399%26allow_ip%3D%26allow_referer%3D%26signature%3DkUVqHuGmZoJ2BENQnpyiIkVzt9Y%253D)

- `Order`서비스에서 `Product`서비스로 주문 상품 수량 수정이나 주문 취소시 차감했던 상품의 재고를 다시 변경하기 위해 `PATCH` 메소드 사용
- `OpenFeign`은 HTTP 요청을 추상화하여 메소드 호출로 REST API 호출 가능하도록 지원해주는데 HTTP 기본 구현체가 추가 의존성 없이 작동하도록 설계됨
- `HttpURLConnection`은 별도 라이브러리 의존 없이 사용 가능하므로 `OpenFeign`에서 사용
- `PATCH`는 비교적 최신 기능이라 `HttpURLConnection`에서 지원이 안되는 것

2. 해결방안 <br />

- 외부 HTTP 클라이언트 라이브러리를 사용하여 해결(커스텀)
  - ApachHttpClient, OkHttpClient
- 하지만 각 서비스 간 내부 통신이므로 Restful 규약을 준수할 필요는 없다고 생각함

3. 최종 결정

- `PATCH` -> `POST` 변경

</div>
</details>

<details>
<summary>[황선영] Naver Maps API 응답 구조 미확인으로 인한 오류 </summary>

<div>

1. 문제 상황 <br />
  - 경로 생성 시 INVALID_HUB_ROUTE 예외가 지속적으로 발생
  - 경로 옵션이 여러 개인데, 받아온 옵션 중 traoptimal이 없는 경우 문제가 생기는 것으로 판단
```java
        JsonNode routeNode = response.get("route");
        if (routeNode == null || !routeNode.has("traoptimal") || !routeNode.get("traoptimal").isArray() || routeNode.get("traoptimal").size() == 0) {
            throw new ApplicationException(HubException.INVALID_HUB_ROUTE);
        }

        JsonNode firstRoute = routeNode.get("traoptimal").get(0);
        JsonNode summary = firstRoute.get("summary");
        if (summary == null || !summary.has("distance") || !summary.has("duration")) {
            throw new ApplicationException(HubException.INVALID_HUB_ROUTE);
        }
```
2. 시도 <br />
  - 경로 옵션을 순차적으로 확인한 후 경로를 가져오도록 수정
3. 해결방안 <br />
  - 수정 후 정상적으로 경로가 생성되는 것을 확인
```java
            String[] routeOptions = {"traoptimal", "trafast", "tracomfort"};
            JsonNode selectedRoute = null;
            String selectedOption = null;

            for (String option : routeOptions) {
                if (routeNode.has(option) && routeNode.get(option).isArray() && !routeNode.get(option).isEmpty()) {
                    selectedRoute = routeNode.get(option);
                    selectedOption = option;
                    log.info("경로 옵션 '{}' 사용 가능", option);
                    break;
                }
            }

            if (selectedRoute == null) {
                log.error("사용 가능한 경로 옵션이 없음. route 노드: {}", routeNode);
                throw new ApplicationException(HubException.INVALID_HUB_ROUTE);
            }
```


</div>
</details>

<summary> 트러블 슈팅을 입력하세요. </summary>

<div>

1. 문제 상황 <br />

2. 시도 <br />

3. 해결방안 <br />

</div>
</details>


<details>
<summary> 트러블 슈팅을 입력하세요. </summary>

<div>

1. 문제 상황 <br />

2. 시도 <br />

3. 해결방안 <br />

</div>
</details>

<details>
<summary> 트러블 슈팅을 입력하세요. </summary>

<div>

1. 문제 상황 <br />

2. 시도 <br />

3. 해결방안 <br />

</div>
</details>

<details>
<summary> 트러블 슈팅을 입력하세요. </summary>

<div>

1. 문제 상황 <br />

2. 시도 <br />

3. 해결방안 <br />

</div>
</details>

<br>

<!-- Top Button -->
<p style='background: black; width: 32px; height: 32px; border-radius: 50%; display: flex; justify-content: center; align-items: center; margin-left: auto;'><a href="#top" style='color: white; '>▲</a></p>

<br>

## <span id="14">14. 📝 프로젝트 회고</span>

### 최우탁

- 권한별 기능 설계 및 구현에 시간을 너무 사용하여 `OpenFeign` 통신 장애 대응 로직을 충분히 구현하지 못해 아쉽다.
- 각 기능의 최적화나 고도화를 진행하지 못한 점도 개선할 필요가 있다.
- 다음 프로젝트에서는 설계 단계에서 서비스의 전체적인 도메인에 대한 이해를 충분한 회의나 지식 공유등을 통해 정리하고 가는 것이 좋을 것 같다.

### 황선영

 이번 프로젝트를 통해 처음으로 MSA 기반 시스템을 팀원들과 협업하며 개발하는 경험을 할 수 있었습니다. 협업 과정에서 GitHub의 PR과 브랜치 전략을 직접 사용해 보며 협업 방식을 익힐 수 있었고, 팀원분들이 적극적으로 피드백과 의견을 주셔서 많은 것을 배울 수 있었습니다!

허브 서비스를 맡아 개발하면서 Naver Maps API를 연동해 실시간 경로 탐색 및 저장 기능을 구현했는데, 이 과정에서 외부 API를 다루는 방법을 조금이나마 배울 수 있었습니다.
 
 다만 메시지 큐를 활용한 비동기 처리나 모니터링 시스템 구축 등 시간이 부족해서 시도하지 못한 부분이 많아서 아쉬웠습니다.

### 정성운

외부 API를 사용하기 위해 문서를 읽고 분석하는 능력을 키울 수 있었고, 받은 데이터를 가공하는 과정에서 데이터의 사용성과 로직 구성의 개선 방안을 더 생각하는 계기가 됐습니다. 팀원들이 서로 요청하고 협의하는 과정에서 모듈 간의 구성을 알 수 있었습니다. 다만 아쉬운 점은 초기 설계의 유동성과 자유성이 조금 더 있었더라면 시도해보고 싶은 부분이 있었는데 하지 못 했고, 더 많은 회의를 통해 서로의 생각을 더 잘 반영해 개발했더라면 좋았을 것 같습니다. 설계의 중요성을 다시 한 번 깨달았습니다.

### 이다예

 팀장을 제대로 처음 맡아보는데 일정관리가 중요하다는 것을 몸소..! 깨달았습니다. 
 
  기획할 때 각 단계마다 왜 이런 단계를 거쳐야만 하는지 명확하게 알게되는 경험이였고, MSA구조로 프로젝트를 하다보니 모놀리식에서는 겪을 수 없거나 예상치 못한 부분에서 에러를 경험했습니다. 그동안 몰랐던 부분에 대해서 확실하게 알게돼서 좋았습니다. 
 
 다음에는 좀 더 심화된 부분을 꼭 경험 할 수 있게 부지런히 공부해야겠다 싶습니다.

### 이강혁

MSA 환경에서 인증/인가, 서비스 간 통신, 도메인 권한 정책, 전역 예외 표준화 등을 직접 설계·구현하며 많은 것을 배웠다. 사용자 헤더 주입 폴백 전략과 per-request 캐시 최적화, JPA 감사/소프트 삭제 패턴을 적용해 안정성과 운영 편의성을 동시에 경험했다. 다만 아직 역량이 부족해 Redis 또는 메시지 큐(Kafka 등)와 같은 기술을 실서비스에 적용해 보지 못한 점이 아쉬웠다.

<br>

<!-- Top Button -->
<p style='background: black; width: 32px; height: 32px; border-radius: 50%; display: flex; justify-content: center; align-items: center; margin-left: auto;'><a href="#top" style='color: white; '>▲</a></p>

<br>

<!-- ## <span id="15">15. 🛠️ 시작 가이드</span>

### Installation

```shell
# 1. 클론하기
$ git clone https://github.com/CAREER-For-Me/Career-web.git .

# 2. 의존성 설치하기
$ yarn

# 3. 개발 서버 실행하기
$ yarn dev
```

<br> -->

<!--참고링크
https://mynamesieun.github.io/git/GitHub-README.md-%EC%9E%91%EC%84%B1-%EA%B0%80%EC%9D%B4%EB%93%9C/ -->
