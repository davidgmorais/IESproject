package ies.project.toSeeOrNot.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Wei
 * @date 2020/12/23 21:53
 */
@Configuration
public class RabbitMQConfig {
    public static final String REGISTER_QUEUE = "registerQueue";
    public static final String PAYMENT_QUEUE = "paymentQueue";
    public static final String DIRECT_EXCHANGE = "directExchange";
    public static final String REGISTER_ROUTING_KEY = "register";
    public static final String PAYMENT_ROUTING_KEY = "payment";

    @Bean
    public Queue createRegisterQueue() {
        return new Queue(REGISTER_QUEUE);
    }

    @Bean
    public Queue createPaymentQueue() {
        return new Queue(PAYMENT_QUEUE);
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(DIRECT_EXCHANGE);
    }

    @Bean
    public Binding bindingDirect1() {
        return BindingBuilder.bind(createRegisterQueue()).
                to(directExchange()).with(REGISTER_ROUTING_KEY);
    }

    @Bean
    public Binding bindingDirect2() {
        return BindingBuilder.bind(createPaymentQueue()).
                to(directExchange()).with(PAYMENT_ROUTING_KEY);
    }

}
