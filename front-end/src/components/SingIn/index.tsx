import { Button } from "@/components/ui/button";
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import axios from "axios";
import { useState } from "react";
import { Link } from "react-router-dom";
import { Alert } from "../ui/alert";

export function SingIn() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [statusError, setStatusError] = useState<boolean>();

  const handleSingIn = async (
    e: React.MouseEvent<HTMLButtonElement, MouseEvent>
  ) => {
    e.preventDefault();
    try {
      await axios
        .post(`${import.meta.env.VITE_API_URL}/auth/login`, {
          email,
          password,
        })
        .then((r) => {
          localStorage.setItem("token", r.data.token);
          window.location.href = "/";
        });
    } catch (error) {
      setStatusError(true);
    }
  };

  return (
    <Card className="w-full max-w-sm">
      <CardHeader>
        <CardTitle className="text-2xl">Login</CardTitle>
        <CardDescription>
          Enter your email below to login to your account.
        </CardDescription>
      </CardHeader>
      <CardContent className="grid gap-4">
        <div className="grid gap-2">
          <Label htmlFor="email">Email</Label>
          <Input
            id="email"
            type="email"
            value={email}
            onChange={(e) => {
              setEmail(e.target.value);
            }}
            placeholder="m@example.com"
            required
            minLength={3}
            maxLength={16}
          />
        </div>
        <div className="grid gap-2">
          <Label htmlFor="password">Password</Label>
          <Input
            id="password"
            type="password"
            value={password}
            onChange={(e) => {
              setPassword(e.target.value);
            }}
            required
            minLength={6}
            maxLength={16}
          />
        </div>
      </CardContent>
      <CardFooter>
        <Button className="w-full" onClick={(e) => handleSingIn(e)}>
          Sign in
        </Button>
      </CardFooter>
      {statusError && (
        <Alert className="text-red-500 text-center mb-5">
          <p>Invalid email or password.</p>
        </Alert>
      )}
      <div className="text-center text-sm">
        Don't have an account yet?{" "}
        <Link to="/register" className="underline">
          Sign up
        </Link>
      </div>
    </Card>
  );
}
