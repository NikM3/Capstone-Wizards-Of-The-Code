import Navbar from "../../components/Navbar";
import Footer from "../../components/Footer";
import { Link } from "react-router-dom";

function AdminHome() {

    return (
        <div className="container text-center mt-5">
            <h1 className="display-4">Admin Home</h1>
            <p>Welcome to the Admin Dashboard!</p>
            <Link className="btn btn-primary" to={"/admin/users"}>Manage Users</Link>
            <Link className="btn btn-primary" to={"/admin/database"}>Manage Database</Link>
        </div>
    );
}

export default AdminHome;