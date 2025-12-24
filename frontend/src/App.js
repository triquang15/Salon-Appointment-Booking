import { ThemeProvider } from '@mui/material/styles';
import './App.css';
import greenTheme from './theme/greenTheme';
import Home from './Customer/Home/Home';
import SalonDetails from './Salon/SalonDetails/SalonDetails';
import Booking from './Customer/Booking/Booking';
import Notification from './Customer/Notification/Notification';
import Navbar from './Customer/Navbar/Navbar';

function App() {
  return (
    <ThemeProvider theme={greenTheme}>
      <Navbar />
      <Home />
      {/* <SalonDetails /> */}
      {/* <Booking /> */}
      {/* <Notification /> */}
    </ThemeProvider>
  );
}

export default App;
