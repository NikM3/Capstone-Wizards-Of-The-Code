import Navbar from "../../components/Navbar";
import Footer from "../../components/Footer";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

function AdminHome() {
  const url = "http://localhost:8080/api/admin";
  const [users, setUsers] = useState([]);
  const navigate = useNavigate;

  useEffect(() => {
    const token = localStorage.getItem("token");
    fetch(`${url}/findAll`, {
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
    })
      .then((resp) => {
        if (resp.status === 200) {
          return resp.json();
        } else {
          return Promise.reject(`Unexpected ERROR Code: ${resp.status}`);
        }
      })
      .then((data) => setUsers(data))
      .catch(console.log);
  }, []);

  const handleDelete = (user) => {
    const token = localStorage.getItem("token");
    if (window.confirm(`Are you sure you want to restrict: ${user.email}?`)) {
      const init = {
        method: "DELETE",
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
      };
      fetch(`${url}/${user.userId}`, init)
        .then((resp) => {
          if (resp.status === 204) {
            //navigate(`/admin`);
            window.location.reload();
          } else {
            return Promise.reject(`Unexpected ERROR Code: ${resp.status}`);
          }
        })
        .catch(console.log);
    }
  };

  return (
    <>
      <Navbar />
      <div className="container text-center mt-5">
        <h1 className="display-4">Admin Home</h1>
        <p>Welcome to the Admin Dashboard!</p>
        <section className="container">
          <h3>Users List</h3>

          <table className="table table-striped table-hover">
            <thead className="table-dark">
              <tr>
                <th>Username</th>
                <th>Email</th>
                <th>Restricted?</th>
                <th>Role</th>
                <th>&nbsp;</th>
              </tr>
            </thead>
            <tbody>
              {users.map((user) => (
                <tr key={user.userId}>
                  <td>{user.username}</td>
                  <td>{user.email}</td>
                  <td>
                    {user.restricted === false ? "Unrestricted" : "Restricted"}
                  </td>
                  <td>{user.role}</td>
                  <td>
                    <button
                      className="btn btn-danger mr-2"
                      onClick={() => handleDelete(user)}
                    >
                      Restrict
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </section>
      </div>
      <Footer />
    </>
  );
}

export default AdminHome;
