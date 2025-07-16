import { CommentItem } from "./CommentItem.jsx";

export function CommentList({ commentList, isProcessing, setIsProcessing }) {
  return (
    <div>
      {/* todo : commentList.map is not a function at CommentList*/}
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
