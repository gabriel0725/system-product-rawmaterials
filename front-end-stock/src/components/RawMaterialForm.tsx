/**
 * Hooks do React:
 *
 * useState  → controla estado local do formulário
 * useEffect → sincroniza dados quando entra em modo edição
 */
import { useState, useEffect } from 'react'

/**
 * Hook tipado para disparar actions no Redux.
 */
import { useAppDispatch } from '../app/hooks'

/**
 * Actions assíncronas (thunks) do slice de matéria-prima.
 *
 * createRawMaterial → cria nova matéria-prima
 * updateRawMaterial → atualiza matéria-prima existente
 */
import {
  createRawMaterial,
  updateRawMaterial
} from '../features/rawMaterial/rawMaterialSlice'

/**
 * Tipo RawMaterial usado para tipagem do modo edição.
 */
import type { RawMaterial } from '../features/rawMaterial/types'

/**
 * Componentes estilizados reutilizáveis.
 */
import { ButtonRed, ButtonBlue, StyledForm } from '../styles/Layout'

/**
 * Props do componente:
 *
 * editing    → matéria-prima selecionada para edição (ou null)
 * setEditing → função para sair do modo edição
 */
interface Props {
  editing: RawMaterial | null
  setEditing: (value: RawMaterial | null) => void
}

/**
 * Componente responsável por:
 * - Criar matéria-prima
 * - Editar matéria-prima
 *
 * Ele funciona em dois modos:
 *
 * 🔹 Modo criação
 * 🔹 Modo edição
 */
export default function RawMaterialForm({ editing, setEditing }: Props) {

  /**
   * Dispatch do Redux.
   */
  const dispatch = useAppDispatch()

  /**
   * Estado local do formulário.
   *
   * stockQuantity é string porque:
   * - Inputs HTML trabalham com string
   * - Permite campo vazio sem erro
   * - Conversão ocorre apenas no submit
   */
  const [formData, setFormData] = useState({
    code: '',
    name: '',
    stockQuantity: '' // string para permitir campo vazio
  })

  /**
   * Sincroniza o formulário com a matéria-prima
   * quando entra ou sai do modo edição.
   *
   * Se editing existir:
   * → Preenche campos com os dados existentes.
   *
   * Se editing for null:
   * → Reseta o formulário.
   */
  useEffect(() => {
    if (editing) {
      setFormData({
        code: editing.code,
        name: editing.name,
        stockQuantity: String(editing.stockQuantity)
      })
    } else {
      setFormData({ code: '', name: '', stockQuantity: '' })
    }
  }, [editing])

  /**
   * Atualiza dinamicamente o estado
   * com base no atributo "name" do input.
   *
   * Isso evita criar uma função para cada campo.
   */
  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target
    setFormData((prev) => ({
      ...prev,
      [name]: value
    }))
  }

  /**
   * Executado ao submeter o formulário.
   *
   * Fluxo:
   * 1. Impede reload da página
   * 2. Converte stockQuantity para number
   * 3. Decide entre criar ou atualizar
   * 4. Limpa formulário
   */
  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault()

    /**
     * Conversão segura:
     * Se campo estiver vazio → assume 0
     */
    const stockNumber =
      formData.stockQuantity.trim() === '' ? 0 : Number(formData.stockQuantity)

    /**
     * Objeto final enviado para o backend.
     */
    const rawMaterialToSubmit = {
      code: formData.code,
      name: formData.name,
      stockQuantity: stockNumber
    }

    /**
     * Modo edição:
     * → Atualiza mantendo id original
     * → Sai do modo edição
     *
     * Modo criação:
     * → Cria nova matéria-prima
     */
    if (editing) {
      dispatch(
        updateRawMaterial({
          ...editing,
          ...rawMaterialToSubmit
        })
      )
      setEditing(null)
    } else {
      dispatch(createRawMaterial(rawMaterialToSubmit))
    }

    /**
     * Limpa formulário após envio.
     */
    setFormData({ code: '', name: '', stockQuantity: '' })
  }

  /**
   * Renderização do formulário.
   *
   * key={editing?.id ?? 'new'}
   * → Força re-render quando troca o item editado.
   */
  return (
    <StyledForm
      key={editing?.id ?? 'new'}
      onSubmit={handleSubmit}
      style={{ marginBottom: '20px' }}
    >
      {/* Campo código */}
      <input
        name="code"
        placeholder="Code"
        value={formData.code}
        onChange={handleChange}
        required
      />

      {/* Campo nome */}
      <input
        name="name"
        placeholder="Name"
        value={formData.name}
        onChange={handleChange}
        required
      />

      {/* Campo quantidade em estoque */}
      <input
        name="stockQuantity"
        type="number"
        placeholder="Stock Quantity"
        min={0}
        value={formData.stockQuantity}
        onChange={handleChange}
      />

      {/* Botão principal muda dinamicamente */}
      <ButtonBlue type="submit">
        {editing ? 'Update' : 'Create'}
      </ButtonBlue>

      {/* Botão Cancel aparece apenas no modo edição */}
      {editing && (
        <ButtonRed type="button" onClick={() => setEditing(null)}>
          Cancel
        </ButtonRed>
      )}
    </StyledForm>
  )
}
