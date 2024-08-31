// Auth.js
export const saveToken = (token) => {
  localStorage.setItem("authToken", token);
};

export const getToken = () => {
  return localStorage.getItem("authToken");
};

export const removeToken = () => {
  localStorage.removeItem("authToken");
};

export const isAuthenticated = () => {
  return !!getToken();
};

export const saveUserData = (name, profilePicture, bio, id, email) => {
  localStorage.setItem("userName", name);
  localStorage.setItem("profilePicture", profilePicture);
  localStorage.setItem("bio", bio);
  localStorage.setItem("email", email);
  localStorage.setItem("id", id);
};

export const getUserData = () => {
  return {
    name: localStorage.getItem("userName"),
    id: localStorage.getItem("id"),
    token: localStorage.getItem("authToken"),
    email: localStorage.getItem("email"),
    profilePicture: localStorage.getItem("profilePicture"),
    bio: localStorage.getItem("bio"),
  };
};

export const removeUserData = () => {
  localStorage.removeItem("userName");
  localStorage.removeItem("profilePicture");
  localStorage.removeItem("bio");
  localStorage.removeItem("authToken");
  localStorage.removeItem("email");
  localStorage.removeItem("id");
};
