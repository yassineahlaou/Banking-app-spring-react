import React from 'react'
import './makedeposit.scss'
import CloseIcon from '@mui/icons-material/Close';
import CreateIcon from '@mui/icons-material/Create';
import { useState, useEffect } from "react";
import 'react-toastify/dist/ReactToastify.css';
import { ToastContainer, toast } from 'react-toastify';
import axios from 'axios';
import PaymentsIcon from '@mui/icons-material/Payments';
import Swal from 'sweetalert2'
import withReactContent from 'sweetalert2-react-content'
import PersonIcon from '@mui/icons-material/Person';
import CreditCardIcon from '@mui/icons-material/CreditCard';
import HandshakeIcon from '@mui/icons-material/Handshake';
import SendToMobileIcon from '@mui/icons-material/SendToMobile';
import LocalAtmIcon from '@mui/icons-material/LocalAtm';
import PlaylistAddCircleIcon from '@mui/icons-material/PlaylistAddCircle';
import StyleIcon from '@mui/icons-material/Style';

export default function MakeDeposit({value, setValue, handleClose}){
	
	const [errorDepo, setErrorDepo] = useState("");
	const [depositDone, setDepositDone] = useState(false);
	const [inputs, setInputs] = useState({});
	
	
	
	const handleChange = (e) =>{
        setInputs((prev) => {
            return { ...prev, [e.target.name]: e.target.value };
          }); 
    }
    
    const handleDeposit = async (e)=>{
        e.preventDefault();
    	setValue(value + 1) 
       	try{
	        await axios.post("http://localhost:8080/makeDeposit", {...inputs});
	       	setInputs({accountName:"" , accountNumber:"" , amount:""})
	       	setErrorDepo("")
	       	toast.success('Deposit Validated Successfully', {
					position: "top-left",
					autoClose: 1000,
					hideProgressBar: false,
					closeOnClick: true,
					pauseOnHover: true,
					draggable: true,
					progress: undefined,
					theme: "colored",
				});
				setDepositDone(true)
        }
        catch(error){
			 !error.response.data.includes("Proxy") ? setErrorDepo(error.response.data) : setErrorDepo("Check Your Connection")
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
		}//end try/catch
	}//end function
	return(
		
		<>
		 <ToastContainer className="toast" />
		 <div className={!depositDone ? 'makedeposit' : 'hide'}>
		  	<div className="wrapper">
        		<CloseIcon className='icon' onClick={handleClose} ></CloseIcon>
        		<div className="up"><PlaylistAddCircleIcon className="icone"></PlaylistAddCircleIcon> <p>Make a Deposit</p></div>
        		<span > Enter Account Informations :</span>
        		<div className="one"> <PersonIcon className="person"></PersonIcon>  <input type="text" placeholder="Account Name" name="account_name" value={inputs.accountName} onChange={handleChange}  required></input> </div>
         		<div className="one"><CreditCardIcon className="person"></CreditCardIcon> <input type="text" placeholder='Account Number' name="account_number" value={inputs.accountNumber} onChange={handleChange}  required></input> </div>
          		<span > Enter Amount:</span>
          		<div className="one"><LocalAtmIcon className="person"></LocalAtmIcon> <input type="text" placeholder='Amount' name="amount" value={inputs.amount} onChange={handleChange}  required></input> </div>
         		<button type="submit" onClick={handleDeposit}>Validate Deposit</button>
        	</div>//endwrapper
		 </div>
		 </>
		
	)
	
}