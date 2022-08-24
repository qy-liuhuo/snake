### Magus 贪吃蛇项目

完成人: 马浩天
完成时间: 2022年6月16日
基于JAVAFX实现，使用Maven进行包管理
jdk版本：`jdk11`

功能：
+ 开始游戏，结束游戏，暂停游戏，恢复游戏
+ 事物随机出现，贪吃蛇长度不断增大
+ 碰壁或碰到自身游戏结束
+ 游戏难度选择
+ 地图大小选择
+ 游戏存储
+ 排名


### UML类图设计
![](https://img.qylh.xyz/blog/UML%E7%B1%BB%E5%9B%BE.png)

### 项目地址

[地址](http://hdyb.hbu.cn/git/Mahaotian/Magus_javaSnake)

### 打包方式

#### idea打包

file->project structure->添加javafx打包

![](https://img.qylh.xyz/blog/1655441167301.png)

build->build Artifacts

会打包到out路径下

#### mvn编译

```shell
cd 项目根目录
mvn package assembly:single
```
