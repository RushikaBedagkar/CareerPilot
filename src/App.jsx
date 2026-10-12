import { Routes, Route, Navigate } from "react-router-dom";

import Login from "./pages/Login";
import Register from "./pages/Register";
import Dashboard from "./pages/Dashboard";
import Profile from "./pages/Profile";
import EditProfile from "./pages/EditProfile";
import Jobs from "./pages/Jobs";
import JobDetails from "./pages/JobDetails";
import Applications from "./pages/Applications";
import AdminApplications from "./pages/AdminApplications";
import Resume from "./pages/Resume";
import ResumePreview from "./pages/ResumePreview";

function App() {
  const token = localStorage.getItem("token");

  return (
    <Routes>

      {/* Default Route */}
      <Route
        path="/"
        element={
          token
            ? <Navigate to="/dashboard" replace />
            : <Navigate to="/login" replace />
        }
      />

      {/* Authentication */}
      <Route
        path="/login"
        element={<Login />}
      />

      <Route
        path="/register"
        element={<Register />}
      />

      {/* Dashboard */}
      <Route
        path="/dashboard"
        element={
          token
            ? <Dashboard />
            : <Navigate to="/login" replace />
        }
      />

      {/* Profile */}
      <Route
        path="/profile"
        element={
          token
            ? <Profile />
            : <Navigate to="/login" replace />
        }
      />

      <Route
        path="/profile/edit/:id"
        element={
          token
            ? <EditProfile />
            : <Navigate to="/login" replace />
        }
      />

      {/* Jobs */}
      <Route
        path="/jobs"
        element={
          token
            ? <Jobs />
            : <Navigate to="/login" replace />
        }
      />

      <Route
        path="/jobs/:id"
        element={
          token
            ? <JobDetails />
            : <Navigate to="/login" replace />
        }
      />

      {/* Applications */}
      <Route
        path="/applications"
        element={
          token
            ? <Applications />
            : <Navigate to="/login" replace />
        }
      />

      {/* Admin Applications */}
      <Route
        path="/admin/applications"
        element={
          token
            ? <AdminApplications />
            : <Navigate to="/login" replace />
        }
      />

      {/* Resume */}
      <Route
        path="/resume"
        element={
          token
            ? <Resume />
            : <Navigate to="/login" replace />
        }
      />

      <Route
        path="/resume/preview"
        element={
          token
            ? <ResumePreview />
            : <Navigate to="/login" replace />
        }
      />

      {/* Unknown URL */}
      <Route
        path="*"
        element={<Navigate to="/" replace />}
      />

    </Routes>
  );
}

export default App;