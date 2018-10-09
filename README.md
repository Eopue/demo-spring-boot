# demo-spring-boot
- 这是一个spring-boot的demo项目，持续更新中
+ 数据源管理: DruidDataSource
+ 用户认证: shiro
  包括用户token认证, 用户权限, 用户角色判定
+ 技术框架
  spring mvc + mybatis, redis, mysql
+ 发布
  阿里云服务器支撑 + docker + jenkins
+ 打包命令mvn clean package -U -Dmaven.test.skip=true
+ 日志查看 docker logs -f [容器名称] [--tail 300(可加，查看最近300行的日志)]
