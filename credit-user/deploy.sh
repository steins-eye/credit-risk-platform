# 1. 定义工作目录
APP_DIR="/home/docker-app/credit-system/credit-user"
TGZ_FILE="$APP_DIR/package.tgz"
IMAGE_NAME="credit-user"
CONTAINER_NAME="credit-user"

echo "开始部署模块: $IMAGE_NAME"

# 2. 进入目录并解压
cd $APP_DIR
if [ -f "$TGZ_FILE" ]; then
    echo "发现新制品，正在解压..."
    # --strip-components=1 可以根据你打包时的层级调整，确保解压出的是 target 里的内容
    tar -zxf package.tgz
else
    echo "未发现制品包，请检查流水线产物配置！"
    exit 1
fi

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
  -p 8090:8081 \
  -e "JAVA_OPTS=-Xms128m -Xmx256m" \
  -e "SPRING_CLOUD_NACOS_DISCOVERY_SERVER_ADDR=你的NacosIP:8848" \
  $IMAGE_NAME:latest

# 6. 清理临时文件和虚悬镜像
rm -f package.tgz
docker image prune -f
echo "部署完成！"