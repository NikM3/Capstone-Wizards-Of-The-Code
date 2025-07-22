import './App.css';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Login from './auth/Login';
import Register from './auth/Register';
import Home from './pages/Home'
import ViewCard from './pages/ViewCard';
import NotFound from './pages/NotFound';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/home" element={<Home />} />
        <Route path="/view-card" element={<ViewCard />} />
        <Route path="*" element={<NotFound/>} />
      </Routes>
    </Router>
  );
}

export default App;
