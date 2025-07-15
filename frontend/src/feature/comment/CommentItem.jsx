import axios from "axios";
import { toast } from "react-toastify";
import { Button, FormGroup, FormLabel, Modal, Spinner } from "react-bootstrap";
import { useContext, useState } from "react";
import { AuthenticationContext } from "../../common/AuthenticationContextProvider.jsx";

export function CommentItem({ comment, isProcessing, setIsProcessing }) {
  const [deleteModalShow, setDeleteModalShow] = useState(false);
  const [editModalShow, setEditModalShow] = useState(false);
  const [nextComment, setNextComment] = useState(comment.comment);

  const hasAccess = useContext(AuthenticationContext);

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
        setEditModalShow(false);
      });
  }

  function handleUpdateButtonClick() {
    setIsProcessing(true);
    axios
      .put(`/api/comment/`, {
        id: comment.id,
        comment: nextComment,
      })
      .then(() => {
        toast.success("댓글이 수정되었습니다.");
      })
      .catch(() => {
        toast().error("댓글 수정 중 문제가 발생하였습니다");
      })
      .finally(() => {
        setIsProcessing(false);
        setEditModalShow(false);
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
        <Button
          disabled={isProcessing}
          onClick={() => setDeleteModalShow(true)}
        >
          {isProcessing && <Spinner size="sm" />}
          삭제
        </Button>
        <Button>수정</Button>
      </div>

      {/*  댓글 삭제 모달*/}
      <Modal show={deleteModalShow} onHide={() => setDeleteModalShow(false)}>
        <Modal.Header closeButton>
          <Modal.Title>댓글 삭제</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <FormGroup controlId={"commentTextarea" + comment.id}>
            <FormLabel>수정 할 댓글</FormLabel>
            <FormControl aws={"textarea"} rows={5} />
          </FormGroup>
        </Modal.Body>
        ;
        <Modal.Footer>
          <Button
            variant="outline-dark"
            onClick={() => setDeleteModalShow(false)}
          >
            취소
          </Button>
          <Button
            disabled={isProcessing}
            variant="danger"
            onClick={handleDeleteButtonClick}
          >
            삭제
          </Button>
        </Modal.Footer>
      </Modal>

      {/* 댓글 수정 모달 */}
      <Modal show={editModalShow} onHide={() => setModalShow(false)}>
        <Modal.Header closeButton>
          <Modal.Title>댓글 수정</Modal.Title>
        </Modal.Header>
        <Modal.Body>{board.id}번 게시물을 수정하시겠습니까?</Modal.Body>;
        <Modal.Footer>
          <Button
            variant="outline-dark"
            onClick={() => {
              setNextcommnet(comment.comment);
              setEditModalShow(false);
            }}
          >
            취소
          </Button>
          <Button variant="danger" onClick={handleUpdateButtonClick}>
            삭제
          </Button>
        </Modal.Footer>
      </Modal>
    </div>
  );
}
