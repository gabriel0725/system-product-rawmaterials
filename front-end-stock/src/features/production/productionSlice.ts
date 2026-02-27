import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import type { ProductProduction, ProductionResponse } from "./types";
import { fetchProductionAPI } from "./productionAPI";

interface ProductionState {
  products: ProductProduction[];
  grandTotal: number;
  status: "idle" | "loading" | "succeeded" | "failed";
  error: string | null;
}

const initialState: ProductionState = {
  products: [],
  grandTotal: 0,
  status: "idle",
  error: null,
};

export const fetchProduction = createAsyncThunk<
  ProductionResponse
>("production/fetchProduction", async () => {
  return await fetchProductionAPI();
});

const productionSlice = createSlice({
  name: "production",
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchProduction.pending, (state) => {
        state.status = "loading";
        state.error = null;
      })
      .addCase(fetchProduction.fulfilled, (state, action) => {
        state.status = "succeeded";
        state.products = action.payload.products;
        state.grandTotal = action.payload.grandTotal;
      })
      .addCase(fetchProduction.rejected, (state, action) => {
        state.status = "failed";
        state.error = action.error.message ?? "Failed to fetch production data";
      });
  },
});

export default productionSlice.reducer;
