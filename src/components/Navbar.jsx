import { useNavigate } from "react-router-dom";
import "./Navbar.css";

function Navbar() {
  const navigate = useNavigate();

  const user = JSON.parse(localStorage.getItem("user") || "{}");

  const handleLogout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("user");

    navigate("/login");
  };

  return (
    <nav className="navbar">

      <div
        className="navbar-logo"
        onClick={() => navigate("/dashboard")}
      >
        Career<span>Pilot</span>
      </div>

      <div className="navbar-links">

        <button onClick={() => navigate("/dashboard")}>
          Dashboard
        </button>

        <button onClick={() => navigate("/jobs")}>
          Jobs
        </button>

        <button onClick={() => navigate("/applications")}>
          Applications
        </button>

        <button onClick={() => navigate("/resume")}>
          Resume
        </button>

        <button onClick={() => navigate("/profile")}>
          Profile
        </button>

      </div>

      <div className="navbar-user">

        <div className="user-info">
          <span className="user-name">
            {user.name || "Student"}
          </span>

          <span className="user-role">
            {user.role || "STUDENT"}
          </span>
        </div>

        <button
          className="logout-button"
          onClick={handleLogout}
        >
          Logout
        </button>

      </div>

    </nav>
  );
}

export default Navbar;