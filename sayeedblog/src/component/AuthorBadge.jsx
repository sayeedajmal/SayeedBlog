import React from "react";
import { FaComment, FaShareAlt } from "react-icons/fa";
import { MdOutlineBookmarkAdd } from "react-icons/md";
import { PiHandsClappingFill } from "react-icons/pi";
import img from "../images/bg.jpg";

const AuthorBadge = ({AuthorName}) => {
  return (
    <div className="flex-1 items-center">
      <div className="flex">
        <img src={img} alt="author" className="h-10 w-10 rounded-full" />
        <div className="ml-4">
          <div className="flex items-center">
            <h2 className="mr-2">{AuthorName}</h2>
            <span className="mr-2">&bull;</span>
            <a href="http://sayeedthedev.web.app" className="font-bold text-blue-900">
              Follow
            </a>
          </div>
          <div>
            <h2 className="text-gray-600">4 min read &bull; Sep 2023</h2>
          </div>
        </div>
      </div>
      <div className="w-full p-2 border-y-2 flex justify-around  border-gray-100">
        <PiHandsClappingFill />
        <FaComment />
        <MdOutlineBookmarkAdd />
        <FaShareAlt />
      </div>
    </div>
  );
};

export default AuthorBadge;
