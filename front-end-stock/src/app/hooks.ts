/**
 * Arquivo responsável por criar versões tipadas
 * dos hooks do React Redux.
 *
 * Objetivo:
 * Evitar repetição de tipos (AppDispatch e RootState)
 * em todos os componentes da aplicação.
 */

import { useDispatch, useSelector } from "react-redux"
import type { TypedUseSelectorHook } from "react-redux"
import type { RootState, AppDispatch } from "./store"

/**
 * Hook customizado para substituir o useDispatch padrão.
 *
 * Por padrão, o useDispatch não conhece o tipo real do dispatch
 * da aplicação.
 *
 * Aqui estamos dizendo explicitamente que o dispatch
 * é do tipo AppDispatch (definido no store).
 *
 * Isso permite:
 * - Autocomplete correto
 * - Segurança de tipos
 * - Suporte completo a thunks
 *
 * Uso nos componentes:
 *
 * const dispatch = useAppDispatch()
 */
export const useAppDispatch = () => useDispatch<AppDispatch>()


/**
 * Hook customizado tipado para acessar o estado global.
 *
 * O useSelector padrão não sabe qual é o formato do estado global.
 *
 * Aqui estamos informando que o estado segue o tipo RootState,
 * que representa toda a estrutura do store.
 *
 * TypedUseSelectorHook<RootState>
 * → Garante que o TypeScript saiba exatamente
 *   quais propriedades existem no estado.
 *
 * Uso nos componentes:
 *
 * const products = useAppSelector(state => state.product.list)
 *
 * Benefícios:
 * - Autocomplete automático
 * - Erros de tipo detectados em tempo de compilação
 * - Código mais seguro e legível
 */
export const useAppSelector: TypedUseSelectorHook<RootState> = useSelector
