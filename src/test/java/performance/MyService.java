package performance;

import static java.nio.charset.StandardCharsets.UTF_8;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.DataCenterInfo;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.InstanceInfo.InstanceStatus;
import com.netflix.appinfo.LeaseInfo;
import com.netflix.appinfo.MyDataCenterInstanceConfig;
import com.netflix.config.ConfigurationManager;
import com.netflix.discovery.DefaultEurekaClientConfig;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.EurekaClient;

import org.apache.commons.configuration.AbstractConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 目的：
 * 创建N个Instance链接指定EurekaServer
 * 运行举例：
 * java performance.MyService http://local-eureka:8761/eureka/ 100
 * 第一个参数：EurekaServer注册地址
 * 第二个参数：创建Instance个数，默认1个
 * 按 回车 退出程序
 *
 * @author zhupengcheng
 */
public class MyService {
  private static final int ONE = 1;

  public static void main(final String... args) {
    String eurekaUrl = "http://local-eureka:8761/eureka/";
    if (args.length > 0) {
      eurekaUrl = args[0];
    }

    int connectNum = 1;
    if (args.length > ONE) {
      connectNum = Integer.parseInt(args[1]);
    }

    System.err.println("eurekaServer:" + eurekaUrl + "  instance总数：" + connectNum);

    final List<EurekaClient> clientList = new ArrayList<EurekaClient>();

    for (int i = 0; i < connectNum; i++) {
      final AbstractConfiguration abstractConfiguration = ConfigurationManager.getConfigInstance();

      final String appName = "instance-" + i;
      final String port = "801" + i;

      abstractConfiguration.setProperty("eureka.region", "default");
      abstractConfiguration.setProperty("eureka.name", appName);
      abstractConfiguration.setProperty("eureka.vipAddress", "sampleservice.mydomain.ne");
      abstractConfiguration.setProperty("eureka.port", port);
      abstractConfiguration.setProperty("eureka.preferSameZone", "true");
      abstractConfiguration.setProperty("eureka.shouldUseDns", "false");
      abstractConfiguration.setProperty("eureka.serviceUrl.default", eurekaUrl);

      com.netflix.config.DynamicPropertyFactory.getInstance();

      final InstanceInfo.Builder builder = InstanceInfo.Builder.newBuilder();
      builder.setAppName(appName);
      builder.setInstanceId(appName);
      builder.setPort(Integer.parseInt(port));
      builder.setLeaseInfo(LeaseInfo.Builder.newBuilder().build());
      builder.setDataCenterInfo(getDataCenterInfo());
      // url 随便写，不写的话在Server查看页面看不到链接而已
      builder.setHomePageUrlForDeser("http://www.sina.com.cn");
      builder.setStatusPageUrlForDeser("http://www.sina.com.cn");
      builder.setHealthCheckUrlsForDeser("http://www.sina.com.cn", null);

      final InstanceInfo instanceInfo = builder.build();

      final MyDataCenterInstanceConfig myConfig = new MyDataCenterInstanceConfig();

      final DefaultEurekaClientConfig clientConfig = new DefaultEurekaClientConfig();

      // setup config in advance, used in initialize converter
      final ApplicationInfoManager applicationInfoManager = new ApplicationInfoManager(myConfig, instanceInfo);

      final EurekaClient eurekaClient = new DiscoveryClient(applicationInfoManager, clientConfig);
      applicationInfoManager.setInstanceStatus(InstanceStatus.UP);

      clientList.add(eurekaClient);
    }
    System.out.println("connectNum:" + connectNum + " succ");
    final Scanner sc = new Scanner(System.in, UTF_8.name());
    sc.nextLine();
    System.out.println("Exit Test");

    for (final EurekaClient eurekaClient : clientList) {
      eurekaClient.shutdown();
    }
    sc.close();
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
