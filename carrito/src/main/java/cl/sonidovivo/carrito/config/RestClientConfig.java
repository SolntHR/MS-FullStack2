package cl.sonidovivo.carrito.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    /**
     * Constructor normal (sin balanceo). Es el que usa el propio cliente de Eureka para
     * registrarse en http://localhost:8761. Va como @Primary para que Eureka tome este y no el balanceado.
     */
    @Bean
    @Primary
    public RestClient.Builder restClientBuilder() {
        return RestClient.builder();
    }

    /**
     * Constructor con balanceo de carga, solo para llamar a otros microservicios.
     * Gracias a @LoadBalanced, una URL como http://inventario se resuelve consultando a Eureka.
     * InventarioCliente lo pide con @LoadBalanced en su constructor.
     */
    @Bean
    @LoadBalanced
    public RestClient.Builder restClientBuilderBalanceado() {
        return RestClient.builder();
    }
}
