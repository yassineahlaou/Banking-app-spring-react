import React from 'react'
import './maketransfert.scss'
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
import StyleIcon from '@mui/icons-material/Style';

export default function MakeTransfert({value, setValue, handleClose}){
	
	const [errorTrans, setErrorTrans] = useState("");
	const [transfertDone, setTransfertDone] = useState(false);
	const [inputs, setInputs] = useState({});
	
	
	
	const handleChange = (e) =>{
        setInputs((prev) => {
            return { ...prev, [e.target.name]: e.target.value };
          }); 
    }//end method
    
    const handleTransfert = async (e)=>{
		e.preventDefault();
    	setValue(value + 1) 
        try{
        	await axios.post("http://localhost:8080/makeTransfert", {...inputs});
       		setInputs({senderName:"" , senderAccount:"" , recieverName:"", recieverAccount:"" , amount:""})
         	setErrorTrans("")
            toast.success('Transfert Validated Successfully', {
				position: "top-left",
				autoClose: 1000,
				hideProgressBar: false,
				closeOnClick: true,
				pauseOnHover: true,
				draggable: true,
				progress: undefined,
				theme: "colored",
			});
			setTransfertDone(true)
        }
        catch(error){
			 !error.response.data.includes("Proxy") ? setErrorTrans(error.response.data) : setErrorTrans("Check Your Connection")
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
		 <div className={!transfertDone ? 'maketransfert' : 'hide'}>
		 	  <div className="wrapper">
        			  <CloseIcon className='icon' onClick={handleClose} ></CloseIcon>
        			  <div className="up"><SendToMobileIcon className="icone"></SendToMobileIcon> <p>Make a Transfert</p></div>
        			  <span > Enter Sender Informations :</span>
        			  <div className="one"> <PersonIcon className="person"></PersonIcon>  <input type="text" placeholder="Sender Name" name="sender_name" value={inputs.senderName} onChange={handleChange}  required></input> </div>
         			  <div className="one"><CreditCardIcon className="person"></CreditCardIcon> <input type="text" placeholder='Sender Account Number' name="sender_account" value={inputs.senderAccount} onChange={handleChange}  required></input> </div>
          			  <span > Enter Reciever Informations :</span>
          			  <div className="one"><PersonIcon className="person"></PersonIcon> <input type="text" placeholder='Reciever Name' name="reciever_name" value={inputs.recieverName} onChange={handleChange}  required></input> </div>
           			  <div className="one"><CreditCardIcon className="person"></CreditCardIcon> <input type="text" placeholder='Reciever Account Number' name="reciever_account" value={inputs.recieverAccount} onChange={handleChange}  required></input> </div>
           			  <span > Enter Amount:</span>
           			  <div className="one"><LocalAtmIcon className="person"></LocalAtmIcon> <input type="text" placeholder='Amount' name="amount" value={inputs.amount} onChange={handleChange}  required></input> </div>
         			  <button type="submit" onClick={handleTransfert}>Validate Transfert</button>
           </div>//end wrapper
		 </div>//end maketransfert
		 </>
		
	)
	
}
