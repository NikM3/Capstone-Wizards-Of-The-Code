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
    const searchUrl = "http://localhost:8080/api/search"

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

    const handleSearch = (event) => {
        event.preventDefault();

        const token = localStorage.getItem('token');

        const fuzzySearchName = async () => {
            const query = event.target.elements.searchBar.value;
            const page = 0;
            const size = 10;
            const sort = "name"
            const direction = "asc"

            const definedSearchUrl = `${searchUrl}?query=${encodeURIComponent(query)}&page=${page}&size=${size}&sort=${sort}&direction=${direction}`;

            await fetch(definedSearchUrl, {
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
            .catch((err) => {
                console.error("Fetch failed:", err);
            })
        }
        fuzzySearchName();

    }

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
                        <form className="form-inline" onSubmit={handleSearch}>
                            <div className="form-group mx-2 d-flex">
                                <input id="searchBar" type="text" className="form-control form-control-lg mr-sm-2" placeholder="Search for cards..." />
                                <button className="btn btn-lg bg-blue text-white my-2 my-sm-0" type="submit">Search</button>
                            </div>

                            <div className="form-group d-flex align-items-center flex-wrap mt-2">
                                <p className="mb-0 me-4">Color: </p>

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

                                <div className="form-check form-check-inline">
                                    <input className="form-check-input" type="checkbox" id="black" />
                                    <label className="form-check-label" htmlFor="black">Black</label>
                                </div>
                            </div>
                            <div className="form-group d-flex align-items-center flex-wrap mt-2">
                                <p className="mb-0 me-4">Rarity: </p>

                                <div className="form-check form-check-inline ">
                                    <input className="form-check-input" type="checkbox" id="common" />
                                    <label className="form-check-label" htmlFor="common">Common</label>
                                </div>

                                <div className="form-check form-check-inline">
                                    <input className="form-check-input" type="checkbox" id="uncommon" />
                                    <label className="form-check-label" htmlFor="uncommon">Uncommon</label>
                                </div>

                                <div className="form-check form-check-inline">
                                    <input className="form-check-input" type="checkbox" id="rare" />
                                    <label className="form-check-label" htmlFor="rare">Rare</label>
                                </div>

                                <div className="form-check form-check-inline">
                                    <input className="form-check-input" type="checkbox" id="mythic" />
                                    <label className="form-check-label" htmlFor="mythic">Mythic</label>
                                </div>
                            </div>
                        </form>

                        <div className="row mt-4 ">
                            {/* Add loading for Cards */}

                            { cards === undefined || cards.length === 0 && (
                                <>
                                <div className="d-flex mt-5 justify-content-center">
                                    <div className="spinner-border text-purple" role="status"  style={{ width: '4rem', height: '4rem' }}>
                                        <span className="visually-hidden">Loading...</span>
                                    </div>
                                </div>
                                </>
                            )}
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