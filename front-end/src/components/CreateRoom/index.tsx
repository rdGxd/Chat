import axios from "axios";
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
import { Label } from "../ui/label";

export const CreateRoom = () => {
  const [nameRoom, setNameRoom] = useState("");
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
      await axios
        .post(
          `${import.meta.env.VITE_API_URL}/room`,
          {
            name: nameRoom,
          },
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        )
        .then((r) => {
          if (r.data) {
            window.location.href = `/room/${r.data}`;
          }
        });
    } catch (error) {
      setStatusFailed(true);
    }
  };

  return (
    <Card className="w-full max-w-sm text-center">
      <CardHeader>
        <CardTitle className="text-2xl">Create a room</CardTitle>
      </CardHeader>
      <CardContent className="grid gap-4">
        <div className="grid gap-2">
          <Label htmlFor="createRoom">Nome da sala</Label>
          <Input
            id="createRoom"
            type="text"
            value={nameRoom}
            onChange={(e) => {
              setNameRoom(e.target.value);
            }}
            placeholder="Digite o nome da sala"
            required
            minLength={3}
            maxLength={16}
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
          Criar sala
        </Button>
      </CardFooter>
    </Card>
  );
};
