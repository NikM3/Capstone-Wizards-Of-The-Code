function CardItem({ card, onClick }) {
    return (
        <div className="card-item bg-dark col-2 col-md-6 col-lg-2 mb-2 mx-auto shadow-sm m-2 p-2 rounded-2"
            onClick={onClick} style={{ cursor: 'pointer' }}
        >
            <img src={card.imageUri} alt={card.name}  />
            <div className="card-details align-items-center text-center text-white">
                <h5 className="card-name">{card.name}</h5>
            </div>
        </div>
    );
}

export default CardItem;