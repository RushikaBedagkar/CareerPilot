import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import "./JobDetails.css";

function JobDetails() {
  const { id } = useParams();
  const navigate = useNavigate();

  const [job, setJob] = useState(null);

  const [loading, setLoading] = useState(true);
  const [applying, setApplying] = useState(false);

  const [message, setMessage] = useState("");
  const [error, setError] = useState("");

  useEffect(() => {
    const fetchJob = async () => {
      const token = localStorage.getItem("token");

      if (!token) {
        navigate("/login", { replace: true });
        return;
      }

      try {
        setLoading(true);
        setError("");

        const response = await fetch(
          `http://localhost:8080/api/jobs/${id}`,
          {
            method: "GET",
            headers: {
              Authorization: `Bearer ${token}`,
              "Content-Type": "application/json",
            },
          }
        );

        // Unauthorized
        if (
          response.status === 401 ||
          response.status === 403
        ) {
          localStorage.removeItem("token");
          localStorage.removeItem("user");

          navigate("/login", { replace: true });
          return;
        }

        if (!response.ok) {
          const text = await response.text();

          throw new Error(
            text || "Unable to load job details."
          );
        }

        const data = await response.json();

        console.log("Job details:", data);

        setJob(data);

      } catch (err) {
        console.error("Job details error:", err);

        setError(
          "Unable to load job details. Please try again."
        );

      } finally {
        setLoading(false);
      }
    };

    fetchJob();

  }, [id, navigate]);


  // APPLY FOR JOB

  const handleApply = async () => {

    const token =
      localStorage.getItem("token");

    if (!token) {
      navigate("/login", { replace: true });
      return;
    }

    setApplying(true);
    setMessage("");
    setError("");

    try {

      const response = await fetch(
        "http://localhost:8080/api/applications",
        {
          method: "POST",

          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
          },

          body: JSON.stringify({
            jobId: Number(id),
          }),
        }
      );


      // Unauthorized
      if (
        response.status === 401 ||
        response.status === 403
      ) {
        localStorage.removeItem("token");
        localStorage.removeItem("user");

        navigate("/login", { replace: true });
        return;
      }


      // Read response safely
      let data = {};

      const contentType =
        response.headers.get("content-type");

      if (
        contentType &&
        contentType.includes("application/json")
      ) {
        data = await response.json();
      } else {
        const text = await response.text();

        data = {
          message: text,
        };
      }


      if (!response.ok) {

        throw new Error(
          data.message ||
          "Unable to apply for this job."
        );

      }


      console.log(
        "Application response:",
        data
      );


      setMessage(
        "Application submitted successfully!"
      );

    } catch (err) {

      console.error(
        "Application error:",
        err
      );

      setError(
        err.message ||
        "Unable to submit application."
      );

    } finally {

      setApplying(false);

    }
  };


  // LOADING

  if (loading) {

    return (
      <div className="job-details-page">

        <div className="job-details-loading">

          <div className="job-spinner"></div>

          <h2>
            Loading job details...
          </h2>

          <p>
            Please wait.
          </p>

        </div>

      </div>
    );
  }


  // ERROR

  if (error && !job) {

    return (
      <div className="job-details-page">

        <div className="job-details-error">

          <div>
            ⚠️
          </div>

          <h2>
            Unable to Load Job
          </h2>

          <p>
            {error}
          </p>

          <button
            onClick={() =>
              navigate("/jobs")
            }
          >
            ← Back to Jobs
          </button>

        </div>

      </div>
    );
  }


  // JOB NOT FOUND

  if (!job) {

    return (
      <div className="job-details-page">

        <div className="job-details-error">

          <h2>
            Job not found
          </h2>

          <p>
            This job may have been removed.
          </p>

          <button
            onClick={() =>
              navigate("/jobs")
            }
          >
            ← Back to Jobs
          </button>

        </div>

      </div>
    );
  }


  // MAIN PAGE

  return (
    <div className="job-details-page">

      <div className="job-details-container">

        {/* BACK */}

        <button
          className="back-jobs"
          onClick={() =>
            navigate("/jobs")
          }
        >
          ← Back to Jobs
        </button>


        {/* JOB CARD */}

        <div className="job-details-card">


          {/* HEADER */}

          <div className="job-details-header">

            <div className="job-company-logo">

              {job.company
                ? job.company
                    .charAt(0)
                    .toUpperCase()
                : "J"}

            </div>


            <div>

              <h1>
                {job.title ||
                  "Job Opportunity"}
              </h1>

              <h3>
                {job.company ||
                  "Company"}
              </h3>

            </div>

          </div>


          {/* TAGS */}

          <div className="job-details-tags">

            <span>
              📍{" "}
              {job.location ||
                "Location not specified"}
            </span>

            <span>
              💼{" "}
              {job.jobType ||
                "Job type not specified"}
            </span>

            <span>
              💰{" "}
              {job.salary ||
                "Salary not specified"}
            </span>

          </div>


          {/* DESCRIPTION */}

          <div className="job-details-section">

            <h2>
              Job Description
            </h2>

            <p>
              {job.description ||
                "No description available."}
            </p>

          </div>


          {/* SKILLS */}

          <div className="job-details-section">

            <h2>
              Required Skills
            </h2>


            {job.skills ? (

              <div className="skills-box">

                {job.skills
                  .split(",")
                  .map((skill) =>
                    skill.trim()
                  )
                  .filter(Boolean)
                  .map(
                    (skill, index) => (

                      <span
                        key={index}
                      >
                        {skill}
                      </span>

                    )
                  )}

              </div>

            ) : (

              <div className="skills-box">
                Skills not specified
              </div>

            )}

          </div>


          {/* APPLICATION MESSAGE */}

          {message && (

            <div className="success-message">

              ✓ {message}

            </div>

          )}


          {error && (

            <div className="application-error">

              ⚠️ {error}

            </div>

          )}


          {/* APPLY */}

          <div className="job-apply-section">

            <button
              className="apply-main-button"
              onClick={handleApply}
              disabled={applying || !!message}
            >

              {applying
                ? "Applying..."
                : message
                ? "Applied ✓"
                : "Apply Now"}

            </button>

          </div>


          {/* EXTERNAL APPLICATION */}

          {job.applicationUrl && (

            <div className="external-application">

              <p>
                Prefer to apply directly
                through the company?
              </p>

              <a
                href={job.applicationUrl}
                target="_blank"
                rel="noopener noreferrer"
              >
                Visit Company Application →
              </a>

            </div>

          )}

        </div>

      </div>

    </div>
  );
}

export default JobDetails;