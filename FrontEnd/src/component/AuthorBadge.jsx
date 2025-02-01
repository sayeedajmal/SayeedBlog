import React from "react";
import { FaComment, FaShareAlt } from "react-icons/fa";
import { MdOutlineBookmarkAdd } from "react-icons/md";
import { PiHandsClappingFill } from "react-icons/pi";
import img from "../images/bg.jpg";

const AuthorBadge = ({ AuthorName }) => {
  return (
    <div className="flex flex-col items-center w-full sm:w-10/12 rounded-2xl bg-white dark:bg-gray-800 shadow-lg dark:shadow-gray-700">
      <div className="flex items-center w-full p-4">
        <img src={img} alt="author" className="h-10 w-10 rounded-full" />
        <div className="ml-4">
          <div className="flex items-center">
            <h2 className="text-black dark:text-white mr-2">{AuthorName}</h2>
            <span className="text-gray-500 dark:text-gray-400 mr-2">
              &bull;
            </span>
            <a
              href="http://sayeedthedev.web.app"
              className="font-bold text-blue-900 dark:text-blue-300"
            >
              Follow
            </a>
          </div>
          <div>
            <h2 className="text-gray-600 dark:text-gray-400">
              4 min read &bull; Sep 2023
            </h2>
          </div>
        </div>
      </div>
      <div className="w-full p-2 border-t-2 border-b-2 border-gray-100 dark:border-gray-600 flex justify-around">
        <PiHandsClappingFill className="text-gray-700 dark:text-gray-300" />
        <FaComment className="text-gray-700 dark:text-gray-300" />
        <MdOutlineBookmarkAdd className="text-gray-700 dark:text-gray-300" />
        <FaShareAlt className="text-gray-700 dark:text-gray-300" />
      </div>
    </div>
  );
};

export default AuthorBadge;
