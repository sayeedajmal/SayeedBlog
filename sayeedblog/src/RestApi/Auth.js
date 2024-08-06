// Save JWT to localStorage
export const saveToken = (token) => {
  localStorage.setItem("authToken", token);
};

// Retrieve JWT from localStorage
export const getToken = () => {
  return localStorage.getItem("authToken");
};

// Remove JWT from localStorage
export const removeToken = () => {
  localStorage.removeItem("authToken");
};

// Check if user is authenticated
export const isAuthenticated = () => {
  return !!getToken();
};
