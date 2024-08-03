import React from "react";
import PostCard from "../component/PostCard";

const samplePostData = [
  {
    image: "https://via.placeholder.com/150",
    title: "Exploring the Cosmos",
    date: "August 1, 2024",
    category: "Science",
    content:
      "Discover the wonders of the universe and the latest in space exploration. Our universe is vast and full of mysteries waiting to be unraveled. Join us as we delve into the unknown and explore the cosmos.",
    readMoreLink: "#",
  },
  {
    image: "https://via.placeholder.com/150",
    title: "Advancements in AI",
    date: "August 5, 2024",
    category: "Technology",
    content:
      "Learn about the newest trends and innovations in artificial intelligence. From machine learning algorithms to AI-driven applications, discover how AI is transforming industries and daily life.",
    readMoreLink: "#",
  },
  {
    image: "https://via.placeholder.com/150",
    title: "A Culinary Journey",
    date: "August 10, 2024",
    category: "Food",
    content:
      "Explore delicious recipes and food cultures from around the world. Whether you're a gourmet chef or a home cook, find inspiration and new flavors to enhance your culinary skills.",
    readMoreLink: "#",
  },
  {
    image: "https://via.placeholder.com/150",
    title: "The Future of Renewable Energy",
    date: "August 15, 2024",
    category: "Environment",
    content:
      "Dive into the latest advancements in renewable energy sources. Learn about solar, wind, and other sustainable technologies that are shaping the future of energy production.",
    readMoreLink: "#",
  },
  {
    image: "https://via.placeholder.com/150",
    title: "The Art of Minimalism",
    date: "August 20, 2024",
    category: "Lifestyle",
    content:
      "Discover the principles of minimalism and how adopting a minimalist lifestyle can lead to a more fulfilling and stress-free life. Embrace simplicity and find joy in less.",
    readMoreLink: "#",
  },
];

const AllPosts = () => {
  return (
    <div className="flex h-screen p-3">
      {/* Sidebar */}
      <div className="w-1/4 bg-gray-800 h-full text-white p-4 rounded-2xl">
        Contain SideBar
      </div>
      <div className="w-3/4 bg-white h-full overflow-auto scrollable-content rounded-2xl shadow-2xl p-4">
        <h1 className="text-center font-bold text-3xl mb-4 text-gray-700">
          Your All Valuable BlogPosts
        </h1>
        <h5 className="text-center font-bold text-sm mb-6 text-gray-400">
          You Can Check And Keep Track of Your All Posts
        </h5>
        {samplePostData && samplePostData.length > 0 ? (
          samplePostData.map((post, index) => (
            <PostCard key={index} post={post} />
          ))
        ) : (
          <p className="text-center">No posts available</p>
        )}
      </div>
    </div>
  );
};

export default AllPosts;
