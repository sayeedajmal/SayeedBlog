import axios from "axios";

const getImage = async (fileId) => {
  const apiUrl = process.env.REACT_APP_AUTHOR;

  try {
    const response = await axios.get(`${apiUrl}/api/image/${fileId}`, {
      responseType: "blob",
    });

    // const blob = new Blob([response.data], { type: response.headers['content-type'] });
    // saveAs(blob, 'image.jpg'); // You can specify a filename here

    const imageUrl = URL.createObjectURL(response.data);
    const imgElement = document.createElement("img");
    imgElement.src = imageUrl;
    document.body.appendChild(imgElement);
    return imageUrl;
  } catch (error) {
    console.error("Error fetching image:", error);
    throw error;
  }
};

const fileId = "66d23c94cba82901651ae19c";
getImage(fileId)
  .then((imageUrl) => {
    console.log("Image URL:", imageUrl);
  })
  .catch((error) => {
    console.error("Failed to fetch image:", error);
  });

export default getImage;
