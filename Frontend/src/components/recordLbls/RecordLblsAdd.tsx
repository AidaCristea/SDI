import { Button, Card, CardActions, CardContent, Container, IconButton, TextField } from "@mui/material";
import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";

import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import axios from "axios";

import { BACKEND_API_URL } from "../../constants";


export const RecordLblsAdd = () => {

    const navigate = useNavigate();

	const [recordLbl, setRecordLbl] = useState({
        nameRl:"",
        address: "",
        price: 1,
        review: "",
        nrCollaborations: 1,
	});

	const addRecordLbl = async (event: { preventDefault: () => void }) => {
		event.preventDefault();
		try {
			await axios.post( `${BACKEND_API_URL}/recordLbls`, recordLbl);
			navigate("/recordLbls");
		} catch (error) {
			console.log(error);
		}
	};

	return (
		<Container>
			<Card>
				<CardContent>
					<IconButton component={Link} sx={{ mr: 3 }} to={`/recordLbls`}>
						<ArrowBackIcon />
					</IconButton>{" "}
					<form onSubmit={addRecordLbl}>
						<TextField
							id="nameRl"
							label="Name"
							variant="outlined"
							fullWidth
							sx={{ mb: 2 }}
							onChange={(event) => setRecordLbl({ ...recordLbl, nameRl: event.target.value })}
						/>
						<TextField
							id="address"
							label="Address"
							variant="outlined"
							fullWidth
							sx={{ mb: 2 }}
							onChange={(event) => setRecordLbl({ ...recordLbl, address: event.target.value })}
						/>

                        <TextField
							id="price"
							label="Price"
							variant="outlined"
							fullWidth
							sx={{ mb: 2 }}
							onChange={(event) => setRecordLbl({ ...recordLbl, price:Number(event.target.value) })}
						/>

                        <TextField
							id="review"
							label="Review"
							variant="outlined"
							fullWidth
							sx={{ mb: 2 }}
							onChange={(event) => setRecordLbl({ ...recordLbl, review: event.target.value })}
						/>

                        <TextField
							id="nrCollaborations"
							label="Nr Collaborations"
							variant="outlined"
							fullWidth
							sx={{ mb: 2 }}
							onChange={(event) => setRecordLbl({ ...recordLbl, nrCollaborations: Number(event.target.value) })}
						/>

                       

						<Button type="submit">Add Record lable</Button>
					</form>
				</CardContent>
				<CardActions></CardActions>
			</Card>
		</Container>
	);
};