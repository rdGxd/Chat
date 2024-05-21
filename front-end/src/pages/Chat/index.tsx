import { useParams } from "react-router-dom";

export const Chat = () => {
  const { roomId } = useParams();

  return (
    <>
      <h1>{roomId}</h1>
    </>
  );
};
