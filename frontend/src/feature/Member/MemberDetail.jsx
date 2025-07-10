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
import { useSearchParams } from "react-router";

export function MemberDetail() {
  const [member, setMember] = useState(null);
  const [params] = useSearchParams();
  const [modalShow, setModalShow] = useState(false);
  const [password, setPassword] = useState();

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
      // todo : params
      .delete(`/api/member`, {
        data: { email: member.email, password: password },
      })
      .then((res) => {
        console.log("good");
      })
      .catch((err) => {
        console.log("bad");
      })
      .finally(() => {
        console.log("always");
        setModalShow(false);
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
          <Button
            variant={"outline-danger"}
            size={"sm"}
            className={"me-2"}
            onClick={() => setModalShow(true)}
          >
            회원 탈퇴
          </Button>
          <Button variant={"outline-info"}>수정</Button>
        </div>
      </Col>
      <Modal show={modalShow} onHide={() => setModalShow(false)}>
        <Modal.Header closeButton>
          <Modal.Title>회원 탈퇴 확인</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <FormGroup controlId={"password1"} className={"mb-3"}>
            <FormLabel>암호</FormLabel>
            <FormControl
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target)}
            ></FormControl>
          </FormGroup>
        </Modal.Body>

        <Modal.Footer>
          <Button variant="outline-dark" onClick={() => setModalShow(false)}>
            취소
          </Button>
          <Button variant="danger" onClick={handeDeleteButtonClick}>
            xkfxhl
          </Button>
        </Modal.Footer>
      </Modal>
    </Row>
  );
}
