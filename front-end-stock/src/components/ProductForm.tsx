/**
 * Importação de hooks do React:
 *
 * useState  → controla estado local do formulário
 * useEffect → sincroniza o formulário quando entra em modo edição
 */
import { useState, useEffect } from 'react'

/**
 * Actions assíncronas (thunks) do slice de produto.
 *
 * createProduct → cria novo produto
 * updateProduct → atualiza produto existente
 */
import { createProduct, updateProduct } from '../features/product/productSlice'

/**
 * Hook tipado para disparar actions no Redux.
 */
import { useAppDispatch } from '../app/hooks'

/**
 * Tipo Product usado no modo edição.
 */
import type { Product } from '../features/product/types'

/**
 * Componentes estilizados reutilizáveis.
 */
import { ButtonRed, ButtonBlue, StyledForm } from '../styles/Layout'

/**
 * Props do componente:
 *
 * editing     → produto sendo editado (ou null se for criação)
 * setEditing  → função para ativar/desativar modo edição
 */
interface Props {
  editing: Product | null
  setEditing: (value: Product | null) => void
}

/**
 * Estado interno do formulário.
 *
 * Observação importante:
 * O price é string aqui porque inputs HTML trabalham com string.
 * A conversão para number ocorre apenas no submit.
 */
interface ProductFormData {
  code: string
  name: string
  price: string
}

/**
 * Componente responsável por:
 * - Criar produtos
 * - Editar produtos
 *
 * Ele funciona em dois modos:
 *
 * 🔹 Modo criação (editing = null)
 * 🔹 Modo edição (editing contém um produto)
 */
export default function ProductForm({ editing, setEditing }: Props) {

  /**
   * Dispatch do Redux para enviar actions.
   */
  const dispatch = useAppDispatch()

  /**
   * Estado local do formulário.
   *
   * Controla os valores dos inputs (controlled components).
   */
  const [formData, setFormData] = useState<ProductFormData>({
    code: '',
    name: '',
    price: ''
  })

  /**
   * useEffect responsável por sincronizar o formulário
   * quando o modo edição muda.
   *
   * Se editing existir:
   * → Preenche os campos com os dados do produto.
   *
   * Se editing for null:
   * → Reseta o formulário.
   *
   * Dependência:
   * [editing] → executa sempre que editing mudar.
   */
  useEffect(() => {
    if (editing) {
      setFormData({
        code: editing.code ?? '',
        name: editing.name ?? '',
        price: editing.price != null ? String(editing.price) : ''
      })
    } else {
      setFormData({
        code: '',
        name: '',
        price: ''
      })
    }
  }, [editing])

  /**
   * Atualiza o estado quando o usuário digita.
   *
   * Estratégia:
   * - Usa o atributo "name" do input
   * - Atualiza dinamicamente o campo correspondente
   *
   * Isso permite usar uma única função para todos os inputs.
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
   * 1. Impede reload da página (preventDefault)
   * 2. Converte preço para número
   * 3. Remove espaços extras (trim)
   * 4. Decide entre criar ou atualizar
   * 5. Limpa formulário
   */
  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault()

    /**
     * Conversão segura do preço.
     * Se estiver vazio → assume 0.
     */
    const priceNumber =
      formData.price.trim() === '' ? 0 : Number(formData.price)

    /**
     * Objeto final enviado para o backend.
     */
    const productToSubmit = {
      code: formData.code.trim(),
      name: formData.name.trim(),
      price: priceNumber
    }

    /**
     * Se estiver editando:
     * → Dispara updateProduct
     * → Sai do modo edição
     *
     * Caso contrário:
     * → Dispara createProduct
     */
    if (editing) {
      dispatch(
        updateProduct({
          id: editing.id,
          product: productToSubmit
        })
      )
      setEditing(null)
    } else {
      dispatch(createProduct(productToSubmit))
    }

    /**
     * Limpa formulário após envio.
     */
    setFormData({
      code: '',
      name: '',
      price: ''
    })
  }

  /**
   * Renderização do formulário.
   *
   * key={editing?.id ?? 'new'}
   * → Força re-render quando muda o produto editado.
   *   Isso garante reset correto dos campos.
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

      {/* Campo preço */}
      <input
        name="price"
        type="number"
        step="0.01"
        min="0"
        placeholder="Price"
        value={formData.price}
        onChange={handleChange}
      />

      {/* Botão principal muda dinamicamente */}
      <ButtonBlue type="submit">
        {editing ? 'Update' : 'Create'}
      </ButtonBlue>

      {/* Botão de cancelar só aparece no modo edição */}
      {editing && (
        <ButtonRed
          type="button"
          onClick={() => setEditing(null)}
        >
          Cancel
        </ButtonRed>
      )}
    </StyledForm>
  )
}
