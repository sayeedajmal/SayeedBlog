import React from "react";
import { Route, BrowserRouter as Router, Routes } from "react-router-dom";
import PostCard from "./component/PostCard";
import AllPosts from "./container/AllPosts";
import Dashboard from "./container/Dashboard";
import EditBlog from "./container/EditBlog";
import ShowBlog from "./container/ShowBlog";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Dashboard />} />
        <Route path="/blog" element={<AllPosts />} />
        <Route path="/blog/:postId" element={<ShowBlog />} />
        <Route path="/NewBlog" element={<EditBlog />} />
        <Route path="/postcard" element={<PostCard />} />
      </Routes>
    </Router>
  );
}

export default App;
