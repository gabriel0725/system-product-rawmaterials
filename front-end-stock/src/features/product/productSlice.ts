import { createSlice, createAsyncThunk } from "@reduxjs/toolkit"
import {
  fetchProductsAPI,
  createProductAPI,
  updateProductAPI,
  deleteProductAPI,
  updateProductMaterialsAPI,
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
  { id: number; product: Omit<Product, "id" | "materials"> }
>(
  "products/update",
  async ({ id, product }) => {
    return await updateProductAPI(id, product)
  }
)

export const deleteProduct = createAsyncThunk<number, number>(
  "products/delete",
  async (id) => {
    await deleteProductAPI(id)
    return id
  }
)

export const updateProductMaterials = createAsyncThunk<
  Product,
  { id: number; materials: { rawMaterialId: number; requiredQuantity: number }[] }
>(
  "products/updateMaterials",
  async ({ id, materials }) => {
    return await updateProductMaterialsAPI(id, materials)
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
          state.items[index] = action.payload
        }
      })
      .addCase(updateProductMaterials.fulfilled, (state, action) => {
        const index = state.items.findIndex(p => p.id === action.payload.id)
        if (index !== -1) {
          state.items[index] = action.payload
        }
      })
      .addCase(deleteProduct.fulfilled, (state, action) => {
        state.items = state.items.filter(p => p.id !== action.payload)
      })
  },
})

export default productSlice.reducer
