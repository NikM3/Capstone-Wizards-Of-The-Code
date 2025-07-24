import Footer from "../components/Footer";
import Navbar from "../components/Navbar";
import { useNavigate, useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import { useAuth } from "../AuthContext";

const CARD = {
    cardId: "",
    cardRarity: "",
    cardSet: "",
    cardType: "",
    imageUri: "",
    manaCost: "",
    name: "",
    cardText: "",
}

const COLLECTED_CARD = {
    cardId: 0,
    collectionId: 1,
    quantity: 1,
    condition: "Excellent",
    inUse: false
}

function ViewCard() {
    const { cardId } = useParams();
    const [addCard, setAddCard] = useState(false);
    const [card, setCard] = useState(CARD);
    const [collectedCard, setCollectedCard] = useState(COLLECTED_CARD);
    const url = `http://localhost:8080/api/card`;
    const urlCollection = `http://localhost:8080/api/collection`;
    const urlCollectionCard = `http://localhost:8080/api/collected/card`
    const navigate = useNavigate();
    const [errors, setErrors] = useState([]);

    useEffect(() => {
        setCollectedCard(COLLECTED_CARD)
        const fetchCards = async () => {
            const token = localStorage.getItem('token');
            await fetch(`${url}/${cardId}`, {
                headers: {
                    Authorization: `Bearer ${token}`,
                    'Content-Type': 'application/json',
                },
            })
                .then(resp => {
                    if (resp.status === 200) {
                        return resp.json();
                    } else {
                        return Promise.reject(`Unexpected ERROR Code: ${resp.status}`)
                    }
                })
                .then(data => {
                    console.log("Card fetched:", data);
                    setCard(data);
                    setCollectedCard(data.cardId ? { ...COLLECTED_CARD, cardId: data.cardId } : COLLECTED_CARD);
                })
                .catch(console.log)
        }
        fetchCards();
    }, [cardId]);

    const handleSubmit = async (e) => {
        e.preventDefault();

    const token = localStorage.getItem('token');
    const email = localStorage.getItem('email');

    const initGet = {
        method: 'GET',
        headers: {
            Authorization: `Bearer ${token}`,
            'Content-Type': 'application/json'
        }
    };

    fetch(`${urlCollection}/email/${email}`, initGet)
        .then(resp => {
            if (!resp.ok) {
                return Promise.reject(`GET error: ${resp.status}`);
            }
            return resp.json();
        })
        .then(data => {
            const cardToSubmit = {
                ...collectedCard,
                collectionId: data.collectionId
            };

            console.log('Submitting card:', JSON.stringify(cardToSubmit));

            const initPost = {
                method: 'POST',
                headers: {
                    Authorization: `Bearer ${token}`,
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(cardToSubmit)
            };

            return fetch(urlCollectionCard, initPost);
        })
        .then(resp => {
            if (resp.status === 201 || resp.status === 400) {
                return resp.json();
            } else {
                return Promise.reject(`POST error: ${resp.status}`);
            }
        })
        .then(data => {
            if (data) {
                navigate('/home');
            } else {
                setErrors(data);
            }
        })
        .catch(err => {
            console.error('Error during submit:', err);
        });
    }

    const handleChange = (event) => {
        const newCollectedCard = { ...collectedCard };
        if (event.target.type === 'checkbox') {
            newCollectedCard[event.target.name] = event.target.checked;
        } else {
            newCollectedCard[event.target.name] = event.target.value;
        }
        setCollectedCard(newCollectedCard);
    }


    return (
        <>
            <Navbar />
            <div className="container ">
                <div className="row mb-2 ">
                    <div className="col-8 mt-5">
                        <h3 className="mx-4 text-purple">{card.name}</h3>
                        <p className="justy-text-left text-black mx-4">{card.cardText}</p>

                        <h5 className="mx-4 mt-5 text-purple">Card Details: </h5>
                        <div className="row mx-2">
                            <div className="form-group col-4 mt-3">
                                <label for="color" className="form-label">Color: </label>
                                <input id="color" type="text" className="form-control form-control-lg" name="color" value={card.cardColors?.[0]} disabled />
                            </div>
                            <div className="form-group col-4 mt-3">
                                <label for="manaCost" className="form-label">CMC (Mana Cost): </label>
                                <input id="manaCost" type="text" className="form-control form-control-lg" name="manaCost" value={card.manaCost} disabled />
                            </div>
                            <div className="form-group col-4 mt-3">
                                <label for="rarity" className="form-label">Rarity: </label>
                                <input id="rarity" type="text" className="form-control form-control-lg" value={card.cardRarity} name="rarity" disabled />
                            </div>
                            <div className="form-group col-4 mt-3">
                                <label for="cardType" className="form-label">Card Type: </label>
                                <input id="cardType" type="text" className="form-control form-control-lg" value={card.cardType} name="cardType" disabled />
                            </div>
                            <div className="form-group col-4 mt-3">
                                <label for="set" className="form-label">Set: </label>
                                <input id="set" type="text" className="form-control form-control-lg" value={card.cardSet} name="set" disabled />
                            </div>
                            <br />
                            {addCard && (
                                <form onSubmit={handleSubmit} className="row">
                                    <div className="form-group col-4 mt-3">
                                        <label htmlFor="quantity" className="form-label">Quantity: </label>
                                        <input id="quantity" type="number" className="form-control form-control-lg" value={collectedCard.quantity}
                                            name="quantity" max="1000000" min="1" onChange={handleChange} />
                                    </div>
                                    <div className="form-group col-4 mt-3">
                                        <label htmlFor="condition" className="form-label">Condition: </label>
                                        <select
                                            id="condition" name="condition" className="form-control form-control-lg"
                                            value={collectedCard.condition}
                                            onChange={handleChange}
                                        >
                                            <option value="Excellent">Excellent</option>
                                            <option value="Good">Good</option>
                                            <option value="Fair">Fair</option>
                                            <option value="Poor">Poor</option>
                                        </select>
                                    </div>
                                    <div className="form-group col-4 mt-3">
                                        <label htmlFor="inUse" className="form-label">In use?: </label>
                                        <input type="checkbox" id="inUse" name="inUse" className="mx-3 form-check-input"
                                            value={collectedCard.inUse}
                                            onChange={handleChange}
                                        />
                                    </div>
                                    <button type="submit" className="btn btn-lg bg-light-blue text-white mt-3 col-4 mt-2 my-2">Add Card</button>
                                </form>
                            )}

                            <button className="btn btn-lg bg-blue col-4 text-white mt-3" onClick={() => setAddCard(prev => !prev)}>{addCard ? 'Cancel' : 'Add to My Collection'}</button>

                        </div>

                    </div>
                    <div className="col-4 mt-5 align-items-center d-flex justify-content-center">
                        <img src={card.imageUri} alt="magic the gathering card" className="img-fluid card-image" />
                    </div>
                </div>
            </div>
            <Footer />
        </>
    )
}

export default ViewCard;