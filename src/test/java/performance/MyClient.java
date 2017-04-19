package performance;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.DataCenterInfo;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.LeaseInfo;
import com.netflix.appinfo.MyDataCenterInstanceConfig;
import com.netflix.config.ConfigurationManager;
import com.netflix.discovery.DefaultEurekaClientConfig;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;

import org.apache.commons.configuration.AbstractConfiguration;

/**
 * 目的：
 * 链接EurekaServer并发送N次查询请求<br>
 * 运行举例：
 * java performance.MyClient http://local-eureka:8761/eureka/ 100
 * 第一个参数：EurekaServer注册地址
 * 第二个参数：查询次数，默认10次
 *
 * @author zhupengcheng
 */
public class MyClient {

  private static final int ONE = 1;

  public static void main(final String... args) {
    String eurekaUrl = "http://local-eureka:8761/eureka/";
    if (args.length > 0) {
      eurekaUrl = args[0];
    }

    int totalNum = 10;

    if (args.length > ONE) {
      totalNum = Integer.parseInt(args[1]);
    }
    System.err.println("eurekaServer:" + eurekaUrl + "  查询调用次数：" + totalNum);

    final AbstractConfiguration abstractConfiguration = ConfigurationManager.getConfigInstance();

    abstractConfiguration.setProperty("eureka.registration.enabled", false);
    abstractConfiguration.setProperty("eureka.preferSameZone", true);
    abstractConfiguration.setProperty("eureka.shouldUseDns", false);
    abstractConfiguration.setProperty("eureka.serviceUrl.default", eurekaUrl);
    abstractConfiguration.setProperty("eureka.decoderName", "JacksonJson");

    abstractConfiguration.setProperty("eureka.region", "default");

    final InstanceInfo.Builder builder = InstanceInfo.Builder.newBuilder();
    builder.setAppName("testClient");
    builder.setLeaseInfo(LeaseInfo.Builder.newBuilder().build());
    builder.setDataCenterInfo(getDataCenterInfo());

    final InstanceInfo instanceInfo = builder.build();

    final MyDataCenterInstanceConfig myConfig = new MyDataCenterInstanceConfig();

    final DefaultEurekaClientConfig clientConfig = new DefaultEurekaClientConfig();

    // setup config in advance, used in initialize converter
    final ApplicationInfoManager applicationInfoManager = new ApplicationInfoManager(myConfig, instanceInfo);

    final EurekaClient eurekaClient = new DiscoveryClient(applicationInfoManager, clientConfig);
    final long startTime = System.currentTimeMillis();
    for (int i = 0; i < totalNum; i++) {
      final Application app = eurekaClient.getApplication("EUREKA" + i);

      if (app != null) {
        System.out.println("appName:" + app.getName());
      }
    }

    final long endTime = System.currentTimeMillis();

    final long totalTime = endTime - startTime;
    final long avgTime = totalTime / totalNum;

    System.out.println("totalNum:" + totalNum + "  totalTime:" + totalTime + "  avgTime:" + avgTime);
  }

  protected static DataCenterInfo getDataCenterInfo() {
    return new DataCenterInfo() {
      @Override
      public Name getName() {
        return Name.MyOwn;
      }
    };
  }
}
