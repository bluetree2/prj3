import {
  Button,
  Col,
  FormControl,
  FormGroup,
  FormLabel,
  Modal,
  Row,
  Spinner,
} from "react-bootstrap";
import { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate, useParams, useSearchParams } from "react-router";
import { toast } from "react-toastify";

export function MemberDetail() {
  const [member, setMember] = useState(null);
  const [params] = useSearchParams();
  const [modalShow, setModalShow] = useState(false);
  // const { email } = useParams();
  const navigate = useNavigate();

  useEffect(() => {
    axios
      .get(`/api/member?email=${params.get("email")}`)
      .then((res) => {
        setMember(res.data);
      })
      .catch((err) => {
        console.log("bad");
      })
      .finally(() => {
        console.log("always");
      });
  }, []);

  if (!member) {
    return <Spinner />;
  }

  function handeDeleteButtonClick() {
    axios
      .delete(`/api/member?${params.get("email")}`)
      .then((res) => {
        console.log("잘됨");
        const message = res.data.message;

        if (message) {
          toast(message.text, { type: message.type });
        }
        navigate("/");
      })
      .catch((err) => {
        console.log("안됨");
        console.log(params.get("email"));
        toast("탈퇴에 실패하였습니다.", { type: "warning" });
      })
      .finally(() => {
        console.log("항상");
      });
  }

  return (
    <Row className="justify-content-center">
      <Col xs={12} md={8} lg={6}>
        <h2 className="mb-4">회원 정보</h2>
        <div>
          <FormGroup controlId={"email1"} className={"mb-3"}>
            <FormLabel>이메일</FormLabel>
            <FormControl readOnly value={member.nickName} />
          </FormGroup>
        </div>
        <div>
          <FormGroup controlId={"info1"} className={"mb-3"}>
            <FormLabel>자기소개</FormLabel>
            <FormControl as={"textarea"} readOnly value={member.info} />
          </FormGroup>
        </div>
        <div>
          {/* todo 3시까지 탈퇴와 수정 구현해보기*/}
          <Button
            variant={"outline-danger"}
            size={"sm"}
            className={"me-2"}
            onClick={(e) => setModalShow(true)}
          >
            회원 탈퇴
          </Button>
          <Button variant={"outline-info"} size={"sm"}>
            수정
          </Button>
        </div>
      </Col>
      <Modal show={modalShow} onHide={() => setModalShow(false)}>
        <Modal.Header closeButton>
          <Modal.Title>게시물 삭제 확인</Modal.Title>
        </Modal.Header>
        <Modal.Body>탈퇴를 진행 하시겠습니까?</Modal.Body>;
        <Modal.Footer>
          <Button variant="outline-dark" onClick={() => setModalShow(false)}>
            취소
          </Button>
          <Button variant="danger" onClick={handeDeleteButtonClick}>
            탈퇴
          </Button>
        </Modal.Footer>
      </Modal>
    </Row>
  );
}
