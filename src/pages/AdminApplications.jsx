import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

function AdminApplications() {
  const navigate = useNavigate();

  const [applications, setApplications] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const token = localStorage.getItem("token");

  useEffect(() => {
    if (!token) {
      navigate("/login");
      return;
    }

    loadApplications();
  }, [navigate]);

  const loadApplications = async () => {
    try {
      const response = await fetch(
        "http://localhost:8080/api/applications",
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      if (!response.ok) {
        const text = await response.text();
        throw new Error(text || "Unable to load applications");
      }

      const data = await response.json();

      console.log("Admin applications:", data);

      setApplications(data);
    } catch (err) {
      console.error("Admin applications error:", err);
      setError("Unable to load applications.");
    } finally {
      setLoading(false);
    }
  };

  const updateStatus = async (id, status) => {
    try {
      const response = await fetch(
        `http://localhost:8080/api/applications/${id}/status?status=${status}`,
        {
          method: "PUT",
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      if (!response.ok) {
        const text = await response.text();
        throw new Error(text || "Unable to update status");
      }

      const updatedApplication = await response.json();

      setApplications((previous) =>
        previous.map((application) =>
          application.id === updatedApplication.id
            ? updatedApplication
            : application
        )
      );
    } catch (err) {
      console.error("Status update error:", err);
      alert("Unable to update application status.");
    }
  };

  if (loading) {
    return (
      <div className="admin-applications-page">
        <h2>Loading applications...</h2>
      </div>
    );
  }

  return (
    <div className="admin-applications-page">

      <div className="admin-header">
        <div>
          <h1>Application Management</h1>
          <p>Review and manage student job applications.</p>
        </div>

        <button
          className="secondary-btn"
          onClick={() => navigate("/dashboard")}
        >
          ← Dashboard
        </button>
      </div>

      {error && (
        <div className="error-message">
          {error}
        </div>
      )}

      {applications.length === 0 ? (
        <div className="empty-state">
          <h2>No applications yet</h2>
          <p>
            Student applications will appear here.
          </p>
        </div>
      ) : (
        <div className="applications-table-container">

          <table className="applications-table">

            <thead>
              <tr>
                <th>ID</th>
                <th>Student Email</th>
                <th>Job ID</th>
                <th>Applied Date</th>
                <th>Status</th>
                <th>Action</th>
              </tr>
            </thead>

            <tbody>

              {applications.map((application) => (

                <tr key={application.id}>

                  <td>
                    #{application.id}
                  </td>

                  <td>
                    {application.studentEmail}
                  </td>

                  <td>
                    #{application.jobId}
                  </td>

                  <td>
                    {application.appliedDate}
                  </td>

                  <td>
                    <span
                      className={`status status-${application.status.toLowerCase()}`}
                    >
                      {application.status}
                    </span>
                  </td>

                  <td>

                    <div className="admin-actions">

                      <button
                        className="success-btn"
                        disabled={
                          application.status === "ACCEPTED"
                        }
                        onClick={() =>
                          updateStatus(
                            application.id,
                            "ACCEPTED"
                          )
                        }
                      >
                        Accept
                      </button>

                      <button
                        className="danger-btn"
                        disabled={
                          application.status === "REJECTED"
                        }
                        onClick={() =>
                          updateStatus(
                            application.id,
                            "REJECTED"
                          )
                        }
                      >
                        Reject
                      </button>

                    </div>

                  </td>

                </tr>

              ))}

            </tbody>

          </table>

        </div>
      )}

    </div>
  );
}

export default AdminApplications;