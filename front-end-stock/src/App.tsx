import { BrowserRouter, Routes, Route } from "react-router-dom"
import { GlobalStyle } from "./styles/GlobalStyles"
import { Nav, NavItem } from "./styles/Navbar"

import ProductsPage from "./pages/ProductsPage"
import RawMaterialsPage from "./pages/RawMaterialsPage"
import ProductionPage from "./pages/ProductionPage"

function App() {
  return (
    <BrowserRouter>
      <GlobalStyle />

      <Nav>
        <NavItem to="/products">Products</NavItem>
        <NavItem to="/materials">Raw Materials</NavItem>
        <NavItem to="/production">Production</NavItem>
      </Nav>

      <Routes>
        <Route path="/products" element={<ProductsPage />} />
        <Route path="/materials" element={<RawMaterialsPage />} />
        <Route path="/production" element={<ProductionPage />} />
      </Routes>
    </BrowserRouter>
  )
}

export default App
