### 다예약 - MSA 기반 멀티 컨텐츠 통합 예매 서비스

<div align="center">
  <h1>통합예매 서비스 - '다 예약'</h1>
  <p> 📅 MSA 기반 멀티 컨텐츠 통합 예매 서비스 📅</p>
</div>

<img width="693" height="380" alt="대표이미지" src="https://github.com/user-attachments/assets/950d9a4c-bfca-401c-8fbe-b1ae07cf13b4" />


<br/>

<div align="center">
  <a href="">홈페이지</a>
  &nbsp; | &nbsp;
  <!-- <a href="">Swagger</a>
   &nbsp; | &nbsp; -->
  <a href="https://www.notion.so/teamsparta/4-2612dc3ef51480679e40c1af55c69c0d?pvs=18">Notion</a>
</div>

---

## 0. 목차

1. [프로젝트 소개](#span-id1-1-프로젝트-소개--msa-기반-멀티-컨텐츠-통합-예매-서비스-span)
2. [기능 소개](#span-id2-2-기능-소개span)
3. [기술 스택](#span-id3-3-기술-스택span)
4. [브랜치 및 디렉토리 구조](#span-id4--4-브랜치-및-디렉토리-구조span)
5. [아키텍처 설계](#span-id5-5-아키텍처-설계span)
6. [erd 설계](#span-id6-6-erd-설계span)
8. [의사결정 및 트러블 슈팅](#span-id7-7-의사결정-및-트러블-슈팅span)
9. [API 명세](#span-id8-8-api-명세-span)

<br />

## <span id="1">🚩 1. 프로젝트 소개 : MSA 기반 멀티 컨텐츠 통합 예매 서비스 </span>


### 프로젝트 개발 기간:  25.09.01 ~ 25.10.03

###  제안하는 시스템

- 통합 예매 시스템으로 ‘문화 슈퍼앱’으로 발전
    - 공연·전시·음식점 예약 테마 제공
    - 음식점 웨이팅 서비스 제공
    - 각 테마 정보를 한 곳에서 비교할 수 있도록 통합 조회 기능 제공
  <br>

<br>

<!-- Top Button -->
<p style='background: black; width: 32px; height: 32px; border-radius: 50%; display: flex; justify-content: center; align-items: center; margin-left: auto;'><a href="#top" style='color: white; '>▲</a></p>

<br>


## <span id="2">✨ 2. 기능 소개</span>


### 담당 기능 : 음식점 웨이팅

### 기능 소개 및 정책

- 음식점에서 웨이팅 지원
    - 점주와 사용자의 알람을 통한 상호작용을 통해 원활한 웨이팅 상태 관리


- 음식점 웨이팅 및 노쇼 관리
    - 웨이팅 등록 및 손님·점주 간의 상호작용을 통한 웨이팅 상태 관리
    - 점주 기능
        - 최초/마지막 호출 시 10분 이내 응답 필요
        - 점주 사정에 따른 취소 가능
    - 손님 기능
        - 호출 시 응답 및 이동 시간 입력(최대 15분)
        - 도착 확인 및 취소 가능
        - 무응답 2회 시 노쇼로 등록 처리
    - 호출 및 응답 시 점주와 손님 모두에게 실시간 알림 제공
  

  
- 웨이팅 순서 관리
    - 웨이팅 등록 후 완료되지 않은 상태는 Redis 캐시에 저장하여 빠른 상태 조회와 순차적 대기열 관리 지원
    - 웨이팅 상태

      - [완료되지 않은 상태]

        - 대기중 : WAITING
        - 사장 호출1 : FIRST_CALLED
        - 사장 호출2 : FINAL_CALLED
        - 유저 이동중 : COMMING
        - 유저 도착 : ARRIVED

      - [완료된 상태]
      
        - 입장처리 : ENTERED
        - 무응답1 : NO_ANSWER
        - 무응답2(노쇼) : NO_ANSWER2
        - 사장 취소 : OWNER_CANCEL
        - 사용자 취소 : CANCEL




<br>

<!-- Top Button -->
<p style='background: black; width: 32px; height: 32px; border-radius: 50%; display: flex; justify-content: center; align-items: center; margin-left: auto;'><a href="#top" style='color: white; '>▲</a></p>

<br>




## <span id="3">📚 3. 기술 스택</span>



Java 17, Spring Framework(JPA, EUREKA), PostgreSQL, Redis, Kafka AWS EC2

<br>

<!-- Top Button -->
<p style='background: black; width: 32px; height: 32px; border-radius: 50%; display: flex; justify-content: center; align-items: center; margin-left: auto;'><a href="#top" style='color: white; '>▲</a></p>

<br>


## <span id="4"> 🗂️ 4. 브랜치 및 디렉토리 구조</span>

> 브랜치

- `main`: 배포용 브랜치
- `dev`: 개발용 브랜치
- `feat/waitings`: 개발용 - 웨이팅 기본 상태관리 개발 브랜치
- `feat/waiting_alarm`: 개발용 - 웨이팅 알람 연결 개발 브랜치
- `feat/waiting_noshow`: 개발용 - 웨이팅 노쇼 관리 개발 브랜치


<br>

> 웨이팅 서비스 디렉토리 구조

  ```
 📂 waiting/src
├── 📂 main
│   ├── 📂 java/com/dayaeyak/waiting
│   │   ├── 📂 common                               # 모듈 전역 공통(예외, 설정, 베이스 클래스 등)
│   │   │   ├── 📂 config                           # 전역 설정(WebMVC/Security/Kafka/AOP/ObjectMapper 등)
│   │   │   └── 📂 entity                           # 공통 엔티티/임베디드/감사(BaseEntity 등)
│   │   ├── 📂 domain                               # 웨이팅 도메인 레이어
│   │   │   ├── 📂 controller                       # REST 컨트롤러(API 엔드포인트, @RestController)
│   │   │   ├── 📂 dto                              # 도메인 DTO 모음
│   │   │   │   ├── 📂 request                      # 요청 DTO(검증 애노테이션 포함)
│   │   │   │   └── 📂 response                     # 응답 DTO(API 반환 모델)
│   │   │   ├── 📂 entity                           # 도메인 JPA 엔티티 매핑
│   │   │   ├── 📂 enums                            # 도메인 열거형(상태/타입/코드 등)
│   │   │   ├── 📂 kafka                            # 메시징 어댑터(이벤트 발행/구독)
│   │   │   │   ├── 📂 controller                   # Kafka 운영용/헬스체크용 REST 컨트롤러
│   │   │   │   ├── 📂 dto                          # 이벤트 페이로드 스키마(메시지 모델)
│   │   │   │   ├── 📂 enums                        # 이벤트 종류/버전 등 메시징 관련 enum
│   │   │   │   └── 📂 service                      # 프로듀서/컨슈머/직렬화 로직
│   │   │   ├── 📂 repository                       # 저장소 어댑터 계층
│   │   │   │   ├── 📂 cache                        # 캐시 리포지토리
│   │   │   │   │   └── 📂 redis                    # Redis 설정/템플릿/레포지토리
│   │   │   │   ├── 📂 internal                     # 내부/외부 연동 클라이언트
│   │   │   │   │   └── 📂 feign                    # Feign 클라이언트 인터페이스/설정
│   │   │   │   └── 📂 jpa                          # Spring Data JPA 리포지토리/커스텀 구현
│   │   │   ├── 📂 service                          # 도메인 서비스(트랜잭션 경계/비즈니스 로직)
│   │   │   └── 📂 worker                           # 비동기 작업(@Async), 스케줄러(@Scheduled), 이벤트 핸들러
│   │   ├── 📂 utils                                # 범용 유틸(시간/문자열/컨버터/헬퍼)
│   │   └── 📄 WaitingApplication.java              # Spring Boot 메인 애플리케이션
│   └── 📂 resources
│       └── 📄 application.yml                      # 애플리케이션/프로필별 설정
├── 📂 test                                         # 단위/통합 테스트 코드
└── 📄 build.gradle                                 # Gradle 빌드 스크립트/의존성 관리

  ```


<br>

<!-- Top Button -->
<p style='background: black; width: 32px; height: 32px; border-radius: 50%; display: flex; justify-content: center; align-items: center; margin-left: auto;'><a href="#top" style='color: white; '>▲</a></p>


<br>


## <span id="5">⚙️ 5. 아키텍처 설계</span>
<img width="986" height="386" alt="웨이팅 스트럭쳐 설계" src="https://github.com/user-attachments/assets/abece139-386b-4efe-999d-b23e1240c313" />


<br>
<!-- Top Button -->
<p style='background: black; width: 32px; height: 32px; border-radius: 50%; display: flex; justify-content: center; align-items: center; margin-left: auto;'><a href="#top" style='color: white; '>▲</a></p>

## <span id="6">💻 6. ERD 설계</span>

<img width="1240" height="424" alt="waiting ERD" src="https://github.com/user-attachments/assets/83b7256a-7aa7-41f2-8fbe-934e78461eb0" />

<br>

<!-- Top Button -->
<p style='background: black; width: 32px; height: 32px; border-radius: 50%; display: flex; justify-content: center; align-items: center; margin-left: auto;'><a href="#top" style='color: white; '>▲</a></p>


## <span id="7">📄 7.  의사결정 및 트러블 슈팅</span>


### 동접자 수 증가에 따른 캐싱 적용

**🔒 요구 사항**

---

- 가게별 최대 20팀 웨이팅 예상
- 가게별로 일정 인원이 동시에 접속하여 지속적으로 웨이팅 현황을 조회할 것으로 예상
    - 점주 1명 + 입장에 가까운 대기 순번 인원 5명
- 향후 가게 수 증가를 감안하여 최악의 상황 테스트 시나리오 구성

<br>

**🔐 비교군**

---

    1. 데이터베이스
    - 🔵 장점: 인메모리 리소스 사용 적음
    - ❌ 단점: 가정한 상황 대비 조회 성능이 충분하지 않을 수 있음

    2. Redis 캐싱 (채택)
        - 🔵 장점: 인메모리 기반으로 조회 성능이 매우 빠름
        - ❌ 단점: 인메모리 점유 관리에 주의 필

<br>

**🔑 결정 및 근거**

---

- MVP단계에서 데이터베이스 방식을 채택하여 개발
- 성능 테스트 진행

<aside>

⇒ 최악의 상황 테스트 (6인 동접 - Concurrency Thread Group)

<img width="638" height="691" alt="스크린샷_2025-09-25_오후_9 25 11" src="https://github.com/user-attachments/assets/04064911-8ffa-450d-9fe3-bff0013edb95" />

- 사전 준비
    - 5명을 미리 웨이팅 등록하여 웨이팅 ID 발급
- 테스트 API
    - GET /waiting/${id}
- 결과
    - 에러율 43.67%
    - 처리량 866.5/sec
    - 성능 개선이 필요함 ⇒ 고도화 단계에서 자주 조회되는 정보를 Redis로 캐싱하여 성능을 개선함
 
<img width="1160" height="326" alt="스크린샷_2025-09-25_오후_9 27 22" src="https://github.com/user-attachments/assets/a5da843b-1eb5-4412-bc20-e8373850d81b" />


</aside>

<br>

<!-- Top Button -->
<p style='background: black; width: 32px; height: 32px; border-radius: 50%; display: flex; justify-content: center; align-items: center; margin-left: auto;'><a href="#top" style='color: white; '>▲</a></p>

<br>

## <span id="8">✨ 8. API 명세 </span>

### 목록

#### 웨이팅

1. 웨이팅 등록 - POST /waitings
2. 웨이팅 단건 조회 - GET /waitings/{waitingId}
3. 웨이팅 순서 조회 - GET /waitings/order/{restaurantId}/{waitingId}
4. 웨이팅 목록 조회 - GET /waitings?restaurantId={restaurantId}
5. 웨이팅 단건 삭제 - DELETE /waitings/{waitingId}
6. 웨이팅 목록 삭제 - DELETE /waitings/all/{restaurantId}
7. 웨이팅 상태 변경 waiting_call first - POST /waitings/{restaurantId}/{waitingId}/actions/waiting_call
8. 웨이팅 상태 변경 waiting_call final - POST /waitings/{restaurantId}/{waitingId}/actions/waiting_call
9. 웨이팅 상태 변경 move_time - POST /waitings/{restaurantId}/{waitingId}/actions/waiting_user_coming
10. 웨이팅 상태 변경 user_arrived - POST /waitings/{restaurantId}/{waitingId}/actions/waiting_user_arrived
11. 웨이팅 상태 변경 waiting_entered - POST /waitings/{restaurantId}/{waitingId}/actions/waiting_entered
12. 웨이팅 상태 변경 user_cancel - POST /waitings/{restaurantId}/{waitingId}/actions/waiting_cancel
13. 웨이팅 상태 변경 owner_cancel - POST /waitings/{restaurantId}/{waitingId}/actions/waiting_cancel

#### 노쇼 관리

1. 노쇼 등록 - POST /noShows
2. 노쇼 단건 조회 - GET /noShows/{no_show_id}
3. 노쇼 목록 조회 - GET /noShows?restaurantId={restaurantId}
4. 노쇼 단건 삭제 - DELETE /noShows/{no_show_id}
5. 노쇼 목록 삭제 - DELETE /noShows/all/{restaurantId}


1.	웨이팅 등록 - POST /waitings

#### 설명 
웨이팅을 등록하고 당일 입장 순번과 현재 대기 포지션을 발급합니다. 

#### 요청
- Headers
  - Content-Type: application/json

- Request(JSON Body):		
  - datesId(number) : 영업일자 ID(당일)
  - userId(number): 사용자 ID
  - userCount(number): 인원 수
  - restaurantId(number): 매장 ID

#### 응답
•	201 Created

    {
        "waitingId": 944702,
        "restaurantId": 1,
        "ticketNo": 112,
        "status": "WAITING",
        "position": 7,
        "estimatedWaitMinutes": 18,
        "createdAt": "2025-10-02T12:10:02+09:00"
    }


#### 실패 응답
- 400(유효성)
- 404(restaurant/dates) 
- 409(중복) 
- 422(영업시간 외)

#### cURL 예시

    curl -X POST 'http://13.125.208.17:10900/waitings' \
        -H 'Content-Type: application/json' \
        -H 'Idempotency-Key: 8f6b7d2c-5a3a-4b3a-a2b9-9e2e0f8c9a10' \
        -d '{
            "datesId": 1,
            "userId": 1,
            "userCount": 2,
            "restaurantId": 1
    }'



<br>

<!-- Top Button -->
<p style='background: black; width: 32px; height: 32px; border-radius: 50%; display: flex; justify-content: center; align-items: center; margin-left: auto;'><a href="#top" style='color: white; '>▲</a></p>

<br>

