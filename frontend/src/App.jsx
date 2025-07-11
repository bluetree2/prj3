import { BrowserRouter, Route, Routes } from "react-router";
import { MainLayout } from "./common/MainLayout.jsx";
import { BoardAdd } from "./feature/board/BoardAdd.jsx";
import { BoardList } from "./feature/board/BoardList.jsx";
import { BoardDetail } from "./feature/board/BoardDetail.jsx";
import { BoardEdit } from "./feature/board/BoardEdit.jsx";
import { MemberAdd } from "./feature/Member/MemberAdd.jsx";
import { MemberList } from "./feature/Member/MemberList.jsx";
import { MemberDetail } from "./feature/Member/MemberDetail.jsx";
import { MemberEdit } from "./feature/Member/MemberEdit.jsx";
import axios from "axios";

function App() {
  function handleButton1Click() {
    axios
      .post("/api/learn/jwt/sub1", {
        email: "son@son.com",
        password: "pwd",
      })
      .then((res) => {
        console.log(res.data);
        localStorage.setItem("token", res.data);
      });
  }

  function handleButton2Click() {
    localStorage.removeItem("token");
  }

  function handleButton3Click() {
    // localStorage 에서 token 얻기
    const token = localStorage.getItem("token");
    // get, post, put,delete
    if (token) {
      // 있으면 토큰 들고 요청
      // Authentication 헤더에 "Bearer"를 앞에 붙이고
      axios
        .get("/api/learn/jwt/sub2", {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        })
        .then((res) => {});
    } else {
      axios.get("/api/learn/jwt/sub2").then((res) => {});
    }
  }

  function handleButton4Click() {
    axios.get("/api/learn/jwt/sub2").then((res) => {});
  }

  function handleButton5Click() {
    const token = localStorage.getItem("token");
    // get, post, put,delete
    if (token) {
      // 있으면 토큰 들고 요청
      // Authentication 헤더에 "Bearer"를 앞에 붙이고
      axios
        .get("/api/learn/jwt/sub3", {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        })
        .then((res) => {});
    } else {
      axios.get("/api/learn/jwt/sub3").then((res) => {});
    }
  }

  return (
    <div>
      <h3>jwt 로그인 연습</h3>
      <button onClick={handleButton5Click}>
        5. isauthenticated() 설정된 request handler method에 요청
      </button>
      <button onClick={handleButton4Click}> 4. token 안들고 요청</button>
      <button onClick={handleButton3Click}> 3. token 들고 요청</button>
      <button onClick={handleButton2Click}> 2. token 지우기 (logout)</button>
      <button onClick={handleButton1Click}> 1. token 얻기 (login)</button>
    </div>
    /*<BrowserRouter>
      <Routes>
        <Route path="/" element={<MainLayout />}>
          <Route index element={<BoardList />} />
          <Route path="board/add" element={<BoardAdd />} />
          <Route path="board/:id" element={<BoardDetail />} />
          <Route path="board/edit" element={<BoardEdit />} />
          <Route path="/signup" element={<MemberAdd />} />
          <Route path="member/list" element={<MemberList />} />
          <Route path="member" element={<MemberDetail />} />
          <Route path="member/edit" element={<MemberEdit />} />
        </Route>
      </Routes>
    </BrowserRouter>*/
  );
}

export default App;
