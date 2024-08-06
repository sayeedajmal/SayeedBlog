import React from 'react';
import { Navigate, useLocation } from 'react-router-dom';

const PrivateRoute = ({ element, ...rest }) => {
  const isAuthenticated = localStorage.getItem('token');
  const location = useLocation();

  return isAuthenticated ? element : <Navigate to="/auth" state={{ from: location }} replace />;
};

export default PrivateRoute;
