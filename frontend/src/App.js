import { ThemeProvider } from '@mui/material/styles'
import { Routes, Route } from 'react-router-dom'
import greenTheme from './theme/greenTheme'

import SalonDashboard from './Salon/SalonDashboard'
import BookingTable from './Salon/Bookings/BookingTable'
import NotFound from './ErrorPage/NotFound'
import CustomerRoutes from './Routes/CustomerRoutes'
import ServiceTable from './Salon/Service/ServiceTable'
import AddService from './Salon/Service/AddService'
import Transaction from './Salon/Transaction/Transaction'
import CategoryPage from './Salon/Category/CategoryPage'
import Notifications from './Salon/Notification/Notifications'
import Profile from './Salon/Profile/Profile'
import Payment from './Salon/Payment/Payment'

function App() {
  return (
    <ThemeProvider theme={greenTheme}>
      <Routes>

        {/* ADMIN ROUTES */}
        <Route path="/salon-dashboard" element={<SalonDashboard />}>
          <Route index element={<BookingTable />} /> {/* default */}
          <Route path="bookings" element={<BookingTable />} />
          <Route path="services" element={<ServiceTable />} />
          <Route path="add-service" element={<AddService />} />
          <Route path="transactions" element={<Transaction />} />
          <Route path="categories" element={<CategoryPage />} />
          <Route path="notifications" element={<Notifications />} />
          <Route path="profile" element={<Profile />} />
          <Route path="payment" element={<Payment />} />

          <Route path="*" element={<NotFound />} />
        </Route>

        {/* CUSTOMER ROUTES */}
        <Route path="*" element={<CustomerRoutes />} />

      </Routes>
    </ThemeProvider>
  )
}

export default App
