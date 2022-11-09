import React from 'react'
import './createaccount.scss'
import CloseIcon from '@mui/icons-material/Close';
import CreateIcon from '@mui/icons-material/Create';
import { useState, useEffect } from "react";
import 'react-toastify/dist/ReactToastify.css';
import { ToastContainer, toast } from 'react-toastify';
import axios from 'axios';
import Swal from 'sweetalert2'
import withReactContent from 'sweetalert2-react-content'
import PersonIcon from '@mui/icons-material/Person';
import StyleIcon from '@mui/icons-material/Style';

const CreateAccount = props =>{
	
	const [accountname, setAccountName] = useState("");
	const [accounttype, setAccountType] = useState("--Please choose an option--");
	const [errorAdd, setErrorAdd] = useState("");
	const [created, setCreated] = useState(false);
	
	
	//handling account
	const handleAddAccount = async  (e)=>{
		e.preventDefault();
		props.setValue(props.value + 1) 
       		try{
			await axios.post("http://localhost:8080/addAccount", {accountname,accounttype});
			setAccountName("");
			setAccountType("");
			setErrorAdd("")
              		toast.success('Account Added Successfully', {
					position: "top-left",
					autoClose: 1000,
					hideProgressBar: false,
					closeOnClick: true,
					pauseOnHover: true,
					draggable: true,
					progress: undefined,
					theme: "colored",
			});		
			setCreated(true)
      		}
        	catch(error){
			 !error.response.data.includes("Proxy") ? setErrorAdd(error.response.data) : setErrorAdd("Check Your Connection")
			 toast.error(error.response.data, {
				position: "top-left",
				autoClose: 1000,
				hideProgressBar: false,
				closeOnClick: true,
				pauseOnHover: true,
				draggable: true,
				progress: undefined,
				theme: "colored",
			});
		}			
	}//end function
	
	return (
		<>
		
		 <ToastContainer className="toast" />
		 <div className={!created ? 'create' : 'hide'}>
		 	<div className="wrapper">
        		<CloseIcon className='icon' onClick={props.handleClose} ></CloseIcon>
				<div className="up">
					<CreateIcon className="icone"></CreateIcon> 
					<p>Create an Account</p>
				</div>// end up 
	       		<div className="first"> 
					<PersonIcon className="person"></PersonIcon>  
					<input type="text" placeholder='Account Name' name="accountname" value={accountname} onChange={(e)=>setAccountName(e.target.value)}  required></input> 
				</div>// end first
         		<div className="second">
					<StyleIcon className="options"></StyleIcon>
					<select  className="select" name="accounttype" value={accounttype} onChange={(e)=>setAccountType(e.target.value)}   required>
	          			<option  value="--Please choose an option--" disabled>--Please choose an option--</option>
						<option value="Saving">Saving</option>
						<option value="Check">Check</option>
						<option value="Business">Business</option>
	          		</select>
          		</div>// end second
          		<button type="submit" onClick={handleAddAccount}>Add Account</button>
        	 </div>// end wrapper
		</div>// end create 
		</>
	)
	
	
}


export default CreateAccount;