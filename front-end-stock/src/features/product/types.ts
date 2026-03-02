/**
 * DTO (Data Transfer Object) que representa
 * a relação entre Produto e Matéria-Prima.
 *
 * Esse formato normalmente vem do backend.
 *
 * rawMaterialId     → identificador da matéria-prima
 * rawMaterialName   → nome da matéria-prima (útil para exibição)
 * requiredQuantity  → quantidade necessária para produzir 1 unidade do produto
 */
export interface ProductMaterialDTO {
  rawMaterialId: number
  rawMaterialName: string
  requiredQuantity: number
}

/**
 * Interface principal do Produto.
 *
 * Representa o modelo de dados utilizado:
 * - No estado global (Redux)
 * - Nas respostas da API
 * - Nos formulários
 *
 * id é opcional porque:
 * - Ao criar um produto, ele ainda não possui ID
 * - O backend gera o ID automaticamente
 *
 * materials é opcional porque:
 * - Pode não estar carregado
 * - Pode não existir no momento da criação
 */
export interface Product {
  id?: number
  code: string
  name: string
  price: number
  materials?: ProductMaterialDTO[]
}
