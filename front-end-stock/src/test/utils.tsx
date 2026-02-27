import type { ReactNode } from "react"
import { configureStore } from "@reduxjs/toolkit"
import { Provider } from "react-redux"
import { render } from "@testing-library/react"

import productionReducer from "../features/production/productionSlice"
import productReducer from "../features/product/productSlice"
import rawMaterialReducer from "../features/rawMaterial/rawMaterialSlice"
import productMaterialReducer from "../features/productMaterial/productMaterialSlice"

const rootReducer = {
  production: productionReducer,
  product: productReducer,
  rawMaterial: rawMaterialReducer,
  productMaterial: productMaterialReducer,
}

export type TestRootState = {
  production: ReturnType<typeof productionReducer>
  product: ReturnType<typeof productReducer>
  rawMaterial: ReturnType<typeof rawMaterialReducer>
  productMaterial: ReturnType<typeof productMaterialReducer>
}

export function renderWithProviders(
  ui: ReactNode,
  {
    preloadedState,
  }: {
    preloadedState?: Partial<TestRootState>
  } = {}
) {
  const store = configureStore({
    reducer: rootReducer,
    preloadedState,
  })

  return render(
    <Provider store={store}>
      {ui}
    </Provider>
  )
}
