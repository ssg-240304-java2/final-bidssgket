# 🍪:Bidssgket:🍪
# 프로젝트 소개
- 중고 거래 경매 플랫폼입니다.
- 판매자와 구매자는 중고 물품 경매를 이용해 물품 판매 또는 구매를 통한 직거래 및 안전거래가 가능합니다.
- 중고거래 시장이 급성장하면서, 더욱 다양하고 재미있는 거래 경험을 원하는 사용자들이 증가함에 따라 경매 방식을 채택해 이러한 니즈를 충족시키고, 
  중고거래에 대한 흥미를 유발시키고자 프로젝트를 시작했습니다.

   ° Spring Boot를 사용한 안정적인 웹 구현, MySQL로 데이터 관리

   ° 유지보수성을 높이기 위해 JPA를 선택해 Spring Data JPA와의 통합을 통한 효율적인 데이터 처리를 하고자 함.

   ° JPA의 ORM 기능을 활용하여 객체와 데이터베이스 간의 매핑을 최적화하고, JPQL로 복잡한 쿼리도 간결하게 작성
  <br><br>
  
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
  - Spring Boot를 기반으로 하여, 다양한 기능을 빠르고 쉽게 설정할 수 있습니다.
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
  - MyBatis는 SQL을 직접 사용하여 데이터베이스와 상호작용하는 ORM 프레임워크입니다.
  - MyBatis Spring Boot Starter를 통해 Spring Boot와의 통합이 용이합니다.

- **JDBC 8.0.28**
  - MySQL 데이터베이스와의 연동을 위해 JDBC 드라이버를 사용합니다.

- **Lombok 1.18.24**
  - 반복적인 코드를 줄여주는 어노테이션 프로세서입니다.
  - `@Getter`, `@Setter`, `@Builder` 등을 사용하여 편리하게 코드 작성이 가능합니다.

- **ModelMapper 3.1.1**
  - DTO와 엔티티 간의 변환을 간편하게 해주는 라이브러리입니다.

- **JUnit Platform**
  - 단위 테스트 및 통합 테스트를 위해 사용되며, Spring Boot Test와 함께 사용됩니다.

- **AWS Java SDK 1.12.671**
  - NCP S3와 연동하여 파일 업로드 기능을 제공합니다.

### 추가 라이브러리

- **SockJS Client 1.5.1**
  - 웹소켓 통신을 위한 클라이언트 라이브러리입니다.

- **STOMP WebSocket 2.3.4**
  - STOMP 프로토콜을 이용한 웹소켓 통신을 지원합니다.

- **jQuery 3.6.0**
  - 웹 프론트엔드 개발에서 사용되는 jQuery 라이브러리입니다.

### 개발 및 빌드 도구

- **Java 17**
  - 프로젝트의 기본 언어로 사용되며, 최신 Java 기능을 활용합니다.

- **Spring Boot DevTools**
  - 개발 중 핫 리로드 기능을 제공하여 빠른 피드백을 받을 수 있습니다.

### 테스트

- **Spring Security Test**
  - Spring Security의 테스트 기능을 제공합니다.
  
- **MyBatis Spring Boot Starter Test**
  - MyBatis 통합 테스트를 위한 도구입니다.
  
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
   
- **TestDB와 상용DB를 분리한 데이터 관리 전략**
  - 데이터 충돌 및 예상치 못한 오류를 최소화하고, 사용자 경험을 저해하지 않도록 철저한 테스트 환경 유지를 위함
  - TestDB에서 성능 및 데이터 무결성 검토 후, 검증된 코드를 상용DB로 배포하여 운영 환경에서 안정적인 서비스 제공
  - 새로운 기능 개발 시 TestDB를 활용해 코드와 쿼리의 안전성을 사전에 검증하고, 상용DB에 영향을 미치지 않도록 조치



## 4. 아키텍쳐
![image](https://github.com/user-attachments/assets/aedcf4a7-80fd-4df8-9ed0-648de18a4145)
## 5. 역할 분담
- 김성호 : 관리자
- 고유진 : 경매 시스템 설계, 위시 리스트
- 김민규 : 실시간 경매, 채팅, 상품CRUD
- 장윤지 : 로그인, 회원 마이페이지,리뷰 서비스
- 장준영 : 주문 및 결제


