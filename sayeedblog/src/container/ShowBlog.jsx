import React from "react";
import AuthorBadge from "../component/AuthorBadge";

const ShowBlog = ({ blogData }) => {
  const { title, summary, authorId, content, tags, category } = blogData;

  return (
    <div className="max-w-4xl mx-auto p-6 bg-white shadow-lg rounded-lg">
      <h1 className="text-5xl font-serif font-extrabold uppercase text-center text-gray-800 mb-4">
        {title}
      </h1>
      <p className="text-2xl text-gray-600 mb-6 text-center">{summary}</p>
      <h1 className=" text-end mb-6 text-m text-gray-500">
        Category: {category}
      </h1>
      <AuthorBadge AuthorName={authorId} />

      <div
        className="prose prose-lg"
        dangerouslySetInnerHTML={{ __html: content }}
      ></div>

      {/* Tags */}
      <div className="mt-6">
        <h3 className="text-xl font-semibold text-gray-800">Tags</h3>
        <div className="flex flex-wrap gap-2 mt-2">
          {tags &&
            tags.length > 0 &&
            tags.map((tag, index) => (
              <span
                key={index}
                className="bg-blue-100 text-blue-800 text-xs font-medium px-2.5 py-0.5 rounded"
              >
                {tag}
              </span>
            ))}
        </div>
      </div>
    </div>
  );
};

export default ShowBlog;
