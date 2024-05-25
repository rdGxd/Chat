import { Label } from "@/components/ui/label";
import { useEffect, useState } from "react";
import { Alert } from "../ui/alert";
import { Button } from "../ui/button";
import {
  Card,
  CardContent,
  CardFooter,
  CardHeader,
  CardTitle,
} from "../ui/card";
import { Input } from "../ui/input";

export const ConnectRoom = () => {
  const [idRoom, setIdRoom] = useState("");
  const [token, setToken] = useState<string | null>(null);
  const [statusFailed, setStatusFailed] = useState<boolean>();

  useEffect(() => {
    if (!localStorage.getItem("token")) {
      window.location.href = "/login";
    } else {
      setToken(localStorage.getItem("token"));
    }
  }, []);

  const handleClickCreateRoom = async (
    e: React.MouseEvent<HTMLButtonElement, MouseEvent>
  ) => {
    e.preventDefault();
    try {
      await fetch(`${import.meta.env.VITE_API_URL}/room/${idRoom}/connect`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
      });
      window.location.href = `/room/${idRoom}`;
    } catch (error) {
      setStatusFailed(true);
    }
  };

  return (
    <Card className="w-full max-w-sm text-center">
      <CardHeader>
        <CardTitle className="text-2xl">Connect a room</CardTitle>
      </CardHeader>
      <CardContent className="grid gap-4">
        <div className="grid gap-2">
          <Label htmlFor="connectRoom">ID da sala</Label>
          <Input
            id="connectRoom"
            type="text"
            value={idRoom}
            placeholder="Digite o ID da sala"
            onChange={(e) => {
              setIdRoom(e.target.value);
            }}
            required
          />
        </div>
      </CardContent>
      {statusFailed && (
        <Alert className="mb-5 text-red-500 font-bold">
          Error ao tentar criar sala
        </Alert>
      )}
      <CardFooter>
        <Button className="w-full" onClick={(e) => handleClickCreateRoom(e)}>
          Entrar na sala
        </Button>
      </CardFooter>
    </Card>
  );
};
