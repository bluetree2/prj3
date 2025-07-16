import {
  Button,
  Col,
  FormCheck,
  FormControl,
  FormGroup,
  FormLabel,
  Image,
  ListGroup,
  ListGroupItem,
  Modal,
  Row,
  Spinner,
  Stack,
} from "react-bootstrap";
import { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate, useSearchParams } from "react-router";
import { toast } from "react-toastify";

export function BoardEdit() {
  const [board, setBoard] = useState(null);
  const [files, setFiles] = useState([]);
  const [deleteFiles, setDeleteFiles] = useState([]);
  const [searchParams] = useSearchParams();
  const [modalShow, setModalShow] = useState(false);
  const [isProcessing, setIsProcessing] = useState(false);

  const navigate = useNavigate();

  useEffect(() => {
    axios
      .get(`/api/board/${searchParams.get("id")}`)
      .then((res) => {
        console.log("good1");
        setBoard(res.data);
      })
      .catch((err) => {
        console.log("bad1");
      })
      .finally(() => console.log("always1"));
  }, []);

  function handleSaveButtonClick() {
    setIsProcessing(true);
    axios
      .put(`/api/board/${searchParams.get("id")}`, {
        ...board,
        files: files,
        deleteFiles: deleteFiles,
      })
      .then((res) => {
        console.log("good2");
        const message = res.data.message;
        toast(message.text, { type: message.type });
        navigate(`/board/${board.id}`);
      })
      .catch((err) => {
        console.log("bad2");
        const message = err.response.data.message;
        if (message) {
          toast(message.text, { type: message.type });
        } else {
          toast("게시물 수정 중 오류가 발생하였습니다", { type: "warning" });
        }
      })
      .finally(() => {
        console.log("always2");
        setModalShow(false);
        setIsProcessing(false);
      });
  }

  if (!board) {
    return <Spinner />;
  }

  let validate = true;
  if (board.title.trim() === "") {
    validate = false;
  }
  if (board.content.trim() === "") {
    validate = false;
  }

  return (
    <Row className="justify-content-center">
      <Col xs={12} md={8} lg={6}>
        <h2 className="mb-4">{board.id}번 게시물 수정</h2>
        <div>
          <FormGroup className="mb-3" controlId="title1">
            <FormLabel>제목</FormLabel>
            <FormControl
              value={board.title}
              onChange={(e) => setBoard({ ...board, title: e.target.value })}
            />
          </FormGroup>
        </div>
        <div>
          <FormGroup className="mb-3" controlId="content1">
            <FormLabel>본문</FormLabel>
            <FormControl
              as="textarea"
              rows={6}
              value={board.content}
              onChange={(e) => setBoard({ ...board, content: e.target.value })}
            />
          </FormGroup>
        </div>
        <div>
          {/*   파일 목록 보기   */}
          <ListGroup>
            {board.files.map((file) => (
              <ListGroupItem key={file.name}>
                <Stack direction={"horizontal"} gap={3}>
                  <FormCheck
                    type="switch"
                    value={file.name}
                    onChange={(e) => {
                      if (e.target.checked) {
                        setDeleteFiles([...deleteFiles, e.target.value]);
                      } else {
                        setDeleteFiles(
                          deleteFiles.filter((item) => item !== e.target.value),
                        );
                      }
                    }}
                  />
                  <Image fluid src={file.path} />;
                </Stack>
              </ListGroupItem>
            ))}
          </ListGroup>
        </div>
        <div>
          <FormGroup className="mb-3" controlId="files1">
            <FormLabel>추가 이미지 파일</FormLabel>
            <FormControl
              type="file"
              multiple
              accept="image/*"
              onChange={(e) => setFiles(e.target.files)}
            />
          </FormGroup>
        </div>
        <div>
          <FormGroup className="mb-3" controlId="author1">
            <FormLabel>작성자</FormLabel>
            <FormControl value={board.authorNickName} disabled />
          </FormGroup>
        </div>
        <div></div>
        <div>
          <Button
            className="me-2"
            onClick={() => navigate(-1)}
            variant="outline-secondary"
          >
            취소
          </Button>
          <Button
            disabled={!validate || isProcessing}
            onClick={() => setModalShow(true)}
            variant="primary"
          >
            {isProcessing && <Spinner size="sm" />}
            {isProcessing || "저장"}
          </Button>
        </div>
      </Col>
      <Modal show={modalShow} onHide={() => setModalShow(false)}>
        <Modal.Header closeButton>
          <Modal.Title>게시물 저장 확인</Modal.Title>
        </Modal.Header>
        <Modal.Body>{board.id}번 게시물을 저장하시겠습니까?</Modal.Body>;
        <Modal.Footer>
          <Button variant="outline-dark" onClick={() => setModalShow(false)}>
            취소
          </Button>
          <Button
            disabled={isProcessing}
            variant="primary"
            onClick={handleSaveButtonClick}
          >
            {isProcessing && <Spinner size="sm" />}
            {isProcessing || "저장"}
          </Button>
        </Modal.Footer>
      </Modal>
    </Row>
  );
}
