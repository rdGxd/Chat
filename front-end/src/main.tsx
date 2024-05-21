import React from "react";
import ReactDOM from "react-dom/client";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import { Header } from "./components/Header/index.tsx";
import { ThemeProvider } from "./components/theme-provider.tsx";
import "./index.css";
import { Home } from "./pages/Home/index.tsx";
import { SingUp } from "./pages/SignUp/index.tsx";
import { SingIn } from "./pages/SingIn/index.tsx";

const router = createBrowserRouter([
  {
    path: "/",
    element: <Home />,
  },
  {
    path: "/register",
    element: <SingUp />,
  },
  {
    path: "/login",
    element: <SingIn />,
  },
]);

ReactDOM.createRoot(document.getElementById("root")!).render(
  <React.StrictMode>
    <ThemeProvider defaultTheme="system" storageKey="vite-ui-theme">
      <Header />
      <RouterProvider router={router} />
    </ThemeProvider>
  </React.StrictMode>
);
