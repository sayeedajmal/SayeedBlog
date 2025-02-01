import axios from "axios";
import { React, useEffect, useState } from "react";
import { Link } from "react-router-dom";
import img from "../images/bg.jpg";

const PostCard = ({ post }) => {
  const [imageSrc, setImageSrc] = useState("");

  const apiUrl = process.env.REACT_APP_POST;

  useEffect(() => {
    const fetchImage = async () => {
      if (post.images && post.images.length > 0) {
        try {
          const response = await axios.get(`${apiUrl}/api/image/${post.images[0]}`, {
            responseType: "blob",
          });
          const imageUrl = URL.createObjectURL(response.data);
          setImageSrc(imageUrl);
        } catch (error) {
          console.error("Error fetching the image", error);
        }
      }
    };

    fetchImage();
  }, [post.images, apiUrl]);

  return (
    <div className="flex flex-col md:flex-row items-center rounded-xl overflow-hidden p-5 bg-gray-50 dark:bg-gray-800 m-3 shadow-lg dark:shadow-xl transition-transform duration-300 ease-in-out">
      <img
        src={imageSrc || img}
        alt="postImg"
        className="w-1/3 h-auto md:h-[22vh] object-cover rounded-xl mb-4 md:mb-0 md:mr-5"
      />
      <div className="flex-1">
        <h2 className="font-bold text-xl text-blue-500 dark:text-blue-300 mb-2">
          {post.title}
        </h2>
        <p className="text-gray-600 dark:text-gray-400 mb-2">
          {post.createdAt} | {post.category}
        </p>
        <p className="text-gray-700 text-justify dark:text-gray-300 mb-4">
          {post.summary}
        </p>
        <Link
          to={`/blog/${post._id}`}
          className="text-green-500 dark:text-green-300 hover:underline"
        >
          Read More
        </Link>
      </div>
    </div>
  );
};

export default PostCard;
