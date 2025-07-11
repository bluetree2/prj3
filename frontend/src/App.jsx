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
import { MemberLogin } from "./feature/Member/MemberLogin.jsx";
import { MemberLogout } from "./feature/Member/MemberLogout.jsx";
import { createContext } from "react";
import { AuthenticationContextProvider } from "./common/AuthenticationContextProvider.jsx";

// step1. create context
const AuthenticationContext = createContext(null);

// step2.

function App() {
  return (
    <AuthenticationContextProvider>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<MainLayout />}>
            <Route index element={<BoardList />} />
            <Route path="board/add" element={<BoardAdd />} />
            <Route path="board/:id" element={<BoardDetail />} />
            <Route path="board/edit" element={<BoardEdit />} />
            <Route path="/signup" element={<MemberAdd />} />
            <Route path="/login" element={<MemberLogin />} />
            <Route path="/logout" element={<MemberLogout />} />
            <Route path="member/list" element={<MemberList />} />
            <Route path="member" element={<MemberDetail />} />
            <Route path="member/edit" element={<MemberEdit />} />
          </Route>
        </Routes>
      </BrowserRouter>
    </AuthenticationContextProvider>
  );
}

export default App;
