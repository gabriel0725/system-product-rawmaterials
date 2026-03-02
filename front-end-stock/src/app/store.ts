/**
 * Arquivo responsável por configurar o Store global da aplicação.
 *
 * O Store é o "container central" que guarda todo o estado global.
 *
 * Aqui definimos:
 * - Quais reducers existem
 * - Como o estado é estruturado
 * - Se o Redux DevTools estará habilitado
 * - Os tipos globais RootState e AppDispatch
 */

import { configureStore } from "@reduxjs/toolkit"

/**
 * Importação dos reducers de cada feature da aplicação.
 *
 * Cada slice representa um domínio de negócio isolado:
 *
 * production      → cálculo de produção
 * product         → produtos
 * rawMaterial     → matérias-primas
 * productMaterial → relação entre produto e matéria-prima
 */
import productionReducer from "../features/production/productionSlice"
import productReducer from "../features/product/productSlice"
import rawMaterialReducer from "../features/rawMaterial/rawMaterialSlice"
import productMaterialReducer from "../features/productMaterial/productMaterialSlice"

/**
 * rootReducer define a estrutura do estado global.
 *
 * A chave de cada propriedade será o nome do "slice"
 * dentro do estado.
 *
 * Estrutura final do state:
 *
 * {
 *   production: { ... },
 *   product: { ... },
 *   rawMaterial: { ... },
 *   productMaterial: { ... }
 * }
 */
export const rootReducer = {
  production: productionReducer,
  product: productReducer,
  rawMaterial: rawMaterialReducer,
  productMaterial: productMaterialReducer,
}

/**
 * Configuração do Store utilizando Redux Toolkit.
 *
 * configureStore automaticamente:
 * - Combina reducers
 * - Adiciona middleware padrão (ex: thunk)
 * - Ativa integração com Redux DevTools
 * - Configura boas práticas automaticamente
 */
export const store = configureStore({
  reducer: rootReducer,

  /**
   * DevTools só é habilitado fora de produção.
   *
   * import.meta.env.MODE é uma variável do ambiente
   * (usada normalmente com Vite).
   *
   * Em produção:
   * → DevTools desabilitado por segurança e performance
   */
  devTools: import.meta.env.MODE !== "production",
})

/**
 * RootState representa o tipo completo do estado global.
 *
 * Ele é inferido automaticamente a partir do store.
 *
 * Isso garante que qualquer alteração na estrutura
 * do store atualize o tipo automaticamente.
 */
export type RootState = ReturnType<typeof store.getState>

/**
 * AppDispatch representa o tipo real do dispatch da aplicação.
 *
 * Isso é importante principalmente quando usamos:
 * - Thunks
 * - Async actions
 * - Middleware customizado
 *
 * Ele será usado para tipar o useAppDispatch.
 */
export type AppDispatch = typeof store.dispatch
