#Springboot-Mybatis(去xml版)#  
2018/4/24 20:15:51 
----------
## 配置篇 ##
### maven-pom依赖 ###
		<dependency>
			<groupId>org.mybatis.spring.boot</groupId>
			<artifactId>mybatis-spring-boot-starter</artifactId>
			<version>1.3.2</version>
		</dependency>
### datasource配置 ###
#### 1.单个数据库配置：application.properties####
    	spring.datasource.url=jdbc:oracle:thin:@192.168.100.18:1521:tianque
		spring.datasource.username=lngrid_new
		spring.datasource.password=lngrid_new
		#datasource选型
		spring.datasource.type=com.alibaba.druid.pool.DruidDataSource
		spring.datasource.driver-class-name=oracle.jdbc.driver.OracleDriver
		spring.datasource.maxActive=20
		spring.datasource.initialSize=1
		spring.datasource.maxWait=60000
		spring.datasource.minIdle=1
		spring.datasource.testWhileIdle=true
		spring.datasource.timeBetweenEvictionRunsMillis=60000
		spring.datasource.minEvictableIdleTimeMillis=30000
		spring.datasource.validationQuery=select 'x'
		spring.datasource.poolPreparedStatements=true
		spring.datasource.maxOpenPreparedStatements=20
#### 2.1 多数据源配置：静态分离（不同的mapper路径对应不同的数据库）####
1.首先要将spring boot自带的DataSourceAutoConfiguration禁掉，因为它会读取application.properties文件的spring.datasource.*属性并自动配置单数据源。在@SpringBootApplication注解中添加exclude属性即可
@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class
})  
2.application.properties：添加主从两个数据库配置  

		spring.datasource.master.jdbc-url=jdbc:oracle:thin:@192.168.100.18:1521:tianque
		spring.datasource.master.username=lngrid_new
		spring.datasource.master.password=lngrid_new
		spring.datasource.master.type=com.alibaba.druid.pool.DruidDataSource
		spring.datasource.master.driver-class-name=oracle.jdbc.driver.OracleDriver

		spring.datasource.slave.jdbc-url=jdbc:oracle:thin:@192.168.100.18:1521:tianque
		spring.datasource.slave.username=tjgrid_new
		spring.datasource.slave.password=tjgrid_new
		spring.datasource.slave.type=com.alibaba.druid.pool.DruidDataSource
		spring.datasource.slave.driver-class-name=oracle.jdbc.driver.OracleDriver
（注意：是jdbc-url不是正常的url，如果写成url）  
3.datasource 的配置类：  

		@Configuration
		public class DataSourceConfig {
		    @Bean(name = "masterDS")
		    @ConfigurationProperties(prefix = "spring.datasource.master") // application.properteis中对应属性的前缀
		    public DataSource masterDS() {
		        return DataSourceBuilder.create().build();
		    }
		
		    @Bean(name = "slaveDS")
		    @ConfigurationProperties(prefix = "spring.datasource.slave") // application.properteis中对应属性的前缀
		    public DataSource slaveDS() {
		        return DataSourceBuilder.create().build();
		    }
		}

4.mybatis 的配置类：通过配置两个sql工厂，让两个目录的mapper访问两个不同的数据库
MybatisConfig：  

		@Configuration
		@MapperScan(basePackages = {"com.tianque.mapper"}, sqlSessionFactoryRef = "sqlSessionFactory1")
		public class MybatisConfig1 {
		    @Autowired
		    @Qualifier("masterDS")
		    private DataSource ds1;
		
		    @Bean
		    public SqlSessionFactory sqlSessionFactory1() throws Exception {
		        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		        factoryBean.setDataSource(ds1); // 
		        return factoryBean.getObject();
		    }
		
		    @Bean
		    public SqlSessionTemplate sqlSessionTemplate1() throws Exception {
		        SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactory1()); // 使用上面配置的Factory
		        return template;
		    }
		}  
MybatisConfig1：  

		@Configuration
		@MapperScan(basePackages = {"com.tianque.mapper"}, sqlSessionFactoryRef = "sqlSessionFactory1")
		public class MybatisConfig1 {
		    @Autowired
		    @Qualifier("masterDS")
		    private DataSource ds1;
		
		    @Bean
		    public SqlSessionFactory sqlSessionFactory1() throws Exception {
		        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		        factoryBean.setDataSource(ds1); // 
		        return factoryBean.getObject();
		    }
		
		    @Bean
		    public SqlSessionTemplate sqlSessionTemplate1() throws Exception {
		        SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactory1()); // 使用上面配置的Factory
		        return template;
		    }
		}
#### 2.2 多数据源配置：动态数据源####
使用动态数据源的初衷，是能在应用层做到读写分离，即在程序代码中控制不同的查询方法去连接不同的库。除了这种方法以外，数据库中间件也是个不错的选择，它的优点是数据库集群对应用来说只暴露为单库，不需要切换数据源的代码逻辑。（本例结合aop编程，使用注解切换数据库）
1.首先要将spring boot自带的DataSourceAutoConfiguration禁掉，因为它会读取application.properties文件的spring.datasource.*属性并自动配置单数据源。在@SpringBootApplication注解中添加exclude属性即可：   
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,DataSourceTransactionManagerAutoConfiguration.class, MybatisAutoConfiguration.class})
  
2.配置动态的DataSource（DataSourceConfig）：  

错误代码为：（@ConfigurationProperties不生效，不能加载属性文件，最后得到属性为空的DataSource）

		@ConfigurationProperties(prefix = "spring.datasource.master") // application.properteis中对应属性的前缀
		public DataSource masterDS() {
		    return DataSourceBuilder.create().build();
		}
		@ConfigurationProperties(prefix = "spring.datasource.slave") // application.properteis中对应属性的前缀
		public DataSource slaveDS() {
		    return DataSourceBuilder.create().build();
		}  
正确代码为：  

	    @Autowired
	    private Environment env;
	
	    public DataSource masterDS() throws Exception {
	        Properties props = new Properties();
	        props.put("driverClassName", env.getProperty("spring.datasource.master.driver-class-name"));
	        props.put("url", env.getProperty("spring.datasource.master.jdbc-url"));
	        props.put("username", env.getProperty("spring.datasource.master.username"));
	        props.put("password", env.getProperty("spring.datasource.master.password"));
	        return DruidDataSourceFactory.createDataSource(props);
	    }
	
	    public DataSource slaveDS() throws Exception {
	        Properties props = new Properties();
	        props.put("driverClassName", env.getProperty("spring.datasource.slave.driver-class-name"));
	        props.put("url", env.getProperty("spring.datasource.slave.jdbc-url"));
	        props.put("username", env.getProperty("spring.datasource.slave.username"));
	        props.put("password", env.getProperty("spring.datasource.slave.password"));
	        props.put("type", env.getProperty("spring.datasource.slave.type"));
	        return DruidDataSourceFactory.createDataSource(props);
	    }


	    @Bean
	    @Primary
	    public DynamicDataSource dataSource() throws Exception  {
	        DynamicDataSource dynamicDataSource = new DynamicDataSource();
	
	        // 配置多数据源
	        Map<Object, Object> dsMap = new HashMap(5);
	        dsMap.put(DataSourceType.masterDS, masterDS());
	        dsMap.put(DataSourceType.slaveDS, slaveDS());
	        // 默认数据源
	        dynamicDataSource.setDefaultTargetDataSource(masterDS());
	        dynamicDataSource.setTargetDataSources(dsMap);
	        return dynamicDataSource;
	    }  
mybatisConfig(可以选择自动配置项，也可以手动配置,注意，手动配置，注解很难找到详细的注解资料，xml配置更全面):  
手动配置时指定xml配置文件路径：  

		@Bean(name = "moonlightSqlSessionFactory")
		@Primary
		public SqlSessionFactory moonlightSqlSessionFactory(@Qualifier("moonlightData") DataSource dataSource) throws Exception {
		    SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		    bean.setDataSource(dataSource);
		    bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis-mapper/*.xml"));
		    bean.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
		    return bean.getObject();
		}　
		bean.setConfigLocation(new ClassPathResource("mybatis-config.xml"));

整体配置：  

		@Configuration
		@MapperScan(basePackages = {"com.tianque.mapper"}, sqlSessionFactoryRef = "sqlSessionFactory")
		public class MybatisDynamicConfig {
		    @Autowired
		    private Environment env;
		
		    @Bean
		    public SqlSessionFactory sqlSessionFactory(DynamicDataSource dynamicDS) throws Exception {
		        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		        factoryBean.setDataSource(dynamicDS); // 使用动态数据源
		        return factoryBean.getObject();
		    }
		
		    @Bean
		    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) throws Exception {
		        SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactory); // 使用上面配置的Factory
		        return template;
		    }
		
		    @Bean
		    public DataSourceTransactionManager transactionManager(DynamicDataSource dynamicDS) throws Exception {
		        return new DataSourceTransactionManager(dynamicDS);
		    }
		}

注意：masterDS和slaveDS不能加上@Bean的注解（网上大量资料都使用@Bean，踩过坑），不然会报错：  

		The dependencies of some of the beans in the application context form a cycle:
		
		   mybatisController (field com.tianque.mapper.BaseInfoDao com.tianque.controller.MybatisController.baseInfoDao)
		      ↓
		   baseInfoDao defined in file [D:\workspace\id_workspace\silverBullet\silverbullet-mybatis\target\classes\com\tianque\mapper\BaseInfoDao.class]
		      ↓
		   sqlSessionFactory defined in class path resource [com/tianque/config/MybatisDynamicConfig.class]
		┌─────┐
		|  dynamicDataSource defined in class path resource [com/tianque/config/DataSourceConfig.class]
		↑     ↓
		|  masterDS defined in class path resource [com/tianque/config/DataSourceConfig.class]
		↑     ↓
		|  org.springframework.boot.autoconfigure.jdbc.DataSourceInitializerInvoker
		└─────┘  
配套DynamicDataSource动态数据库为了重写-数据源的选择逻辑：  

		public class DynamicDataSource extends AbstractRoutingDataSource {
		    private static final Logger log = LoggerFactory.getLogger(DynamicDataSource.class);
		
		    @Override
		    protected Object determineCurrentLookupKey() {
		        log.debug("数据源为{}", DataSourceContextHolder.getDB());
		        return DataSourceContextHolder.getDB();
		    }
		}

3.通过本地线程保证DataSourceType容器的线程安全：  
		
		public class DataSourceContextHolder {
		    public static final DataSourceType DEFAULT_DS = DataSourceType.masterDS;
		    private static final ThreadLocal<DataSourceType> contextHolder = new ThreadLocal<>();
		    static{
		        contextHolder.set(DEFAULT_DS);
		    }
		    // 设置数据源名
		    public static void setDB(DataSourceType dbType) {
		        contextHolder.set(dbType);
		    }
		
		    // 获取数据源名
		    public static DataSourceType getDB() {
		        return (contextHolder.get());
		    }
		
		    // 清除数据源名
		    public static void clearDB() {
		        contextHolder.remove();
		    }
		}
配套DataSourceType数据类型类：  

		public enum DataSourceType {
		    masterDS,slaveDS
		}

4.自定义注解：  
注解类：  

		@Retention(RetentionPolicy.RUNTIME)
		@Target({
		        ElementType.METHOD
		})
		public @interface DS {
		    DataSourceType  value() default DataSourceType.masterDS;
		}
切面类（@Component切面类必须加上@Component才能被扫描自动注入）:  

		@Aspect
		@Component
		public class DynamicDataSourceAspect {
		    @Before("@annotation(DS)")
		    public void beforeSwitchDS(JoinPoint point){
		        //获得当前访问的class
		        Class<?> className = point.getTarget().getClass();
		        //获得访问的方法名
		        String methodName = point.getSignature().getName();
		        //得到方法的参数的类型
		        Class[] argClass = ((MethodSignature)point.getSignature()).getParameterTypes();
		        DataSourceType dataSource = DataSourceContextHolder.DEFAULT_DS;
		        try {
		            // 得到访问的方法对象
		            Method method = className.getMethod(methodName, argClass);
		            // 判断是否存在@DS注解
		            if (method.isAnnotationPresent(DS.class)) {
		                DS annotation = method.getAnnotation(DS.class);
		                // 取出注解中的数据源名
		                dataSource = annotation.value();
		            }
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		        // 切换数据源
		        DataSourceContextHolder.setDB(dataSource);
		
		    }
		
		
		    @After("@annotation(DS)")
		    public void afterSwitchDS(JoinPoint point){
		
		        DataSourceContextHolder.clearDB();
		
		    }
		}

5.注解使用（不能直接用在接口类方法上，不起作用！！）：  
错误示例：  

		@Mapper
		public interface BaseInfoDao {
    		@DS(DataSourceType.slaveDS)
		    @Select("SELECT * from Baseinfo where id <100")
		    List<Map> selectAll();
		}  
正确示例：  

		@service
		public class XXXServiceImpl {
			@DS(DataSourceType.slaveDS)
		    List<Map> selectAll(){}
		}
6.注意事务跟数据库切换的关系，事务要在数据库切换之后再开启，不然会出现问题。注解上来说，@DS注解要在事务注解上方
### 扫描配置 ###
- 接口路径：在启动类或者配置类中加入@MapperScan("XXX.XXX.XXX") 或者接口类上加入@Mapper
- 配置实体类：application.properties加入mybatis.type-aliases-package=XXX.XXX.domain或者  
在domain类上添加@Alias("XXX")
- 配置文件xml：application.properties加入mybatis.config-location=
- 类型转换类：application.properties加入mybatis.type-handlers-package=  

### 优化配置   

		#mybatis配置# 
		#全局映射器启用缓存  
		mybatis.configuration.cache-enabled=true  
		#查询时,关闭关联对象及时加载以提高性能  
		mybatis.configuration.lazy-loading-enabled=false  
		#按需加载字段(加载字段由SQL指定),不会加载关联表的所有字段,以提高性能  
		mybatis.configuration.aggressive-lazy-loading=false  
		#允许返回不同的结果集以达到通用的效果  
		mybatis.configuration.multiple-result-sets-enabled=true  
		#对于批量更新操作缓存SQL以提高性能  
		mybatis.configuration.default-executor-type=REUSE  
		#数据库执行超时时间  
		mybatis.configuration.default-statement-timeout=25000  

----------
## 应用篇 ##
### 类型装换：typehandler ###
在我们利用mybatis作为持久层框架存储数据时，从mybatis接收参数到mysql存储数据，都会用到typeHandler类型处理器。这也就是从JavaType->JdbcType的转化过程。由于mybatis初始时已经内置大部分基础类型转化的TypeHandler，已经足够我们平常的简单应用开发了，所以大多数情况下并不需要我们自己去定义类型转换器。但是，当遇到一些特殊情况时，为了开发的方便性，我们才回去自定义一些类型转换器。
在本篇中就不对这一块内容做过多的研究，请自行查询配置。  

### 参数和返回值 ###
@Param:
	     
		int selectRoleCount(@Param("businessId") Integer businessId,@Param("memberId") Long memberId); 
@MapKey:  
		
        @MapKey("id")
		Map<Integer, Employee> getAllEmpsAsMap();

@Result： 这一块内容需要单独讲一下
普通的@Select的返回结果： 

    @Results({
            @Result(property="updateTime",column="update_time",typeHandler = "",jdbcType = "",javaType = "")
    })
引用其他的Result @ResultMap:  

	@ResultMap("com.owen.mybatis.mappers.StudentMapper.StudentResult")  
一对一映射@One:  

		@Results({  
		@Result(id=true, column="stud_id", property="studId"),  
		@Result(property="address", column="addr_id",  
		one=@One(select="com.owen.mybatis.mappers.StudentMapper.  
		findAddressById"))  
		})  
一对多映射@Many:  

		@Results({  
		@Result(id=true, column="tutor_id", property="tutorId"),  
		@Result(property="address", column="addr_id",  
		one=@One(select=" com.owen.mybatis.  
		mappers.TutorMapper.findAddressById")),  
		@Result(property="courses", column="tutor_id",  
		many=@Many(select="com.owen.mybatis.mappers.TutorMapper.  
		findCoursesByTutorId"))  
		}) 
### 增删改查 ###
#### 新增： ####
新增主键（oracle）：  

		@Insert("insert into table1(id) values(#{id})")
		@SelectKey(statement="SELECT DDS_SEQ.nextval AS ID FROM DUAL", keyProperty="id", before=true, resultType=Long.class)
		void saveNews(News news);  

InsertProvider(内部類提供方法，同理@SelectProvider @InsertProvider @UpdateProvider @DeleteProvider):  

		@InsertProvider(type = UserSqlProvider.class, method = "save")
		@SelectKey(statement="SELECT DDS_SEQ.nextval AS ID FROM DUAL", keyProperty="id", before=true, resultType=Long.class)
		void saveNews(News news);
		public class UserSqlProvider {
			String save(){
				return "insert into news(id) valus(#{id})"
			}
		}  
新增字段默认不为空：  
application.properties的jdbc-type-for-null=NULL保证mybatis在空值插入时正常转换null,不会报无效的列类型: 1111错误(另外一种办法是指定#{pxh,jdbcType=NUMERIC}类型)  

		mybatis.configuration.jdbc-type-for-null=NULL  

批量插入：  

	    @InsertProvider(type = UserDAOProvider.class, method = "insertAll")  
	    void insertAll(@Param("list") List<User> users);  
		public class UserDAOProvider {  
		    public String insertAll(Map map) {  
		        List<User> users = (List<User>) map.get("list");  
		        StringBuilder sb = new StringBuilder();  
		        sb.append("INSERT INTO User ");  
		        sb.append("(id, name) ");  
		        sb.append("VALUES ");  
		        MessageFormat mf = new MessageFormat("(null, #'{'list[{0}].name})");  
		        for (int i = 0; i < users.size(); i++) {  
		            sb.append(mf.format(new Object[]{i}));  
		            if (i < users.size() - 1) {  
		                sb.append(",");  
		            }  
		        }  
		        return sb.toString();  
		    }  
		}  



## 扩展篇   
### pagehelper(springboot插件版) ###
pom.xml:  

		<dependency>
			<groupId>com.github.pagehelper</groupId>
			<artifactId>pagehelper-spring-boot-autoconfigure</artifactId>
			<version>1.2.5</version>
		</dependency>
		<dependency>
			<groupId>com.github.pagehelper</groupId>
			<artifactId>pagehelper-spring-boot-starter</artifactId>
			<version>1.2.5</version>
		</dependency>
		<dependency>
			<groupId>org.mybatis.spring.boot</groupId>
			<artifactId>mybatis-spring-boot-starter</artifactId>
			<version>1.3.2</version>
		</dependency>  
(注意:选用不同版本的starter时，需要查看依赖文件，版本相互之间存在依赖关系，错误的版本依赖可能回导致分页无效)

application.properties配置（因为使用的是pagehelper-spring-boot-starter,自动配置插件，不需要重写sqlfactoryBean）：  

		pagehelper.helperDialect=oracle（mysql）
		pagehelper.reasonable=true  
		pagehelper.supportMethodsArguments=true
		pagehelper.params=count=countSql  
		logging.level.com.tianque.mapper=debug

基本使用：  
只做分页操作：  

		Page page = PageHelper.startPage(1, 20);
        List<Map> maps =  baseInfoDao.selectAll();

分页并获取查询总数量：
		
		Page page = PageHelper.startPage(1, 20, true);
        List<Map> maps =  baseInfoDao.selectAll();
        System.out.println(page.getTotal());  
查询全部：  

		PageHelper.startPage(1,0);  
其他API：  

	    String orderBy = PageHelper.getOrderBy();    //获取orderBy语句
	    Page<?> page = PageHelper.startPage(Object params);
	    Page<?> page = PageHelper.startPage(int pageNum, int pageSize);
	    Page<?> page = PageHelper.startPage(int pageNum, int pageSize, boolean isCount);
	    Page<?> page = PageHelper.startPage(pageNum, pageSize, orderBy);
	    Page<?> page = PageHelper.startPage(pageNum, pageSize, isCount, isReasonable);    //isReasonable分页合理化,null时用默认配置
	    Page<?> page = PageHelper.startPage(pageNum, pageSize, isCount, isReasonable, isPageSizeZero);    //isPageSizeZero是否支持PageSize为0，true且pageSize=0时返回全部结果，false时分页,null时用默认配置

(注意：PageHelper只对紧跟着的第一个SQL语句起作用)

### mybatis-plus [链接地址](http://mp.baomidou.com/#/?id=%e7%ae%80%e4%bb%8b "中文API")###
https://github.com/baomidou/mybatis-plus  
#### maven-pom 依赖 ####
主依赖：  

		<dependency>
			<groupId>com.baomidou</groupId>
			<artifactId>mybatis-plus-boot-starter</artifactId>
			<version>2.2.0</version>
		</dependency>

数据库驱动，数据源，模板引擎等：  

		<dependency>
			<groupId>org.apache.velocity</groupId>
			<artifactId>velocity-engine-core</artifactId>
			<version>2.0</version>
		</dependency>
		<dependency>
			<groupId>com.oracle</groupId>
			<artifactId>ojdbc6</artifactId>
			<version>11.2.0.3</version>
		</dependency>
		<dependency>
			<groupId>com.alibaba</groupId>
			<artifactId>druid</artifactId>
			<version>1.1.9</version>
		</dependency>
		<!-- https://mvnrepository.com/artifact/org.freemarker/freemarker -->
		<dependency>
			<groupId>org.freemarker</groupId>
			<artifactId>freemarker</artifactId>
			<version>2.3.28</version>
		</dependency>
#### appliation.properties 配置  

		mybatis-plus:
		    # 如果是放在src/main/java目录下 classpath:/com/yourpackage/*/mapper/*Mapper.xml
		    # 如果是放在resource目录 classpath:/mapper/*Mapper.xml
		    mapper-locations: classpath:/mapper/*Mapper.xml
		    #实体扫描，多个package用逗号或者分号分隔
		    typeAliasesPackage: com.yourpackage.*.entity
		    global-config:
		
		        #主键类型  0:"数据库ID自增", 1:"用户输入ID",2:"全局唯一ID (数字类型唯一ID)", 3:"全局唯一ID UUID";
		        id-type: 3
		        #字段策略 0:"忽略判断",1:"非 NULL 判断"),2:"非空判断"
		        field-strategy: 2
		        #驼峰下划线转换
		        db-column-underline: true
		        isCapitalMode: true
		        #刷新mapper 调试神器
		        #refresh-mapper: true
		        #数据库大写下划线转换
		        #capital-mode: true
		        # Sequence序列接口实现类配置
		        key-generator: com.baomidou.mybatisplus.incrementer.OracleKeyGenerator
		        #逻辑删除配置（下面3个配置）
		        logic-delete-value: 1
		        logic-not-delete-value: 0
		        sql-injector: com.baomidou.mybatisplus.mapper.LogicSqlInjector
		        #自定义填充策略接口实现
		        meta-object-handler: com.baomidou.springboot.MyMetaObjectHandler
		    configuration:
		        #配置返回数据库(column下划线命名&&返回java实体是驼峰命名)，自动匹配无需as（没开启这个，SQL需要写as： select user_id as userId）
		        map-underscore-to-camel-case: true
		        cache-enabled: false
		        #配置JdbcTypeForNull, oracle数据库必须配置
		        jdbc-type-for-null: 'null'
		
		
		spring:
		    datasource:
		        url: jdbc:oracle:thin:@192.168.100.18:1521:tianque
		        username: lngrid_new
		        password: lngrid_new
		        type: com.alibaba.druid.pool.DruidDataSource
		        driver-class-name: oracle.jdbc.driver.OracleDriver

#### 自动生成代码mybatis-generator ####
			package com.tianque;
			import org.junit.Test;
			import com.baomidou.mybatisplus.generator.AutoGenerator;
			import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
			import com.baomidou.mybatisplus.generator.config.GlobalConfig;
			import com.baomidou.mybatisplus.generator.config.PackageConfig;
			import com.baomidou.mybatisplus.generator.config.StrategyConfig;
			import com.baomidou.mybatisplus.generator.config.rules.DbType;
			import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
			/**
			 *
			 * @author linlinan
			 * @date 2018/4/27
			 */
			public class MpGenerator {
			
			    @Test
			    public void generateCode() {
			        String packageName = "com.tianque";
			        boolean serviceNameStartWithI = false;//user -> UserService, 设置成true: user -> IUserService
			        generateByTables(serviceNameStartWithI, packageName, "data_operate_logs");
			    }
			
			    private void generateByTables(boolean serviceNameStartWithI, String packageName, String... tableNames) {
			        GlobalConfig config = new GlobalConfig();
			        String dbUrl = "jdbc:oracle:thin:@192.168.100.18:1521:tianque";
			        DataSourceConfig dataSourceConfig = new DataSourceConfig();
			        dataSourceConfig.setDbType(DbType.ORACLE)
			                .setUrl(dbUrl)
			                .setUsername("lngrid_new")
			                .setPassword("lngrid_new")
			                .setDriverName("oracle.jdbc.driver.OracleDriver");
			        StrategyConfig strategyConfig = new StrategyConfig();
			        strategyConfig
			                .setDbColumnUnderline(true)
			                .setCapitalMode(true)
			                .setEntityLombokModel(false)
			                .setDbColumnUnderline(true)
			                .setNaming(NamingStrategy.underline_to_camel)
			                .setInclude(tableNames);//修改替换成你需要的表名，多个表名传数组
			        config.setActiveRecord(false)
			                .setAuthor("K神带你飞")
			                .setOutputDir("d:\\codeGen")
			                .setFileOverride(true);
			        if (!serviceNameStartWithI) {
			            config.setServiceName("%sService");
			        }
			        new AutoGenerator().setGlobalConfig(config)
			                .setDataSource(dataSourceConfig)
			                .setStrategy(strategyConfig)
			                .setPackageInfo(
			                        new PackageConfig()
			                                .setParent(packageName)
			                                .setController("controller")
			                                .setEntity("Domain")
			                ).execute();
			    }
			
			    private void generateByTables(String packageName, String... tableNames) {
			        generateByTables(true, packageName, tableNames);
			    }
			}