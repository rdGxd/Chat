import { useEffect, useState } from "react";
import Logo from "../../assets/Logo.svg";
import { ModeToggle } from "../mode-toggle";
import { Button } from "../ui/button";

export const Header = () => {
  const [login, setLogin] = useState<boolean>();

  useEffect(() => {
    setLogin(localStorage.getItem("token") ? true : false);
  }, []);

  const handleClickLogout = () => {
    localStorage.removeItem("token");
    window.location.href = "/login";
  };

  return (
    <div className="flex items-center justify-around my-2">
      <ModeToggle />
      <img src={Logo} alt="" />
      {login && <Button onClick={handleClickLogout}>Logout</Button>}
    </div>
  );
};
