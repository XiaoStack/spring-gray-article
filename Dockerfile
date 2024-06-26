# 基础镜像
FROM openjdk:8-jdk-alpine

# 指定工作目录
WORKDIR /app

# 将 jar 包添加到工作目录，比如 /target/spring-gray-article-0.0.1-SNAPSHOT.jar
ADD target/spring-gray-article-0.0.1-SNAPSHOT.jar .

# 暴露端口
EXPOSE 8881 8882

# 启动命令
ENTRYPOINT ["java","-jar","/app/spring-gray-article-0.0.1-SNAPSHOT.jar"]
