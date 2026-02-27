import type { ReactNode } from 'react'
import { configureStore } from '@reduxjs/toolkit'
import { Provider } from 'react-redux'
import { render } from '@testing-library/react'

export function renderWithProviders(
  ui: ReactNode,
  {
    preloadedState = {},
    reducer
  }: any
) {
  const store = configureStore({
    reducer,
    preloadedState
  })

  return render(
    <Provider store={store}>
      {ui}
    </Provider>
  )
}
