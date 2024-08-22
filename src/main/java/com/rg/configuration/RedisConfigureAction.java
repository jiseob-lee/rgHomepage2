package com.rg.configuration;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
//import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
//import org.springframework.security.core.Authentication;
import org.springframework.session.data.redis.config.ConfigureRedisAction;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;


/*
 * @EnableRedisHttpSession(maxInactiveIntervalInSeconds=60)
 * 이 부분에서 Redis 세션 타임 아웃에 걸리는 시간을 설정한다.
 * 어드민은 30분, 프론트는 9시간이다.
 * 초 단위로 입력한다.
 */


@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds=324000)
@PropertySource("classpath:application.properties")
public class RedisConfigureAction {

    @Value("${redis.host}")
    private String redisHostName;

    @Value("${redis.port}")
    private int redisPort;
    
    @Value("${redis.password}")
    private String redisPwd;
    
    //private String redisHostName = "127.0.0.1";
	//private int redisPort = 6379;
	//private String redisPwd = "jiseob9123";
	
    
    // lettuce
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        
    	//LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisHostName, redisPort);
    	//lettuceConnectionFactory.setPassword(redisPwd);
    	
    	//return lettuceConnectionFactory;

    	// 서버 환경 설정
    	RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(redisHostName, redisPort);
    	redisStandaloneConfiguration.setPassword(redisPwd);

    	// redis 서버와 연결 수행 객체
    	LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration);

    	return lettuceConnectionFactory;
    	
    	//JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
    	//jedisConnectionFactory.setHostName(redisHostName);
    	//jedisConnectionFactory.setPort(redisPort);
    	//jedisConnectionFactory.setPassword(redisPwd);
    	//return jedisConnectionFactory;

    	
        //RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        //redisStandaloneConfiguration.setHostName(redisHostName);
        //redisStandaloneConfiguration.setPort(redisPort);
        //redisStandaloneConfiguration.setPassword(redisPwd); //redis에 비밀번호가 설정 되어 있는 경우 설정해주면 됩니다.
        //LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration);
        //return lettuceConnectionFactory;
        //[출처] [spring boot 2.x] redis 설정|작성자 codegun

    }

    /*
    @Bean
    public ObjectMapper objectMapper() {
        return Jackson2ObjectMapperBuilder.json()
                .serializationInclusion(JsonInclude.Include.NON_NULL) // Don’t include null values
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS) //ISODate
                .build();
    }
    */
    
	@Bean
	//public RedisTemplate<String, Object> redisTemplate() {
	public StringRedisTemplate redisTemplate() {
		//RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		StringRedisTemplate redisTemplate = new StringRedisTemplate();
		
		redisTemplate.setConnectionFactory(redisConnectionFactory());
		//redisTemplate.setKeySerializer(new StringRedisSerializer());

        //redisTemplate.setDefaultSerializer(new GenericJackson2JsonRedisSerializer(objectMapper));
        
        //redisTemplate.setDefaultSerializer(new StringRedisSerializer());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //redisTemplate.setValueSerializer(new StringRedisSerializer());
        //redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<String>(String.class));
        //redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setValueSerializer(new GenericToStringSerializer<String>(String.class));
        
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        //redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        //redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<String>(String.class));
        //redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericToStringSerializer<String>(String.class));

		return redisTemplate;
		
		//Serializer serializer = new GenericJackson2JsonRedisSerializer(objectMapper); var tpl = new RedisTemplate<String, Object>(); tpl.setConnectionFactory(connectionFactory); tpl.setKeySerializer(new StringRedisSerializer()); tpl.setValueSerializer(serializer); tpl.setHashKeySerializer(new StringRedisSerializer()); tpl.setHashValueSerializer(serializer); return tpl;

		//출처: https://somoly.tistory.com/134 [somoly.tistory.com]
	}
	
	/*
	@Bean
    public RedisTemplate<?, ?> redisTemplate() {
        RedisTemplate<byte[], byte[]> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        return redisTemplate;
    }
    */
    
    @Bean
    public static ConfigureRedisAction configureRedisAction() {
        return ConfigureRedisAction.NO_OP;
    }
}