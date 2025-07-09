import { Link, Outlet } from "react-router";
import { AppNavBar } from "./AppNavBar.jsx";
import { Container, Nav, Navbar } from "react-bootstrap";

export function MainLayout() {
  return (
    <div>
      <div className="mb-3">
        <AppNavBar />
      </div>
      <div>
        <Container>
          <Outlet />
        </Container>
      </div>
    </div>
  );
}
