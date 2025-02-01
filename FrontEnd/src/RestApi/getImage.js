import axios from "axios";

const getImage = async (fileId) => {
  const apiUrl = process.env.REACT_APP_AUTHOR;

  if (fileId) {
    try {
      const response = await axios.get(`${apiUrl}/api/image/${fileId}`, {
        responseType: "blob",
      });

      return response.data;
    } catch (error) {
      console.error("Error fetching image:", error);
      throw error;
    }
  }
};

export default getImage;
