import React from 'react'
import './main.scss';
import { Link } from "react-router-dom";
export default function Main() {
	return(
		 <div className='main'>
		 	<div className='wrapper'>
				<div className="content">
					<p className="intro"> &nbsp;&nbsp;The href attribute requires a valid value to be accessible. Provide a valid, navigable address as the href value. If you cannot provide a valid href, but still need the element to resemble a link, use a button and change it with approp </p>
		 			<div className="buttons">
		  				<Link to="register" style={{ color: 'inherit', textDecoration: 'inherit'}}>
        					  <button className="goRegister">Register</button>
         					</Link>
		 				<Link to="login" style={{ color: 'inherit', textDecoration: 'inherit'}}>
						  <button className="goLogin">Login In</button>
						 </Link>
					</div>
				</div>
		 	</div>
		</div>
	)
}