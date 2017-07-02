Guard
==

Guard是一个集成登录控制和权限控制的框架.
Guard is a framework to manage sign in and authority checks;

Guard是基于jwt的控制框架,致力于方便用户统一管理web和app端的用户以及相应的`登录和权限`.

Guard启动:
--
* 首先在web.xml加入Guard Filter
* Guard初始化:
    * Guard.init()
    * 首先初始化config的init()
    * 之后查找用户实现的guardService
        * 根据用户实现的service导入角色,权限等信息
        * 对角色,权限进行匹配
        * 加进缓存
* Guard登录过程:
    * 用户直接调用Guard.signin(...)进行登录
    * 登录:
        * 根据用户实现的service获得账户信息,并且按照客户实现的密码匹配方式进行request中的密码和数据库中的密码进行匹配
        * 登陆成功返回token对象,否则返回null
    * 往http header添加jwt,往cache中添加token
* Guard验证过程:
    * 首先根据request path判断该请求是否需要被验证
    * 如需验证,则看http header是否有合法的jwt
    * 验证cache中是否已有相应jwt
    * 验证权限是否可以请求该path
    * 放行 or 401 or 403