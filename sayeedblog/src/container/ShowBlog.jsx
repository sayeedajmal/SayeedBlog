import axios from "axios";
import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import AuthorBadge from "../component/AuthorBadge";
import Navbar from "../component/Navbar";

const ShowBlog = () => {
  const { postId } = useParams();
  const [blogData, setBlogData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const BASE_URL = process.env.REACT_APP_POST;

  useEffect(() => {
    const fetchBlogData = async () => {
      try {
        const response = await axios.get(`${BASE_URL}/api/post/${postId}`);
        setBlogData(response.data);
      } catch (err) {
        setError("Failed to fetch blog data.");
      } finally {
        setLoading(false);
      }
    };

    fetchBlogData();
  }, [postId, BASE_URL]);

  if (loading)
    return (
      <p className="text-center text-gray-500 dark:text-gray-400">Loading...</p>
    );
  if (error)
    return (
      <p className="text-center text-red-500 dark:text-red-400">{error}</p>
    );
  if (!blogData)
    return (
      <p className="text-center text-gray-500 dark:text-gray-400">
        Blog not found.
      </p>
    );

  const { title, summary, authorId, content, tags, category } = blogData;

  return (
    <>
      <Navbar />
      <div className="p-4 sm:p-8 md:p-10 bg-white dark:bg-gray-900 text-black dark:text-white">
        <div className="w-10/12 mx-auto">
          <h1 className="text-4xl sm:text-5xl font-serif font-extrabold uppercase text-gray-800 dark:text-gray-100 mb-4">
            {title}
          </h1>
          <p className="text-lg sm:text-xl text-gray-600 dark:text-gray-300 mb-6">
            {summary}
          </p>
          <h2 className="text-sm sm:text-base text-gray-500 dark:text-gray-400 mb-6">
            Category: {category}
          </h2>
          <AuthorBadge AuthorName={authorId} />
        </div>
        <div className="mt-8 flex flex-col lg:flex-row lg:gap-8 xl:gap-12">
          <div className="hidden lg:block lg:w-64 border-2 border-gray-700 dark:border-gray-600 rounded-3xl p-4">
            <h1 className="text-gray-800 dark:text-gray-200">
              This is Only For SideBar
            </h1>
          </div>
          <div className="flex-1 mx-auto p-4 bg-white dark:bg-gray-800 dark:text-blue-500 rounded-lg shadow-md space-y-6 md:p-6 lg:p-8">
            <div
              className="prose prose-sm sm:prose-base lg:prose-lg dark:prose-invert"
              dangerouslySetInnerHTML={{ __html: content }}
            ></div>

            {/* Tags */}
            <div className="mt-6">
              <h3 className="text-lg font-semibold text-gray-800 dark:text-gray-100">
                Tags
              </h3>
              <div className="flex flex-wrap gap-2 mt-2">
                {tags &&
                  tags.length > 0 &&
                  tags.map((tag, index) => (
                    <span
                      key={index}
                      className="bg-blue-100 text-blue-800 dark:bg-blue-600 dark:text-blue-200 text-xs font-medium px-2 py-0.5 rounded"
                    >
                      {tag}
                    </span>
                  ))}
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};

export default ShowBlog;
