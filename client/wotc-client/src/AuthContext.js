import { createContext, useContext, useState, useEffect } from 'react';
import parseJwt from './utils/jwtUtils';

const AuthContext = createContext();

export function AuthProvider({ children }) {

    const [user, setUser] = useState(() => {
        const token = localStorage.getItem('token');
        if (!token) return null;
        const payload = parseJwt(token);
        return payload || null;
    });

    const login = (token) => {
        localStorage.setItem('token', token);
        const payload = parseJwt(token);
        if(payload) {
            localStorage.setItem('email', payload.sub)
            localStorage.setItem('role', payload?.roles?.[0]?.authority)
        }
        setUser(payload);
    };

    const logout = () => {
        localStorage.removeItem('token');
        localStorage.removeItem('user');
        localStorage.removeItem('role');
        localStorage.removeItem('email');
        setUser(null);
    };

    return (
        <AuthContext.Provider value={{ user, login, logout }}>
            {children}
        </AuthContext.Provider>
    );
}


export const useAuth = () => useContext(AuthContext);