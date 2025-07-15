import { useContext, useState } from "react";
import axios from "axios";
import { Button, FloatingLabel, FormControl, Spinner } from "react-bootstrap";
import { CommentList } from "./CommentList.jsx";
import { AuthenticationContext } from "../../common/AuthenticationContextProvider.jsx";
import { toast } from "react-toastify";

export function CommentAdd({ boardId }) {
  const [comment, setComment] = useState("");
  const [isProcessing, setIsProcessing] = useState(false);
  const { user } = useContext(AuthenticationContext);

  function handleCommentSaveClick() {
    setIsProcessing(true);
    axios
      .post("/api/comment", { boardId: boardId, comment: comment })
      .then((res) => {
        const message = res.data.message;
        if (message) {
          toast(message.text, { type: message.type });
        }
        setComment("");
      })
      .catch((err) => {
        console.log(err);
        const message = err.response.data.message;
        if (message) {
          toast(message.text, { type: message.type });
        }
      })
      .finally(() => {
        setIsProcessing(false);
      });
  }

  let saveButtonDisabled = false;
  if (comment.trim().length === 0) {
    saveButtonDisabled = true;
  }

  return (
    <div>
      <FloatingLabel
        controlId={"commentTextrarea1"}
        label={user === null ? "댓글을 작성해보세요" : "댓글을 작성해보세요"}
      >
        <FormControl
          placeholder={
            user === null ? "댓글을 작성해보세요" : "댓글을 작성해보세요"
          }
          as={"textarea"}
          style={{ height: "150px" }}
          value={comment}
          disabled={user === null || user === undefined}
          onChange={(e) => setComment(e.target.value)}
        />
      </FloatingLabel>
      <Button
        disabled={isProcessing || saveButtonDisabled}
        onClick={handleCommentSaveClick}
      >
        {isProcessing && <Spinner size="sm" />}
        댓글 저장
      </Button>
    </div>
  );
}
