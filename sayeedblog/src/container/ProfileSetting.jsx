import { LogoutSharp } from "@mui/icons-material";
import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { removeUserData } from "../RestApi/Auth";
import getImage from "../RestApi/getImage";

const list = ["account", "privacy", "notifications", "security"];

const ProfileSetting = () => {
  const navigate = useNavigate();
  const [activeItem, setActiveItem] = useState("account");
  const [imageSrc, setImageSrc] = useState(null);

  const handleClick = (item) => {
    setActiveItem(item);
  };

  const fileId = localStorage.getItem("profilePicture");
  useEffect(() => {
    const fetchImage = async () => {
      try {
        const imageBlob = await getImage(fileId);
        const imageUrl = URL.createObjectURL(imageBlob);
        setImageSrc(imageUrl);
      } catch (error) {
        console.error("Error fetching image:", error);
      }
    };

    fetchImage();
  }, [fileId]);

  const logout = () => {
    removeUserData();
    navigate("/");
  };
  return (
    <div className="flex flex-row gap-4 w-full">
      {/* Side Panel */}
      <div className="w-1/4 h-screen bg-white dark:bg-gray-700 p-4">
        <h1 className="font-bold text-3xl mb-4">Setting</h1>
        <ul>
          {list.map((value) => (
            <li
              key={value}
              onClick={() => handleClick(value)}
              className={`py-2 cursor-pointer my-1 rounded-xl px-2 font-bold transition-all ${
                activeItem === value
                  ? "bg-blue-500 text-white"
                  : "bg-gray-200 dark:bg-gray-600"
              }`}
            >
              {value}
            </li>
          ))}
        </ul>
        <button
          onClick={logout}
          className=" w-1/2 p-2 rounded-xl m-2 bg-blue-500 text-white dark:bg-gray-200 dark:text-black"
        >
          <LogoutSharp />
        </button>
      </div>
      {/* Main Container */}
      <div className="flex-1 p-6 bg-gray-200 dark:bg-gray-700">
        {/* Profile Image */}
        <div className="flex justify-center mb-6">
          <img
            src={imageSrc}
            className="h-32 w-32 object-cover rounded-full border-4 border-blue-500"
            alt="Profile"
          />
        </div>

        {/* Profile Form */}
        <div className="max-w-md mx-auto bg-gray-100 dark:bg-gray-800 p-6 rounded-xl shadow-md">
          {activeItem === "account" && (
            <>
              <h2 className="text-xl font-bold mb-4">Account Information</h2>
              <form className="space-y-4">
                <div>
                  <label
                    htmlFor="name"
                    className="block text-sm font-medium text-gray-700 dark:text-gray-300"
                  >
                    Name
                  </label>
                  <input
                    id="name"
                    type="text"
                    className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500 sm:text-sm dark:bg-gray-900 dark:border-gray-600 dark:text-gray-300"
                    placeholder="Enter your name"
                  />
                </div>
                <div>
                  <label
                    htmlFor="bio"
                    className="block text-sm font-medium text-gray-700 dark:text-gray-300"
                  >
                    Bio
                  </label>
                  <textarea
                    id="bio"
                    rows="3"
                    className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500 sm:text-sm dark:bg-gray-900 dark:border-gray-600 dark:text-gray-300"
                    placeholder="Tell us about yourself"
                  ></textarea>
                </div>
                <div>
                  <label
                    htmlFor="email"
                    className="block text-sm font-medium text-gray-700 dark:text-gray-300"
                  >
                    Email
                  </label>
                  <input
                    id="email"
                    type="email"
                    className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500 sm:text-sm dark:bg-gray-900 dark:border-gray-600 dark:text-gray-300"
                    placeholder="Enter your email"
                  />
                </div>
                <div>
                  <label
                    htmlFor="password"
                    className="block text-sm font-medium text-gray-700 dark:text-gray-300"
                  >
                    Password
                  </label>
                  <input
                    id="password"
                    type="password"
                    className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500 sm:text-sm dark:bg-gray-900 dark:border-gray-600 dark:text-gray-300"
                    placeholder="Enter your password"
                  />
                </div>
                <button
                  type="submit"
                  className="w-full bg-blue-500 text-white py-2 px-4 rounded-md hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2"
                >
                  Save Changes
                </button>
              </form>
            </>
          )}

          {activeItem === "privacy" && (
            <>
              <h2 className="text-xl font-bold mb-4">Privacy Settings</h2>
              <p>privacy.</p>
            </>
          )}

          {activeItem === "notifications" && (
            <>
              <h2 className="text-xl font-bold mb-4">Notification Settings</h2>
              <p>notification preferences .</p>
            </>
          )}

          {activeItem === "security" && (
            <>
              <h2 className="text-xl font-bold mb-4">Security Settings</h2>
              <p>security settings .</p>
            </>
          )}
        </div>
      </div>
    </div>
  );
};

export default ProfileSetting;
