import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import "./Applications.css";

function Applications() {
  const navigate = useNavigate();

  const [applications, setApplications] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    const token = localStorage.getItem("token");

    if (!token) {
      navigate("/login");
      return;
    }

    fetch("http://localhost:8080/api/applications/my", {
      method: "GET",
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
    })
      .then(async (response) => {
        if (!response.ok) {
          throw new Error(await response.text());
        }

        return response.json();
      })
      .then((data) => {
        console.log("My applications:", data);
        setApplications(data);
      })
      .catch((err) => {
        console.error("Applications error:", err);
        setError("Unable to load applications.");
      })
      .finally(() => {
        setLoading(false);
      });
  }, [navigate]);

  if (loading) {
    return (
      <div className="applications-page">
        <div className="applications-loading">
          <div className="application-spinner"></div>
          <h2>Loading applications...</h2>
        </div>
      </div>
    );
  }

  return (
    <div className="applications-page">

      <div className="applications-container">

        <button
          className="back-dashboard-btn"
          onClick={() => navigate("/dashboard")}
        >
          ← Back to Dashboard
        </button>

        <div className="applications-header">
          <div>
            <h1>My Applications</h1>
            <p>Track all your job applications in one place.</p>
          </div>

          <div className="application-count">
            <span>{applications.length}</span>
            <small>Applications</small>
          </div>
        </div>

        {error && (
          <div className="applications-error">
            <strong>{error}</strong>

            <button onClick={() => window.location.reload()}>
              Try Again
            </button>
          </div>
        )}

        {applications.length === 0 && !error && (
          <div className="no-applications">

            <div className="empty-icon">
              📄
            </div>

            <h2>No applications yet</h2>

            <p>
              You haven't applied for any jobs yet.
              Start exploring available opportunities.
            </p>

            <button
              className="browse-jobs-btn"
              onClick={() => navigate("/jobs")}
            >
              Browse Jobs
            </button>

          </div>
        )}

        {applications.length > 0 && (
          <div className="applications-list">

            {applications.map((application) => (

              <div
                className="application-card"
                key={application.id}
              >

                <div className="application-card-top">

                  <div className="application-icon">
                    💼
                  </div>

                  <div className="application-title">
                    <h2>
                      Application #{application.id}
                    </h2>

                    <p>
                      Job ID: {application.jobId}
                    </p>
                  </div>

                  <span
                    className={`status status-${(
                      application.status || ""
                    ).toLowerCase()}`}
                  >
                    {application.status}
                  </span>

                </div>

                <div className="application-details">

                  <div className="application-detail">
                    <span>📌</span>
                    <div>
                      <small>Job ID</small>
                      <strong>
                        {application.jobId}
                      </strong>
                    </div>
                  </div>

                  <div className="application-detail">
                    <span>📅</span>
                    <div>
                      <small>Applied Date</small>
                      <strong>
                        {application.appliedDate}
                      </strong>
                    </div>
                  </div>

                  <div className="application-detail">
                    <span>📊</span>
                    <div>
                      <small>Status</small>
                      <strong>
                        {application.status}
                      </strong>
                    </div>
                  </div>

                </div>

                <div className="application-card-footer">

                  <button
                    onClick={() =>
                      navigate(`/jobs/${application.jobId}`)
                    }
                  >
                    View Job
                  </button>

                </div>

              </div>

            ))}

          </div>
        )}

      </div>

    </div>
  );
}

export default Applications;