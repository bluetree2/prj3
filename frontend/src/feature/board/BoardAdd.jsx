import { useContext, useState } from "react";
import { useNavigate } from "react-router";
import axios from "axios";
import { toast } from "react-toastify";
import {
  Button,
  Col,
  FormControl,
  FormGroup,
  FormLabel,
  ListGroup,
  Row,
  Spinner,
} from "react-bootstrap";
import { AuthenticationContext } from "../../common/AuthenticationContextProvider.jsx";

export function BoardAdd() {
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const [files, setFiles] = useState(null);
  const { user } = useContext(AuthenticationContext);
  const [isProcessing, setIsProcessing] = useState(false);

  const navigate = useNavigate();

  function handleSaveButtonClick() {
    setIsProcessing(true);
    axios
      .postForm("/api/board/add", {
        title: title,
        content: content,
        files: files,
      })
      .then((res) => {
        const message = res.data.message;
        if (message) {
          // toast 띄우기
          toast(message.text, { type: message.type });
        }
        navigate("/");
        // "/"로 이동
        console.log("잘됨");
      })
      .catch((err) => {
        console.log(err);
        console.log("잘 안됨");
        const message = err.response.data.message;
        // console.log(message);
        if (message) {
          // toast 띄우기
          toast(message.text, { type: message.type });
        }
      })
      .finally(() => {
        console.log("무조건 실행");
        setIsProcessing(false);
      });
  }

  // 작성자, 제목, 본문 썻는지
  let validata = true;
  if (title.trim() === "") {
    validata = false;
  }
  if (content.trim() === "") {
    validata = false;
  }
  if (!user) {
    return <Spinner />;
  }
  
  return (
    <Row className="justify-content-center">
      <Col xs={12} md={8} lg={6}>
        <h2 className="mb-4">글 작성</h2>
        <div>
          <FormGroup className="mb-3" controlId="title1">
            <FormLabel>제목</FormLabel>
            <FormControl
              value={title}
              onChange={(e) => setTitle(e.target.value)}
            ></FormControl>
          </FormGroup>
        </div>
        <div>
          <FormGroup className="mb-3" controlId="content1">
            <FormLabel>본문</FormLabel>
            <FormControl
              as="textarea"
              rows={6}
              value={content}
              onChange={(e) => setContent(e.target.value)}
            />
          </FormGroup>
        </div>
        <div>
          <FormGroup className="mb-3" controlId="files1">
            <FormLabel>파일 첨부</FormLabel>
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
            <FormControl
              // todo : 수정
              // value={author}
              value={user.nickName}
              disabled
            />
          </FormGroup>
        </div>
        <div className="mb-3">
          <Button
            onClick={handleSaveButtonClick}
            disabled={isProcessing || !validata}
          >
            {isProcessing && <Spinner size="sm" />}
            {isProcessing || "저장"}
          </Button>
        </div>
      </Col>
    </Row>
  );
}
