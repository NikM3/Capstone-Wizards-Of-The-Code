import Footer from "../components/Footer";
import Navbar from "../components/Navbar";
import { useNavigate, useParams } from "react-router-dom";
import { useEffect, useState } from "react";

const CARD = {
    cardId: "",
    cardRarity: "",
    cardSet: "",
    cardType: "",
    imageUri: "",
    manaCost: "",
    name: ""
}

const COLLECTED_CARD = {
    collectedCardId: "",
    cardId: "",
    collectionId: "",
    quantity: 1,
    condition: "",
    inUse: false
}

function ViewCard() {
    const { cardId } = useParams();
    const [addCard, setAddCard] = useState(false);
    const [card, setCard] = useState(CARD);
    const [collectedCard, setCollectedCard] = useState(COLLECTED_CARD);
    const url = `http://localhost:8080/api/card`;
    const [token, setToken] = useState("");
    const navigate = useNavigate();
    const [errors, setErrors] = useState([]);

    useEffect(() => {
        setToken(localStorage.getItem('token'));
        // Fetch Card by ID
        
        setCard(CARD)
    }, [cardId]);

    const handleSubmit = (e) => {
        e.preventDefault();
        const init = {
            method: 'POST',
            headers: {
                Authorization: `Bearer ${token}`,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(card)
        }
        console.log("Submitting card:", JSON.stringify(card));
        fetch(url, init)
            .then(resp => {
                if (resp.status === 201 || resp.status === 400) {
                    return resp.json()
                } else {
                    return Promise.reject(`Unexpected ERROR Code: ${resp.status}`)
                }
            })
            .then(data => {
                if (data.agentId) {
                    navigate('/home')
                } else {
                    setErrors(data)
                }
            })
            .catch(console.log)
    }

    const handleChange = (event) => {
        const newCard = { ...card };
        newCard[event.target.name] = event.target.value;
        setCard(newCard);
    }


    return (
        <>
            <Navbar />
            <div className="container ">
                <div className="row mb-2 ">
                    <div className="col-8 mt-5">
                        <h3 className="mx-4 text-purple">{cardId}</h3>
                        <p className="justy-text-left text-black mx-4">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec a tempus dolor. Pellentesque eu ultricies dui. Aliquam lobortis aliquet venenatis. Pellentesque eleifend elit ac neque suscipit malesuada sed nec purus. Maecenas risus ligula, sollicitudin et orci nec, posuere suscipit tortor. In congue ornare eros sed facilisis. Donec faucibus rhoncus leo a rhoncus. Fusce at quam ac diam rutrum porttitor. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Duis vitae magna vitae sem dapibus sagittis. Phasellus vestibulum arcu neque, vel feugiat orci feugiat id. </p>

                        <h5 className="mx-4 mt-5 text-purple">Card Details: </h5>
                        <div className="row mx-2">
                            <div className="form-group col-4 mt-3">
                                <label for="color" className="form-label">Color: </label>
                                <input id="color" type="text" className="form-control form-control-lg" name="color" value={card.cardId} disabled />
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
                                        <label for="quantity" className="form-label">Quantity: </label>
                                        <input id="quantity" type="number" className="form-control form-control-lg" value={card.c} min="1" name="quantity" onChange={handleChange}/>
                                    </div>
                                    <div className="form-group col-4 mt-3">
                                        <label htmlFor="condition" className="form-label">Condition: </label>
                                        <select
                                            id="condition" name="condition" className="form-control form-control-lg"
                                        //value={form.condition}
                                        //onChange={handleChange}
                                        >
                                            <option value="Excellent">Excellent</option>
                                            <option value="Good">Good</option>
                                            <option value="Fair">Fair</option>
                                            <option value="Poor">Poor</option>
                                        </select>
                                    </div>
                                    <div className="form-group col-4 mt-3">
                                        <label htmlFor="inUse" className="form-label">In use?: </label>
                                        <input type="checkbox" id="inUse" name="inUse" className="mx-3 form-check-input" />
                                    </div>
                                    <button type="submit" className="btn btn-lg bg-light-blue text-white mt-3 col-4 mt-2 my-2">Add Card</button>
                                </form>
                            )}

                            <button className="btn btn-lg bg-blue col-4 text-white mt-3" onClick={() => setAddCard(prev => !prev)}>{addCard ? 'Cancel' : 'Add to My Collection'}</button>

                        </div>

                    </div>
                    <div className="col-4 mt-5 align-items-center d-flex justify-content-center">
                        <img src="https://cards.scryfall.io/large/front/d/f/dfd977dc-a7c3-4d0a-aca7-b25bd154e963.jpg?1721426785" alt="magic the gathering card" className="img-fluid card-image" />
                    </div>
                </div>
            </div>
            <Footer />
        </>
    )
}

export default ViewCard;