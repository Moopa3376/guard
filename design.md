框架功能:
1. 拦截特定http请求，进行登录校验和权限校验
2. 基于token和account的类似session存储功能

设计：
1. 授权者，专门管理登陆下线
    * 其实感觉主要就是对于token的管理(新建token和删除token)
2. 检验者：专门来检验权限，基于url的检验
3. rule导入者
4. 加密模块,用来提供加密工具

授权者有login方法，用jwt的方式来标识用户，之后把jwt放在http header中。我觉得可以让用户去自行设置使用cookie还是header。

* 授权者登陆检验，权限检验
    * 登陆检验则是根据header中是否正确的token来进行校验(当然你需要过滤signin操作)
* 服务接口
    * 导入用户信息
    * 校验用户密码



和web的集成操作
* 通过filter的方式进行集成


1. controller中根据当前http request来获得已登录的用户
2. 所有信息以用户为单位来进行存储,controller首先获得用户,再获得其中存储的信息.