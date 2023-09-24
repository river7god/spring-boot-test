# 使用基础镜像，可以选择适合您应用程序的基础镜像
# 在此示例中，使用 OpenJDK 11 作为基础镜像
FROM openjdk:11-jre-slim

# 设置工作目录，这将是容器内应用程序的根目录
WORKDIR /app

# 复制应用程序的 JAR 文件到容器中
# 如果您的应用程序有多个构建产物，需要根据实际情况进行复制
COPY target/rozwork-admin.jar /app/rozwork-admin.jar

# 可以设置环境变量，如果您的应用程序需要它们
# ENV APP_ENV=production

# 暴露应用程序的端口，如果您的应用程序监听特定端口
# EXPOSE 8080

# 指定启动命令，这里的命令应该与您的应用程序启动命令一致
CMD ["java", "-jar", "rozwork-admin.jar"]
