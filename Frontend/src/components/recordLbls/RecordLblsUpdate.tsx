import { Button, Card, CardActions, CardContent, CircularProgress, Container, IconButton, TextField } from "@mui/material";
import { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";

import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import axios from "axios";

import { BACKEND_API_URL } from "../../constants";



export const RecordLblsUpdate = () => {

	const navigate = useNavigate();
    const { recId } = useParams();

	const [loading, setLoading] = useState(true)
	const [recLbl, setRecLbl] = useState({
        nameRl:"",
        address: "",
        price: 1,
        review: "",
        nrCollaborations: 1,
    });

    useEffect(() => {
		const fetchRecLbl = async () => {
			const response = await fetch(`${BACKEND_API_URL}/recordLbls/${recId}`);
			const recLbl = await response.json();
			setRecLbl({
                nameRl:recLbl.nameRl,
                address: recLbl.address,
                price: recLbl.price,
                review: recLbl.review,
                nrCollaborations: recLbl.nrCollaborations,
		})
			setLoading(false);
            console.log(recLbl);
		};
		fetchRecLbl();
	}, [recId]);

	const updateRecordLbl = async (event: { preventDefault: () => void }) => {
		event.preventDefault();
		try {
			await axios.put(`${BACKEND_API_URL}/recordLbls/${recId}`, recLbl);
			navigate(`/recordLbls/${recId}`);
		} catch (error) {
			console.log(error);
		}
	};


	return (
		<Container>

		{loading && <CircularProgress />}

		{!loading && !recLbl && <div>Record lable not found</div>}

		{!loading && (
			<Card>
				<CardContent>
					<IconButton component={Link} sx={{ mr: 3 }} to={`/recordLbls/${recId}`}>
						<ArrowBackIcon />
					</IconButton>{" "}
					<form onSubmit={updateRecordLbl}>
						<TextField value={recLbl.nameRl}
							id="nameRl"
							label="Name"
							variant="outlined"
							fullWidth
							sx={{ mb: 2 }}
							onChange={(event) => setRecLbl({ ...recLbl, nameRl: event.target.value })}
						/>
						<TextField value={recLbl.address}
							id="address"
							label="Address"
							variant="outlined"
							fullWidth
							sx={{ mb: 2 }}
							onChange={(event) => setRecLbl({ ...recLbl, address: event.target.value })}
						/>

                        <TextField value={recLbl.price}
							id="sprice"
							label="Price"
							variant="outlined"
							fullWidth
							sx={{ mb: 2 }}
							onChange={(event) => setRecLbl({ ...recLbl, price:Number(event.target.value) })}
						/>

                        <TextField value={recLbl.review}
							id="review" 
							label="Review"
							variant="outlined"
							fullWidth
							sx={{ mb: 2 }}
							onChange={(event) => setRecLbl({ ...recLbl, review: event.target.value })}
						/>

                        <TextField value={recLbl.nrCollaborations}
							id="nrCollaborations"
							label="Nr collaborations"
							variant="outlined"
							fullWidth
							sx={{ mb: 2 }}
							onChange={(event) => setRecLbl({ ...recLbl, nrCollaborations: Number(event.target.value) })}
						/>
                    

						<Button type="submit">Update record lable</Button>
					</form>
				</CardContent>
				<CardActions></CardActions>
			</Card>
		)
}
		</Container>
	);
};