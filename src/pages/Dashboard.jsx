import { useNavigate } from "react-router-dom";
import Navbar from "../components/Navbar";
import "./Dashboard.css";

function Dashboard() {
  const navigate = useNavigate();

  const user = JSON.parse(
    localStorage.getItem("user") || "{}"
  );

  return (
    <div className="dashboard-page">

      <Navbar />

      <main className="dashboard-container">

        {/* Welcome Section */}

        <section className="welcome-section">

          <div>
            <p className="welcome-small">
              Welcome back 👋
            </p>

            <h1>
              Hello, {user.name || "Student"}!
            </h1>

            <p className="welcome-description">
              Find the right opportunities and build
              your career with CareerPilot.
            </p>
          </div>

          <button
            className="profile-button"
            onClick={() => navigate("/profile")}
          >
            View Profile
          </button>

        </section>


        {/* Statistics */}

        <section className="stats-grid">

          <div className="stat-card">

            <div className="stat-icon">
              💼
            </div>

            <div>
              <h3>Available Jobs</h3>
              <p>Explore opportunities</p>
            </div>

          </div>


          <div className="stat-card">

            <div className="stat-icon">
              📄
            </div>

            <div>
              <h3>Applications</h3>
              <p>Track your applications</p>
            </div>

          </div>


          <div className="stat-card">

            <div className="stat-icon">
              📝
            </div>

            <div>
              <h3>Resume</h3>
              <p>Build your resume</p>
            </div>

          </div>

        </section>


        {/* Main Features */}

        <h2 className="section-title">
          Career Tools
        </h2>


        <section className="feature-grid">

          <div
            className="feature-card"
            onClick={() => navigate("/jobs")}
          >

            <div className="feature-icon">
              💼
            </div>

            <h3>Find Jobs</h3>

            <p>
              Discover internships and job
              opportunities that match your skills.
            </p>

            <button>
              Explore Jobs →
            </button>

          </div>


          <div
            className="feature-card"
            onClick={() => navigate("/applications")}
          >

            <div className="feature-icon">
              📋
            </div>

            <h3>My Applications</h3>

            <p>
              Track your submitted applications
              and their current status.
            </p>

            <button>
              View Applications →
            </button>

          </div>


          <div
            className="feature-card"
            onClick={() => navigate("/resume")}
          >

            <div className="feature-icon">
              📄
            </div>

            <h3>Resume</h3>

            <p>
              Manage your resume and keep your
              professional profile updated.
            </p>

            <button>
              Manage Resume →
            </button>

          </div>


          <div
            className="feature-card"
            onClick={() => navigate("/profile")}
          >

            <div className="feature-icon">
              👤
            </div>

            <h3>My Profile</h3>

            <p>
              Update your education, skills,
              GitHub and LinkedIn information.
            </p>

            <button>
              Manage Profile →
            </button>

          </div>

        </section>


        {/* Quick Action */}

        <section className="quick-section">

          <div>

            <h2>
              Ready for your next opportunity?
            </h2>

            <p>
              Start exploring jobs and internships
              today.
            </p>

          </div>

          <button
            onClick={() => navigate("/jobs")}
          >
            Find Opportunities
          </button>

        </section>

      </main>

    </div>
  );
}

export default Dashboard;