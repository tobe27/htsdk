# Hetun SDK
Springboot + Shiro + MySQL + Mybatis框架

## 框架结构
- common -工具模块
  -enums --枚举类
  -exception --自定义异常类
  -utils --工具类
  -validation --validate分组类

- system - 功能模块
  - config - 配置
    - cors - 跨域配置
    - handler - 异常统一拦截
    - shiro - shiro配置
  - logging - AOP记录日志/JPA
  - modules - 功能模块开发
  - app - app模块
  - system - 用户角色权限模块
  - xxx - 可添加其他模块
