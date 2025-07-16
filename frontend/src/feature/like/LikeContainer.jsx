import { GoHeart, GoHeartFill } from "react-icons/go";
import axios from "axios";
import { useState } from "react";

export function LikeContainer({ boardId }) {
  const [isProcessing, setIsProcessing] = useState(false);

  // const [likeCount, setLikeCount] = useState(0);

  function handleHeartClick() {
    axios
      .post("/api/like", { boardId: boardId })
      .then((res) => {})
      .catch((err) => {})
      .finally(() => {
        console.log("always");
        setIsProcessing(false);
      });
  }

  return (
    <div className="d-flex gap-2 h2">
      <div onClick={handleHeartClick}>
        <GoHeart />
        <GoHeartFill />
      </div>
      <div>9</div>
    </div>
  );
}
