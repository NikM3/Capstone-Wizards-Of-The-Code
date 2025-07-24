import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import Navbar from "../../components/Navbar";

function AdminDatabase() {
  const url = "http://localhost:8080/api/card/database";

  const handleDatabaseUpdate = () => {
    const token = localStorage.getItem("token");
    const init = {
      method: `POST`,
      headers: {
        Authorization: `Bearer ${token}`,
      },
    };
    fetch(url, init).then((response) => {
      if (response.status === 200) {
        return response.json();
      } else {
        return Promise.reject(`Unexpected Status Code: ${response.status}`);
      }
    });
  };

  return (
    <>
      <Navbar />
      <div className="container text-center mt-5">
        <h1 className="display-4">Admin Database</h1>
        <p>Please only update the database once a day!</p>
        <button className="btn btn-primary" onClick={handleDatabaseUpdate}>
          Update Database
        </button>
      </div>
    </>
  );
}

export default AdminDatabase;
