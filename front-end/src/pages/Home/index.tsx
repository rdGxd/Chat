import { ConnectRoom } from "@/components/ConnectRoom";
import { CreateRoom } from "@/components/CreateRoom";

export const Home = () => {
  return (
    <div className="flex justify-center flex-wrap">
      <div className="mt-10">
        <CreateRoom />
      </div>
      <div className="mt-5">
        <ConnectRoom />
      </div>
    </div>
  );
};
