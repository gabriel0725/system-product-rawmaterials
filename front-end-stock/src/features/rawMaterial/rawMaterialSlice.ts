import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import * as api from "./rawMaterialAPI";
import type { RawMaterial } from "./types";

interface RawMaterialState {
  rawMaterials: RawMaterial[];
  status: "idle" | "loading" | "succeeded" | "failed";
  error: string | null;
}

const initialState: RawMaterialState = {
  rawMaterials: [],
  status: "idle",
  error: null,
};

export const fetchRawMaterials = createAsyncThunk<RawMaterial[]>(
  "rawMaterial/fetchAll",
  async () => {
    return await api.getAll();
  }
);

export const createRawMaterial = createAsyncThunk<
  RawMaterial,
  Omit<RawMaterial, "id">
>("rawMaterial/create", async (data) => {
  return await api.create(data);
});

export const updateRawMaterial = createAsyncThunk<
  RawMaterial,
  RawMaterial
>("rawMaterial/update", async (data) => {
  return await api.update(data);
});

export const deleteRawMaterial = createAsyncThunk<number, number>(
  "rawMaterial/delete",
  async (id) => {
    await api.remove(id);
    return id;
  }
);

const rawMaterialSlice = createSlice({
  name: "rawMaterial",
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchRawMaterials.pending, (state) => {
        state.status = "loading";
      })
      .addCase(fetchRawMaterials.fulfilled, (state, action) => {
        state.status = "succeeded";
        state.rawMaterials = action.payload;
      })
      .addCase(fetchRawMaterials.rejected, (state, action) => {
        state.status = "failed";
        state.error = action.error.message ?? "Error loading raw materials";
      })
      .addCase(createRawMaterial.fulfilled, (state, action) => {
        state.rawMaterials.push(action.payload);
      })
      .addCase(updateRawMaterial.fulfilled, (state, action) => {
        const index = state.rawMaterials.findIndex(
          (m) => m.id === action.payload.id
        );
        if (index !== -1) {
          state.rawMaterials[index] = action.payload;
        }
      })
      .addCase(deleteRawMaterial.fulfilled, (state, action) => {
        state.rawMaterials = state.rawMaterials.filter(
          (m) => m.id !== action.payload
        );
      });
  },
});

export default rawMaterialSlice.reducer;
