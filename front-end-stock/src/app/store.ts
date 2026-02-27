import { configureStore } from "@reduxjs/toolkit"

import productionReducer from "../features/production/productionSlice"
import productReducer from "../features/product/productSlice"
import rawMaterialReducer from "../features/rawMaterial/rawMaterialSlice"
import productMaterialReducer from "../features/productMaterial/productMaterialSlice"

export const rootReducer = {
  production: productionReducer,
  product: productReducer,
  rawMaterial: rawMaterialReducer,
  productMaterial: productMaterialReducer,
}

export const store = configureStore({
  reducer: rootReducer,
  devTools: import.meta.env.MODE !== "production",
})

export type RootState = ReturnType<typeof store.getState>
export type AppDispatch = typeof store.dispatch
