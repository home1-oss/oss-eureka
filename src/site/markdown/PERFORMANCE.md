
# Eureka压测报告  

## 测试环境  

|环境 | 值 |     
|--- | --- |  
|CPU | ntel(R) Core(TM) i7-4790 CPU @ 3.60GHz 8 cores |  
|RAM |	8 GB，1600MHz |  
|硬盘IO |	1 TB，7200rpm |  
|网卡 |	1 Gigabit Ethernet |  
|操作系统 |	CentOS Linux release 7.2.1511 (Core) |  
| 网络ping值 | --- ping statistics --- <br/> 17 packets transmitted, 17 received, 0% packet loss, time 16000ms<br/>rtt min/avg/max/mdev = 0.123/0.167/0.204/0.024 ms|  

## 测试Eureka

### EurekaClient响应时间
*** 测试方案  ***   
第一步：EurekaClient链接EurekaServer  
第二步：EurekaClient发送N（N>0）次查询请求  
第三步：得到总的处理时间后求平均值   
*** 结果 ***  
时间消耗非常小，平均处理时间基本为0.00ms  
*** 分析 ***  
EurekaClient创建连接的时候会同步EurekaServer上的数据（Client会定期同步Server上的数据）并保存在本地内存，EurkaClient发起的查询请求实际上调用的是本地缓存。

### EurekaServer的性能
*** 测试方案 ***  
第一步：N（N>=0）台Instance链接EurekaServer  
第二步：P（P>0）个链接同时发送REST指令进行请求  
*** 结果 ***   

|Instance num | Requests per second | Time per request（ms)|  
| --- | --- |--- |   
| 0 | 21939.64 | 0.046 [ms] |
|500|  6365.27 | 0.157 [ms] |

*** 分析 ***  
响应时间不足0.2ms，每秒处理6k多条，考虑到EurekaClient有缓存，实际的吞吐量应该远远大于这个数  

### 测试方法

  参考 src/test/java/performance/
