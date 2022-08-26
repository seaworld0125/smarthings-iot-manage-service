# smart-app-with-smarthings
삼성 SmarThings Cloud를 이용한 IoT 기기 관리 홈페이지

# List of IoT devices currently supported
- Dawon smart plug: http://dawondns.com/new/ai%EC%8A%A4%EB%A7%88%ED%8A%B8%ED%94%8C%EB%9F%AC%EA%B7%B816a/

# Function
## KOR
- SmarThings Cloud에 등록된 IoT 디바이스 원격 조작 가능 (on/off)
- IoT 디바이스들의 일, 시간 단위 전기 사용량을 기록하여 저장함
- 디바이스 상세 페이지에서 일일 전기 사용량 그래프를 제공함
- 디바이스 상세 페이지에서 기간을 설정하여 시간 단위 데이터가 기록된 Excel 파일을 다운로드할 수 있음
- Docker image를 제공하기 때문에 Docker 환경 어디에서나 홈페이지를 호스팅할 수 있음

## ENG
- IoT device remote operation registered in SmartTHings Cloud (on/off)
- Measure and store the power usage of IoT devices on a daily, hourly basis
- Provides a daily units of electricity consumption graph on the device detail page
- By setting the period on the device detail page, you can download the Excel file recorded with time unit data
- Because it provides Docker Image, you can host the homepage anywhere in the Docker environment

# Stack
- Spring Boot
- Spring Security with Session cookie
- Apache POI
- JPA, JPQL
- MySQL
- Thymeleaf

# How to use (With Docker)
## 1. Docker 네트워크 생성합니다: 컨테이너 간 통신을 지원하기 위함
> Create a Docker network: To support communication between containers
- COMMAND: docker network create smartapp-net

## 2. Docker 볼륨 생성합니다: DB 데이터 유실을 방지하기 위함
> Create Docker Volume: To prevent DB data loss
- COMMAND: docker volume create smartapp-vol

## 3. Docker 네트워크와 볼륨을 사용하여 MySQL 컨테이너 실행합니다
> Run mysql container using Docker network and volume

> MYSQL_ROOT_PASSWORD 수정 가능(Can be modified)
- COMMAND: docker run -d --rm --name smartapp-mysql --network smartapp-net -v smartapp-vol:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=0000 mysql:latest

## 4. MySQL 최초 실행인 경우에는 smartapp_db database를 생성합니다
> For the first run of mysql, create SmartApp_db Database
### COMMAND
- docker exec -it smartapp-mysql bash
- mysql -u root -p
- Enter password: <your root passwd>
- create database smartapp_db;
- quit
- exit

## 5. 위 과정이 모두 완료되면 Smart app 컨테이너를 실행합니다
> When the above process is completed, run the Smart App container.

> DB_PASS must be the same as MYSQL_ROOT_PASSWORD
- COMMAND: docker run -d --rm --name smartapp --network smartapp-net -p 8080:8080 -e "SPRING_PROFILES_ACTIVE=prod" -e "DB_URL=jdbc:mysql://smartapp-mysql:3306" -e "DB_NAME=smartapp_db" -e "DB_USER=root" -e "DB_PASS=0000" seaworld0125/smart-app:latest


# Page
## 1. Login Page [/auth/login]
![login](https://user-images.githubusercontent.com/75168305/186823881-e29931e1-7e0d-462f-8f8e-ab2d9b7200a3.JPG)

## 2. Join Page [/auth/join]
![sign](https://user-images.githubusercontent.com/75168305/186823958-27bc9822-331c-44ef-9cde-b90895a60987.JPG)

## 3. Main Page [/main]
![main](https://user-images.githubusercontent.com/75168305/186823903-b49186e0-e07c-478e-b9df-1175045c720d.JPG)

## 4. Main Page: On/Off your Device
![GifMaker_20220826134405207](https://user-images.githubusercontent.com/75168305/186824011-10be777b-401f-4314-9c42-ab61f24aeb4e.gif)

## 5. Device Page [/main/detail/{deviceId}]
![device](https://user-images.githubusercontent.com/75168305/186824094-53e321a7-4b1d-4713-8347-f40c9f4ccfbc.JPG)

## 5. Device Page: Download your data to Excel file
### By setting the desired period, you can download the electricity usage record to Excel.
![excel1](https://user-images.githubusercontent.com/75168305/186824748-6a5c844e-a657-4280-bfe7-4e341f04f4c9.JPG)
![excel2](https://user-images.githubusercontent.com/75168305/186824753-9fb23292-7103-42e6-a50a-f2b35781fa1d.png)
![excel3](https://user-images.githubusercontent.com/75168305/186824764-b9732b57-6ae7-4703-b427-85ce2badb3e7.png)
