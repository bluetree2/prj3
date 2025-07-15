import axios from "axios";
import { toast } from "react-toastify";
import { Button, Modal, Spinner } from "react-bootstrap";
import { useState } from "react";

export function CommentItem({ comment, isProcessing, setIsProcessing }) {
  const [deleteModalShow, setDeleteModalShow] = useState(false);
  const [editModalShow, setEditModalShow] = useState(false);

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
    // todo : 댓글 삭제
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
          <Modal.Title>게시물 삭제 확인</Modal.Title>
        </Modal.Header>
        <Modal.Body>댓글 삭제하시겠습니까?</Modal.Body>;
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
          <Modal.Title>게시물 삭제 확인</Modal.Title>
        </Modal.Header>
        <Modal.Body>{board.id}번 게시물을 수정하시겠습니까?</Modal.Body>;
        <Modal.Footer>
          <Button variant="outline-dark" onClick={() => setModalShow(false)}>
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
