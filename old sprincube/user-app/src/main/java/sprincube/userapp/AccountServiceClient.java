package sprincube.userapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import java.util.HashMap;
import java.util.Map;

@Component
public class AccountServiceClient {

    private final Logger logger = LoggerFactory.getLogger(URestController.class);

    private RestTemplate restTemplate;

    private String accountUrl = "http://account-service:8082/";


    @Autowired
    public AccountServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @HystrixCommand(fallbackMethod = "defaultUser")
    public Map<String,String> getUserInfo() {
        logger.info("AccountServiceClient: Getting User: " + accountUrl);
        //logger.info(accountUrl);
        Map<String,String> result = restTemplate.getForObject(accountUrl, Map.class);
        return result;
    }
    @HystrixCommand(fallbackMethod = "defaultUser")
    public Map<String, String> getUserById(int i) {
        logger.info("AccountServiceClient: Getting User With ID: " + accountUrl);
        String accountIdUrl = accountUrl + Integer.toString(i);
        logger.info(accountIdUrl);
        Map<String,String> result = restTemplate .getForObject(accountIdUrl, Map.class);
        return result;
    }


    public Map<String, String> defaultUser() {
        Map<String, String> map = new HashMap<String, String>();
        logger.info("AccountServiceClient: Fallback User: " + accountUrl);
        map.put("Empty", "User");
        return map;
    }

    public Map<String, String> defaultUser(int id) {
        Map<String, String> map = new HashMap<String, String>();
        logger.info("AccountServiceClient: Fallback User With ID: " + accountUrl);
        map.put("EmptyUser", Integer.toString(id))  ;
        return map;
    }


}
