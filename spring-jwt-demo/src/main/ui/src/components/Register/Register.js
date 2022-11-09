import React from 'react'
import './register.scss';
import { useState, useEffect } from "react";
import axios from 'axios';
import Swal from 'sweetalert2'
import withReactContent from 'sweetalert2-react-content'
import { Link } from "react-router-dom";
import AppRegistrationIcon from '@mui/icons-material/AppRegistration';
import KeyboardBackspaceIcon from '@mui/icons-material/KeyboardBackspace';
import 'react-toastify/dist/ReactToastify.css';
import { ToastContainer, toast } from 'react-toastify';
export default function Register() {
	const [inputs, setInputs] = useState({});
	const [errorReg, setErrorReg] = useState("");
	const [loading, setLoading] = useState(false);
	const [disable, setDisable] = useState(false);
	
	
	//handling inputs values
	const handleChange = (e) =>{
		setInputs((prev) => {
		    return { ...prev, [e.target.name]: e.target.value };
		  }); 
	}
	//function end
	
	//handling registration process
	const handleRegistration = async (e) =>{ 
		setLoading(true);
		setDisable(true)
	 	e.preventDefault();
       		try{
        		await axios.post("http://localhost:8080/register", {...inputs}).then(result => {setLoading(false); setDisable(false)})
         		setInputs({firstname:"" , email:"" , lastname:"", phone:"", password:""})
         		setErrorReg("")
          		//toaster
              		toast.info('Check you Mail To complete Registration', {
					position: "top-left",
					autoClose: 5000,
					hideProgressBar: false,
					closeOnClick: true,
					pauseOnHover: true,
					draggable: true,
					progress: undefined,
					theme: "colored",
					});
			}
        	catch(error){
			setLoading(false)
	 		setDisable(false)
			!error.response.data.includes("Proxy") ? setErrorReg(error.response.data) : setErrorReg("Check Your Connection")
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
      }//function end
	
	return(
		 <div className='register'> 
		 	<ToastContainer />
			<div className='container'>
	 			<div className='form'>
		 			<div className="head">
						<p className="title" ><AppRegistrationIcon className="icon"></AppRegistrationIcon>Sign In</p>
						 <Link to="/" style={{ color: 'inherit', textDecoration: 'inherit'}}>
							<KeyboardBackspaceIcon className="back"></KeyboardBackspaceIcon>
						</Link>
					</div>// end head 
					<input type="email" placeholder='Enter Your Email' name="email" value={inputs.email} onChange={handleChange}  required></input>
					<input type="text" placeholder='Enter Your Firstname' name="firstname" value={inputs.firstname} onChange={handleChange}   required ></input>
					<input type="text" placeholder='Enter Your LastName' name="lastname"  value={inputs.lastname} onChange={handleChange}  required></input>
					<input type="text" placeholder='Enter Your Phone Number' name="phone" value={inputs.phone} onChange={handleChange}  required ></input>
					<input type="text" placeholder='Enter Password' name="password" value={inputs.password} onChange={handleChange}  required ></input>
					<input type="text" placeholder='Confirm Password' name="confirmpassword"  required ></input>
					<div className="foot">
						<button className='registerprocess'  onClick={handleRegistration} type="submit"  disabled={disable} >Register</button>
						<img src= "/assets/loader.svg" className={ loading ? 'spinner' : 'hide'}></img>
  						<div className='already'>
			 				<p>Already Have An Account ?</p>
							 <Link to="/login" style={{ color: 'inherit', textDecoration: 'inherit'}}>
							 	<p className='link'>Login</p>
							 </Link>
		 	
						</div>//end already
		 			</div>// end foot 
		 		</div>//end form
			</div>//end container 
 		</div>// end register 
		
	)//end return
}// component function