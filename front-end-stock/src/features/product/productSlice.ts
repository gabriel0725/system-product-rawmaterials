import { createSlice, createAsyncThunk } from "@reduxjs/toolkit"
import {
  fetchProductsAPI,
  createProductAPI,
  updateProductAPI,
  deleteProductAPI,
} from "./productAPI"
import type { Product } from "./types"

interface ProductState {
  items: Product[]
  status: "idle" | "loading" | "succeeded" | "failed"
  error: string | null
}

const initialState: ProductState = {
  items: [],
  status: "idle",
  error: null,
}

export const fetchProducts = createAsyncThunk<Product[]>(
  "products/fetchAll",
  async () => {
    return await fetchProductsAPI()
  }
)

export const createProduct = createAsyncThunk<Product, Omit<Product, "id">>(
  "products/create",
  async (product) => {
    return await createProductAPI(product)
  }
)

export const updateProduct = createAsyncThunk<
  Product,
  { id: number; product: Product }
>(
  "products/update",
  async ({ id, product }) => {
    // Garante que o backend retorna o produto atualizado com materiais
    const updated = await updateProductAPI(id, product)
    return updated
  }
)

export const deleteProduct = createAsyncThunk<number, number>(
  "products/delete",
  async (id) => {
    await deleteProductAPI(id)
    return id
  }
)

const productSlice = createSlice({
  name: "products",
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchProducts.pending, (state) => {
        state.status = "loading"
      })
      .addCase(fetchProducts.fulfilled, (state, action) => {
        state.status = "succeeded"
        state.items = action.payload
      })
      .addCase(fetchProducts.rejected, (state, action) => {
        state.status = "failed"
        state.error = action.error.message ?? "Erro ao buscar produtos"
      })

      .addCase(createProduct.fulfilled, (state, action) => {
        state.items.push(action.payload)
      })

      .addCase(updateProduct.fulfilled, (state, action) => {
        const index = state.items.findIndex(p => p.id === action.payload.id)
        if (index !== -1) {
          // Substitui todo o produto pela versão retornada do backend, incluindo materiais
          state.items[index] = action.payload
        }
      })

      .addCase(deleteProduct.fulfilled, (state, action) => {
        state.items = state.items.filter(p => p.id !== action.payload)
      })
  },
})

export default productSlice.reducer
