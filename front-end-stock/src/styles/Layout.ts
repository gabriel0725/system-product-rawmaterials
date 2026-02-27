import styled from 'styled-components'

export const Container = styled.div`
  max-width: 1100px;
  margin: 40px auto;
  padding: 20px;
`

export const Card = styled.div`
  background: white;
  padding: 24px;
  border-radius: 12px;
  margin-bottom: 24px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.04);
`

export const Title = styled.h1`
  margin-bottom: 24px;
  font-size: 28px;
`

export const Table = styled.table`
  width: 100%;
  border-collapse: collapse;
  background: white;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.04);

  @media (max-width: 768px) {
    border-radius: 0;
    box-shadow: none;
  }
`

export const Thead = styled.thead`
  background: #f3f4f6;

  @media (max-width: 768px) {
    display: none;
  }
`

export const Th = styled.th`
  padding: 14px 16px;
  text-align: left;
  font-size: 14px;
  text-transform: uppercase;
  color: #6b7280;
`

export const Td = styled.td`
  padding: 14px 16px;

  @media (max-width: 768px) {
    display: flex;
    justify-content: space-between;
    padding: 6px 0;
    border: none;

    &::before {
      font-weight: 600;
      margin-right: 10px;
    }

    &:nth-child(1)::before {
      content: "Code";
    }
    &:nth-child(2)::before {
      content: "Name";
    }
    &:nth-child(3)::before {
      content: "Price";
    }
    &:nth-child(4)::before {
      content: "Materials";
    }
    &:nth-child(5)::before {
      content: "Actions";
    }
  }
`

export const TdMaterials = styled(Td)`
   @media (max-width: 768px) {
    display: block;
   }
`

export const Tr = styled.tr`
  border-bottom: 1px solid #e5e7eb;

  @media (max-width: 768px) {
    display: block;
    background: white;
    margin-bottom: 16px;
    padding: 16px;
    border-radius: 12px;
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.05);
  }
`

export const ButtonBlue = styled.button`
  padding: 8px 14px;
  border-radius: 6px;
  border: none;
  background: #3b82f6;
  color: white;
  cursor: pointer;
  margin-right: 6px;

  &:hover {
    opacity: 0.85;
  }

  @media (max-width: 768px) {
    width: 100%;
    margin-top: 10px;
  }
`

export const ButtonRed = styled(ButtonBlue)`
  background: #ef4444;
`

export const ButtonGreen = styled(ButtonBlue)`
  background: #3aa824;
`

export const MaterialsSection = styled.div`
  margin-bottom: 20px;
`

export const MaterialsButtons = styled.div`
  display: flex;
  gap: 10px;
  flex-wrap: wrap;
  margin-bottom: 12px;

  @media (max-width: 768px) {
    button {
      flex: 1 1 45%;
    }
  }
`

export const SelectedMaterialRow = styled.div`
  display: flex;
  align-items: center;
  gap: 10px;
  margin-top: 8px;

  input {
    width: 100px;
    padding: 6px;
  }

  @media (max-width: 768px) {
    flex-direction: column;
    align-items: stretch;
    background: #f9fafb;
    padding: 12px;
    border-radius: 10px;

    input,
    button {
      width: 100%;
    }
  }
`

export const StyledForm = styled.form`
  display: flex;
  gap: 12px;

  input {
    flex: 1;
    padding: 8px;
  }

  /* MOBILE */
  @media (max-width: 768px) {
    flex-direction: column;

    input,
    button {
      width: 100%;
    }
  }
`
export const MaterialRow = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;

  @media (max-width: 768px) {
    width: 100%;
    margin: 4px 0;
  }
`

export const MaterialName = styled.span`
  font-weight: 500;

  @media (max-width: 768px) {
    font-weight: normal;
  }
`

export const MaterialQuantity = styled.span`
  font-weight: 600;
`
