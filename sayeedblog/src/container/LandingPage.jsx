import React from "react";
import Navbar from "../component/Navbar";
import img from "../images/bg.jpg";

const LandingPage = () => {
  return (
    <div className="">
      <Navbar />
      <img
        src={img}
        alt="bgImg"
        className="absolute top-0 pr-1 opacity-85 w-full object-cover rounded-lg h-3/5"
      />
      <h1 className="z-2 text-9xl text-center text-orange-800 font-bold relative ">
        Hello Blog
      </h1>
    </div>
  );
};

export default LandingPage;
