import React from 'react'
import './login.scss';
import { useState, useEffect } from "react";
import axios from 'axios';
import Swal from 'sweetalert2'
import { Link , useLocation} from "react-router-dom";
import withReactContent from 'sweetalert2-react-content'
import 'react-toastify/dist/ReactToastify.css';
import { ToastContainer, toast } from 'react-toastify';
import AppRegistrationIcon from '@mui/icons-material/AppRegistration';
import KeyboardBackspaceIcon from '@mui/icons-material/KeyboardBackspace';
export default function Login() {
	const [inputs, setInputs] = useState({});
	const [errorLog, setErrorLog] = useState("");
	
	
	//handling inputs values
	const handleChange = (e) =>{
		setInputs((prev) => {
		    return { ...prev, [e.target.name]: e.target.value };
		  }); 
	}//end function
	
	//handling login process
 	const query = useLocation().search;
	const handleLogin = async (e) =>{ 
		e.preventDefault();
		if (query == ""){
			try{
       			await axios.post("http://localhost:8080/authenticate", {...inputs},{withCredentials: true})
    			setInputs({ email:"", password:""})
				setErrorLog("")
             	toast.success('Logged In!', {
					position: "top-left",
					autoClose: 1500,
					hideProgressBar: false,
					closeOnClick: true,
					pauseOnHover: true,
					draggable: true,
					progress: undefined,
					theme: "colored",
				});
              
  			}
        	catch(error){
				!error.response.data.includes("Proxy") ? setErrorLog(error.response.data) : setErrorLog("Check Your Connection")	  
              	toast.error(error.response.data, {
					position: "top-left",
					autoClose: 1500,
					hideProgressBar: false,
					closeOnClick: true,
					pauseOnHover: true,
					draggable: true,
					progress: undefined,
					theme: "colored",
				});
			}//end try/catch
		}
		else{
			try{
				 await axios.post(`http://localhost:8080/validate${query}`, {...inputs}, {withCredentials: true})
				 setInputs({email:"" , password:""})
				 setErrorLog("")
              	 toast.success('Account validated!', {
					position: "top-left",
					autoClose: 1500,
					hideProgressBar: false,
					closeOnClick: true,
					pauseOnHover: true,
					draggable: true,
					progress: undefined,
					theme: "colored",
				});
           	}
        	catch(error){ 
			 !error.response.data.includes("Proxy") ? setErrorLog(error.response.data) : setErrorLog("Check Your Connection")	  
			  toast.error(error.response.data, {
				position: "top-left",
				autoClose: 1500,
				hideProgressBar: false,
				closeOnClick: true,
				pauseOnHover: true,
				draggable: true,
				progress: undefined,
				theme: "colored",
			       });	
			}
		}//end if statement
      }//end function
	
	return(
		 <div className='login'>
		 	<ToastContainer />
			<div className='container'>
				<div className='form'>
					<div className="head">
						<p className="title" ><AppRegistrationIcon className="icon"></AppRegistrationIcon>Login</p>
						<Link to="/" style={{ color: 'inherit', textDecoration: 'inherit'}}>
							<KeyboardBackspaceIcon className="back"></KeyboardBackspaceIcon>
						</Link>
					</div>//end head 
					<input type="email" placeholder='Enter Your Email' name="email" value={inputs.email} onChange={handleChange} required></input>
					<input type="text" placeholder='Enter Password' name="password" value={inputs.password} onChange={handleChange} required ></input>
					<div className="foot">
						<button className="loginprocess"  onClick={handleLogin} type="submit" >Login</button>

				 	</div>// end foot 
		 		</div>//end form 
		 	</div>// end container 
		 
      		</div>// end login
	)
}