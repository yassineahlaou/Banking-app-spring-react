import React from 'react'
import './listpayments.scss'
import Navbar from "../Navbar/Navbar.js";
import { useState, useEffect, useCallback } from "react";
import axios from 'axios';
import SearchIcon from '@mui/icons-material/Search';
import DownloadForOfflineIcon from '@mui/icons-material/DownloadForOffline';
import 'react-toastify/dist/ReactToastify.css';
import { ToastContainer, toast } from 'react-toastify';

export default function ListPayments(){
	
	const [listOfPayments, setListOfPayments] = useState([]);
	const [searchInput, setSearchInput] = useState("");
	const [startDate, setStartDate] = useState("");
	const [endDate, setEndDate] = useState("");
	const [paymentId, setPaymentId] = useState();
	const [loading, setLoading] = useState(false);
	const [loadDown, setLoadDown] = useState([]);
	const [stopLoad, setStopLoad] = useState();
	
	
	//fetch all payments
	const fetchPayments = async () =>{
		setLoading(true);
		const res = await axios.get("http://localhost:8080/allPayments")
		setLoading(false)
		setListOfPayments(res.data)
		
	} 
	
	 useEffect (()=>{
 		fetchPayments()	 

	}, [])
			  
	
	//serch by reference 
	const handleSearch = async(e)=>{
		let list = [];
		if (searchInput == ""){
			fetchPayments()		
		}
		else{
			setLoading(true)
			e.preventDefault();
			await axios.get(`http://localhost:8080/getPayment/${searchInput}`).then((result)=>{ result.data != "" && list.push(result.data) })
			setLoading(false)
			setListOfPayments(list)
		}
	}//end function
			
	const handleSearchDate = async(e)=>{
		let list = [];
		let startms = new Date(startDate).getTime();
		let endms = new Date(endDate).getTime();
		if (startDate == ""){
			toast.error('Please select a start date', {
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
		else if (endDate == ""){
			toast.error('Please select an end date', {
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
		else if (startms>endms){
			toast.error('Please select a valid dates range', {
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
		else{
			setLoading(true)
			await axios.get(`http://localhost:8080/getPayment/${startDate}/${endDate}`).then((result)=>{result.data != "" && list.push(result.data)})
			setLoading(false)	
			setListOfPayments(list)
		}
	}//end function
	
	
	//hendle download report
	const download = async ()=>{
		const response = 	await axios.get(`http://localhost:8080/getInvoice/${paymentId}`, { responseType: 'blob'}) 
		const filename =  response.headers.get('content-disposition').split('filename=')[1];
     	let blob = new Blob([response.data])
        let url = window.URL.createObjectURL(blob);
        let a = document.createElement('a');
        a.href = url;
        a.download = filename;
        a.click();
        let array = []
        for (var i=0 ; i<listOfPayments.length ; i++){
			array.push("false")
		}
		setLoadDown(array);
	}//end function
	
	const getPaymentId = (e)=>{
		setStopLoad(e.currentTarget.getAttribute('keyitem'))
		let array = []
		for (var i=0 ; i<listOfPayments.length ; i++){
			array.push("false")
		}
		if (array[e.currentTarget.getAttribute('keyitem')] == "false"){
			array[e.currentTarget.getAttribute('keyitem')] = "true"
			setPaymentId(e.currentTarget.getAttribute('itemid'))
		}
		setLoadDown(array)
	}//end function
			
			
	useEffect (()=>{
		if (paymentId != null){download()}
	}, [paymentId])
			  
			  
	//handle load more
	const startingRecord = 4;
	const [next, setNext] = useState(startingRecord);
	const handleLoadMore = () =>{
		setNext(next + startingRecord);
	}
	
	
	return(
		<>
		<Navbar></Navbar>
		<ToastContainer  className="toast"/>
		<div className='listpayments'>
			<div className="wrapper">
				<div className="head">
					<div className="searchDate">
	  					<div className="start"><label>Start date : </label> <input type="date" className="date" value={startDate} onChange={(e)=>setStartDate(e.target.value)} ></input></div>
	  					<div className="end"><label> End date : </label><input type="date"  className="date" value={endDate} onChange={(e)=>setEndDate(e.target.value)} ></input></div>
  						<button className="searchbydate " type="submit" onClick={handleSearchDate}>Search</button>
					</div>//end searchDate
					<div className="searchRef">
						<input type="text" placeholder='Search for a reference' className="ref" value={searchInput} onChange={(e)=>setSearchInput(e.target.value)}></input>
						<SearchIcon className="icon" onClick={handleSearch}></SearchIcon>
					</div>//end searchRef
				</div>//end head
				<hr></hr>
			</div>//end wrapper
			<div className="data">
				<table className="table">
				 	<tr className="tableheader">
					    <th>Reference</th>
					    <th>benefactor_name</th>
					    <th>benefactor_account	</th>
					    <th>beneficiary_name</th>
					    <th>beneficiary_account	</th>
					    <th>motif</th>
					    <th>amount	</th>
					    <th>status</th>
					    <th>created_at</th>
					    <th>Invoice</th>
					 </tr>		    
		 			{ loading ? <tr className="trlight"><td colspan="10" className="nodata"> <img src= "/assets/loaderorange.svg" className={ loading ? 'spinner' : 'hide'}></img></td></tr> :( listOfPayments.length  != 0 ? ( listOfPayments.slice(0, next).map((item, key)=>
	
				 	<tr className={key%2 == 0 ? "trlight" : "trdark" }>
					    <td className="others">{item.reference}</td>
					    <td className="others">{item.benefactor_name}</td>
					    <td className="others">{item.benefactor_account}</td>
					    <td className="others">{item.beneficiary_name}</td>
					    <td className="others">{item.beneficiary_account}</td>
					    <td className="others">{item.motif}</td>
					    <td className="others">{item.amount?.toLocaleString("en-US", {minimumFractionDigits: 2})}</td>
					    <td className="others">{item.status}</td>
					    <td className="others">{item.created_at}</td>
					    <td className="invoice" title="transaction statemnt">{loadDown[key] == "true"? <img src= "/assets/loaderorange.svg" className='spinner' ></img> : <DownloadForOfflineIcon keyitem={key} itemid = {item.id} onClick={(e)=>getPaymentId(e)}></DownloadForOfflineIcon>}</td>
					  </tr>
					
		
					)) : (<tr className="trlight"><td colspan="10" className="nodata">No Data Found!</td></tr>) )}
		
			</table>
		 </div>//end data
		
		
		{ next < listOfPayments.length && <button className="add" onClick={handleLoadMore}>Load More</button>}
		</div>//end listPayments
		</>
	)
	
}