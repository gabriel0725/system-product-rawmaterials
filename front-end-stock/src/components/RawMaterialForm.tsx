import { useState, useEffect } from 'react'
import { useAppDispatch } from '../app/hooks'
import {
  createRawMaterial,
  updateRawMaterial
} from '../features/rawMaterial/rawMaterialSlice'
import type { RawMaterial } from '../features/rawMaterial/types'
import { ButtonRed, ButtonBlue, StyledForm } from '../styles/Layout'

interface Props {
  editing: RawMaterial | null
  setEditing: (value: RawMaterial | null) => void
}

export default function RawMaterialForm({ editing, setEditing }: Props) {
  const dispatch = useAppDispatch()

  const [formData, setFormData] = useState({
    code: '',
    name: '',
    stockQuantity: '' //string para permitir campo vazio
  })

  //Sincroniza o form com o raw material selecionado para edição
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

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target
    setFormData((prev) => ({
      ...prev,
      [name]: value
    }))
  }

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault()

    const stockNumber =
      formData.stockQuantity.trim() === '' ? 0 : Number(formData.stockQuantity)

    const rawMaterialToSubmit = {
      code: formData.code,
      name: formData.name,
      stockQuantity: stockNumber
    }

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

    setFormData({ code: '', name: '', stockQuantity: '' })
  }

  return (
    <StyledForm
      key={editing?.id ?? 'new'}
      onSubmit={handleSubmit}
      style={{ marginBottom: '20px' }}
    >
      <input
        name="code"
        placeholder="Code"
        value={formData.code}
        onChange={handleChange}
        required
      />
      <input
        name="name"
        placeholder="Name"
        value={formData.name}
        onChange={handleChange}
        required
      />
      <input
        name="stockQuantity"
        type="number"
        placeholder="Stock Quantity"
        min={0}
        value={formData.stockQuantity}
        onChange={handleChange}
      />
      <ButtonBlue type="submit">{editing ? 'Update' : 'Create'}</ButtonBlue>
      {editing && (
        <ButtonRed type="button" onClick={() => setEditing(null)}>
          Cancel
        </ButtonRed>
      )}
    </StyledForm>
  )
}
