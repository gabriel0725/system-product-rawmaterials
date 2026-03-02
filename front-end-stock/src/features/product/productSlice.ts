/**
 * createSlice        → cria reducer + actions automaticamente
 * createAsyncThunk   → cria actions assíncronas (thunks)
 */
import { createSlice, createAsyncThunk } from "@reduxjs/toolkit"

/**
 * Funções responsáveis pela comunicação HTTP com o backend.
 * (Camada de API isolada)
 */
import {
  fetchProductsAPI,
  createProductAPI,
  updateProductAPI,
  deleteProductAPI,
  updateProductMaterialsAPI,
} from "./productAPI"

/**
 * Tipo do produto utilizado na tipagem do estado.
 */
import type { Product } from "./types"

/**
 * Estrutura do estado do slice.
 *
 * items  → lista de produtos
 * status → controla estado da requisição (útil para loading UI)
 * error  → mensagem de erro caso ocorra falha
 */
interface ProductState {
  items: Product[]
  status: "idle" | "loading" | "succeeded" | "failed"
  error: string | null
}

/**
 * Estado inicial do slice.
 */
const initialState: ProductState = {
  items: [],
  status: "idle",
  error: null,
}

/**
 * ============================
 * THUNKS (AÇÕES ASSÍNCRONAS)
 * ============================
 *
 * createAsyncThunk cria automaticamente 3 actions:
 *
 * pending
 * fulfilled
 * rejected
 *
 * Isso permite controlar loading e erro facilmente.
 */

/**
 * Busca todos os produtos.
 */
export const fetchProducts = createAsyncThunk<Product[]>(
  "products/fetchAll",
  async () => {
    return await fetchProductsAPI()
  }
)

/**
 * Cria um novo produto.
 *
 * Omit<Product, "id">
 * → Remove o campo id do tipo, pois o backend gera o ID.
 */
export const createProduct = createAsyncThunk<
  Product,
  Omit<Product, "id">
>(
  "products/create",
  async (product) => {
    return await createProductAPI(product)
  }
)

/**
 * Atualiza um produto existente.
 *
 * Recebe:
 * - id
 * - product (sem id e sem materials)
 */
export const updateProduct = createAsyncThunk<
  Product,
  { id: number; product: Omit<Product, "id" | "materials"> }
>(
  "products/update",
  async ({ id, product }) => {
    return await updateProductAPI(id, product)
  }
)

/**
 * Remove um produto.
 *
 * Retorna apenas o id para atualizar o estado local.
 */
export const deleteProduct = createAsyncThunk<number, number>(
  "products/delete",
  async (id) => {
    await deleteProductAPI(id)
    return id
  }
)

/**
 * Atualiza a lista de materiais associados ao produto.
 */
export const updateProductMaterials = createAsyncThunk<
  Product,
  { id: number; materials: { rawMaterialId: number; requiredQuantity: number }[] }
>(
  "products/updateMaterials",
  async ({ id, materials }) => {
    return await updateProductMaterialsAPI(id, materials)
  }
)

/**
 * ============================
 * SLICE
 * ============================
 *
 * createSlice cria:
 * - Reducer
 * - Actions síncronas (caso existam)
 */
const productSlice = createSlice({
  name: "products",
  initialState,

  /**
   * reducers seria usado para ações síncronas.
   * Atualmente está vazio.
   */
  reducers: {},

  /**
   * extraReducers trata as actions criadas pelos thunks.
   */
  extraReducers: (builder) => {
    builder

      /**
       * ======================
       * FETCH PRODUCTS
       * ======================
       */
      .addCase(fetchProducts.pending, (state) => {
        state.status = "loading"
      })
      .addCase(fetchProducts.fulfilled, (state, action) => {
        state.status = "succeeded"
        state.items = action.payload
      })
      .addCase(fetchProducts.rejected, (state, action) => {
        state.status = "failed"
        state.error =
          action.error.message ?? "Erro ao buscar produtos"
      })

      /**
       * ======================
       * CREATE PRODUCT
       * ======================
       *
       * Adiciona o novo produto à lista.
       */
      .addCase(createProduct.fulfilled, (state, action) => {
        state.items.push(action.payload)
      })

      /**
       * ======================
       * UPDATE PRODUCT
       * ======================
       *
       * Substitui o produto atualizado na lista.
       */
      .addCase(updateProduct.fulfilled, (state, action) => {
        const index = state.items.findIndex(
          p => p.id === action.payload.id
        )

        if (index !== -1) {
          state.items[index] = action.payload
        }
      })

      /**
       * ======================
       * UPDATE PRODUCT MATERIALS
       * ======================
       *
       * Atualiza o produto com nova lista de materiais.
       */
      .addCase(updateProductMaterials.fulfilled, (state, action) => {
        const index = state.items.findIndex(
          p => p.id === action.payload.id
        )

        if (index !== -1) {
          state.items[index] = action.payload
        }
      })

      /**
       * ======================
       * DELETE PRODUCT
       * ======================
       *
       * Remove o produto da lista local.
       */
      .addCase(deleteProduct.fulfilled, (state, action) => {
        state.items = state.items.filter(
          p => p.id !== action.payload
        )
      })
  },
})

/**
 * Exporta apenas o reducer.
 *
 * As actions (thunks) já foram exportadas acima.
 */
export default productSlice.reducer
