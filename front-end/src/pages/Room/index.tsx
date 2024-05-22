import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import axios from "axios";
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
    setInterval(async () => {
      try {
        await axios
          .get(`${import.meta.env.VITE_API_URL}/room/${roomId}`, {
            headers: {
              Authorization: `Bearer ${localStorage.getItem("token")}`,
            },
          })
          .then((r) => {
            if (r.data.message) {
              setGetMessage(r.data.message);
            }
          });
      } catch (error) {
        console.log(error);
      }
    }, 5000);
  }, [roomId]);

  const handleClickSend = async (
    e:
      | React.MouseEvent<HTMLButtonElement, MouseEvent>
      | React.KeyboardEvent<HTMLInputElement>
  ) => {
    try {
      e.preventDefault();
      await axios.post(
        `${import.meta.env.VITE_API_URL}/message/${roomId}`,
        {
          text,
        },
        {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`,
          },
        }
      );
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
