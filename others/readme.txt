1.项目全路径必须设为英文路径

2.启动应用：
windows下执行start.bat；linux下执行start.sh
启动成功后访问：
http://localhost/

3.若本地端口冲突或想要自定义端口，修改相应启动脚本：
java -jar ./jettyDemo-1.0-SNAPSHOT.jar 端口号
例如：
java -jar ./jettyDemo-1.0-SNAPSHOT.jar 8888
相应访问地址：http://localhost:8888/

4.目录清单：

jettyDemo.db数据库文件
jettyDemo-1.0-SNAPSHOT.jar主要应用程序
logs：web访问日志
${HOME}/jettyDemo_logs:应用详细日志
