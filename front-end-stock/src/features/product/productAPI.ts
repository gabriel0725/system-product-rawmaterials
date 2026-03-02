/**
 * Tipo Product utilizado para tipagem das requisições
 * e respostas da API.
 */
import type { Product } from "./types";

/**
 * URL base da API.
 *
 * import.meta.env é usado normalmente em projetos com Vite.
 * VITE_API_URL deve estar definido no arquivo .env.
 *
 * Exemplo:
 * VITE_API_URL=http://localhost:8080
 */
const API_URL = import.meta.env.VITE_API_URL;

/**
 * Busca todos os produtos.
 *
 * Método: GET
 * Endpoint: /products
 *
 * Retorno:
 * Promise<Product[]>
 *
 * Fluxo:
 * 1. Faz requisição HTTP
 * 2. Verifica se a resposta foi bem-sucedida (response.ok)
 * 3. Retorna o JSON convertido automaticamente
 */
export const fetchProductsAPI = async (): Promise<Product[]> => {
  const response = await fetch(`${API_URL}/products`);

  if (!response.ok)
    throw new Error("Error when searching for products");

  return response.json();
};

/**
 * Cria um novo produto.
 *
 * Método: POST
 * Endpoint: /products
 *
 * Envia:
 * JSON no body da requisição
 *
 * Retorno:
 * Produto criado (normalmente com ID gerado)
 */
export const createProductAPI = async (
  product: Product
): Promise<Product> => {
  const response = await fetch(`${API_URL}/products`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(product),
  });

  if (!response.ok)
    throw new Error("Error creating product");

  return response.json();
};

/**
 * Atualiza um produto existente.
 *
 * Método: PUT
 * Endpoint: /products/{id}
 *
 * Parâmetros:
 * id      → identificador do produto
 * product → dados atualizados
 *
 * Retorno:
 * Produto atualizado
 */
export const updateProductAPI = async (
  id: number,
  product: Product
): Promise<Product> => {
  const response = await fetch(`${API_URL}/products/${id}`, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(product),
  });

  if (!response.ok)
    throw new Error("Error when updating produc");

  return response.json();
};

/**
 * Remove um produto.
 *
 * Método: DELETE
 * Endpoint: /products/{id}
 *
 * Retorno:
 * void (não espera corpo de resposta)
 */
export const deleteProductAPI = async (
  id: number
): Promise<void> => {
  const response = await fetch(`${API_URL}/products/${id}`, {
    method: "DELETE",
  });

  if (!response.ok)
    throw new Error("Error when deleting product");
};

/**
 * Atualiza os materiais associados a um produto.
 *
 * Método: PUT
 * Endpoint: /products/{id}/materials
 *
 * Parâmetros:
 * id        → produto
 * materials → lista contendo:
 *              - rawMaterialId
 *              - requiredQuantity
 *
 * Retorno:
 * Produto atualizado com nova lista de materiais
 */
export const updateProductMaterialsAPI = async (
  id: number,
  materials: { rawMaterialId: number; requiredQuantity: number }[]
): Promise<Product> => {

  const response = await fetch(`${API_URL}/products/${id}/materials`, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(materials),
  });

  if (!response.ok)
    throw new Error("Error updating product materials");

  return response.json();
};
