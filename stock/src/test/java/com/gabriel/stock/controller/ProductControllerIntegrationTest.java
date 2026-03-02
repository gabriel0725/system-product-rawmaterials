package com.gabriel.stock.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Teste de integração para o ProductController.
 *
 * Este teste sobe o contexto completo da aplicação
 * (Spring Boot + Controller + Service + Repository + Banco).
 *
 * Objetivo:
 * Verificar se o endpoint POST /products
 * está funcionando corretamente e retornando HTTP 201 (Created).
 *
 * Tipo de teste:
 * - Integration Test
 * - Testa o fluxo completo da requisição HTTP
 */
@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerIntegrationTest {

    /**
     * MockMvc permite simular requisições HTTP
     * sem necessidade de subir um servidor real.
     *
     * Ele executa o fluxo completo do Spring MVC:
     *
     * Requisição simulada
     *      ↓
     * Controller
     *      ↓
     * Service
     *      ↓
     * Repository
     *      ↓
     * Banco de dados
     *      ↓
     * Response
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * Testa a criação de um produto via endpoint REST.
     *
     * Fluxo testado:
     * 1. Envia um JSON para POST /products
     * 2. O controller recebe o JSON
     * 3. Converte para objeto
     * 4. Chama o service
     * 5. Persiste no banco
     * 6. Retorna HTTP 201
     *
     * O teste valida apenas o status HTTP retornado.
     */
    @Test
    void shouldCreateProduct() throws Exception {

        /*
         * JSON que será enviado na requisição.
         *
         * Representa o corpo (body) da requisição HTTP.
         *
         * Campos:
         * - code  → código do produto
         * - name  → nome do produto
         * - price → preço do produto
         */
        String json = """
                {
                  "code": "P100",
                  "name": "Integration Test Product",
                  "price": 200
                }
                """;

        /*
         * mockMvc.perform(...)
         *
         * Simula uma requisição HTTP POST para o endpoint /products.
         *
         * contentType(MediaType.APPLICATION_JSON)
         * → Define o tipo de conteúdo da requisição.
         *
         * content(json)
         * → Define o corpo da requisição.
         *
         * andExpect(status().isCreated())
         * → Valida se o retorno foi HTTP 201.
         *
         * HTTP 201 significa:
         * O recurso foi criado com sucesso.
         */
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());
    }
}