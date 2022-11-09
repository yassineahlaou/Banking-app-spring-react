import Main from "./components/Main/Main.js";
import Register from "./components/Register/Register.js";
import Login from "./components/Login/Login.js";
import Navbar from "./components/Navbar/Navbar.js";
import Dashboard from "./components/Dashboard/Dashboard.js";
import ListPayments from "./components/ListPayments/ListPayments.js";
import ListDeposits from "./components/ListDeposits/ListDeposits.js";
import ListWithdraws from "./components/ListWithdraws/ListWithdraws.js";
import ListTransferts from "./components/ListTransferts/ListTransferts.js";
import {BrowserRouter,Routes,Route,} from "react-router-dom";
import './app.scss';
function App() {
  return (
    <div className="app">
      <BrowserRouter>
    		<Routes>
    		     <Route path="/">
    		       	<Route index element={<Main></Main> } />
              		<Route path="register" element={<Register   type="register" ></Register> } />
              		<Route path="login" element={<Login type="login" ></Login> } />
    		      	<Route path="dashboard" element={<Dashboard   type="dashboard" ></Dashboard> } />
    		       	<Route path="payments" element={<ListPayments   type="payments" ></ListPayments> } />
    		        <Route path="deposits" element={<ListDeposits   type="deposits" ></ListDeposits> } />
    		        <Route path="transferts" element={<ListTransferts   type="transferts" ></ListTransferts> } />
    		        <Route path="withdraws" element={<ListWithdraws   type="withdraws" ></ListWithdraws> } /> 	
    		     </Route>
          </Routes>
     </BrowserRouter>
   </div>
  );
}

export default App;
