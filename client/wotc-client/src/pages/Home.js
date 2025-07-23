import Navbar from '../components/Navbar'
import Footer from '../components/Footer'
import CardItem from '../components/CardItem'
import { useNavigate } from 'react-router-dom';
import { useEffect, useState } from 'react';
import { useAuth } from '../AuthContext';
import ViewCard from './ViewCard';

const CARD = {
    id: 1,
    imageUrl: "https://cards.scryfall.io/large/front/d/f/dfd977dc-a7c3-4d0a-aca7-b25bd154e963.jpg?1721426785",
    name: "Example Card"
}

function Home() {
    const navigate = useNavigate();
    const [cards, setCards] = useState([]);
    const url = "http://localhost:8080/api/card"

    useEffect(() => {
        const token = localStorage.getItem('token');
        const fetchCards = async () => {
            await fetch(url, {
                headers: {
                    Authorization: `Bearer ${token}`,
                    'Content-Type': 'application/json',
                },
            })
            .then(resp => {
                if (resp.status === 200) {
                    return resp.json()
                } else {
                    return Promise.reject(`Unexpected ERROR Code: ${resp.status}`)
                }
            })
            .then(data => {
                console.log("Cards fetched:", data);
                setCards(data);
            })
            .catch(console.log)
        }
        fetchCards();
    }, []);

    const handleCardView = (cardId) => {
        console.log("Card ID:", cardId);
        navigate(`/card/${cardId}`);
    }

    return (
        <>
            <Navbar />
            <div className="container">
                <div className="row">
                    <div className="col-12 col-md-10 mx-auto mt-5">
                        <form className="form-inline ">
                            <div className="form-group mx-2 d-flex">
                                <input type="text" className="form-control form-control-lg mr-sm-2" placeholder="Search for cards..." />
                                <button className="btn btn-lg bg-blue text-white my-2 my-sm-0" type="submit">Search</button>
                            </div>

                            <div className="form-group d-flex align-items-center flex-wrap mt-2">
                                <p className="mb-0 mr-4">Color: </p>

                                <div className="form-check form-check-inline ">
                                    <input className="form-check-input" type="checkbox" id="red" />
                                    <label className="form-check-label" htmlFor="red">Red</label>
                                </div>

                                <div className="form-check form-check-inline">
                                    <input className="form-check-input" type="checkbox" id="blue" />
                                    <label className="form-check-label" htmlFor="blue">Blue</label>
                                </div>

                                <div className="form-check form-check-inline">
                                    <input className="form-check-input" type="checkbox" id="white" />
                                    <label className="form-check-label" htmlFor="white">White</label>
                                </div>

                                <div className="form-check form-check-inline">
                                    <input className="form-check-input" type="checkbox" id="green" />
                                    <label className="form-check-label" htmlFor="green">Green</label>
                                </div>
                            </div>
                            <div className="form-group d-flex align-items-center flex-wrap mt-2">
                                <p className="mb-0 mr-4">Rarity: </p>

                                <div className="form-check form-check-inline ">
                                    <input className="form-check-input" type="checkbox" id="red" />
                                    <label className="form-check-label" htmlFor="red">Red</label>
                                </div>

                                <div className="form-check form-check-inline">
                                    <input className="form-check-input" type="checkbox" id="blue" />
                                    <label className="form-check-label" htmlFor="blue">Blue</label>
                                </div>

                                <div className="form-check form-check-inline">
                                    <input className="form-check-input" type="checkbox" id="white" />
                                    <label className="form-check-label" htmlFor="white">White</label>
                                </div>

                                <div className="form-check form-check-inline">
                                    <input className="form-check-input" type="checkbox" id="green" />
                                    <label className="form-check-label" htmlFor="green">Green</label>
                                </div>
                            </div>
                        </form>

                        <div className="row mt-4 ">
                            {cards.map(card => (
                                <CardItem key={card.cardId} card={card} onClick={() => handleCardView(card.cardId)} />
                            ))}
                            {/* <CardItem card={CARD} onClick={() => handleCardView(CARD.id)} /> */}
                        </div>


                    </div>
                </div>
            </div>

            <Footer />
        </>
    )
}

export default Home;