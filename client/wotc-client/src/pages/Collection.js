import { useEffect, useState } from "react";
import CardItem from "../components/CardItem"
import Footer from "../components/Footer"
import Navbar from "../components/Navbar"
import { useNavigate } from "react-router-dom";


const CARD = {
    id: 1,
    imageUrl: "https://cards.scryfall.io/large/front/d/f/dfd977dc-a7c3-4d0a-aca7-b25bd154e963.jpg?1721426785",
    name: "Example Card"
}

function Collection() {
    const urlCollection = `http://localhost:8080/api/collection`
    const urlCollectionCard = `http://localhost:8080/api/collected/card`
    const urlCard = `http://localhost:8080/api/card`
    const navigate = useNavigate();
    const [collectionCards, setCollectionCards] = useState()
    const [cards, setCards] = useState([]);
    const [collectionId, setCollectionId] = useState(0)

    useEffect(() => {
        const token = localStorage.getItem('token');
        const email = localStorage.getItem('email')

        const fetchCards = () => {
            fetch(`${urlCollection}/email/${email}`, {
                headers: {
                    Authorization: `Bearer ${token}`,
                    'Content-Type': 'application/json',
                },
            })
                .then(resp => {
                    if (!resp.ok) {
                        return Promise.reject(`GET error: ${resp.status}`);
                    }
                    return resp.json();
                })
                .then(data => {
                    const collectionId = data.collectionId;
                    setCollectionId(collectionId)
                    return fetch(`${urlCollectionCard}/${collectionId}`, {
                        headers: {
                            Authorization: `Bearer ${token}`,
                            'Content-Type': 'application/json',
                        },
                    })

                })
                .then(resp => {
                    if (resp.status === 200) {
                        return resp.json()
                    } else {
                        return Promise.reject(`Unexpected ERROR Code: ${resp.status}`)
                    }
                })
                .then(data => {
                    console.log("CollectionCards fetched:", data);
                    setCollectionCards(data);
                    // Fetch each card in collectionCards by cardId
                    return Promise.all(
                        data.map(card =>
                            fetch(`${urlCard}/${card.cardId}`, {
                                headers: {
                                    Authorization: `Bearer ${token}`,
                                    'Content-Type': 'application/json',
                                },
                            }).then(resp => {
                                if (!resp.ok) throw new Error(`Card fetch failed: ${resp.status}`);
                                return resp.json();
                            })
                        )
                    );
                })
                .then(allCards => {
                    console.log("All individual cards fetched:", allCards);
                    setCards(allCards);
                })
                .catch(console.log)
        }
        fetchCards();
    }, []);

    const handleCardView = (cardId) => {
        console.log("Card ID:", cardId);
        navigate(`/collection/card/${cardId}`);
    }

    return (
        <>
            <Navbar />

            <div className="container">
                <div className="row">
                    <div className="col-12 col-md-10 mx-auto mt-2">
                        {collectionCards !== undefined && (
                            <>
                                <h3 className="mt-3 ">My Collection</h3>
                                <h4 className="my-3">Total Cards: {collectionCards.length}</h4>
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
                            </>
                        )}
                        {collectionCards === undefined && (
                            <>
                                <p>Loading</p>
                            </>
                        )}

                        {cards !== undefined && (
                            <>
                                <div className="row mt-4 ">
                                    {cards.map(card => (
                                        <CardItem key={card.cardId} card={card} onClick={() => handleCardView(card.cardId)} />
                                    ))}
                                </div>
                            </>
                        )}

                    </div>
                </div>
            </div>
            <Footer />
        </>
    )
}

export default Collection