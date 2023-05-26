package ru.aslantemirkanov.lab5.dataservice.rabbit;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public ConnectionFactory connectionFactory(){
        return new CachingConnectionFactory("localhost");
    }

    @Bean
    public AmqpAdmin amqpAdmin(){
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate(){
        return new RabbitTemplate(connectionFactory());
    }

    @Bean
    public Queue catRegisterQueue(){
        return new Queue("catRegisterQueue");
    }
    @Bean
    public Queue catFriendRegisterQueue(){
        return new Queue("catFriendRegisterQueue");
    }

    @Bean
    public Queue getCatByIdQueue(){
        return new Queue("getCatByIdQueue");
    }

    @Bean
    public Queue getAllCatsQueue(){
        return new Queue("getAllCatsQueue");
    }

    @Bean
    public Queue getCatsByColorQueue(){
        return new Queue("getCatsByColorQueue");
    }

    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    //owners queues
    @Bean
    public Queue getAllOwnersQueue(){
        return new Queue("getAllOwnersQueue");
    }

    @Bean
    public Queue registerOwnerQueue(){
            return new Queue("registerOwnerQueue");
    }

    @Bean
    public Queue addOwnerForCatQueue(){
        return new Queue("addOwnerForCatQueue");
    }

    @Bean
    public Queue getOwnerByIdQueue(){
        return new Queue("getOwnerByIdQueue");
    }

    @Bean
    public Queue getOwnersCatsQueue(){
        return new Queue("getOwnersCatsQueue");
    }

}
