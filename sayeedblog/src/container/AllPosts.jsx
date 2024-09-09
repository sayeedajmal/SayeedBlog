import axios from "axios";
import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import DarkMode from "../component/DarkMode";
import PostCard from "../component/PostCard";

const AllPosts = () => {
  const [posts, setPosts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const BASE_URL =
    process.env.REACT_APP_POST;

  useEffect(() => {
    const fetchPosts = async () => {
      try {
        const response = await axios.get(
          `${BASE_URL}/api/post`
        );
        setPosts(response.data);
      } catch (err) {
        setError("Failed to fetch posts.");
      } finally {
        setLoading(false);
      }
    };

    fetchPosts();
  }, [BASE_URL]);

  if (loading)
    return (
      <p className="text-center text-gray-500 dark:text-gray-400">
        Loading posts...
      </p>
    );

  if (error)
    return (
      <p className="text-center text-red-500 dark:text-red-400">{error}</p>
    );

  return (
    <>
      <div className="absolute bottom-1/4 right-10 border-2 border-green-600 bg-green-600 rounded-full">
        <DarkMode />
      </div>
      <div className="flex flex-col md:flex-row h-screen p-4 bg-white dark:bg-gray-900">
        {/* Sidebar */}
        <div className="hidden md:block w-full md:w-1/4 bg-gray-800 dark:bg-gray-700 h-full text-white p-4 rounded-2xl mb-4 md:mb-0">
          <Link
            to="/NewBlog"
            className="w-full bg-green-500 rounded-xl font-bold text-lg py-2 text-center block"
          >
            Add New Blog
          </Link>
        </div>
        {/* Container */}
        <div className="w-full md:w-3/4 bg-white dark:bg-gray-800 h-full overflow-auto rounded-2xl shadow-xl p-4">
          <h1 className="text-center font-bold text-3xl mb-4 text-gray-700 dark:text-gray-100">
            Your All Valuable BlogPosts
          </h1>

          <h5 className="text-center font-bold text-sm mb-6 text-gray-400 dark:text-gray-500">
            You Can Check And Keep Track of Your All Posts
          </h5>
          {posts && posts.length > 0 ? (
            posts.map((post, index) => <PostCard key={index} post={post} />)
          ) : (
            <p className="text-center text-gray-500 dark:text-gray-400">
              No posts available
            </p>
          )}
        </div>
      </div>
    </>
  );
};

export default AllPosts;
