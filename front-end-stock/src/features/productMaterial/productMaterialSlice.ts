import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import * as api from "./ProductMaterialAPI";

export interface ProductMaterial {
  id?: number;
  productId: number;
  materialId: number;
  quantity: number;
}

interface ProductMaterialState {
  status: "idle" | "loading" | "succeeded" | "failed";
  error: string | null;
}

const initialState: ProductMaterialState = {
  status: "idle",
  error: null,
};

export const createProductMaterial = createAsyncThunk(
  "productMaterial/create",
  async (data: {
    productId: number;
    materialId: number;
    quantity: number;
  }) => {
    return await api.create(data);
  }
);

export const updateProductMaterial = createAsyncThunk(
  "productMaterial/update",
  async (data: { id: number; quantity: number }) => {
    return await api.update(data.id, data.quantity);
  }
);

export const deleteProductMaterial = createAsyncThunk(
  "productMaterial/delete",
  async (id: number) => {
    await api.remove(id);
    return id;
  }
);

const productMaterialSlice = createSlice({
  name: "productMaterial",
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(createProductMaterial.pending, (state) => {
        state.status = "loading";
      })
      .addCase(createProductMaterial.fulfilled, (state) => {
        state.status = "succeeded";
      })
      .addCase(createProductMaterial.rejected, (state, action) => {
        state.status = "failed";
        state.error = action.error.message ?? "Error";
      });
  },
});

export default productMaterialSlice.reducer;
