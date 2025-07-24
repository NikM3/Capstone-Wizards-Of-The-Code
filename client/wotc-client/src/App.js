import './App.css';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Login from './auth/Login';
import Register from './auth/Register';
import Home from './pages/Home'
import ViewCard from './pages/ViewCard';
import NotFound from './pages/NotFound';
import ProtectedRoute from './components/ProtectedRoute';
import AdminHome from './pages/admin/AdminHome';
import Collection from './pages/Collection';
import ViewCollectionCard from './pages/ViewCollectionCard';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="*" element={<NotFound/>} />
        {/* User Views */}
        <Route path="/home" element={ <ProtectedRoute><Home /></ProtectedRoute> } />
        <Route path="/card/:cardId" element={<ProtectedRoute><ViewCard /></ProtectedRoute>} />
        <Route path="/collection" element={<ProtectedRoute><Collection /></ProtectedRoute>} />
        <Route path="/collection/card/:cardId" element={<ProtectedRoute><ViewCollectionCard /></ProtectedRoute>} />
        {/* Admin Views */}
        <Route path="/admin" element={<ProtectedRoute requiredRole="ADMIN"><AdminHome /></ProtectedRoute>} />
      </Routes>
    </Router>
  );
}

export default App;
