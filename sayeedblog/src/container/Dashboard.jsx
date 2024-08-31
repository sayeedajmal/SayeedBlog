// Dashboard.js
import React, { useEffect } from "react";
import Navbar from "../component/Navbar";
import img from "../images/bg.jpg";
import fetchUserData from "../RestApi/fetchUserData";
import getImage from "../RestApi/getImage";

function Dashboard() {
  useEffect(() => {
    fetchUserData();
    getImage();
  }, []);

  return (
    <div className="flex flex-col overflow-hidden h-screen bg-white text-black dark:bg-gray-900 dark:text-white">
      <Navbar className="w-screen border-b-2 border-black dark:border-gray-700" />
      <div className="flex-1 flex flex-col items-center justify-center p-4 sm:p-8 md:p-16">
        <h1 className="text-4xl sm:text-5xl md:text-6xl lg:text-8xl font-bold m-4 sm:m-6 md:m-8 lg:m-8 text-center dark:text-gray-100">
          Human stories & ideas
          <span className="text-black dark:text-gray-900 mx-2 px-2 rounded-3xl text-xl sm:text-2xl md:text-3xl lg:text-5xl cursor-pointer animate-pulse bg-green-300 dark:bg-green-600">
            Read Now
          </span>
        </h1>
        <img
          src={img}
          alt="img"
          className="w-full sm:w-3/4 md:w-2/3 lg:w-1/2 m-4 sm:m-6 md:m-8 rounded-xl shadow-lg dark:shadow-gray-800"
        />
      </div>
    </div>
  );
}

export default Dashboard;
