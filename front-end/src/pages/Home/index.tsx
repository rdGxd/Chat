import { Label } from "@radix-ui/react-label";
import axios from "axios";
import { useEffect, useState } from "react";
import { Alert } from "../../components/ui/alert";
import { Button } from "../../components/ui/button";
import {
  Card,
  CardContent,
  CardFooter,
  CardHeader,
  CardTitle,
} from "../../components/ui/card";
import { Input } from "../../components/ui/input";

export const Home = () => {
  const [nameRoom, setNameRoom] = useState("");
  const [token, setToken] = useState<string | null>(null);
  const [statusSuccess, setStatusSuccess] = useState<boolean>();
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
          console.log(r.data);
          setStatusSuccess(true);
          setStatusFailed(false);
        });
    } catch (error) {
      setStatusFailed(true);
      setStatusSuccess(false);
    }
  };

  return (
    <Card className="w-full max-w-sm text-center">
      <CardHeader>
        <CardTitle className="text-2xl">Create a room</CardTitle>
      </CardHeader>
      <CardContent className="grid gap-4">
        <div className="grid gap-2">
          <Label htmlFor="nameRoom">Digite o nome da sala</Label>
          <Input
            id="nameRoom"
            type="text"
            value={nameRoom}
            onChange={(e) => {
              setNameRoom(e.target.value);
            }}
            placeholder="SALA DOS AMIGOS"
            required
            minLength={3}
            maxLength={16}
          />
        </div>
      </CardContent>
      {statusSuccess && (
        <Alert className="mb-5 text-green-500 font-bold">
          Sala criada com sucesso
        </Alert>
      )}
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
