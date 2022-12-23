# zup-user-service

### User service for zup application.

---
![GitHub top language](https://img.shields.io/github/languages/top/cccaaannn/zup-user-service?style=flat-square) ![](https://img.shields.io/github/repo-size/cccaaannn/zup-user-service?style=flat-square) [![GitHub license](https://img.shields.io/github/license/cccaaannn/zup-user-service?style=flat-square)](https://github.com/cccaaannn/zup-user-service/blob/master/LICENSE)

### zup is a messaging application, built by microservice architecture.
### Services
- [Frontend](https://github.com/cccaaannn/zup-frontend)
- [User service](https://github.com/cccaaannn/zup-user-service) (This project)
- [Message service](https://github.com/cccaaannn/zup-message-service)
- [K8s configurations](https://github.com/cccaaannn/zup-k8s)

<hr>

### Configurations
1. Fill the empty fields of `application-prod.yaml`.

### Build requirements
- MAVEN_PACKAGE_TOKEN has to be set as a GitHub token to download `cccaaannn/javacore` package.
- A custom maven settings.xml file is used for building. `.mvn/project-settings.xml`

<hr>

## Running with Docker
1. Build
```shell
docker build --build-arg MAVEN_PACKAGE_TOKEN=<MAVEN_PACKAGE_TOKEN> -t cccaaannn/zup-user-service:latest .
```

2. Run
```shell
docker run -d --name zup-user-service -p 8080:8080 cccaaannn/zup-user-service:latest
```

## Running Native
1. Build
```shell
expot MAVEN_PACKAGE_TOKEN=<MAVEN_PACKAGE_TOKEN>
mvn --settings .mvn/project-settings.xml clean install
```

2. Run
```shell
java -jar target/zup-user-service-0.0.1-SNAPSHOT.jar
```

