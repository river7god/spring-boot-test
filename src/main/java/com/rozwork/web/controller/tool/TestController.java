package com.rozwork.web.controller.tool;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rozwork.web.core.Anonymous;
import com.rozwork.web.core.BaseController;
import com.rozwork.common.core.domain.R;
import com.rozwork.common.utils.StringUtils;
import com.rozwork.servers.mq.ProducerService;

import org.springframework.amqp.AmqpException;

import java.io.UnsupportedEncodingException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.builder.ExchangeBuilder;



/**
 * swagger 用户测试方法
 * 
 * @author rozwork
 */
@Api("用户信息管理")
@RestController
@Anonymous
@RequestMapping("/test/user")
public class TestController extends BaseController
{

    @Autowired
	private ProducerService producerService;

    @Autowired
    private CamelContext camelContext;
	
	//helloWorld 直连模式
    @ApiOperation("直连模式")
    @Anonymous
	@GetMapping("/helloWorldSend")
	public Object helloWorldSend(String message) throws AmqpException, UnsupportedEncodingException {
		
        producerService.helloWorldSend(message);
		return "message sended : "+message;
	}


	//工作队列模式
    @ApiOperation("工作队列模式")
    @Anonymous
	@GetMapping(value="/workqueueSend")
	public Object workqueueSend(String message) throws AmqpException, UnsupportedEncodingException {
		producerService.workqueueSend(message);
		return "message sended : "+message;
	}


	// pub/sub 发布订阅模式   交换机类型 fanout
    @ApiOperation("pub/sub 发布订阅模式   交换机类型 fanout")
    @Anonymous
	@GetMapping(value="/fanoutSend")
	public Object fanoutSend(String message) throws AmqpException, UnsupportedEncodingException {
		producerService.fanoutSend(message);

        Exchange exchange = ExchangeBuilder.anExchange(camelContext)
            .withBody("Message to trigger the route")
            .build();

        // 触发直接路由
        camelContext.createProducerTemplate().send("direct:startRoute", exchange);
		return "message sended : "+message;
	}


	//routing路由工作模式  交换机类型 direct
    @ApiOperation("routing路由工作模式  交换机类型 direct")
    @Anonymous
	@GetMapping(value="/directSend")
	public Object routingSend(String routingKey,String message) throws AmqpException, UnsupportedEncodingException {
        producerService.routingSend(routingKey, message);
        return "message sended : routingKey >"+routingKey+";message > "+message;
	}


	//topic 工作模式   交换机类型 topic
    @ApiOperation("工作模式   交换机类型 topic")
    @Anonymous
	@GetMapping(value="/topicSend")
	public Object topicSend(String routingKey,String message) throws AmqpException, UnsupportedEncodingException {
        producerService.topicSend(routingKey, message);
        return "message sended : routingKey >"+routingKey+";message > "+message;
	}

    private final static Map<Integer, UserEntity> users = new LinkedHashMap<Integer, UserEntity>();
    {
        users.put(1, new UserEntity(1, "admin", "admin123", "15888888888"));
        users.put(2, new UserEntity(2, "ry", "admin123", "15666666666"));
    }

    @ApiOperation("获取用户列表")
    @Anonymous
    @GetMapping("/list")
    public R<List<UserEntity>> userList()
    {
        List<UserEntity> userList = new ArrayList<UserEntity>(users.values());
        return R.ok(userList);
    }

    @ApiOperation("获取用户详细")
    @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "int", paramType = "path", dataTypeClass = Integer.class)
    @GetMapping("/{userId}")
    public R<UserEntity> getUser(@PathVariable Integer userId)
    {
        if (!users.isEmpty() && users.containsKey(userId))
        {
            return R.ok(users.get(userId));
        }
        else
        {
            return R.fail("用户不存在");
        }
    }

    @ApiOperation("新增用户")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "userId", value = "用户id", dataType = "Integer", dataTypeClass = Integer.class),
        @ApiImplicitParam(name = "username", value = "用户名称", dataType = "String", dataTypeClass = String.class),
        @ApiImplicitParam(name = "password", value = "用户密码", dataType = "String", dataTypeClass = String.class),
        @ApiImplicitParam(name = "mobile", value = "用户手机", dataType = "String", dataTypeClass = String.class)
    })
    @PostMapping("/save")
    public R<String> save(UserEntity user)
    {
        if (StringUtils.isNull(user) || StringUtils.isNull(user.getUserId()))
        {
            return R.fail("用户ID不能为空");
        }
        users.put(user.getUserId(), user);
        return R.ok();
    }

    @ApiOperation("更新用户")
    @PutMapping("/update")
    public R<String> update(@RequestBody UserEntity user)
    {
        if (StringUtils.isNull(user) || StringUtils.isNull(user.getUserId()))
        {
            return R.fail("用户ID不能为空");
        }
        if (users.isEmpty() || !users.containsKey(user.getUserId()))
        {
            return R.fail("用户不存在");
        }
        users.remove(user.getUserId());
        users.put(user.getUserId(), user);
        return R.ok();
    }

    @ApiOperation("删除用户信息")
    @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "int", paramType = "path", dataTypeClass = Integer.class)
    @DeleteMapping("/{userId}")
    public R<String> delete(@PathVariable Integer userId)
    {
        if (!users.isEmpty() && users.containsKey(userId))
        {
            users.remove(userId);
            return R.ok();
        }
        else
        {
            return R.fail("用户不存在");
        }
    }
}

@ApiModel(value = "UserEntity", description = "用户实体")
class UserEntity
{
    @ApiModelProperty("用户ID")
    private Integer userId;

    @ApiModelProperty("用户名称")
    private String username;

    @ApiModelProperty("用户密码")
    private String password;

    @ApiModelProperty("用户手机")
    private String mobile;

    public UserEntity()
    {

    }

    public UserEntity(Integer userId, String username, String password, String mobile)
    {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.mobile = mobile;
    }

    public Integer getUserId()
    {
        return userId;
    }

    public void setUserId(Integer userId)
    {
        this.userId = userId;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getMobile()
    {
        return mobile;
    }

    public void setMobile(String mobile)
    {
        this.mobile = mobile;
    }
}
