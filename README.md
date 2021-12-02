# 工程简介
## 使用maven搭建的springboot+mybatis-plus+mysql项目
1.完成用户模块的开发
用户增删查改等功能（数据库中的密码进行加密）
用户应包含用户名username，密码password，真实姓名real_name，手机号码phone等信息
查找功能应具备分页功能，并可根据用户名，真实姓名，手机号码等信息单条件或多条件查询

2.完成用户的登入功能
登入时，返回用户的基本信息，以及token（使用uuid生成32位随机数，并以"USER_TOKEN + uuid"作为key，使用fastjson，将用户信息序列化为json串，作为value值，存储到redis当中）

3.加上接口权限，使用拦截器 拦截登入接口 以外的接口
获取请求头中的"token"，并以"USER_TOKEN + token"作为key，去redis中查询。如果查询到，则放行。否则，返回401，无接口访问权限
