import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import DarkMode from "../component/DarkMode";
import img from "../images/bg.jpg";

const navContent = [
  { name: "HOME", path: "/" },
  { name: "BLOG", path: "/blog" },
  { name: "CATEGORIES", path: "/categories" },
  { name: "SEARCH", path: "/search" },
  { name: "ABOUT", path: "/about" },
  { name: "CONTACT", path: "/contact" },
];

const Navbar = () => {
  const [isMenuOpen, setMenuOpen] = useState(false);

  const toggleMenu = () => setMenuOpen(!isMenuOpen);

  useEffect(() => {
    if (isMenuOpen) {
      document.body.style.overflow = "hidden";
    } else {
      document.body.style.overflow = "auto";
    }

    return () => {
      document.body.style.overflow = "auto";
    };
  }, [isMenuOpen]);

  return (
    <div className="bg-white dark:bg-gray-800 w-full py-2 rounded-md border-b-2 border-b-slate-100 dark:border-b-gray-600">
      <div className="container mx-auto flex justify-between items-center px-4 sm:px-6 lg:px-8">
        <img src={img} alt="Logo" className="h-8 w-8 bg-cover rounded-full" />
        <div className="flex-1 flex justify-center">
          <ul className="hidden md:flex md:items-center md:space-x-4">
            {navContent.map((item) => (
              <li className="font-bold dark:text-gray-200" key={item.name}>
                <Link to={item.path}>{item.name}</Link>
              </li>
            ))}
          </ul>
        </div>
        <div className="flex items-center space-x-3">
          <img
            src={img}
            alt="Login"
            className="h-8 w-8 bg-cover rounded-full"
          />
          <DarkMode />
        </div>
        {/* Mobile Menu Button */}
        <button
          className="md:hidden p-2"
          onClick={toggleMenu}
          aria-label="Toggle menu"
        >
          <svg
            xmlns="http://www.w3.org/2000/svg"
            className="h-6 w-6 dark:text-gray-200"
            fill="none"
            viewBox="0 0 24 24"
            stroke="currentColor"
          >
            <path
              strokeLinecap="round"
              strokeLinejoin="round"
              strokeWidth="2"
              d={
                isMenuOpen ? "M6 18L18 6M6 6l12 12" : "M4 6h16M4 12h16M4 18h16"
              }
            />
          </svg>
        </button>
      </div>

      {/* Mobile Menu */}
      <div
        className={`md:hidden px-4 py-2 space-y-2 bg-white dark:bg-gray-800 border-t border-gray-200 dark:border-gray-600 transition-transform duration-300 ease-in-out ${
          isMenuOpen ? "block h-screen" : "hidden"
        }`}
      >
        <ul>
          {navContent.map((item) => (
            <li className="font-bold dark:text-gray-200" key={item.name}>
              <Link to={item.path}>{item.name}</Link>
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
};

export default Navbar;
