import axios from "axios";
import { toast } from "react-toastify";
import { Button, Spinner } from "react-bootstrap";

export function CommentItem({ comment, isProcessing, setIsProcessing }) {
  function handleDeleteButtonClick() {
    setIsProcessing(true);
    axios
      .delete(`/api/comment/${comment.id}`)
      .then((res) => {
        toast("댓글이 삭제 되었습니다.", { type: "success" });
        console.log(res);
      })
      .catch((err) => {
        toast("댓글 삭제 중 문제가 발생하여습니다.", { type: "error" });
      })
      .finally(() => {
        setIsProcessing(false);
      });
  }

  return (
    <div className="border-1 m-3">
      <div className="d-flex justify-content-between m-3">
        <div>{comment.authorNickName}</div>
        <div>{comment.timesAgo}</div>
      </div>
      <div>{comment.comment}</div>
      <div>
        <Button disabled={isProcessing} onClick={handleDeleteButtonClick}>
          {isProcessing && <Spinner size="sm" />}
          삭제
        </Button>
        <Button>수정</Button>
      </div>
    </div>
  );
}
