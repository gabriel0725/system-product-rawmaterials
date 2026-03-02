/**
 * Interface que define as propriedades (props) esperadas pelo componente.
 *
 * message   → texto exibido na caixa de confirmação
 * onConfirm → função executada caso o usuário confirme a ação
 *
 * Tipar as props garante:
 * - Segurança de tipos
 * - Autocomplete
 * - Melhor manutenção
 */
interface Props {
  message: string
  onConfirm: () => void
}

/**
 * Componente reutilizável para confirmação de exclusão.
 *
 * Ele exibe um botão "Delete".
 * Ao clicar:
 * 1. Mostra um diálogo nativo do navegador (window.confirm)
 * 2. Se o usuário confirmar, executa a função recebida via props
 *
 * Esse padrão desacopla:
 * - A lógica de confirmação
 * - Da lógica real de exclusão
 *
 * Assim o componente pode ser reutilizado em qualquer tela.
 */
export default function ConfirmDelete({ message, onConfirm }: Props) {

  /**
   * Função executada quando o botão é clicado.
   *
   * Fluxo:
   * - Abre um diálogo de confirmação usando window.confirm
   * - Se o usuário clicar em "OK", chama onConfirm()
   * - Se clicar em "Cancelar", não faz nada
   */
  const handleClick = () => {
    const confirmed = window.confirm(message)

    if (confirmed) {
      onConfirm()
    }
  }

  /**
   * Renderização do botão.
   *
   * onClick → dispara a função handleClick
   * style   → adiciona margem à esquerda para espaçamento visual
   *
   * Observação:
   * window.confirm é uma API nativa do navegador,
   * portanto não depende de nenhuma biblioteca externa.
   */
  return (
    <button onClick={handleClick} style={{ marginLeft: "8px" }}>
      Delete
    </button>
  )
}
