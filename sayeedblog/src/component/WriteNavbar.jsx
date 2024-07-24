import React from "react";

const WriteNavbar = () => {
  return (
    <div className="bg-white content-center sticky w-full h-[4rem] px-2 z-50 border-b-2 border-b-black-400 ">
      <div className="flex justify-between">
        <img src="" alt="Hello" className="mx-3" />
        <input
          type="text"
          placeholder="Search"
          className="w-full px-3 rounded-lg bg-gray-200 border-none focus:"
        />
        <button className="mx-1 bg-slate-200 p-2 rounded-md ">Write</button>
        <button className="mx-1 bg-slate-200 p-2 rounded-md ">SignUp</button>
        <button className="mx-1 bg-slate-200 p-2 rounded-md ">SignIn</button>
      </div>
    </div>
  );
};

export default WriteNavbar;
