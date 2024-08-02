import React from "react";
import AuthorBadge from "../component/AuthorBadge";

const ShowBlog = ({ blogData }) => {
  const { title, summary, authorId, content, tags, category } = blogData;

  return (
    <div className="m-10">
      <div className="m-10">
        <h1 className="text-5xl font-serif font-extrabold uppercase text-left text-gray-800 mb-4">
          {title}
        </h1>
        <p className="text-2xl text-gray-600 mb-6 text-left">{summary}</p>
        <h1 className=" text-start mb-6 text-m text-gray-500">
          Category: {category}
        </h1>
        <AuthorBadge AuthorName={authorId} />
      </div>
      <div className="flex flex-col gap-8 lg:flex-row lg:gap-20 xl:gap-24 w-11/12">
        <div className="hidden lg:block border-2 border-gray-700 rounded-3xl lg:w-64 lg:shrink-0">
          <h1>This is Only For SideBar</h1>
        </div>
        <div className=" mx-auto p-6 bg-white rounded-lg max-w-full space-y-8 lg:flex-1 lg:space-y-12">
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
      </div>
    </div>
  );
};

export default ShowBlog;
