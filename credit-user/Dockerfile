# 基础镜像使用 JDK17
FROM eclipse-temurin:17-jdk-alpine
# 署名
LABEL maintainer="Wu Junfeng"
# 设置时区
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
# 拷贝 jar (注意：mvn package 后的产物路径)
COPY target/*.jar app.jar
# 启动命令，限制内存防止压垮服务器
ENTRYPOINT ["java", "-Xmx256m", "-Xms128m", "-jar", "/app.jar"]