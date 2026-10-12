import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

function EditProfile() {
  const navigate = useNavigate();
  const { id } = useParams();

  const [formData, setFormData] = useState({
    name: "",
    email: "",
    phone: "",
    college: "",
    degree: "",
    graduationYear: "",
    cgpa: "",
    githubUrl: "",
    linkedinUrl: "",
    resumeUrl: "",
  });

  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState("");

  // Load existing profile
  useEffect(() => {
    const token = localStorage.getItem("token");

    if (!token) {
      navigate("/login");
      return;
    }

    fetch(`http://localhost:8080/api/students/${id}`, {
      method: "GET",
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
      .then(async (response) => {
        if (!response.ok) {
          const message = await response.text();
          throw new Error(message || "Failed to load profile");
        }

        return response.json();
      })
      .then((data) => {
        setFormData({
          name: data.name || "",
          email: data.email || "",
          phone: data.phone || "",
          college: data.college || "",
          degree: data.degree || "",
          graduationYear: data.graduationYear || "",
          cgpa: data.cgpa || "",
          githubUrl: data.githubUrl || "",
          linkedinUrl: data.linkedinUrl || "",
          resumeUrl: data.resumeUrl || "",
        });
      })
      .catch((error) => {
        console.error("Profile loading error:", error);
        setError("Unable to load profile.");
      })
      .finally(() => {
        setLoading(false);
      });
  }, [id, navigate]);

  // Handle input changes
  const handleChange = (e) => {
    const { name, value } = e.target;

    setFormData((previous) => ({
      ...previous,
      [name]: value,
    }));
  };

  // Update profile
  const handleSubmit = async (e) => {
    e.preventDefault();

    const token = localStorage.getItem("token");

    if (!token) {
      navigate("/login");
      return;
    }

    setSaving(true);
    setError("");

    try {
      const response = await fetch(
        `http://localhost:8080/api/students/${id}`,
        {
          method: "PUT",
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            ...formData,
            graduationYear: formData.graduationYear
              ? Number(formData.graduationYear)
              : null,
            cgpa: formData.cgpa
              ? Number(formData.cgpa)
              : null,
          }),
        }
      );

      if (!response.ok) {
        const message = await response.text();
        throw new Error(message || "Failed to update profile");
      }

      const updatedProfile = await response.json();

      console.log("Updated profile:", updatedProfile);

      alert("Profile updated successfully!");

      navigate("/profile");
    } catch (error) {
      console.error("Update error:", error);
      setError("Unable to update profile.");
    } finally {
      setSaving(false);
    }
  };

  if (loading) {
    return (
      <div>
        <h2>Loading profile...</h2>
      </div>
    );
  }

  if (error) {
    return (
      <div>
        <h2>{error}</h2>

        <button onClick={() => navigate("/profile")}>
          Back to Profile
        </button>
      </div>
    );
  }

  return (
    <div className="edit-profile-page">

      <button onClick={() => navigate("/profile")}>
        ← Back to Profile
      </button>

      <h1>Edit Profile</h1>

      <form onSubmit={handleSubmit}>

        <div>
          <label>Name</label>
          <input
            type="text"
            name="name"
            value={formData.name}
            onChange={handleChange}
            required
          />
        </div>

        <div>
          <label>Email</label>
          <input
            type="email"
            name="email"
            value={formData.email}
            onChange={handleChange}
            required
          />
        </div>

        <div>
          <label>Phone</label>
          <input
            type="text"
            name="phone"
            value={formData.phone}
            onChange={handleChange}
          />
        </div>

        <div>
          <label>College</label>
          <input
            type="text"
            name="college"
            value={formData.college}
            onChange={handleChange}
          />
        </div>

        <div>
          <label>Degree</label>
          <input
            type="text"
            name="degree"
            value={formData.degree}
            onChange={handleChange}
          />
        </div>

        <div>
          <label>Graduation Year</label>
          <input
            type="number"
            name="graduationYear"
            value={formData.graduationYear}
            onChange={handleChange}
          />
        </div>

        <div>
          <label>CGPA</label>
          <input
            type="number"
            step="0.01"
            name="cgpa"
            value={formData.cgpa}
            onChange={handleChange}
          />
        </div>

        <div>
          <label>GitHub URL</label>
          <input
            type="url"
            name="githubUrl"
            value={formData.githubUrl}
            onChange={handleChange}
          />
        </div>

        <div>
          <label>LinkedIn URL</label>
          <input
            type="url"
            name="linkedinUrl"
            value={formData.linkedinUrl}
            onChange={handleChange}
          />
        </div>

        <div>
          <label>Resume URL</label>
          <input
            type="text"
            name="resumeUrl"
            value={formData.resumeUrl}
            onChange={handleChange}
          />
        </div>

        {error && <p>{error}</p>}

        <button type="submit" disabled={saving}>
          {saving ? "Saving..." : "Save Changes"}
        </button>

      </form>
    </div>
  );
}

export default EditProfile;