import axios from "axios";
import { jwtDecode } from "jwt-decode";
import { getToken, saveUserData } from "./Auth";

const fetchUserData = async () => {
  const apiUrl = process.env.REACT_APP_AUTHOR;

  try {
    const token = getToken();
    if (token && token != null) {
      const decodedToken = jwtDecode(token);
      const email = decodedToken.sub;
      if (!email) {
        throw new Error("Email not found in token");
      }

      const response = await axios.get(`${apiUrl}/api/author/email`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
        params: {
          email: email,
        },
      });

      const userData = response.data;
      saveUserData(
        userData.name,
        userData.profilePicture,
        userData.bio,
        userData._id,
        userData.email
      );
    }
  } catch (err) {
    console.error("Error fetching user data:", err.response.data.message);
  }
};
export default fetchUserData;
