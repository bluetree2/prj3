import { CommentAdd } from "./CommentAdd.jsx";
import { CommentList } from "./CommentList.jsx";
import { useEffect, useState } from "react";
import axios from "axios";
import { Spinner } from "react-bootstrap";
import { TfiWrite } from "react-icons/tfi";

export function CommentContainer({ boardId }) {
  const [isProcessing, setIsProcessing] = useState(false);
  const [commentList, setCommentList] = useState(null);

  useEffect(() => {
    if (!isProcessing) {
      axios
        .get(`/api/board/${boardId}`)
        .then((res) => {
          setCommentList(res.data);
          console.log("rea data : ", res.data);
        })
        .catch((err) => {})
        .finally(() => {});
    }
  }, [isProcessing]);

  if (commentList === null) {
    return <Spinner />;
  }

  return (
    <div>
      <span>
        <TfiWrite />
        <h4 className="mb-3">댓글 ({commentList.length})</h4>
      </span>
      <CommentAdd
        boardId={boardId}
        isProcessing={isProcessing}
        setIsProcessing={setIsProcessing}
      />
      <CommentList
        boardId={boardId}
        isProcessing={isProcessing}
        setIsProcessing={setIsProcessing}
        commentList={commentList}
        setCommentList={setCommentList}
      />
    </div>
  );
}
