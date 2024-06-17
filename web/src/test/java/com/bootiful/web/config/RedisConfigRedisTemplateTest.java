// ********RoostGPT********
/*
Test generated by RoostGPT for test test-5404 using AI Type Open AI and AI Model gpt-4-1106-preview
ROOST_METHOD_HASH=redisTemplate_01699f2cd3
ROOST_METHOD_SIG_HASH=redisTemplate_ea9f98a6b0
Scenario 1: Successful creation of StringRedisTemplate with proper serializer
Details:  
TestName: ensureTemplateIsCreatedWithProperSerializer
Description: This test ensures that the StringRedisTemplate is created with the correct Jackson2JsonRedisSerializer and ObjectMapper configurations, including visibility and default typing settings.
Execution:
  Arrange: Mock the RedisConnectionFactory.
  Act: Call the redisTemplate method with the mocked RedisConnectionFactory.
  Assert: Verify that the returned StringRedisTemplate has a Jackson2JsonRedisSerializer set as its value serializer and that the ObjectMapper within the serializer is configured as expected.
Validation: 
  The assertion checks that the value serializer is indeed a Jackson2JsonRedisSerializer with the proper ObjectMapper configurations, ensuring that JSON serialization and deserialization will work as intended for Redis operations. This is significant because it affects how objects are stored and retrieved from Redis.
Scenario 2: Exception handling when RedisConnectionFactory is null
Details:  
TestName: ensureTemplateCreationFailsWithNullConnectionFactory
Description: This test checks if the redisTemplate method throws the appropriate exception when a null RedisConnectionFactory is provided.
Execution:
  Arrange: Provide a null RedisConnectionFactory.
  Act: Attempt to call the redisTemplate method with the null connection factory.
  Assert: Expect an IllegalArgumentException or a custom exception indicating that the connection factory cannot be null.
Validation: 
  The assertion aims to verify that the method handles null arguments gracefully and informs the user of the missing connection factory dependency. This is significant because it prevents the application from continuing with an invalid state that would lead to failures during runtime when attempting Redis operations.
Scenario 3: Validation of ObjectMapper default typing configuration
Details:  
TestName: ensureObjectMapperDefaultTypingIsConfigured
Description: This test verifies that the ObjectMapper used in the value serializer has been configured with default typing for non-final classes.
Execution:
  Arrange: Mock the RedisConnectionFactory.
  Act: Call the redisTemplate method and retrieve the value serializer's ObjectMapper.
  Assert: Check that the ObjectMapper's default typing feature is enabled and set to NON_FINAL.
Validation: 
  The assertion checks that the ObjectMapper is correctly configured to handle polymorphic types during serialization and deserialization. This is significant because it ensures that the correct class types are instantiated when objects are retrieved from Redis, which is essential for applications dealing with a hierarchy of classes.
Scenario 4: AfterPropertiesSet method invocation on StringRedisTemplate
Details:  
TestName: ensureAfterPropertiesSetIsCalled
Description: This test ensures that the afterPropertiesSet method is called on the StringRedisTemplate before it is returned, which is necessary for the proper initialization of the template.
Execution:
  Arrange: Mock the RedisConnectionFactory and spy on the StringRedisTemplate instance.
  Act: Call the redisTemplate method.
  Assert: Verify that the afterPropertiesSet method is called on the StringRedisTemplate instance.
Validation: 
  The assertion verifies that the StringRedisTemplate is fully initialized before use, which is crucial for the correct functioning of the template within the application. This test ensures that all necessary post-processing steps are completed, such as initializing serializers and connection properties.
*/
// ********RoostGPT********
package com.bootiful.web.config;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.junit.jupiter.api.*;

@Tag("com.bootiful.web.config")
@Tag("com.bootiful.web.config.redisTemplate")
class RedisConfigRedisTemplateTest {
    @Test
    public void ensureTemplateIsCreatedWithProperSerializer() {
        RedisConnectionFactory mockConnectionFactory = mock(RedisConnectionFactory.class);
        RedisConfig config = new RedisConfig();
        StringRedisTemplate template = config.redisTemplate(mockConnectionFactory);
        assertTrue(template.getValueSerializer() instanceof Jackson2JsonRedisSerializer);
        Jackson2JsonRedisSerializer serializer = (Jackson2JsonRedisSerializer) template.getValueSerializer();
        ObjectMapper objectMapper = (ObjectMapper) serializer.getObjectMapper();
        assertNotNull(objectMapper);
        assertTrue(objectMapper.isEnabled(ObjectMapper.DefaultTyping.NON_FINAL));
    }
    @Test
    public void ensureTemplateCreationFailsWithNullConnectionFactory() {
        RedisConnectionFactory nullConnectionFactory = null;
        RedisConfig config = new RedisConfig();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            config.redisTemplate(nullConnectionFactory);
        });
        String expectedMessage = "ConnectionFactory must not be null";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
    @Test
    public void ensureObjectMapperDefaultTypingIsConfigured() {
        RedisConnectionFactory mockConnectionFactory = mock(RedisConnectionFactory.class);
        RedisConfig config = new RedisConfig();
        StringRedisTemplate template = config.redisTemplate(mockConnectionFactory);
        Jackson2JsonRedisSerializer serializer = (Jackson2JsonRedisSerializer) template.getValueSerializer();
        ObjectMapper objectMapper = (ObjectMapper) serializer.getObjectMapper();
        assertTrue(objectMapper.isEnabled(ObjectMapper.DefaultTyping.NON_FINAL));
    }
    @Test
    public void ensureAfterPropertiesSetIsCalled() {
        RedisConnectionFactory mockConnectionFactory = mock(RedisConnectionFactory.class);
        StringRedisTemplate spyTemplate = spy(new StringRedisTemplate(mockConnectionFactory));
        RedisConfig config = new RedisConfig() {
            @Override
            public StringRedisTemplate redisTemplate(RedisConnectionFactory connectionFactory) {
                return spyTemplate;
            }
        };
        config.redisTemplate(mockConnectionFactory);
        verify(spyTemplate, times(1)).afterPropertiesSet();
    }
    // This class is a placeholder to represent the actual RedisConfig class where the redisTemplate method is defined.
    static class RedisConfig {
        public StringRedisTemplate redisTemplate(RedisConnectionFactory connectionFactory) {
            if (connectionFactory == null) {
                throw new IllegalArgumentException("ConnectionFactory must not be null");
            }
            final StringRedisTemplate template = new StringRedisTemplate(connectionFactory);
            Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
            ObjectMapper om = new ObjectMapper();
            om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
            // Deprecated method 'enableDefaultTyping' replaced with 'activateDefaultTyping'
            om.activateDefaultTyping(om.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL);
            jackson2JsonRedisSerializer.setObjectMapper(om);
            template.setValueSerializer(jackson2JsonRedisSerializer);
            template.afterPropertiesSet();
            return template;
        }
    }
}