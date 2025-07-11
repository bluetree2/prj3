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

  return (
    <div>
      <h3>jwt 로그인 연습</h3>
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
