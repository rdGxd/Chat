import { Button } from "@/components/ui/button";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import axios from "axios";
import { useState } from "react";
import { Link } from "react-router-dom";
import { Alert } from "../../components/ui/alert";

export function SingUp() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [status, setStatus] = useState<boolean>();
  const [statusError, setStatusError] = useState<boolean>();

  const handleSingIn = async (
    e: React.MouseEvent<HTMLButtonElement, MouseEvent>
  ) => {
    e.preventDefault();
    try {
      await axios
        .post(`${import.meta.env.VITE_API_URL}/auth/register`, {
          email,
          password,
        })
        .then(() => {
          setStatus(true);
          setStatusError(false);
          window.location.href = "/login";
          return;
        });
    } catch (error) {
      setStatus(false);
      setStatusError(true);
    }
  };

  return (
    <Card className="mx-auto max-w-sm">
      <CardHeader>
        <CardTitle className="text-xl">Sign Up</CardTitle>
        <CardDescription>
          Enter your information to create an account
        </CardDescription>
      </CardHeader>
      <CardContent>
        <div className="grid gap-4">
          <div className="grid gap-2">
            <Label htmlFor="email">Email</Label>
            <Input
              id="email"
              type="email"
              value={email}
              minLength={3}
              maxLength={16}
              onChange={(event) => setEmail(event.target.value)}
              placeholder="m@example.com"
              required
            />
          </div>
          <div className="grid gap-2">
            <Label htmlFor="password">Password</Label>
            <Input
              id="password"
              type="password"
              minLength={6}
              maxLength={16}
              onChange={(e) => setPassword(e.target.value)}
              value={password}
              required
            />
          </div>
          <Button
            type="submit"
            className="w-full"
            onClick={(e) => handleSingIn(e)}
          >
            Create an account
          </Button>
          {status && (
            <Alert className="text-green-500 text-center">
              <p>Account created successfully.</p>
            </Alert>
          )}

          {statusError && (
            <Alert className="text-red-500 text-center">
              <p>Error try register account.</p>
            </Alert>
          )}
        </div>
        <div className="mt-4 text-center text-sm">
          Already have an account?{" "}
          <Link to="/login" className="underline">
            Sign in
          </Link>
        </div>
      </CardContent>
    </Card>
  );
}
