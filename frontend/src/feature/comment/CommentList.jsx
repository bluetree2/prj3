import { CommentItem } from "./CommentItem.jsx";

export function CommentList({ commentList, isProcessing, setIsProcessing }) {
  return (
    <div>
      {console.log("commentList : ", commentList)}
      {commentList.map((comment) => (
        <CommentItem
          setIsProcessing={setIsProcessing}
          isProcessing={isProcessing}
          comment={comment}
          key={comment.id}
        />
      ))}
    </div>
  );
}
