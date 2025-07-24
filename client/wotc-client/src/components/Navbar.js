import {  useAuth } from "../AuthContext";
import { Link } from "react-router-dom";
import { useNavigate } from "react-router-dom";
function Navbar() {

    const {user, logout} = useAuth();
    const navigate = useNavigate();

    const handleLogout = () => {
        console.log("Logging out user:");
        logout();
        navigate('/');
    }

    return (
        <>
            <nav className="navbar navbar-light bg-purple text-white justify-content-between">
                <div className="d-flex px-5 my-1 align-items-center">
                    <Link to={'/home'} className="navbar-brand text-white">
                        <h2 className=" mb-0 mr-4 ">Arcane Archive</h2>
                    </Link>
                    <ul className="nav">
                        <li className="nav-item ">
                            <Link to={'/home'} className="mx-4 nav-link text-white link-size">Home</Link>
                        </li>
                        <li className="nav-item ">
                            <Link to={'/collection'} className="nav-link text-white link-size">My Collection</Link>
                        </li>
                        <li className="nav-item ">
                            {user?.roles?.[0]?.authority === 'ADMIN' && (
                                <Link to={'/admin'} className="nav-link text-white link-size">Admin</Link>
                            )}
                        </li>
                    </ul>

                </div>
                <div className="d-flex align-items-center">
                    <p className="mb-0 mx-3 text-white" >{user.sub}</p>
                    
                    <p className="btn btn-lg bg-blue text-white mt-2 mx-3" onClick={handleLogout}>Logout</p>
                </div>
            </nav>
        </>
    )
}

export default Navbar;