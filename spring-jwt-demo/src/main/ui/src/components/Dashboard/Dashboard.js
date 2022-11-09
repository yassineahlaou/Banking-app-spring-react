import React from 'react'
import './dashboard.scss'
import Navbar from "../Navbar/Navbar.js";
import { useState, useEffect } from "react";
import axios from 'axios';
import CreateAccount from "../CreateAccount/CreateAccount.js";
import MakePayment from "../MakePayment/MakePayment.js";
import MakeDeposit from "../MakeDeposit/MakeDeposit.js";
import MakeWithdraw from "../MakeWithdraw/MakeWithdraw.js";
import MakeTransfert from "../MakeTransfert/MakeTransfert.js";
import OutsideClickHandler from 'react-outside-click-handler';
import ReportProblemIcon from '@mui/icons-material/ReportProblem';

export default function Dashboard(){
	
	const [openCreateAccount, setOpenCreateAccount] = useState(false);
	const [openDeposit, setOpenDeposit] = useState(false);
	const [openTransfert, setOpenTransfert] = useState(false);
	const [openWithdraw, setOpenWithdraw] = useState(false);
	const [openPayment, setOpenPayment] = useState(false);
	const [listAccounts , setListAccounts] = useState([])
	const [loading, setLoading] = useState(true);
	const [value, setValue] = useState(1);
	const [test, setTest] = useState(1);
	const [accountId, setAccountId] = useState(null);
	const [details, setDetails] = useState({});
	const [clicked, setClicked] = useState([]);
	const [total, setTotal] = useState();
	
	//get availible accounts and total balance
	const getAllAccounts = async ()=> {
		 await axios.get("http://localhost:8080/allAccounts").then(result=>{setListAccounts(result.data); setLoading(false) })
		 await axios.get("http://localhost:8080/totalBalance").then(result=>{setTotal(result.data) })	
	}//end function
	
    useEffect (()=>{
 			getAllAccounts()
 	}, [value])

  
	//handling pop ups
	const popUpCreate = () => {
        setOpenCreateAccount(!openCreateAccount);
    }
    const popUpDeposit = () => {
      	setOpenDeposit(!openDeposit);
    }
    const popUpPayment = () => {
       setOpenPayment(!openPayment);
    }
    const popUpWithdraw = () => {
        setOpenWithdraw(!openWithdraw);
    }
    const popUpTransfert = () => {
        setOpenTransfert(!openTransfert);
    }
      
      //get id of clicked account
    const getAccountId = (e)=>{
		setTest(test + 1);
		var array = []
      		for (var i=0 ; i<listAccounts.length ; i++){
			array.push("false")
		}//end for 

        	if (array[e.currentTarget.getAttribute('keyto')] == "false"){
			setDetails({})
			array[e.currentTarget.getAttribute('keyto')] = "true"
			setAccountId(e.currentTarget.getAttribute('accountid')) 
		}
   		if (clicked[e.currentTarget.getAttribute('keyto')] == "true"){
			array[e.currentTarget.getAttribute('keyto')] = "false"
			setAccountId(e.currentTarget.getAttribute(null)) 
			setDetails({})
		}
		setClicked(array)
	}//end function
    
    const getAccountDetails = async ()=>{
		try{
			const res =  await axios.get(`http://localhost:8080/getAccount/${accountId}`)
			setDetails(res.data)
		}catch(error){
			console.log(error)
			
		}
	}//end function
	
	useEffect (()=>{
		if (accountId != null){getAccountDetails()}
 	}, [test])
 	
	//handling load more functionality
	const startingRecord = 2;
	const [next, setNext] = useState(startingRecord);
	const handleLoadMore = () =>{
		setNext(next + startingRecord);
	}//end function

    if (!loading){
	return (
		<>
		<Navbar></Navbar>
		
		{ listAccounts.length != 0 ? (
			<div className="dashboard">
				<div className="wrapper">
					<div className="head">
						<p className="total">Total of all accounts</p>
						<p className="totvalue">{total}</p>
					</div>// end head 
					<hr></hr>
					<div className="operations">
						<button className="addaccount " onClick={popUpCreate}>Add Account</button>
						<div className="dropdown">
							<button className="trans">Make A Transaction</button>
							<div className="dropdown-content">
							  <p onClick={popUpPayment}>Payment</p>
							  <p onClick={popUpTransfert}>Transfert</p>
							  <p onClick={popUpDeposit}>Deposit</p>
							  <p onClick={popUpWithdraw}>Withdraw</p>
					  
					 		</div>//end dropdown-content 
						</div>// end dropdown 
					</div>// end operations
				</div>// end wrapper
				<div className="data">
					{listAccounts.slice(0,next).map((account, key)=>
						<>
						<div className={clicked[key] == "true" ? 'cardclicked' : 'card'} keyto = {key}  accountid={account.id} onClick={(e)=>getAccountId(e)}>
							<p className="accountname">{account.accountname}</p>
							<p className={account.accounttype == "Saving" ? 'saving' : (account.accounttype == "Check" ? 'check' : 'busniss')}>{account.accounttype}</p>
						</div>// end card 
						<div className={clicked[key] == "true" ? 'container' : 'none'}>
							<div className="section">
								<p className="title">Account Name</p>
								<p className="value">{details.accountname}</p>
							</div>
							<div className="hr"></div>
							<div className="section">
								<p className="title">Account Number</p>
								<p className="value">{details.accountnumber}</p>
							</div>
							<div className="hr"></div>
							<div className="section">
								<p className="title">Account Type</p>
								<p className="value">{details.accounttype}</p>
							</div>
							<div className="hr"></div>
							<div className="section">
								<p className="title">Account Balance</p>
								<p className="value">{details.balance?.toLocaleString("en-US", {minimumFractionDigits: 2})}</p>
							</div>
							<div className="hr"></div>
							<div className="section">
								<p className="title">Created At</p>
								<p className="value">{details.created_at}</p>
							</div>
					
						</div>// end container 
						</>
					)}//end map function
				</div>// end data 
				{ next < listAccounts.length && <button onClick={handleLoadMore}>Load More</button>}
		
			</div>// end dashboard 
		) : 
		
		(  
			<div className="dashboard no">
				<div className="noaccount">
					<div className="up">
						<ReportProblemIcon className="icon"></ReportProblemIcon>
						<p>No accounts</p>
					</div>
					
					<hr></hr>
		
					<div className="text">
						<p> &nbsp;&nbsp; Click hereClick hereClick hereClick hereClick hereClick hereClick hereClick hereClick hereClick hereClick hereClick hereClick hereClick hereClick hereClick hereClick hereClick hereClick here</p>
					</div>
			
					<div className="addacount">
						<button className="add"  onClick={popUpCreate}>Create A New Account</button>
					</div>
				</div>//end no account
			
		</div>//end dashboard no 
		)
		}

		 {openCreateAccount && <OutsideClickHandler  onOutsideClick={()=>setOpenCreateAccount(false)}> <CreateAccount value={value} setValue={setValue} handleClose={popUpCreate} /> </OutsideClickHandler>}
		 {openPayment && <OutsideClickHandler  onOutsideClick={()=>setOpenPayment(false)}> <MakePayment value={value} setValue={setValue} handleClose={popUpPayment} /> </OutsideClickHandler>}
		 {openDeposit && <OutsideClickHandler  onOutsideClick={()=>setOpenDeposit(false)}> <MakeDeposit value={value} setValue={setValue} handleClose={popUpDeposit} /> </OutsideClickHandler>}
		 {openTransfert && <OutsideClickHandler  onOutsideClick={()=>setOpenTransfert(false)}> <MakeTransfert value={value} setValue={setValue} handleClose={popUpTransfert} /> </OutsideClickHandler>}
		 {openWithdraw && <OutsideClickHandler  onOutsideClick={()=>setOpenWithdraw(false)}> <MakeWithdraw value={value} setValue={setValue} handleClose={popUpWithdraw} /> </OutsideClickHandler>}
		</>
	    )
       }//end if 
}