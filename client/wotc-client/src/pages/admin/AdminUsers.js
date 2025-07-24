import { useEffect, useState } from "react";
import { Link } from "react-router-dom";

function AdminUsers() {
    // STATE
    const [users, setUsers] = useState([]);
    const url = "http:/localhost:8080/api/admin"

    
    useEffect(() => {
        fetch(`${url}/findAll`)
        .then((response) => {
            if (response.status === 200) {
            return response.json();
            } else {
            return Promise.reject(`Unexpected Status Code: ${response.status}`);
            }
        })
        .then((data) => setUsers(data))
        .catch(console.log);
    }, []); 

    const handleDeleteUser = (userId) => {

    }

    return (
        <>
      <section className="container">
        <h2 className="mb-4">Search bar goes here?</h2>
        <table className="table table-stripped table-hover">
          <thead className="thead-dark">
            <tr>
              <th>Email</th>
              <th>Username</th>
              <th>Status</th>
              <th>Update</th>
              <th>Delete</th>
            </tr>
          </thead>
          <tbody>
            {users.map((user) => (
              <tr key={user.user_id}>
                <td>{user.email}</td>
                <td>{user.username}</td>
                <td>{user.restricted ? "Restricted" : "Not Restricted"}</td>
                <td>
                  <Link
                    className="btn btn-outline-warning mr-4"
                    to={`/`}
                  >
                    Update
                  </Link>
                </td>

                <td>
                  <button
                    className="btn btn-outline-danger"
                    onClick={() => handleDeleteUser(user.user_id)}
                  >
                    Delete
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </section>
    </>
    );
}

export default AdminUsers;