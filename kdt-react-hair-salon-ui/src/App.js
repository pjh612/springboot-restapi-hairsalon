import './App.css';
import 'bootstrap/dist/css/bootstrap.css';
import React, {useEffect, useState} from 'react';

import axios from "axios";
import {DesignerList} from "./components/DesignerList";
import {MenuList} from "./components/MenuList";
import {DesignerAdd} from "./components/DesignerAdd";
import {MenuAdd} from "./components/MenuAdd";
import {AppointmentList} from "./components/AppointmentList";
import {AppointmentAdd} from "./components/AppointmentAdd";
import {CustomerList} from "./components/CustomerList";
import {CustomerAdd} from "./components/CustomerAdd";
import {CustomerDetail} from "./components/CustomerDetail";

function App() {
    const [menus, setMenus] = useState([

    ]);
    const [designers, setDesigners] = useState([
        {id: 'uuid-1', name: 'park', position: 'INTERN',specialty:'', joinedAt: ''},
    ]);

    const [appointments, setAppointments] = useState([
        {appointmentId: 'uuid-1', customerDto: '', designerDto: '', menuDto: '', status: '', appointedAt: ''},
    ]);

    const [customers, setCustomers] = useState([
        {id: 'uuid-1', name: '', email: '', gender: '', birth: '', comment: ''},
    ]);

    const [appointment, setAppointment] = useState({
        appointmentId: "", designerId: "", menuId: "", customerId: "", appointedAt: ""
    });

    const handleDesignerIdInputChanged = (e) => setAppointment({...appointment, designerId: e.target.value})
    const handleMenuIdInputChanged = (e) => setAppointment({...appointment, menuId: e.target.value})
    const handleCustomerIdInputChanged = (e) => setAppointment({...appointment, customerId: e.target.value})
    const handleAppointedAtInputChanged = (e) => setAppointment({...appointment, appointedAt: e.target.value})

    const onDesignerItemClick = (value) => {
        setAppointment({...appointment, "designerId": value});
    }
    const onMenuItemClick = (value) => {
        setAppointment({...appointment, "menuId": value});
    }
    const onCustomerItemClick = (value) => {
        setAppointment({...appointment, "customerId": value});
    }

    const onMenuAdd = (o, id) => {
        o = {...o, id: id};
        setMenus(menus.concat(o));
    };
    const onCustomerAdd = (o, id) => {
        o = {...o, id: id};
        setCustomers(customers.concat(o));
    };
    const onDesignerAdd = (o, id) => {
        console.log(id);
        o = {...o, id: id};
        setDesigners(designers.concat(o));

    };
    const onAppointmentAdd = (data) => {
        setAppointments(appointments.concat(data));
    };

    const [commentModal, setCommentModal] = useState({open: false,id:"", title: "", message: "", callback: false});



    useEffect(() => {
        axios.get('http://localhost:8080/api/v1/designers')
            .then(v => setDesigners(v.data));
        axios.get('http://localhost:8080/api/v1/menus')
            .then(
                v => {
                    setMenus(v.data);
                }
            );
        axios.get('http://localhost:8080/api/v1/appointments')
            .then(v => {
                setAppointments(v.data)
            });
        axios.get('http://localhost:8080/api/v1/customers')
            .then(v => setCustomers(v.data));
    }, []);

    return (
        <div className="container-fluid">
            <div>
                <CustomerDetail open = {commentModal.open} id={commentModal.id} customers={customers} setCustomers={setCustomers} commentModal={commentModal} setCommentModal = {setCommentModal} message = {commentModal.message} title = {commentModal.title} callback = {commentModal.callback}/>
            </div>
            <div className="row justify-content-center m-4">
                <h1 className="text-center">Hello Hair Salon</h1>
            </div>
            <div className="card">
                <div className="row">
                    <div className="col-md-8 mt-4 d-flex flex-column align-items-start p-3 pt-0">
                        <DesignerList designers={designers} setDesigners={setDesigners}
                                      onItemClick={onDesignerItemClick}/>
                    </div>
                    <div className="col-md-4 summary p-4">
                        <DesignerAdd onAdd={onDesignerAdd} appointment={appointment} setAppointment={setAppointment}/>
                    </div>
                    <div className="col-md-8 mt-4 d-flex flex-column align-items-start p-3 pt-0">
                        <MenuList menus={menus} setMenus={setMenus} onItemClick={onMenuItemClick} />
                    </div>
                    <div className="col-md-4 summary p-4">
                        <MenuAdd onAdd={onMenuAdd}/>
                    </div>
                    <div className="col-md-8 mt-4 d-flex flex-column align-items-start p-3 pt-0">
                        <AppointmentList appointments={appointments} setAppointments={setAppointments}/>
                    </div>
                    <div className="col-md-4 summary p-4">
                        <AppointmentAdd onAdd={onAppointmentAdd} appointment={appointment}
                                        setAppointment={setAppointment}
                                        handleDesignerIdInputChanged={handleDesignerIdInputChanged}
                                        handleMenuIdInputChanged={handleMenuIdInputChanged}
                                        handleCustomerIdInputChanged={handleCustomerIdInputChanged}
                                        handleAppointedAtInputChanged={handleAppointedAtInputChanged}/>
                    </div>

                    <div className="col-md-8 mt-4 d-flex flex-column align-items-start p-3 pt-0">
                        <CustomerList customers={customers} setCustomers={setCustomers}
                                      onItemClick={onCustomerItemClick} setCommentModal={setCommentModal} commentModal={commentModal}/>
                    </div>
                    <div className="col-md-4 summary p-4">
                        <CustomerAdd onAdd={onCustomerAdd}/>
                    </div>

                </div>

            </div>




        </div>
    );
}

export default App;
