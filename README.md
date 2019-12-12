# medicine_manager_system
The program design of dataBase course
## 技术栈
### 前端
Vue+Vuex+ElementUI+axios+v-chart
### 后端
SpringBoot+MyBatis Plus+SpringSecurity+JWT+Redis

## 如何运行本项目
更改项目中的配置，如数据库等

切换目录到medicine_fonter
```
npm run build 
```
切换目录到medicine_backend
```
mvn clean package
```
之后将dist目录和jar包依次放在不同目录，并且将Dockerfile目录中的文件修改放置好
```
docker-compose up -d
```
