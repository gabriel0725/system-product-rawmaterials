interface Props {
  message: string
  onConfirm: () => void
}

export default function ConfirmDelete({ message, onConfirm }: Props) {
  const handleClick = () => {
    const confirmed = window.confirm(message)
    if (confirmed) {
      onConfirm()
    }
  }

  return (
    <button onClick={handleClick} style={{ marginLeft: "8px" }}>
      Delete
    </button>
  )
}
