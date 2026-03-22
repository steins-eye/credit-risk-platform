# 1. 定义工作目录
IMAGE_NAME="credit-common"
CONTAINER_NAME="credit-common"

echo "开始部署模块: $IMAGE_NAME"

# 3. 停止并删除旧容器
if [ $(docker ps -aq -f name=$CONTAINER_NAME) ]; then
    echo "正在停止旧容器..."
    docker stop $CONTAINER_NAME
    docker rm $CONTAINER_NAME
fi

# 4. 构建本地镜像 (利用解压出来的 Dockerfile)
# 注意：确保你的 Dockerfile 在 package.tgz 根目录下
echo "开始构建 Docker 镜像..."
docker build -t $IMAGE_NAME:latest .

# 5. 启动容器
echo "正在启动新容器..."
docker run -d \
  --name $CONTAINER_NAME \
  --restart always \
  -p 8091:8081 \
  -e "JAVA_OPTS=-Xms128m -Xmx256m" \
  $IMAGE_NAME:latest

# 6. 清理临时文件和虚悬镜像
rm -f package.tgz
docker image prune -f
echo "部署完成！"