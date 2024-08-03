import React from "react";

const PostCard = ({ post }) => {
  return (
    <div className="flex items-center rounded-xl overflow-auto w-10/12 p-5 bg-gray-50 m-3">
      <img
        src={post.image}
        alt="postImg"
        className="w-[15vw] h-[22vh] mr-5 rounded-xl"
      />
      <div className="">
        <h2 className="font-bold text-xl text-blue-300">{post.title}</h2>
        <p className="text-gray-500">
          {post.date} | {post.category}
        </p>
        <p className="text-gray-600">{post.content}</p>
        <a href={post.readMoreLink} className="text-green-500">
          Read More
        </a>
      </div>
    </div>
  );
};

export default PostCard;
