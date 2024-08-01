import "./App.css";
import EditBlog from "./container/EditBlog";
import ShowBlog from "./container/ShowBlog";
const sampleBlogData = {
  title: "Exploring the Moon",
  summary: "A journey through lunar science and exploration.",
  authorId: "AuthorXYZ",
  content: `
    <p>Here's WE Goo</p>
    <img style="margin:1rem auto; max-width:70vw; max-height:70vh;" src="http://localhost:8080/api/images/66abee4dc20700346b33c1d4" alt="pizza.png">
    <p>sdfds</p>
    <img style="margin:1rem auto; max-width:70vw; max-height:70vh;" src="http://localhost:8080/api/images/66abee51c20700346b33c1fa" alt="image.png">
  `,
  tags: ["exploration", "moon", "science"],
  category: "space",
};

function App() {
  return (
    <div className="App">
      <ShowBlog blogData={sampleBlogData} />
      {/* <EditBlog /> */}
    </div>
  );
}

export default App;
