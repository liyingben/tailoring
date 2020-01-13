#1. 依赖环境：
- windows 或 linux
- JDK 1.8
- sql server 2012


#2. 数据导入myql
- 将tailoring_all.sql导入到sql server


#3. java程序
- 启动命令
```
$ java -jar tailoring.war
```

- 程序后台运行命令
```
$ nohup java -jar tailoring.war >> Log.log  &
```

- 验证是否启动成访问URL
http://127.0.0.1:8182/tailoringPlans

#4.备注

生成的war包是和生产环境一致的，所以不用再修改其它配置


