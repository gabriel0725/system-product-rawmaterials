import styled from "styled-components"
import { NavLink } from "react-router-dom"

export const Nav = styled.nav`
  background: #111827;
  padding: 16px 32px;
  display: flex;
  gap: 24px;
`

export const NavItem = styled(NavLink)`
  color: #e5e7eb;
  text-decoration: none;
  font-weight: 500;

  &.active {
    color: #3b82f6;
  }

  &:hover {
    color: #3b82f6;
  }
`
