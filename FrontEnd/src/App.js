import React from "react";
import { Route, BrowserRouter as Router, Routes } from "react-router-dom";
import PostCard from "./component/PostCard";
import AllPosts from "./container/AllPosts";
import Auth from "./container/Auth";
import Dashboard from "./container/Dashboard";
import EditBlog from "./container/EditBlog";
import ProfileSetting from "./container/ProfileSetting";
import ShowBlog from "./container/ShowBlog";
import PrivateRoute from "./RestApi/PrivateRoute";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/auth" element={<Auth />} />
        <Route path="/" element={<Dashboard />} />
        <Route path="/blog" element={<PrivateRoute element={<AllPosts />} />} />
        <Route
          path="/blog/:postId"
          element={<PrivateRoute element={<ShowBlog />} />}
        />
        <Route
          path="/NewBlog"
          element={<PrivateRoute element={<EditBlog />} />}
        />
        <Route
          path="/postcard"
          element={<PrivateRoute element={<PostCard />} />}
        />
        <Route
          path="/Setting"
          element={<PrivateRoute element={<ProfileSetting />} />}
        />
      </Routes>
    </Router>
  );
}

export default App;
