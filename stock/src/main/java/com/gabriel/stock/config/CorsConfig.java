/**
 * @author Gabri
 */

package com.gabriel.stock.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Indica que esta classe é uma classe de configuração do Spring.
 * Classes anotadas com @Configuration são utilizadas para definir
 * beans e customizações do comportamento da aplicação.
 */
@Configuration
public class CorsConfig {

    /**
     * Define um Bean do tipo WebMvcConfigurer.
     *
     * Esse bean permite customizar configurações do Spring MVC,
     * como por exemplo regras de CORS.
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {

        /**
         * Retorna uma implementação anônima de WebMvcConfigurer
         * sobrescrevendo apenas o metodo addCorsMappings.
         */
        return new WebMvcConfigurer() {

            /**
             * Metodo responsável por configurar as regras de CORS
             * (Cross-Origin Resource Sharing).
             *
             * CORS controla quais domínios externos podem acessar
             * sua API via navegador.
             */
            @Override
            public void addCorsMappings(CorsRegistry registry) {

                /**
                 * addMapping("/**")
                 *
                 * Aplica essa configuração para TODOS os endpoints
                 * da aplicação.
                 */
                registry.addMapping("/**")

                        /**
                         * allowedOriginPatterns(...)
                         *
                         * Define quais origens (domínios) podem acessar a API.
                         *
                         * "http://localhost:*"
                         * -> Permite qualquer porta no localhost (útil para desenvolvimento).
                         *
                         * "https://*.vercel.app"
                         * -> Permite qualquer subdomínio hospedado na Vercel.
                         *    Exemplo: https://meu-projeto.vercel.app
                         */
                        .allowedOriginPatterns(
                                "http://localhost:*",
                                "https://*.vercel.app"
                        )

                        /**
                         * allowedMethods("*")
                         *
                         * Permite todos os métodos HTTP:
                         * GET, POST, PUT, DELETE, PATCH, etc.
                         */
                        .allowedMethods("*")

                        /**
                         * allowedHeaders("*")
                         *
                         * Permite todos os headers na requisição,
                         * como Authorization, Content-Type, etc.
                         */
                        .allowedHeaders("*")

                        /**
                         * allowCredentials(false)
                         *
                         * Indica se a aplicação permite envio de cookies
                         * ou credenciais (como Authorization via navegador).
                         *
                         * false -> Não permite envio de cookies/sessão.
                         *
                         * Se fosse true:
                         * - Não poderia usar "*"
                         * - Precisaria especificar domínios exatos.
                         */
                        .allowCredentials(false);
            }
        };
    }
}