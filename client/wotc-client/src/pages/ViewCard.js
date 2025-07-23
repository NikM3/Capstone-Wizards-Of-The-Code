import Footer from "../components/Footer";
import Navbar from "../components/Navbar";

function ViewCard() {
    return (
        <>
            <Navbar />
            <div className="container ">
                <div className="row ">
                    <div className="col-8 mt-5">
                        <h3 className="mx-4 text-purple">View Card</h3>
                        <p className="justy-text-left text-black mx-4">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec a tempus dolor. Pellentesque eu ultricies dui. Aliquam lobortis aliquet venenatis. Pellentesque eleifend elit ac neque suscipit malesuada sed nec purus. Maecenas risus ligula, sollicitudin et orci nec, posuere suscipit tortor. In congue ornare eros sed facilisis. Donec faucibus rhoncus leo a rhoncus. Fusce at quam ac diam rutrum porttitor. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Duis vitae magna vitae sem dapibus sagittis. Phasellus vestibulum arcu neque, vel feugiat orci feugiat id. </p>

                        <h5 className="mx-4 mt-5 text-purple">Card Details: </h5>
                        <div className="row mx-2">
                            <div className="form-group col-4 mt-3">
                                <label for="color" className="form-label">Color: </label>
                                <input id="color" type="text" className="form-control form-control-lg" />
                            </div>
                            <div className="form-group col-4 mt-3">
                                <label for="color" className="form-label">CMC (Mana Cost): </label>
                                <input id="color" type="text" className="form-control form-control-lg" />
                            </div>
                            <div className="form-group col-4 mt-3">
                                <label for="color" className="form-label">Rarity: </label>
                                <input id="color" type="text" className="form-control form-control-lg" />
                            </div>
                            <div className="form-group col-4 mt-3">
                                <label for="color" className="form-label">Card Type: </label>
                                <input id="color" type="text" className="form-control form-control-lg" />
                            </div>
                            <div className="form-group col-4 mt-3">
                                <label for="color" className="form-label">Set: </label>
                                <input id="color" type="text" className="form-control form-control-lg" />
                            </div>
                            <br />
                            <button className="btn btn-lg bg-light-blue col-4 text-white mt-3">Add to Deck</button>
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