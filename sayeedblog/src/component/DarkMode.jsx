import React from "react";
import { DarkModeSwitch } from "react-toggle-dark-mode";

const DarkMode = () => {
  const [isDarkMode, setDarkMode] = React.useState(() => {
    const savedMode = localStorage.getItem("darkMode");
    return savedMode ==="true";
  });

  const toggleDarkMode = (checked) => {
    setDarkMode(checked);
    localStorage.setItem("darkMode", checked);
    document.documentElement.classList.toggle("dark", checked);
  };

  React.useEffect(() => {
    document.documentElement.classList.toggle("dark", isDarkMode);
  }, [isDarkMode]);

  return (
    <DarkModeSwitch
      checked={isDarkMode}
      onChange={toggleDarkMode}
      size={40}
    />
  );
};

export default DarkMode;
