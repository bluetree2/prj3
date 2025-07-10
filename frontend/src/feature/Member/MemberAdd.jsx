import {
  Button,
  Col,
  FormControl,
  FormGroup,
  FormLabel,
  Row,
} from "react-bootstrap";
import { useState } from "react";
import { toast } from "react-toastify";
import axios from "axios";

export function MemberAdd() {
  const [email, setEmail] = useState();
  const [password, setPassword] = useState();
  const [nickname, setNickname] = useState();
  const [info, setInfo] = useState();

  function handleSaveClick() {
    //post /api/member/add, {email,password,nickname,info}
    axios
      .post("/api/member/add", {
        email: email,
        password: password,
        nickname: nickname,
        info: info,
      })
      .then((res) => {
        console.log("good");
        const message = res.data.message;
        if (message) {
          toast(message.text, { type: message.type });
        }
      })
      .catch((err) => {
        console.log("bad");
        const message = err.response.data.message;
        if (message) {
          toast(message.text, { type: message.type });
        }
      })
      .finally(() => console.log("always"));
  }

  return (
    <Row className="justify-content-center">
      <Col xs={12} md={8} lg={6}>
        <h2 className="mb-4">회원 가입</h2>
        <div>
          <FormGroup className="mb-3" controlId="email1">
            <FormLabel>이메일</FormLabel>
            <FormControl
              type={"email"}
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            />
          </FormGroup>
        </div>
        <div>
          <FormGroup className="mb-3" controlId="password1">
            <FormLabel>암호</FormLabel>
            {/*type는 password인데 보이도록 text로 둠*/}
            <FormControl
              type={"text"}
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />
          </FormGroup>
        </div>
        <div>
          <FormGroup className="mb-3" controlId="password2">
            {/*todo 나중에 적용*/}
            <FormLabel>암호 확인</FormLabel>
            <FormControl
              type="text"

              // onChange={(e) => setPassword(e.target.value)}
            />
            <FormControl type={"password2"} />
          </FormGroup>
        </div>
        <div>
          <FormGroup>
            <FormLabel className="mb-3" controlId="nickname1">
              별명
            </FormLabel>
            <FormControl
              type={"text"}
              value={nickname}
              onChange={(e) => setNickname(e.target.value)}
            />
            <FormControl type={"nickname1"} />
          </FormGroup>
        </div>
        <div>
          <FormGroup>
            <FormLabel className="mb-3" controlId="info1">
              자기소개
            </FormLabel>
            <FormControl
              as={"textarea"}
              rows={6}
              value={info}
              onChange={(e) => setInfo(e.target.value)}
            />
            <FormControl />
          </FormGroup>
        </div>
        <div>
          <Button onClick={handleSaveClick}>가입</Button>
        </div>
      </Col>
    </Row>
  );
}
