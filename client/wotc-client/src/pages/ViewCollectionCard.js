import Footer from "../components/Footer";
import Navbar from "../components/Navbar";
import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";

function ViewCollectionCard() {
    const { id } = useParams();
    const [editCard, setEditCard] = useState(false);

    useEffect(() => {
        console.log("Fetch Card ID:", id);

    }, [id]);

    const handleSubmit = (e) => {
        e.preventDefault();
        console.log("Card Updated to collection");
    }

    return (
        <>
            <Navbar />
            <div className="container ">
                <div className="row ">
                    <div className="col-8 mt-5">
                        <h3 className="mx-4 text-purple">Cache Grab</h3>
                        <p className="justy-text-left text-black mx-4">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec a tempus dolor. Pellentesque eu ultricies dui. Aliquam lobortis aliquet venenatis. Pellentesque eleifend elit ac neque suscipit malesuada sed nec purus. Maecenas risus ligula, sollicitudin et orci nec, posuere suscipit tortor. In congue ornare eros sed facilisis. Donec faucibus rhoncus leo a rhoncus. Fusce at quam ac diam rutrum porttitor. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Duis vitae magna vitae sem dapibus sagittis. Phasellus vestibulum arcu neque, vel feugiat orci feugiat id. </p>

                        <h5 className="mx-4 mt-5 text-purple">Card Details: </h5>
                        <div className="row mx-2">
                            <div className="form-group col-4 mt-3">
                                <label for="color" className="form-label">Color: </label>
                                <input id="color" type="text" className="form-control form-control-lg" value="green" name="color" disabled />
                            </div>
                            <div className="form-group col-4 mt-3">
                                <label for="manaCost" className="form-label">CMC (Mana Cost): </label>
                                <input id="manaCost" type="text" className="form-control form-control-lg" value="200" name="manaCost" disabled />
                            </div>
                            <div className="form-group col-4 mt-3">
                                <label for="rarity" className="form-label">Rarity: </label>
                                <input id="rarity" type="text" className="form-control form-control-lg" value="Medium" name="rarity" disabled />
                            </div>
                            <div className="form-group col-4 mt-3">
                                <label for="cardType" className="form-label">Card Type: </label>
                                <input id="cardType" type="text" className="form-control form-control-lg" value="Defensive" name="cardType" disabled />
                            </div>
                            <div className="form-group col-4 mt-3">
                                <label for="set" className="form-label">Set: </label>
                                <input id="set" type="text" className="form-control form-control-lg" value="Magic" name="set" disabled />
                            </div>
                            <form onSubmit={handleSubmit} className="row">
                                <div className="form-group col-4 mt-3">
                                    <label for="quantity" className="form-label">Quantity: </label>
                                    <input id="quantity" type="number" className="form-control form-control-lg" value="1" min="1" name="quantity" disabled={!editCard} />
                                </div>
                                <div className="form-group col-4 mt-3">
                                    <label htmlFor="condition" className="form-label">Condition: </label>
                                    <select
                                        id="condition" name="condition" className="form-control form-control-lg" disabled={!editCard}
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
                                    <input type="checkbox" id="inUse" name="inUse" disabled={!editCard} className="mx-3 form-check-input" />
                                </div>
                                {editCard && (
                                    <button type="submit" className="btn btn-lg bg-light-blue text-white mt-3 col-4 mt-2 my-2">Update</button>
                                )}
                            </form>

                            <br />
                            <button className="btn btn-lg bg-blue col-4 text-white mt-3" onClick={() => setEditCard(prev => !prev)}>{editCard ? 'Cancel' : 'Edit'}</button>
                            {!editCard && (
                                <button className="btn btn-lg bg-red col-4 text-white mt-3 mx-3" data-toggle="modal" data-target="#deleteModel" onClick={() => console.log("Card removed from collection")}>Delete</button>
                            )}
                        </div>
                        {/* Modal for deleting */}
                        <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="deleteModel" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="deleteModel">Modal title</h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body">
                                        ...
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                        <button type="button" class="btn btn-primary">Save changes</button>
                                    </div>
                                </div>
                            </div>
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

export default ViewCollectionCard;