import "./App.css";
import EditBlog from "./container/EditBlog";
import ShowBlog from "./container/ShowBlog";
import PostCard from "./component/PostCard"
import AllPosts from "./container/AllPosts";
const sampleBlogData = {
  _id: "78a42ad4",
  title: "A Complete Guide On How To Start Blogging On Medium In 2024",
  summary:
    "Ready to kickstart your blogging journey on Medium? Create engaging content and building a following on this popular platform with our guide.",
  authorId: "AuthorABC",
  createdAt: "2024-08-02T21:14:35",
  updatedAt: null,
  category: "Science",
  comments: null,
  likes: null,
  tags: ["Medium", "Medium.com", "Blogging"],
  content:
    '<img style="margin:1rem auto; max-height:70vh; border-radius:1rem;" src="http://localhost:8080/api/images/66acfed01f458877f7487260" alt="download.webp"><p><span style="color: inherit; background-color: inherit; color: rgb(0, 0, 0); font-size: 24px; font-family: trebuchet-ms">When it comes to the </span><span style="color: inherit; background-color: inherit; color: inherit; font-size: 24px; font-family: trebuchet-ms">benefits of blogging for business</span><span style="color: inherit; background-color: inherit; color: rgb(0, 0, 0); font-size: 24px; font-family: trebuchet-ms">, blogging on Medium can be a game-changer. If you\'re seeking a platform that can help you leverage your blog content, build a following, and collaborate with like-minded writers, look no further. With its array of features and customization options, Medium can help you take your brand and your blog to the next level. The potential is endless!</span></p><p></p><p></p><h2><strong><span style="color: inherit; background-color: inherit; color: inherit; font-size: 30px">What Is Medium?</span></strong></h2><img style="margin:1rem auto;  max-height:70vh; border-radius:1rem;" src="http://localhost:8080/api/images/66acfee01f458877f74872b3" alt="download (1).webp"><p><span style="color: inherit; background-color: inherit; color: rgb(0, 0, 0); font-size: 24px; font-family: trebuchet-ms">Medium is a social publishing platform that is open to all and home to a diverse array of stories, ideas, and perspectives. Anyone can write for the platform, so it\'s great for beginners. Anyone can create an account on Medium and start using it and writers can earn money from the platform when they meet certain conditions.</span></p><p></p><h3><strong><span style="color: inherit; background-color: inherit; color: inherit; font-size: 30px">Diverse Content Landscape</span></strong></h3><p><span style="color: inherit; background-color: inherit; color: rgb(0, 0, 0); font-size: 24px; font-family: trebuchet-ms">Popular topics on Medium range from mental health to social media, from world affairs and trending news to productivity hacks. This makes it a solid choice for anyone who wants to start blogging.</span></p><p></p><h3><strong><span style="color: inherit; background-color: inherit; color: inherit; font-size: 30px; margin-top:3rem; margin-bottom:3rem;  ">Accessibility and Audience Engagement on Medium</span></strong></h3><p><span style="color: inherit; background-color: inherit; color: rgb(0, 0, 0); font-size: 24px; font-family: trebuchet-ms">Medium is user-friendly and easy to navigate, making it accessible to both seasoned bloggers and newcomers to the blogging scene. The platform\'s intuitive design and simple publishing process attract a wide range of content creators. Medium also has a built-in audience of engaged readers who visit the platform to consume and interact with content.</span></p><p></p><h3><strong><span style="color: inherit; background-color: inherit; color: inherit; font-size: 30px">Origins and Vision</span></strong></h3><p><span style="color: inherit; background-color: inherit; color: rgb(0, 0, 0); font-size: 24px; font-family: trebuchet-ms">According to SimilarWeb reports, the Medium blog has 170M monthly visits. This makes it a place for readers and bloggers to meet each other with less effort. This ready audience provides bloggers with an opportunity to reach a wider readership and </span><span style="color: inherit; background-color: inherit; color: inherit; font-size: 24px; font-family: trebuchet-ms">gain exposure for their content</span><span style="color: inherit; background-color: inherit; color: rgb(0, 0, 0); font-size: 24px; font-family: trebuchet-ms">.</span></p><p></p><h3><strong><span style="color: inherit; background-color: inherit; color: inherit; font-size: 30px">Unveiling the Origins of Medium</span></strong></h3><p><span style="color: inherit; background-color: inherit; color: rgb(0, 0, 0); font-size: 24px; font-family: trebuchet-ms">Medium was founded by former Twitter co-founder and CEO—Evan Clark Williams—mostly known as just Ev Williams. Ev also founded Blogger—one of the earliest blogging platforms. So, as you’d expect, Medium shares many similarities with Twitter, but like Blogger, it was built for medium to long-form content. And that is actually where the name “Medium” came from.</span></p>',
  images: ["66acfed01f458877f7487260", "66acfee01f458877f74872b3"],
};

function App() {
  return (
    <div className="App">
      {/* <ShowBlog blogData={sampleBlogData} /> */}
      {/* <EditBlog /> */}
      {/* <PostCard /> */}
      <AllPosts/>
    </div>
  );
}

export default App;
