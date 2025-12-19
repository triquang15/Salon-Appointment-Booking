import { ThemeProvider } from '@mui/material/styles';
import './App.css';
import greenTheme from './theme/greenTheme';
import Home from './Customer/Home/Home';
import SalonDetails from './Salon/SalonDetails/SalonDetails';

function App() {
  return (
    <ThemeProvider theme={greenTheme}>
        {/* <Home /> */}
        <SalonDetails />
    </ThemeProvider>
  );
}

export default App;
