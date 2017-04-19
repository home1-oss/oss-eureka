package provider;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * ① 测试服务实例的相关内容. 
 * ② 为后来的服务做提供.
 */
@RestController
public class UserController {
  @Autowired
  private DiscoveryClient discoveryClient;
  @Autowired
  private UserRepository userRepository;

  /**
   * 注：@GetMapping("/{id}")是spring 4.3的新注解等价于：
   * 
   * @RequestMapping(value = "/id", method = RequestMethod.GET)
   *                       类似的注解还有@PostMapping等等
   * @param id - 用户id
   * @return user信息
   */
  @GetMapping("/{id}")
  public User findById(@PathVariable final Long id) {
    return this.userRepository.findOne(id);
  }

  /**
   * 本地服务实例的信息.
   * 
   * @return 服务Instance
   */
  @GetMapping("/instance-info")
  public ServiceInstance showInfo() {
    return this.discoveryClient.getLocalServiceInstance();
  }
}
