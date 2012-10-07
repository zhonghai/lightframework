1. baseframe 项目定义了接口，及公共类
2. exchangeDbData 项目实现了接口，可以完成从配置数据生成传输类的工作
   MainWorkDB 是从数据库的配置数据生成交换类
   MainWorkSpring 是从spring配置文件生成交换类
3. eddmanager 实现网页配置数据交换参数
4. eddmanager\database  derby数据库
	目前配置数据使用的数据库,如果要使用网络数据库模式，则
	需要启动startup.bat,如果是嵌入式数据库，则不用
5. 测试数据库 目录存放的是测试使用的数据库	