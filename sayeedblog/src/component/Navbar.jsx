import React from "react";
var navContent = ["HOME", "BLOG", "CATEGORIES", "SEARCH", "ABOUT", "CONTACT"];

const Navbar = () => {
  return (
    <div className="bg-white mt-1 content-center relative w-10/12 h-[5rem] rounded-md m-auto z-50">
      <div className="flex justify-between">
        <img src="" alt="Hello" className="mx-3" />
        <ul className="flex items-center">
          {/* Place center from top and bottom . content center from left to right */}
          {navContent.map((item) => (
            <li className="font-bold mx-3" key={`link-${item}`}>
              <a href={`#${item}`}>{item.toUpperCase()}</a>
            </li>
          ))}
        </ul>
        <button className="mx-3 bg-slate-200 p-2 rounded-md ">
          Login/Register
        </button>
      </div>
    </div>
  );
};

export default Navbar;
