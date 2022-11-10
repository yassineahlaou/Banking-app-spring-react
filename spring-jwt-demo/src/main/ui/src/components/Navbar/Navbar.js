import React from 'react'
import './navbar.scss'
import { Link } from "react-router-dom";
import LogoutIcon from '@mui/icons-material/Logout';

export default function Navbar(){
	return (
		<div className="navbar">
			<div className="left">
				 <Link to="/" style={{ color: 'inherit', textDecoration: 'inherit'}}>
			       	<img className='logo'  src='/assets/logo.png' alt=""></img>
			      </Link>
			</div>
			<div className="cent">
			    <Link to="/dashboard" style={{ color: 'inherit', textDecoration: 'inherit'}}>
					<button className="dashboardbut">Dashboard</button>
				</Link>
				<Link to="/payments" style={{ color: 'inherit', textDecoration: 'inherit'}}>
					<button className="payments">Payments</button>
				</Link>
				<Link to="/deposits" style={{ color: 'inherit', textDecoration: 'inherit'}}>
					<button className="deposits">Deposits</button>
				</Link>
				<Link to="/tarnsferts" style={{ color: 'inherit', textDecoration: 'inherit'}}>
					<button className="transferts">Transferts</button>
				</Link>
				<Link to="/withdraws" style={{ color: 'inherit', textDecoration: 'inherit'}}>
					<button className="withdraws">Withdraws</button>
				</Link>
			</div>
			<div className="right">
				<p className="welcome">Welcome... Yassine Ahlaou</p>
				<div className="leave"><LogoutIcon></LogoutIcon> <p>Leave </p></div>
			</div>
		</div>
	)
}