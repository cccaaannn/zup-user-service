# zup-user-service

### User service for zup application.

---
![GitHub top language](https://img.shields.io/github/languages/top/cccaaannn/zup-user-service?style=flat-square) ![](https://img.shields.io/github/repo-size/cccaaannn/zup-user-service?style=flat-square) [![GitHub license](https://img.shields.io/github/license/cccaaannn/zup-user-service?style=flat-square)](https://github.com/cccaaannn/zup-user-service/blob/master/LICENSE)


## Configurations
- Fill the empty parts of `application-prod.yaml`.

## Build requirements
- MAVEN_PACKAGE_TOKEN GitHub token to download `cccaaannn/javacore` package.

## Build native
````shell
expoty MAVEN_PACKAGE_TOKEN=<MAVEN_PACKAGE_TOKEN>
mvn --settings .mvn/project-settings.xml clean install
````
## Build for docker
````shell
docker build --build-arg MAVEN_PACKAGE_TOKEN=<MAVEN_PACKAGE_TOKEN> -t cccaaannn/zup-user-service:latest .
````