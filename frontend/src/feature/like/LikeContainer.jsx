import { GoHeart, GoHeartFill } from "react-icons/go";
import axios from "axios";
import { useEffect, useState } from "react";

export function LikeContainer({ boardId }) {
  const [isProcessing, setIsProcessing] = useState(false);

  // const [likeCount, setLikeCount] = useState(0);

  useEffect(() => {
    if (!isProcessing) {
      axios
        .get(`/api/like/board/${boardId}`)
        .then((res) => {
          console.log(res.data);
        })
        .catch()
        .finally();
    }
  }, []);

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
        {likeInfo.liked ? <GoHeartFill /> : <GoHeart />}
      </div>
      <div>{likeInfo.count}</div>
    </div>
  );
}
