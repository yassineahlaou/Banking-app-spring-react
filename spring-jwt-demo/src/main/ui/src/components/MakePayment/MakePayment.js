import React from 'react'
import './makepayment.scss'
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
import LocalAtmIcon from '@mui/icons-material/LocalAtm';
import StyleIcon from '@mui/icons-material/Style';

export default function MakePayment({value, setValue, handleClose}){
	
	const [errorPay, setErrorPay] = useState("");
	const [paymentDone, setPaymentDone] = useState(false);
	const [inputs, setInputs] = useState({});
	
	
	
	const handleChange = (e) =>{
        setInputs((prev) => {
            return { ...prev, [e.target.name]: e.target.value };
          }); 
    }//end function
    
    const handlePayment = async (e)=>{
	   e.preventDefault();
       setValue(value + 1);
       try{
	       const response =  await axios.post("http://localhost:8080/makePayment", {...inputs},  { responseType: 'blob'});
	       setInputs({benefactorName:"" , benefactorAccount:"" , beneficiaryName:"", beneficiaryAccount:"", motif:"" , amount:""})
	       setErrorPay("")
	       toast.success('Payment Validated Successfully', {
				position: "top-left",
				autoClose: 1000,
				hideProgressBar: false,
				closeOnClick: true,
				pauseOnHover: true,
				draggable: true,
				progress: undefined,
				theme: "colored",
			});
		   setPaymentDone(true)
		   
		   //auth download report after payment submit
	       const filename =  response.headers.get('content-disposition').split('filename=')[1];
	       let blob = new Blob([response.data])
	       let url = window.URL.createObjectURL(blob);
	       let a = document.createElement('a');
	       a.href = url;
	       a.download = filename;    
	       a.click();
        }
        catch(error){
			// because we set the response type to be blob , that's why we error.response.data will not console.log the error text . the response is a promise.
			let err = await error.response.data.text();
			!err.includes("Proxy") ? setErrorPay(err) : setErrorPay("Check Your Connection") 
			 toast.error(errorPay, {
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
		 <div className={!paymentDone ? 'makepayment' : 'hide'}>
		 	 <div className="wrapper">
        		<CloseIcon className='icon' onClick={handleClose} ></CloseIcon>
 				<div className="up"><PaymentsIcon className="icone"></PaymentsIcon> <p>Make a Payment</p></div>
                <span> Enter Sender Informations (Benefactor):</span>
                <div className="one"> <PersonIcon className="person"></PersonIcon>  <input type="text" placeholder="Benefactor Name" name="benefactor_name" value={inputs.benefactorName} onChange={handleChange}  required></input> </div>
         		<div className="one"><CreditCardIcon className="person"></CreditCardIcon> <input type="text" placeholder='Benefactor Account Number' name="benefactor_account" value={inputs.benefactorAccount} onChange={handleChange}  required></input> </div>
          		<span > Enter Reciever Informations (Beneficiary):</span>
          		<div className="one"><PersonIcon className="person"></PersonIcon> <input type="text" placeholder='Beneficiary Name' name="beneficiary_name" value={inputs.beneficiaryName} onChange={handleChange}  required></input> </div>
           		<div className="one"><CreditCardIcon className="person"></CreditCardIcon> <input type="text" placeholder='Beneficiary Account Number' name="beneficiary_account" value={inputs.beneficiaryAccount} onChange={handleChange}  required></input> </div>
           		<span > Enter Motif and Amount:</span>
            	<div className="one"><HandshakeIcon className="person"></HandshakeIcon> <input type="text" placeholder='Enter Motif' name="motif" value={inputs.motif} onChange={handleChange}  required></input> </div>
             	<div className="one"><LocalAtmIcon className="person"></LocalAtmIcon> <input type="text" placeholder='Amount' name="amount" value={inputs.amount} onChange={handleChange}  required></input> </div>
         		<button type="submit" onClick={handlePayment}>Validate Payment</button>
        	</div>//end wrapper
		 </div>//end makepayment
		</>
		
	)
	
}

