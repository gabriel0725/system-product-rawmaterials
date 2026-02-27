import { BrowserRouter, Routes, Route, Link } from "react-router-dom"
import ProductsPage from "./pages/ProductsPage"
import RawMaterialsPage from "./pages/RawMaterialsPage"
import ProductionPage from "./pages/ProductionPage"


function App() {
  return (
    <BrowserRouter>
      <nav>
        <Link to="/products">Products</Link>
        <Link to="/materials">Raw-material</Link>
        <Link to="/production">Production</Link>
      </nav>

      <Routes>
        <Route path="/products" element={<ProductsPage />} />
        <Route path="/materials" element={<RawMaterialsPage />} />
        <Route path="/production" element={<ProductionPage />} />
      </Routes>
    </BrowserRouter>
  )
}

export default App
