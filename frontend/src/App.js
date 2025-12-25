import { ThemeProvider } from '@mui/material/styles'
import { Routes, Route } from 'react-router-dom'
import './App.css'

import greenTheme from './theme/greenTheme'
import SalonDashboard from './Salon/SalonDashboard'
import CustomerRoutes from './Routes/CustomerRoutes'

function App() {
  return (
    <ThemeProvider theme={greenTheme}>
      <Routes>
        <Route path="/salon-dashboard/*" element={<SalonDashboard />} />
        <Route path="*" element={<CustomerRoutes />} />
      </Routes>

    </ThemeProvider>
  )
}

export default App
