export interface ProductProduction {
  productId?: number
  productName: string;
  quantityPossible: number;
  unitPrice: number;
  totalValue: number;
}

export interface ProductionResponse {
  products: ProductProduction[];
  grandTotal: number;
}
