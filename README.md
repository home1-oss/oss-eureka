# oss-eureka  

### 使用docker 启动单点

    export EUREKA_VERSION="latest"
    export HOST_IP_ADDRESS="${给EUREKA客户端访问的IP地址}"
    export EUREKA_INSTANCE_NONSECUREPORT="8761"
    docker-compose up -d

### 使用docker 启动单机集群 (开发环境用)

    export EUREKA_VERSION="latest"
    export HOST_IP_ADDRESS="${给EUREKA客户端访问的IP地址}"
    docker-compose -f docker-compose-eureka-cluster.yml up -d

### 使用docker 启动集群 (生产环境)

    # 在全部主机上执行
    export EUREKA_VERSION="latest"
    export EUREKA_PEER1_IP_ADDRESS="${主机1给EUREKA客户端访问的IP地址}"
    export EUREKA_PEER1_PORT="8761"
    export EUREKA_PEER2_IP_ADDRESS="${主机2给EUREKA客户端访问的IP地址}"
    export EUREKA_PEER2_PORT="8762"
    export EUREKA_PEER3_IP_ADDRESS="${主机3给EUREKA客户端访问的IP地址}"
    export EUREKA_PEER3_PORT="8763"

    # 在主机1上执行
    export PEER="peer1"
    export HOST_IP_ADDRESS="${EUREKA_PEER1_IP_ADDRESS}"
    export EUREKA_INSTANCE_NONSECUREPORT="${EUREKA_PEER1_PORT}"
    docker-compose -f docker-compose-eureka-peer.yml up -d

    # 在主机2上执行
    export PEER="peer2"
    export HOST_IP_ADDRESS="${EUREKA_PEER2_IP_ADDRESS}"
    export EUREKA_INSTANCE_NONSECUREPORT="${EUREKA_PEER2_PORT}"
    docker-compose -f docker-compose-eureka-peer.yml up -d

    # 在主机3上执行
    export PEER="peer3"
    export HOST_IP_ADDRESS="${EUREKA_PEER3_IP_ADDRESS}"
    export EUREKA_INSTANCE_NONSECUREPORT="${EUREKA_PEER3_PORT}"
    docker-compose -f docker-compose-eureka-peer.yml up -d

### 使用集成开发环境或java命令运行

注意设置合理的 VM选项(-Dspring.profiles.active=) 和 环境变量  
具体需设置哪些变量 请参考 `docker-compose-eureka-*.yml` 和 `src/main/resources/application.yml`


### 配置参数

| 变量名 | 默认值 |	说明 |  
| ---  | --- | ---- |     
| server.port |	8080 |eurekaServer监听端口|  
|spring.application.name|	| 应用名|
|eureka.client.register-with-eureka	|TRUE	|	是否将服务发布到Eureka Server|    
|eureka.client.fetch-registry	|	TRUE	|	是否从EurekaServer获取Instance信息|    
|eureka.client.serviceUrl.defaultZone	|		|	默认注册服务地址，多个地址之间用,分隔|    
|eureka.client.registry-fetch-interval-seconds	|	30	|单位s，间隔多长时间从Eureka Server获取注册信息|  
|eureka.client.enabled	|	TRUE	|	Ereka clien是否可用，通常测试时不需要启用真实服务时需要|    
|eureka.client.eureka-connection-idle-timeout-seconds	|	30	|单位s，一个链接Eureka Server的Http 链接在关闭前可以保持Idle状态的最大时间|     
|eureka.client.eureka-server-connect-timeout-seconds	|	5	|单位s，同Eureka Server之间创建链接的超时时间|    
|eureka.client.eureka-server-read-timeout-seconds	|	8	|	单位s，读数据超时时长|    
|eureka.client.eureka-server-total-connections	|	200	|	Eureka Client链接Server的最大链接数|  
|eureka.client.eureka-server-total-connections-per-host	|	50	|	单个Eureka Client与单个Server之间的最大链接数|    
|eureka.client.eureka-service-url-poll-interval-seconds	|	0	|	单位s，客户端间隔多长时间知道Server的状态变化（增、删）|  
|eureka.client.filter-only-up-instances	|	TRUE	|	是否只展示处于存活状态的应用|  
|eureka.client.g-zip-content	|	TRUE	|client从Server获得的内容是否进行了压缩处理。注册信息进行了压缩处理|   
|eureka.client.heartbeat-executor-thread-pool-size	|	2	|	执行心跳的线程池初始值大小|  
|eureka.client.initial-instance-info-replication-interval-seconds	|	40 | 单位s，启动后，间隔多少时间后开始复制instance info到Server|    
|eureka.client.instance-info-replication-interval-seconds	|	30	|	单位s，没间隔多少时间复制instance info变动到Server  
|eureka.client.log-delta-diff	|	FALSE	|	是否记录服务器与客户端之间关于注册信息的变化|  
|eureka.client.on-demand-update-status-change	|	TRUE	|通过ApplicationInfoManager修改的状态变化是否远端服务器 |  
|eureka.client.prefer-same-zone-eureka	|	TRUE	|	instance是否采用同一个Zone的Ereka Server|  
|eureka.dashboard.enabled	|	TRUE	|	是否开启Dashboard|    
|eureka.dashboard.path	|	/	|	Dashboad根路径（相对路径）|    
|eureka.instance.app-group-name	|		|	注册在Eureka上的应用组名|    
|eureka.instance.appname	|	unknown	|	注册在Eureka上的应用名|    
|eureka.instance.health-check-url	|		|健康检查Url绝对地址，如提供此地址，健康检查会优先采用此地址|    
|eureka.instance.health-check-url-path	|	/health	|	健康检查相对地址|    
|eureka.instance.home-page-url	|		|	instance主页|  
|eureka.instance.home-page-url-path	|	／	|	instance相对地址|    
|eureka.instance.hostname	|		|在采用域名时，如果有设置的话，会选取这个|    
|eureka.instance.initial-status|		|注册到远端Eureka上的初始状态|   
|eureka.instance.instance-enabled-onit	|	FALSE	|	instance注册后是否可以立刻提供服务。通常注册后在提供服务前还需要做些处理|  
|eureka.instance.instance-id	|		|instance唯一id，默认为ip:appName:port|    
|eureka.instance.ip-address	|		|指定ip地址|  
|eureka.instance.lease-renewal-interval-in-seconds	|	30	|	单位s，eureka client发送心跳的时间间隔|    
|eureka.instance.lease-expiration-duration-in-seconds	|	90	|	单位s，instance被Eureka Server移除的时间间隔|   
|eureka.instance.metadata-map	|		|	metadata name/value键值队|  
|eureka.instance.namespace	|	eureka	|	查询属性的命名空间，Spring Cloud中已忽略|    
|eureka.instance.non-secure-port	|	80	|	当服务是EurekaServer时，默认采用EurekaServer的端口|  
|eureka.instance.non-secure-port-enabled	|	TRUE	|	是否开启http访问|    
|eureka.instance.prefer-ip-address	|	FALSE	|	是否优先采用ip，默认优先采用域名|  
|eureka.instance.secure-health-check-url	|		|	安全模式下健康检查Url绝对地址，如提供此地址，健康检查会优先采用此地址|  
|eureka.instance.secure-port	|	443	|	安全模式下监听端口|  
|eureka.instance.secure-port-enabled	|	FALSE	|	安全模式监听端口是否生效|  
|eureka.instance.secure-virtual-host-name	| |	安全虚拟主机名|  
|eureka.instance.status-page-url	| | 	状态页面绝对地址|  
|eureka.instance.status-page-url-path	|	/info	|	状态页面相对路径|  
|eureka.instance.virtual-host-name	| |	虚拟主机名|  
|eureka.server.batch-replication	|	FALSE	|	是否批量复制|  
|eureka.server.max-idle-thread-age-in-minutes-for-peer-replication	|	15	|	单位分，节点间负责复制信息的线程的最大空闲时间|   
|eureka.server.max-idle-thread-in-minutes-age-for-status-replication	|	10	|	单位分，节点间负责复制状态的线程的最大空闲时间|  
|eureka.server.max-threads-for-peer-replication	|	20	|	负责节点间复制的最大线程数|  
|eureka.server.max-threads-for-status-replication	|	1	|	负责状态复制的最大线程数|    
|eureka.server.max-time-for-replication	|	30000	|	复制的最大时长|  
|eureka.server.min-threads-for-peer-replication	|	5	|	负责节点间复制的最小线程数|    
|eureka.server.min-threads-for-status-replication	|	1	|	负责状态复制的最小线程数|  
|eureka.server.number-of-replication-retries	|	5	|	复制失败后重新尝试的次数限制|  
|eureka.server.peer-node-connect-timeout-ms	|200	|	单位ms，节点间链接超时时长|    
|eureka.server.peer-node-connection-idle-timeout-seconds	|	30	|	单位s，节点链接空闲状态时长|    
|eureka.server.peer-node-read-timeout-ms	|	200	|	单位ms，节点读超时时长|  
|eureka.server.peer-node-total-connections	|	1000	|	节点总链接数上限|  
|eureka.server.peer-node-total-connections-per-host	|	500	|	节点与单个主机之间的链接数上限|  
