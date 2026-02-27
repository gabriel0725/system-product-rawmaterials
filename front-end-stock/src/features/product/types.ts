export interface ProductMaterialDTO {
  rawMaterialId: number
  rawMaterialName: string
  requiredQuantity: number
}

export interface Product {
  id?: number
  code: string
  name: string
  price: number
  materials?: ProductMaterialDTO[]
}
