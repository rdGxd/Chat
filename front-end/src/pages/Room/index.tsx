import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

interface IMessage {
  id: string;
  text: string;
  userId: string;
  userName: string;
}

export const Chat = () => {
  const [getMessage, setGetMessage] = useState<IMessage[]>([]);
  const [text, setText] = useState<string>("");
  const { roomId } = useParams();

  useEffect(() => {
    const interval = setInterval(async () => {
      try {
        const response = await fetch(
          `${import.meta.env.VITE_API_URL}/room/${roomId}`,
          {
            method: "GET",
            headers: {
              Authorization: `Bearer ${localStorage.getItem("token")}`,
            },
          }
        );
        const data = await response.json();
        setGetMessage(data.message);
        console.log(data.message);
      } catch (error) {
        console.log(error);
      }
    }, 5000);

    return () => clearInterval(interval);
  }, [roomId]);

  const handleClickSend = async (
    e:
      | React.MouseEvent<HTMLButtonElement, MouseEvent>
      | React.KeyboardEvent<HTMLInputElement>
  ) => {
    try {
      e.preventDefault();
      await fetch(`${import.meta.env.VITE_API_URL}/message/${roomId}`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
        body: JSON.stringify({ text }),
      });
      setText("");
    } catch (error) {
      console.log(error);
    }
  };

  const handlePressEnter = (e: React.KeyboardEvent<HTMLInputElement>) => {
    if (e.key === "Enter") {
      handleClickSend(e);
    }
  };

  return (
    <>
      <h1>{roomId}</h1>
      <div className="rounded m-5 mt-5 border border-black p-3 h-full ">
        {getMessage.map((message) => (
          <p className="flex flex-wrap" key={message.id}>
            <span className="font-bold mr-2">{message.userName}:</span>
            <span className="">{message.text}</span>
          </p>
        ))}
        <div className="flex w-full max-w-sm items-center space-x-2 my-5">
          <Input
            type="text"
            placeholder="Mensagem"
            onChange={(e) => setText(e.target.value)}
            value={text}
            onKeyDown={(e) => handlePressEnter(e)}
          />
          <Button type="submit" onClick={(e) => handleClickSend(e)}>
            Enviar
          </Button>
        </div>
      </div>
    </>
  );
};
