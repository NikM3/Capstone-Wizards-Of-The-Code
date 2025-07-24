import { useAuth } from "../AuthContext";
import { Navigate } from "react-router-dom";

function ProtectedRoute({ children, requiredRole }) {
    const { user } = useAuth();


    if (!user) {
        return <Navigate to="/" replace />;
    }

    if (requiredRole && user?.roles?.[0]?.authority !== requiredRole) {
        // maybe redirect to an unauthorized page
        return <Navigate to="/home" replace />;
    }

    return children;
} 

export default ProtectedRoute;