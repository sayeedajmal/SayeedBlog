import React from "react";
import AuthorBadge from "../component/AuthorBadge";
import WriteNabar from "../component/WriteNavbar";

const ShowBlog = () => {
  return (
    <div>
      <WriteNabar />
      <div className="post border-2 border-red-50 w-2/3 m-auto">
        <h1>Getting Started with Spring Boot: A Beginner’s Guide</h1>
        <AuthorBadge />
      </div>
    </div>
  );
};

export default ShowBlog;
