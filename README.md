# 🍪:Bidssgket:🍪
# 프로젝트 소개
- 중고 거래 경매 플랫폼입니다. <br><br>
- 판매자와 구매자는 중고 물품 경매를 이용해 물품 판매 또는 구매를 통한 직거래 및 안전거래가 가능합니다.<br><br>
- 중고거래 시장이 급성장하면서, 더욱 다양하고 재미있는 거래 경험을 원하는 사용자들이 증가함에 따라 경매 방식을 채택해 이러한 니즈를 충족시키고, 
  중고거래에 대한 흥미를 유발시키고자 프로젝트를 시작했습니다.<br><br>
- 발표영상 url : https://www.youtube.com/watch?v=iA8XMXnLVLk&t=1098s <br><br>
- 시연영상 : https://drive.google.com/file/d/173JmS22anXW3zAul16A5fiStc58jfLBu/view?usp=sharing <br><br>

   ° Spring Boot를 사용한 안정적인 웹 구현, MySQL로 데이터 관리

   ° 유지보수성을 높이기 위해 JPA를 선택해 Spring Data JPA와의 통합을 통한 효율적인 데이터 처리를 하고자 함.

   ° JPA의 ORM 기능을 활용하여 객체와 데이터베이스 간의 매핑을 최적화하고, JPQL로 복잡한 쿼리도 간결하게 작성

  <br>
  
## 팀원 구성
<div align="center">

| 김성호 | 고유진 | 김민규 | 장윤지 | 장준영 | 
| :------: |  :------: | :------: | :------: | :------: |
|[<img width="98" src="https://github.com/user-attachments/assets/e1e937b4-7d71-4b47-85b5-f8c72ee9f20c"> <br> @OOOIOOOIO](https://github.com/OOOIOOOIO)|[<image width="98" src="https://github.com/user-attachments/assets/753f6f05-3b62-4340-bdf7-232311604546"><br> @yujin4sth00](https://github.com/yujin4sth00)|[<img width="99" src="https://github.com/user-attachments/assets/4d9daaeb-2ede-4282-82f0-9c8cd7a3879f"> <br>@Gyunorld](https://github.com/Gyunorld)|[<img width="98" src="https://github.com/user-attachments/assets/d6ae8a2c-ef21-4112-aac7-10b7243f2e7a"> <br>@elliaaa](https://github.com/elliaaa)|[<img width="120" src="https://github.com/user-attachments/assets/78868622-2a57-4717-99d1-7cb0a3890c84"> <br>@finite2030](https://github.com/finite2030)|
</div>

## 1. 개발 환경
- 프로젝트 개발 환경 : IntelliJ
- 사용 기술 : Java, Spring Boot, Thymleaf, CSS, JS, Bootstrap
- DB 및 서버 관리 : NCP(Server-Ubuntu22.04, Object Storage)
- 배포 : Docker, Git Action
- 수행 환경 : Spring Security, Spring Data JPA, myBatis, mySQL, Redis, Nginx, Socket, …
- 버전 관리 : Git
- 협업 툴 : Slack, Figma, miro, Notion
  
### 주요 라이브러리 및 도구
- **Spring Boot 3.3.2**
  - Starter Dependencies:
    - `spring-boot-starter-data-jpa`
    - `spring-boot-starter-oauth2-client`
    - `spring-boot-starter-security`
    - `spring-boot-starter-thymeleaf`
    - `spring-boot-starter-validation`
    - `spring-boot-starter-web`
    - `spring-boot-starter-websocket`
  - 기타 의존성:
    - `thymeleaf-extras-springsecurity6`
    - `thymeleaf-layout-dialect`

- **MyBatis 3.0.3**

- **JDBC 8.0.28**

- **Lombok 1.18.24**

- **ModelMapper 3.1.1**

- **JUnit Platform**

- **AWS Java SDK 1.12.671**

### 추가 라이브러리

- **SockJS Client 1.5.1**

- **STOMP WebSocket 2.3.4**

- **jQuery 3.6.0**

### 개발 및 빌드 도구

- **Java 17**

- **Spring Boot DevTools**

### 테스트

- **Spring Security Test**
- **MyBatis Spring Boot Starter Test**
  <br><br>
## 2. 개발 기간 및 작업 관리
- 2024-07-19 ~ 2024-08-30
![image](https://github.com/user-attachments/assets/4dda0109-41de-465e-937f-8e620d498389)
## 3. 프로젝트 관리
- **GitFlow 전략을 활용한 프로젝트 관리**
  - main과 develop 브랜치를 사용해 안정적인 배포와 기능 개발 분리
  - 각 기능을 feature 브랜치로 분리하여 독립적 개발 진행
  - 충돌 방지를 위해 develop 브랜치에서 최신 코드 fetch 후 작업
  - 기능 완성 후 feature 브랜치를 develop 브랜치에 병합
  - develop 브랜치의 안정성 검증 후 주 1회 main 브랜치로 병합 및 배포
   <br>
- **TestDB와 상용DB를 분리한 데이터 관리 전략**
  - 데이터 충돌 및 예상치 못한 오류를 최소화하고, 사용자 경험을 저해하지 않도록 철저한 테스트 환경 유지를 위함
  - TestDB에서 성능 및 데이터 무결성 검토 후, 검증된 코드를 상용DB로 배포하여 운영 환경에서 안정적인 서비스 제공
  - 새로운 기능 개발 시 TestDB를 활용해 코드와 쿼리의 안전성을 사전에 검증하고, 상용DB에 영향을 미치지 않도록 조치
  <br>
- **노션을 활용한 문서화 작업**
  - 프로젝트 소개, 시장 조사, 유사 프로그램 분석, 주요 기능 등 모든 문서화 작업을 통합
  - 회의 내용을 문서화하고 팀원들의 피드백을 기록하여 프로젝트의 진행 상황과 개선점을 지속적으로 모니터링 <br><br>  



## 4. 아키텍쳐
![image](https://github.com/user-attachments/assets/aedcf4a7-80fd-4df8-9ed0-648de18a4145)

## 5. 시퀀스 다이어그램
- **실시간 채팅**
![image](https://github.com/user-attachments/assets/5b0be057-5fb0-4ccf-9468-3509d6e03ea8)
- **소셜로그인**
![image](https://github.com/user-attachments/assets/a48062f8-8f6b-4fe9-bf7d-6a55d1350f3a)

## 6. 주요 기능
- **경매 차이점**
![image](https://github.com/user-attachments/assets/19ef1616-b0c9-4943-97a7-3ff1a753d495) <br><br>
- **비스킷온도** <br>
![image](https://github.com/user-attachments/assets/31df667e-aec0-4f61-91b9-7cfb9f1cbdce)

## 7. 트러블슈팅
![image](https://github.com/user-attachments/assets/438a0c81-7066-424f-b3ee-d7c4126774c3)

## 8. 역할 분담
- 김성호 : 관리자
- 고유진 : 경매 시스템 설계, 위시 리스트
- 김민규 : 실시간 경매, 채팅, 상품CRUD
- 장윤지 : 로그인, 회원 마이페이지,리뷰 서비스
- 장준영 : 주문 및 결제


